package org.apache.log4j.helpers;

import java.io.FilterWriter;
import java.io.Writer;
import org.apache.log4j.spi.ErrorHandler;

public class QuietWriter extends FilterWriter {
   protected ErrorHandler errorHandler;

   public QuietWriter(Writer writer, ErrorHandler errorHandler) {
      super(writer);
      this.setErrorHandler(errorHandler);
   }

   public void write(String string) {
      if (string != null) {
         try {
            this.out.write(string);
         } catch (Exception var3) {
            this.errorHandler.error("Failed to write [" + string + "].", var3, 1);
         }
      }

   }

   public void flush() {
      try {
         this.out.flush();
      } catch (Exception var2) {
         this.errorHandler.error("Failed to flush writer,", var2, 2);
      }

   }

   public void setErrorHandler(ErrorHandler eh) {
      if (eh == null) {
         throw new IllegalArgumentException("Attempted to set null ErrorHandler.");
      } else {
         this.errorHandler = eh;
      }
   }
}
