package jdk.nashorn.internal.codegen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.logging.DebugLogger;

public final class DumpBytecode {
   public static void dumpBytecode(ScriptEnvironment env, DebugLogger logger, byte[] bytecode, String className) {
      File dir = null;

      try {
         if (env._print_code) {
            StringBuilder sb = new StringBuilder();
            sb.append("class: " + className).append('\n').append(ClassEmitter.disassemble(bytecode)).append("=====");
            if (env._print_code_dir != null) {
               String name = className;
               int dollar = className.lastIndexOf(36);
               if (dollar != -1) {
                  name = className.substring(dollar + 1);
               }

               dir = new File(env._print_code_dir);
               if (!dir.exists() && !dir.mkdirs()) {
                  throw new IOException(dir.toString());
               }

               int uniqueId = 0;

               while(true) {
                  String fileName = name + (uniqueId == 0 ? "" : "_" + uniqueId) + ".bytecode";
                  File file = new File(env._print_code_dir, fileName);
                  ++uniqueId;
                  if (!file.exists()) {
                     PrintWriter pw = new PrintWriter(new FileOutputStream(file));
                     Throwable var12 = null;

                     try {
                        pw.print(sb.toString());
                        pw.flush();
                        break;
                     } catch (Throwable var38) {
                        var12 = var38;
                        throw var38;
                     } finally {
                        if (pw != null) {
                           if (var12 != null) {
                              try {
                                 pw.close();
                              } catch (Throwable var37) {
                                 var12.addSuppressed(var37);
                              }
                           } else {
                              pw.close();
                           }
                        }

                     }
                  }
               }
            } else {
               env.getErr().println(sb);
            }
         }

         if (env._dest_dir != null) {
            String fileName = className.replace('.', File.separatorChar) + ".class";
            int index = fileName.lastIndexOf(File.separatorChar);
            if (index != -1) {
               dir = new File(env._dest_dir, fileName.substring(0, index));
            } else {
               dir = new File(env._dest_dir);
            }

            if (!dir.exists() && !dir.mkdirs()) {
               throw new IOException(dir.toString());
            }

            File file = new File(env._dest_dir, fileName);
            FileOutputStream fos = new FileOutputStream(file);
            Throwable var46 = null;

            try {
               fos.write(bytecode);
            } catch (Throwable var36) {
               var46 = var36;
               throw var36;
            } finally {
               if (fos != null) {
                  if (var46 != null) {
                     try {
                        fos.close();
                     } catch (Throwable var35) {
                        var46.addSuppressed(var35);
                     }
                  } else {
                     fos.close();
                  }
               }

            }

            logger.info("Wrote class to '" + file.getAbsolutePath() + '\'');
         }
      } catch (IOException var41) {
         logger.warning("Skipping class dump for ", className, ": ", ECMAErrors.getMessage("io.error.cant.write", dir.toString()));
      }

   }
}
