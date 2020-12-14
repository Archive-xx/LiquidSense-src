package org.apache.log4j.net;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

public class TelnetAppender extends AppenderSkeleton {
   private TelnetAppender.SocketHandler sh;
   private int port = 23;

   public boolean requiresLayout() {
      return true;
   }

   public void activateOptions() {
      try {
         this.sh = new TelnetAppender.SocketHandler(this.port);
         this.sh.start();
      } catch (InterruptedIOException var2) {
         Thread.currentThread().interrupt();
         var2.printStackTrace();
      } catch (IOException var3) {
         var3.printStackTrace();
      } catch (RuntimeException var4) {
         var4.printStackTrace();
      }

      super.activateOptions();
   }

   public int getPort() {
      return this.port;
   }

   public void setPort(int port) {
      this.port = port;
   }

   public void close() {
      if (this.sh != null) {
         this.sh.close();

         try {
            this.sh.join();
         } catch (InterruptedException var2) {
            Thread.currentThread().interrupt();
         }
      }

   }

   protected void append(LoggingEvent event) {
      if (this.sh != null) {
         this.sh.send(this.layout.format(event));
         if (this.layout.ignoresThrowable()) {
            String[] s = event.getThrowableStrRep();
            if (s != null) {
               StringBuffer buf = new StringBuffer();

               for(int i = 0; i < s.length; ++i) {
                  buf.append(s[i]);
                  buf.append("\r\n");
               }

               this.sh.send(buf.toString());
            }
         }
      }

   }

   protected class SocketHandler extends Thread {
      private Vector writers = new Vector();
      private Vector connections = new Vector();
      private ServerSocket serverSocket;
      private int MAX_CONNECTIONS = 20;

      public void finalize() {
         this.close();
      }

      public void close() {
         synchronized(this) {
            Enumeration e = this.connections.elements();

            while(true) {
               if (!e.hasMoreElements()) {
                  break;
               }

               try {
                  ((Socket)e.nextElement()).close();
               } catch (InterruptedIOException var8) {
                  Thread.currentThread().interrupt();
               } catch (IOException var9) {
               } catch (RuntimeException var10) {
               }
            }
         }

         try {
            this.serverSocket.close();
         } catch (InterruptedIOException var5) {
            Thread.currentThread().interrupt();
         } catch (IOException var6) {
         } catch (RuntimeException var7) {
         }

      }

      public synchronized void send(String message) {
         Iterator ce = this.connections.iterator();
         Iterator e = this.writers.iterator();

         while(e.hasNext()) {
            ce.next();
            PrintWriter writer = (PrintWriter)e.next();
            writer.print(message);
            if (writer.checkError()) {
               ce.remove();
               e.remove();
            }
         }

      }

      public void run() {
         while(true) {
            if (!this.serverSocket.isClosed()) {
               try {
                  Socket newClient = this.serverSocket.accept();
                  PrintWriter pw = new PrintWriter(newClient.getOutputStream());
                  if (this.connections.size() < this.MAX_CONNECTIONS) {
                     synchronized(this) {
                        this.connections.addElement(newClient);
                        this.writers.addElement(pw);
                        pw.print("TelnetAppender v1.0 (" + this.connections.size() + " active connections)\r\n\r\n");
                        pw.flush();
                        continue;
                     }
                  }

                  pw.print("Too many connections.\r\n");
                  pw.flush();
                  newClient.close();
                  continue;
               } catch (Exception var8) {
                  if (var8 instanceof InterruptedIOException || var8 instanceof InterruptedException) {
                     Thread.currentThread().interrupt();
                  }

                  if (!this.serverSocket.isClosed()) {
                     LogLog.error("Encountered error while in SocketHandler loop.", var8);
                  }
               }
            }

            try {
               this.serverSocket.close();
            } catch (InterruptedIOException var5) {
               Thread.currentThread().interrupt();
            } catch (IOException var6) {
            }

            return;
         }
      }

      public SocketHandler(int port) throws IOException {
         this.serverSocket = new ServerSocket(port);
         this.setName("TelnetAppender-" + this.getName() + "-" + port);
      }
   }
}
