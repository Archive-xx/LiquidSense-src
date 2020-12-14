package org.apache.log4j.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.xml.DOMConfigurator;

public class JMSSink implements MessageListener {
   static Logger logger;

   public static void main(String[] args) throws Exception {
      if (args.length != 5) {
         usage("Wrong number of arguments.");
      }

      String tcfBindingName = args[0];
      String topicBindingName = args[1];
      String username = args[2];
      String password = args[3];
      String configFile = args[4];
      if (configFile.endsWith(".xml")) {
         DOMConfigurator.configure(configFile);
      } else {
         PropertyConfigurator.configure(configFile);
      }

      new JMSSink(tcfBindingName, topicBindingName, username, password);
      BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
      System.out.println("Type \"exit\" to quit JMSSink.");

      String s;
      do {
         s = stdin.readLine();
      } while(!s.equalsIgnoreCase("exit"));

      System.out.println("Exiting. Kill the application if it does not exit due to daemon threads.");
   }

   public JMSSink(String tcfBindingName, String topicBindingName, String username, String password) {
      try {
         Context ctx = new InitialContext();
         TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory)lookup(ctx, tcfBindingName);
         TopicConnection topicConnection = topicConnectionFactory.createTopicConnection(username, password);
         topicConnection.start();
         TopicSession topicSession = topicConnection.createTopicSession(false, 1);
         Topic topic = (Topic)ctx.lookup(topicBindingName);
         TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);
         topicSubscriber.setMessageListener(this);
      } catch (JMSException var11) {
         logger.error("Could not read JMS message.", var11);
      } catch (NamingException var12) {
         logger.error("Could not read JMS message.", var12);
      } catch (RuntimeException var13) {
         logger.error("Could not read JMS message.", var13);
      }

   }

   public void onMessage(Message message) {
      try {
         if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage)message;
            LoggingEvent event = (LoggingEvent)objectMessage.getObject();
            Logger remoteLogger = Logger.getLogger(event.getLoggerName());
            remoteLogger.callAppenders(event);
         } else {
            logger.warn("Received message is of type " + message.getJMSType() + ", was expecting ObjectMessage.");
         }
      } catch (JMSException var5) {
         logger.error("Exception thrown while processing incoming message.", var5);
      }

   }

   protected static Object lookup(Context ctx, String name) throws NamingException {
      try {
         return ctx.lookup(name);
      } catch (NameNotFoundException var3) {
         logger.error("Could not find name [" + name + "].");
         throw var3;
      }
   }

   static void usage(String msg) {
      System.err.println(msg);
      System.err.println("Usage: java " + JMSSink.class.getName() + " TopicConnectionFactoryBindingName TopicBindingName username password configFile");
      System.exit(1);
   }

   static {
      logger = Logger.getLogger(JMSSink.class);
   }
}
