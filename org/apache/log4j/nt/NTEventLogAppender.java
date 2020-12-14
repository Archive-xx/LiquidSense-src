package org.apache.log4j.nt;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.TTCCLayout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

public class NTEventLogAppender extends AppenderSkeleton {
   private int _handle;
   private String source;
   private String server;

   public NTEventLogAppender() {
      this((String)null, (String)null, (Layout)null);
   }

   public NTEventLogAppender(String source) {
      this((String)null, source, (Layout)null);
   }

   public NTEventLogAppender(String server, String source) {
      this(server, source, (Layout)null);
   }

   public NTEventLogAppender(Layout layout) {
      this((String)null, (String)null, layout);
   }

   public NTEventLogAppender(String source, Layout layout) {
      this((String)null, source, layout);
   }

   public NTEventLogAppender(String server, String source, Layout layout) {
      this._handle = 0;
      this.source = null;
      this.server = null;
      if (source == null) {
         source = "Log4j";
      }

      if (layout == null) {
         this.layout = new TTCCLayout();
      } else {
         this.layout = layout;
      }

      try {
         this._handle = this.registerEventSource(server, source);
      } catch (Exception var5) {
         var5.printStackTrace();
         this._handle = 0;
      }

   }

   public void close() {
   }

   public void activateOptions() {
      if (this.source != null) {
         try {
            this._handle = this.registerEventSource(this.server, this.source);
         } catch (Exception var2) {
            LogLog.error("Could not register event source.", var2);
            this._handle = 0;
         }
      }

   }

   public void append(LoggingEvent event) {
      StringBuffer sbuf = new StringBuffer();
      sbuf.append(this.layout.format(event));
      if (this.layout.ignoresThrowable()) {
         String[] s = event.getThrowableStrRep();
         if (s != null) {
            int len = s.length;

            for(int i = 0; i < len; ++i) {
               sbuf.append(s[i]);
            }
         }
      }

      int nt_category = event.getLevel().toInt();
      this.reportEvent(this._handle, sbuf.toString(), nt_category);
   }

   public void finalize() {
      this.deregisterEventSource(this._handle);
      this._handle = 0;
   }

   public void setSource(String source) {
      this.source = source.trim();
   }

   public String getSource() {
      return this.source;
   }

   public boolean requiresLayout() {
      return true;
   }

   private native int registerEventSource(String var1, String var2);

   private native void reportEvent(int var1, String var2, int var3);

   private native void deregisterEventSource(int var1);

   static {
      String[] archs;
      try {
         archs = new String[]{System.getProperty("os.arch")};
      } catch (SecurityException var4) {
         archs = new String[]{"amd64", "ia64", "x86"};
      }

      boolean loaded = false;
      int i = 0;

      while(i < archs.length) {
         try {
            System.loadLibrary("NTEventLogAppender." + archs[i]);
            loaded = true;
            break;
         } catch (UnsatisfiedLinkError var5) {
            loaded = false;
            ++i;
         }
      }

      if (!loaded) {
         System.loadLibrary("NTEventLogAppender");
      }

   }
}
