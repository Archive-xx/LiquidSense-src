package org.apache.log4j.net;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

public class SocketAppender extends AppenderSkeleton {
   public static final int DEFAULT_PORT = 4560;
   static final int DEFAULT_RECONNECTION_DELAY = 30000;
   String remoteHost;
   public static final String ZONE = "_log4j_obj_tcpconnect_appender.local.";
   InetAddress address;
   int port = 4560;
   ObjectOutputStream oos;
   int reconnectionDelay = 30000;
   boolean locationInfo = false;
   private String application;
   private SocketAppender.Connector connector;
   int counter = 0;
   private static final int RESET_FREQUENCY = 1;
   private boolean advertiseViaMulticastDNS;
   private ZeroConfSupport zeroConf;

   public SocketAppender() {
   }

   public SocketAppender(InetAddress address, int port) {
      this.address = address;
      this.remoteHost = address.getHostName();
      this.port = port;
      this.connect(address, port);
   }

   public SocketAppender(String host, int port) {
      this.port = port;
      this.address = getAddressByName(host);
      this.remoteHost = host;
      this.connect(this.address, port);
   }

   public void activateOptions() {
      if (this.advertiseViaMulticastDNS) {
         this.zeroConf = new ZeroConfSupport("_log4j_obj_tcpconnect_appender.local.", this.port, this.getName());
         this.zeroConf.advertise();
      }

      this.connect(this.address, this.port);
   }

   public synchronized void close() {
      if (!this.closed) {
         this.closed = true;
         if (this.advertiseViaMulticastDNS) {
            this.zeroConf.unadvertise();
         }

         this.cleanUp();
      }
   }

   public void cleanUp() {
      if (this.oos != null) {
         try {
            this.oos.close();
         } catch (IOException var2) {
            if (var2 instanceof InterruptedIOException) {
               Thread.currentThread().interrupt();
            }

            LogLog.error("Could not close oos.", var2);
         }

         this.oos = null;
      }

      if (this.connector != null) {
         this.connector.interrupted = true;
         this.connector = null;
      }

   }

   void connect(InetAddress address, int port) {
      if (this.address != null) {
         try {
            this.cleanUp();
            this.oos = new ObjectOutputStream((new Socket(address, port)).getOutputStream());
         } catch (IOException var5) {
            if (var5 instanceof InterruptedIOException) {
               Thread.currentThread().interrupt();
            }

            String msg = "Could not connect to remote log4j server at [" + address.getHostName() + "].";
            if (this.reconnectionDelay > 0) {
               msg = msg + " We will try again later.";
               this.fireConnector();
            } else {
               msg = msg + " We are not retrying.";
               this.errorHandler.error(msg, var5, 0);
            }

            LogLog.error(msg);
         }

      }
   }

   public void append(LoggingEvent event) {
      if (event != null) {
         if (this.address == null) {
            this.errorHandler.error("No remote host is set for SocketAppender named \"" + this.name + "\".");
         } else {
            if (this.oos != null) {
               try {
                  if (this.locationInfo) {
                     event.getLocationInformation();
                  }

                  if (this.application != null) {
                     event.setProperty("application", this.application);
                  }

                  event.getNDC();
                  event.getThreadName();
                  event.getMDCCopy();
                  event.getRenderedMessage();
                  event.getThrowableStrRep();
                  this.oos.writeObject(event);
                  this.oos.flush();
                  if (++this.counter >= 1) {
                     this.counter = 0;
                     this.oos.reset();
                  }
               } catch (IOException var3) {
                  if (var3 instanceof InterruptedIOException) {
                     Thread.currentThread().interrupt();
                  }

                  this.oos = null;
                  LogLog.warn("Detected problem with connection: " + var3);
                  if (this.reconnectionDelay > 0) {
                     this.fireConnector();
                  } else {
                     this.errorHandler.error("Detected problem with connection, not reconnecting.", var3, 0);
                  }
               }
            }

         }
      }
   }

   public void setAdvertiseViaMulticastDNS(boolean advertiseViaMulticastDNS) {
      this.advertiseViaMulticastDNS = advertiseViaMulticastDNS;
   }

   public boolean isAdvertiseViaMulticastDNS() {
      return this.advertiseViaMulticastDNS;
   }

   void fireConnector() {
      if (this.connector == null) {
         LogLog.debug("Starting a new connector thread.");
         this.connector = new SocketAppender.Connector();
         this.connector.setDaemon(true);
         this.connector.setPriority(1);
         this.connector.start();
      }

   }

   static InetAddress getAddressByName(String host) {
      try {
         return InetAddress.getByName(host);
      } catch (Exception var2) {
         if (var2 instanceof InterruptedIOException || var2 instanceof InterruptedException) {
            Thread.currentThread().interrupt();
         }

         LogLog.error("Could not find address of [" + host + "].", var2);
         return null;
      }
   }

   public boolean requiresLayout() {
      return false;
   }

   public void setRemoteHost(String host) {
      this.address = getAddressByName(host);
      this.remoteHost = host;
   }

   public String getRemoteHost() {
      return this.remoteHost;
   }

   public void setPort(int port) {
      this.port = port;
   }

   public int getPort() {
      return this.port;
   }

   public void setLocationInfo(boolean locationInfo) {
      this.locationInfo = locationInfo;
   }

   public boolean getLocationInfo() {
      return this.locationInfo;
   }

   public void setApplication(String lapp) {
      this.application = lapp;
   }

   public String getApplication() {
      return this.application;
   }

   public void setReconnectionDelay(int delay) {
      this.reconnectionDelay = delay;
   }

   public int getReconnectionDelay() {
      return this.reconnectionDelay;
   }

   class Connector extends Thread {
      boolean interrupted = false;

      public void run() {
         while(true) {
            if (!this.interrupted) {
               try {
                  sleep((long)SocketAppender.this.reconnectionDelay);
                  LogLog.debug("Attempting connection to " + SocketAppender.this.address.getHostName());
                  Socket socket = new Socket(SocketAppender.this.address, SocketAppender.this.port);
                  synchronized(this) {
                     SocketAppender.this.oos = new ObjectOutputStream(socket.getOutputStream());
                     SocketAppender.this.connector = null;
                     LogLog.debug("Connection established. Exiting connector thread.");
                  }
               } catch (InterruptedException var5) {
                  LogLog.debug("Connector interrupted. Leaving loop.");
                  return;
               } catch (ConnectException var6) {
                  LogLog.debug("Remote host " + SocketAppender.this.address.getHostName() + " refused connection.");
                  continue;
               } catch (IOException var7) {
                  if (var7 instanceof InterruptedIOException) {
                     Thread.currentThread().interrupt();
                  }

                  LogLog.debug("Could not connect to " + SocketAppender.this.address.getHostName() + ". Exception is " + var7);
                  continue;
               }
            }

            return;
         }
      }
   }
}
