package jdk.nashorn.internal.runtime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.runtime.options.Options;

final class AstSerializer {
   private static final int COMPRESSION_LEVEL = Options.getIntProperty("nashorn.serialize.compression", 4);

   static byte[] serialize(FunctionNode fn) {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      Deflater deflater = new Deflater(COMPRESSION_LEVEL);

      try {
         ObjectOutputStream oout = new ObjectOutputStream(new DeflaterOutputStream(out, deflater));
         Throwable var4 = null;

         try {
            oout.writeObject(fn);
         } catch (Throwable var22) {
            var4 = var22;
            throw var22;
         } finally {
            if (oout != null) {
               if (var4 != null) {
                  try {
                     oout.close();
                  } catch (Throwable var21) {
                     var4.addSuppressed(var21);
                  }
               } else {
                  oout.close();
               }
            }

         }
      } catch (IOException var24) {
         throw new AssertionError("Unexpected exception serializing function", var24);
      } finally {
         deflater.end();
      }

      return out.toByteArray();
   }
}
