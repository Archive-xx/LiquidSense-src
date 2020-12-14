package org.apache.log4j.varia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import org.apache.log4j.helpers.LogLog;

class HUPNode implements Runnable {
   Socket socket;
   DataInputStream dis;
   DataOutputStream dos;
   ExternallyRolledFileAppender er;

   public HUPNode(Socket socket, ExternallyRolledFileAppender er) {
      this.socket = socket;
      this.er = er;

      try {
         this.dis = new DataInputStream(socket.getInputStream());
         this.dos = new DataOutputStream(socket.getOutputStream());
      } catch (InterruptedIOException var4) {
         Thread.currentThread().interrupt();
         var4.printStackTrace();
      } catch (IOException var5) {
         var5.printStackTrace();
      } catch (RuntimeException var6) {
         var6.printStackTrace();
      }

   }

   public void run() {
      try {
         String line = this.dis.readUTF();
         LogLog.debug("Got external roll over signal.");
         if ("RollOver".equals(line)) {
            synchronized(this.er) {
               this.er.rollOver();
            }

            this.dos.writeUTF("OK");
         } else {
            this.dos.writeUTF("Expecting [RollOver] string.");
         }

         this.dos.close();
      } catch (InterruptedIOException var5) {
         Thread.currentThread().interrupt();
         LogLog.error("Unexpected exception. Exiting HUPNode.", var5);
      } catch (IOException var6) {
         LogLog.error("Unexpected exception. Exiting HUPNode.", var6);
      } catch (RuntimeException var7) {
         LogLog.error("Unexpected exception. Exiting HUPNode.", var7);
      }

   }
}
