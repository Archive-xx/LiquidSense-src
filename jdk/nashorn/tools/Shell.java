package jdk.nashorn.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.debug.ASTWriter;
import jdk.nashorn.internal.ir.debug.PrintVisitor;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.ScriptingFunctions;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.options.Options;

public class Shell {
   private static final String MESSAGE_RESOURCE = "jdk.nashorn.tools.resources.Shell";
   private static final ResourceBundle bundle = ResourceBundle.getBundle("jdk.nashorn.tools.resources.Shell", Locale.getDefault());
   public static final int SUCCESS = 0;
   public static final int COMMANDLINE_ERROR = 100;
   public static final int COMPILATION_ERROR = 101;
   public static final int RUNTIME_ERROR = 102;
   public static final int IO_ERROR = 103;
   public static final int INTERNAL_ERROR = 104;

   protected Shell() {
   }

   public static void main(String[] args) {
      try {
         int exitCode = main(System.in, System.out, System.err, args);
         if (exitCode != 0) {
            System.exit(exitCode);
         }
      } catch (IOException var2) {
         System.err.println(var2);
         System.exit(103);
      }

   }

   public static int main(InputStream in, OutputStream out, OutputStream err, String[] args) throws IOException {
      return (new Shell()).run(in, out, err, args);
   }

   protected final int run(InputStream in, OutputStream out, OutputStream err, String[] args) throws IOException {
      Context context = makeContext(in, out, err, args);
      if (context == null) {
         return 100;
      } else {
         Global global = context.createGlobal();
         ScriptEnvironment env = context.getEnv();
         List<String> files = env.getFiles();
         if (files.isEmpty()) {
            return readEvalPrint(context, global);
         } else if (env._compile_only) {
            return compileScripts(context, global, files);
         } else {
            return env._fx ? runFXScripts(context, global, files) : this.runScripts(context, global, files);
         }
      }
   }

   private static Context makeContext(InputStream in, OutputStream out, OutputStream err, String[] args) {
      PrintStream pout = out instanceof PrintStream ? (PrintStream)out : new PrintStream(out);
      PrintStream perr = err instanceof PrintStream ? (PrintStream)err : new PrintStream(err);
      PrintWriter wout = new PrintWriter(pout, true);
      PrintWriter werr = new PrintWriter(perr, true);
      ErrorManager errors = new ErrorManager(werr);
      Options options = new Options("nashorn", werr);
      if (args != null) {
         try {
            String[] prepArgs = preprocessArgs(args);
            options.process(prepArgs);
         } catch (IllegalArgumentException var27) {
            werr.println(bundle.getString("shell.usage"));
            options.displayHelp(var27);
            return null;
         }
      }

      if (!options.getBoolean("scripting")) {
         Iterator var31 = options.getFiles().iterator();

         while(var31.hasNext()) {
            String fileName = (String)var31.next();
            File firstFile = new File(fileName);
            if (firstFile.isFile()) {
               try {
                  FileReader fr = new FileReader(firstFile);
                  Throwable var14 = null;

                  try {
                     int firstChar = fr.read();
                     if (firstChar == 35) {
                        options.set("scripting", true);
                        break;
                     }
                  } catch (Throwable var28) {
                     var14 = var28;
                     throw var28;
                  } finally {
                     if (fr != null) {
                        if (var14 != null) {
                           try {
                              fr.close();
                           } catch (Throwable var26) {
                              var14.addSuppressed(var26);
                           }
                        } else {
                           fr.close();
                        }
                     }

                  }
               } catch (IOException var30) {
               }
            }
         }
      }

      return new Context(options, errors, wout, werr, Thread.currentThread().getContextClassLoader());
   }

   private static String[] preprocessArgs(String[] args) {
      if (args.length == 0) {
         return args;
      } else {
         List<String> processedArgs = new ArrayList();
         processedArgs.addAll(Arrays.asList(args));
         if (args[0].startsWith("-") && !System.getProperty("os.name", "generic").startsWith("Mac OS X")) {
            processedArgs.addAll(0, ScriptingFunctions.tokenizeString((String)processedArgs.remove(0)));
         }

         int shebangFilePos = -1;

         for(int i = 0; i < processedArgs.size(); ++i) {
            String a = (String)processedArgs.get(i);
            if (!a.startsWith("-")) {
               Path p = Paths.get(a);
               String l = "";

               try {
                  BufferedReader r = Files.newBufferedReader(p);
                  Throwable var8 = null;

                  try {
                     l = r.readLine();
                  } catch (Throwable var18) {
                     var8 = var18;
                     throw var18;
                  } finally {
                     if (r != null) {
                        if (var8 != null) {
                           try {
                              r.close();
                           } catch (Throwable var17) {
                              var8.addSuppressed(var17);
                           }
                        } else {
                           r.close();
                        }
                     }

                  }
               } catch (IOException var20) {
               }

               if (l.startsWith("#!")) {
                  shebangFilePos = i;
               }
               break;
            }
         }

         if (shebangFilePos != -1) {
            processedArgs.add(shebangFilePos + 1, "--");
         }

         return (String[])processedArgs.toArray(new String[0]);
      }
   }

   private static int compileScripts(Context context, Global global, List<String> files) throws IOException {
      Global oldGlobal = Context.getGlobal();
      boolean globalChanged = oldGlobal != global;
      ScriptEnvironment env = context.getEnv();

      try {
         if (globalChanged) {
            Context.setGlobal(global);
         }

         ErrorManager errors = context.getErrorManager();
         Iterator var7 = files.iterator();

         while(var7.hasNext()) {
            String fileName = (String)var7.next();
            FunctionNode functionNode = (new Parser(env, Source.sourceFor(fileName, new File(fileName)), errors, env._strict, 0, context.getLogger(Parser.class))).parse();
            byte var10;
            if (errors.getNumberOfErrors() != 0) {
               var10 = 101;
               return var10;
            }

            Compiler.forNoInstallerCompilation(context, functionNode.getSource(), env._strict | functionNode.isStrict()).compile(functionNode, Compiler.CompilationPhases.COMPILE_ALL_NO_INSTALL);
            if (env._print_ast) {
               context.getErr().println(new ASTWriter(functionNode));
            }

            if (env._print_parse) {
               context.getErr().println(new PrintVisitor(functionNode));
            }

            if (errors.getNumberOfErrors() != 0) {
               var10 = 101;
               return var10;
            }
         }
      } finally {
         env.getOut().flush();
         env.getErr().flush();
         if (globalChanged) {
            Context.setGlobal(oldGlobal);
         }

      }

      return 0;
   }

   private int runScripts(Context context, Global global, List<String> files) throws IOException {
      Global oldGlobal = Context.getGlobal();
      boolean globalChanged = oldGlobal != global;

      try {
         if (globalChanged) {
            Context.setGlobal(global);
         }

         ErrorManager errors = context.getErrorManager();
         Iterator var7 = files.iterator();

         while(var7.hasNext()) {
            String fileName = (String)var7.next();
            if ("-".equals(fileName)) {
               int res = readEvalPrint(context, global);
               if (res != 0) {
                  int var10 = res;
                  return var10;
               }
            } else {
               File file = new File(fileName);
               ScriptFunction script = context.compileScript(Source.sourceFor(fileName, file), global);
               if (script == null || errors.getNumberOfErrors() != 0) {
                  byte var11 = 101;
                  return var11;
               }

               try {
                  this.apply(script, global);
               } catch (NashornException var16) {
                  errors.error(var16.toString());
                  if (context.getEnv()._dump_on_error) {
                     var16.printStackTrace(context.getErr());
                  }

                  byte var12 = 102;
                  return var12;
               }
            }
         }

         return 0;
      } finally {
         context.getOut().flush();
         context.getErr().flush();
         if (globalChanged) {
            Context.setGlobal(oldGlobal);
         }

      }
   }

   private static int runFXScripts(Context context, Global global, List<String> files) throws IOException {
      Global oldGlobal = Context.getGlobal();
      boolean globalChanged = oldGlobal != global;

      byte var6;
      try {
         if (globalChanged) {
            Context.setGlobal(global);
         }

         global.addOwnProperty("$GLOBAL", 2, global);
         global.addOwnProperty("$SCRIPTS", 2, files);
         context.load(global, "fx:bootstrap.js");
         return 0;
      } catch (NashornException var10) {
         context.getErrorManager().error(var10.toString());
         if (context.getEnv()._dump_on_error) {
            var10.printStackTrace(context.getErr());
         }

         var6 = 102;
      } finally {
         context.getOut().flush();
         context.getErr().flush();
         if (globalChanged) {
            Context.setGlobal(oldGlobal);
         }

      }

      return var6;
   }

   protected Object apply(ScriptFunction target, Object self) {
      return ScriptRuntime.apply(target, self);
   }

   private static int readEvalPrint(Context context, Global global) {
      String prompt = bundle.getString("shell.prompt");
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      PrintWriter err = context.getErr();
      Global oldGlobal = Context.getGlobal();
      boolean globalChanged = oldGlobal != global;
      ScriptEnvironment env = context.getEnv();

      try {
         if (globalChanged) {
            Context.setGlobal(global);
         }

         global.addShellBuiltins();

         while(true) {
            String source;
            do {
               err.print(prompt);
               err.flush();
               source = "";

               try {
                  source = in.readLine();
               } catch (IOException var14) {
                  err.println(var14.toString());
               }

               if (source == null) {
                  return 0;
               }
            } while(source.isEmpty());

            try {
               Object res = context.eval(global, source, global, "<shell>");
               if (res != ScriptRuntime.UNDEFINED) {
                  err.println(JSType.toString(res));
               }
            } catch (Exception var15) {
               err.println(var15);
               if (env._dump_on_error) {
                  var15.printStackTrace(err);
               }
            }
         }
      } finally {
         if (globalChanged) {
            Context.setGlobal(oldGlobal);
         }

      }
   }
}
