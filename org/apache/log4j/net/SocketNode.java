package org.apache.log4j.net;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.LoggingEvent;

public class SocketNode implements Runnable {
   Socket socket;
   LoggerRepository hierarchy;
   ObjectInputStream ois;
   static Logger logger;

   public SocketNode(Socket socket, LoggerRepository hierarchy) {
      this.socket = socket;
      this.hierarchy = hierarchy;

      try {
         this.ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
      } catch (InterruptedIOException var4) {
         Thread.currentThread().interrupt();
         logger.error("Could not open ObjectInputStream to " + socket, var4);
      } catch (IOException var5) {
         logger.error("Could not open ObjectInputStream to " + socket, var5);
      } catch (RuntimeException var6) {
         logger.error("Could not open ObjectInputStream to " + socket, var6);
      }

   }

   public void run() {
      try {
         if (this.ois != null) {
            while(true) {
               LoggingEvent event;
               Logger remoteLogger;
               do {
                  event = (LoggingEvent)this.ois.readObject();
                  remoteLogger = this.hierarchy.getLogger(event.getLoggerName());
               } while(!event.getLevel().isGreaterOrEqual(remoteLogger.getEffectiveLevel()));

               remoteLogger.callAppenders(event);
            }
         }
      } catch (EOFException var36) {
         logger.info("Caught java.io.EOFException closing conneciton.");
      } catch (SocketException var37) {
         logger.info("Caught java.net.SocketException closing conneciton.");
      } catch (InterruptedIOException var38) {
         Thread.currentThread().interrupt();
         logger.info("Caught java.io.InterruptedIOException: " + var38);
         logger.info("Closing connection.");
      } catch (IOException var39) {
         logger.info("Caught java.io.IOException: " + var39);
         logger.info("Closing connection.");
      } catch (Exception var40) {
         logger.error("Unexpected exception. Closing conneciton.", var40);
      } finally {
         if (this.ois != null) {
            try {
               this.ois.close();
            } catch (Exception var35) {
               logger.info("Could not close connection.", var35);
            }
         }

         if (this.socket != null) {
            try {
               this.socket.close();
            } catch (InterruptedIOException var33) {
               Thread.currentThread().interrupt();
            } catch (IOException var34) {
            }
         }

      }

   }

   static {
      logger = Logger.getLogger(SocketNode.class);
   }
}
