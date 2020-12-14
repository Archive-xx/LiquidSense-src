package jdk.nashorn.internal.runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.NativeArray;

public final class ScriptingFunctions {
   public static final MethodHandle READLINE = findOwnMH("readLine", Object.class, Object.class, Object.class);
   public static final MethodHandle READFULLY = findOwnMH("readFully", Object.class, Object.class, Object.class);
   public static final MethodHandle EXEC = findOwnMH("exec", Object.class, Object.class, Object[].class);
   public static final String EXEC_NAME = "$EXEC";
   public static final String OUT_NAME = "$OUT";
   public static final String ERR_NAME = "$ERR";
   public static final String EXIT_NAME = "$EXIT";
   public static final String THROW_ON_ERROR_NAME = "throwOnError";
   public static final String ENV_NAME = "$ENV";
   public static final String PWD_NAME = "PWD";

   private ScriptingFunctions() {
   }

   public static Object readLine(Object self, Object prompt) throws IOException {
      if (prompt != ScriptRuntime.UNDEFINED) {
         System.out.print(JSType.toString(prompt));
      }

      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      return reader.readLine();
   }

   public static Object readFully(Object self, Object file) throws IOException {
      File f = null;
      if (file instanceof File) {
         f = (File)file;
      } else if (JSType.isString(file)) {
         f = new File(((CharSequence)file).toString());
      }

      if (f != null && f.isFile()) {
         return new String(Source.readFully(f));
      } else {
         throw ECMAErrors.typeError("not.a.file", ScriptRuntime.safeToString(file));
      }
   }

   public static Object exec(Object self, Object... args) throws IOException, InterruptedException {
      ScriptObject global = Context.getGlobal();
      Object string = args.length > 0 ? args[0] : ScriptRuntime.UNDEFINED;
      Object input = args.length > 1 ? args[1] : ScriptRuntime.UNDEFINED;
      Object[] argv = args.length > 2 ? Arrays.copyOfRange(args, 2, args.length) : ScriptRuntime.EMPTY_ARRAY;
      List<String> cmdLine = tokenizeString(JSType.toString(string));
      Object[] additionalArgs = argv.length == 1 && argv[0] instanceof NativeArray ? ((NativeArray)argv[0]).asObjectArray() : argv;
      Object[] var8 = additionalArgs;
      int var9 = additionalArgs.length;

      Object pwd;
      for(int var10 = 0; var10 < var9; ++var10) {
         pwd = var8[var10];
         cmdLine.add(JSType.toString(pwd));
      }

      ProcessBuilder processBuilder = new ProcessBuilder(cmdLine);
      Object env = global.get("$ENV");
      if (env instanceof ScriptObject) {
         ScriptObject envProperties = (ScriptObject)env;
         pwd = envProperties.get("PWD");
         if (pwd != ScriptRuntime.UNDEFINED) {
            File pwdFile = new File(JSType.toString(pwd));
            if (pwdFile.exists()) {
               processBuilder.directory(pwdFile);
            }
         }

         Map<String, String> environment = processBuilder.environment();
         environment.clear();
         Iterator var13 = envProperties.entrySet().iterator();

         while(var13.hasNext()) {
            Entry<Object, Object> entry = (Entry)var13.next();
            environment.put(JSType.toString(entry.getKey()), JSType.toString(entry.getValue()));
         }
      }

      final Process process = processBuilder.start();
      final IOException[] exception = new IOException[2];
      final StringBuilder outBuffer = new StringBuilder();
      Thread outThread = new Thread(new Runnable() {
         public void run() {
            char[] buffer = new char[1024];

            try {
               InputStreamReader inputStream = new InputStreamReader(process.getInputStream());
               Throwable var3 = null;

               try {
                  int length;
                  try {
                     while((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                        outBuffer.append(buffer, 0, length);
                     }
                  } catch (Throwable var13) {
                     var3 = var13;
                     throw var13;
                  }
               } finally {
                  if (inputStream != null) {
                     if (var3 != null) {
                        try {
                           inputStream.close();
                        } catch (Throwable var12) {
                           var3.addSuppressed(var12);
                        }
                     } else {
                        inputStream.close();
                     }
                  }

               }
            } catch (IOException var15) {
               exception[0] = var15;
            }

         }
      }, "$EXEC output");
      final StringBuilder errBuffer = new StringBuilder();
      Thread errThread = new Thread(new Runnable() {
         public void run() {
            char[] buffer = new char[1024];

            try {
               InputStreamReader inputStream = new InputStreamReader(process.getErrorStream());
               Throwable var3 = null;

               try {
                  int length;
                  try {
                     while((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                        errBuffer.append(buffer, 0, length);
                     }
                  } catch (Throwable var13) {
                     var3 = var13;
                     throw var13;
                  }
               } finally {
                  if (inputStream != null) {
                     if (var3 != null) {
                        try {
                           inputStream.close();
                        } catch (Throwable var12) {
                           var3.addSuppressed(var12);
                        }
                     } else {
                        inputStream.close();
                     }
                  }

               }
            } catch (IOException var15) {
               exception[1] = var15;
            }

         }
      }, "$EXEC error");
      outThread.start();
      errThread.start();
      String in;
      if (!JSType.nullOrUndefined(input)) {
         try {
            OutputStreamWriter outputStream = new OutputStreamWriter(process.getOutputStream());
            Throwable var17 = null;

            try {
               in = JSType.toString(input);
               outputStream.write(in, 0, in.length());
            } catch (Throwable var29) {
               var17 = var29;
               throw var29;
            } finally {
               if (outputStream != null) {
                  if (var17 != null) {
                     try {
                        outputStream.close();
                     } catch (Throwable var28) {
                        var17.addSuppressed(var28);
                     }
                  } else {
                     outputStream.close();
                  }
               }

            }
         } catch (IOException var31) {
         }
      }

      int exit = process.waitFor();
      outThread.join();
      errThread.join();
      String out = outBuffer.toString();
      in = errBuffer.toString();
      global.set("$OUT", out, 0);
      global.set("$ERR", in, 0);
      global.set("$EXIT", exit, 0);
      IOException[] var19 = exception;
      int var20 = exception.length;

      for(int var21 = 0; var21 < var20; ++var21) {
         IOException element = var19[var21];
         if (element != null) {
            throw element;
         }
      }

      if (exit != 0) {
         Object exec = global.get("$EXEC");

         assert exec instanceof ScriptObject : "$EXEC is not a script object!";

         if (JSType.toBoolean(((ScriptObject)exec).get("throwOnError"))) {
            throw ECMAErrors.rangeError("exec.returned.non.zero", ScriptRuntime.safeToString(exit));
         }
      }

      return out;
   }

   private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), ScriptingFunctions.class, name, Lookup.MH.type(rtype, types));
   }

   public static List<String> tokenizeString(String str) {
      StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(str));
      tokenizer.resetSyntax();
      tokenizer.wordChars(0, 255);
      tokenizer.whitespaceChars(0, 32);
      tokenizer.commentChar(35);
      tokenizer.quoteChar(34);
      tokenizer.quoteChar(39);
      List<String> tokenList = new ArrayList();
      StringBuilder toAppend = new StringBuilder();

      while(nextToken(tokenizer) != -1) {
         String s = tokenizer.sval;
         if (s.endsWith("\\")) {
            toAppend.append(s.substring(0, s.length() - 1)).append(' ');
         } else {
            tokenList.add(toAppend.append(s).toString());
            toAppend.setLength(0);
         }
      }

      if (toAppend.length() != 0) {
         tokenList.add(toAppend.toString());
      }

      return tokenList;
   }

   private static int nextToken(StreamTokenizer tokenizer) {
      try {
         return tokenizer.nextToken();
      } catch (IOException var2) {
         return -1;
      }
   }
}
