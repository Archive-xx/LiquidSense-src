package jdk.nashorn.api.scripting;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import jdk.Exported;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Version;

@Exported
public final class NashornScriptEngineFactory implements ScriptEngineFactory {
   private static final String[] DEFAULT_OPTIONS = new String[]{"-doe"};
   private static final List<String> names = immutableList("nashorn", "Nashorn", "js", "JS", "JavaScript", "javascript", "ECMAScript", "ecmascript");
   private static final List<String> mimeTypes = immutableList("application/javascript", "application/ecmascript", "text/javascript", "text/ecmascript");
   private static final List<String> extensions = immutableList("js");

   public String getEngineName() {
      return (String)this.getParameter("javax.script.engine");
   }

   public String getEngineVersion() {
      return (String)this.getParameter("javax.script.engine_version");
   }

   public List<String> getExtensions() {
      return Collections.unmodifiableList(extensions);
   }

   public String getLanguageName() {
      return (String)this.getParameter("javax.script.language");
   }

   public String getLanguageVersion() {
      return (String)this.getParameter("javax.script.language_version");
   }

   public String getMethodCallSyntax(String obj, String method, String... args) {
      StringBuilder sb = (new StringBuilder()).append(obj).append('.').append(method).append('(');
      int len = args.length;
      if (len > 0) {
         sb.append(args[0]);
      }

      for(int i = 1; i < len; ++i) {
         sb.append(',').append(args[i]);
      }

      sb.append(')');
      return sb.toString();
   }

   public List<String> getMimeTypes() {
      return Collections.unmodifiableList(mimeTypes);
   }

   public List<String> getNames() {
      return Collections.unmodifiableList(names);
   }

   public String getOutputStatement(String toDisplay) {
      return "print(" + toDisplay + ")";
   }

   public Object getParameter(String key) {
      byte var3 = -1;
      switch(key.hashCode()) {
      case -1073020410:
         if (key.equals("javax.script.engine_version")) {
            var3 = 2;
         }
         break;
      case -1047659667:
         if (key.equals("javax.script.engine")) {
            var3 = 1;
         }
         break;
      case -917703229:
         if (key.equals("javax.script.language")) {
            var3 = 3;
         }
         break;
      case -852670884:
         if (key.equals("javax.script.language_version")) {
            var3 = 4;
         }
         break;
      case -125973898:
         if (key.equals("javax.script.name")) {
            var3 = 0;
         }
         break;
      case 1224369528:
         if (key.equals("THREADING")) {
            var3 = 5;
         }
      }

      switch(var3) {
      case 0:
         return "javascript";
      case 1:
         return "Oracle Nashorn";
      case 2:
         return Version.version();
      case 3:
         return "ECMAScript";
      case 4:
         return "ECMA - 262 Edition 5.1";
      case 5:
         return null;
      default:
         return null;
      }
   }

   public String getProgram(String... statements) {
      StringBuilder sb = new StringBuilder();
      String[] var3 = statements;
      int var4 = statements.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String statement = var3[var5];
         sb.append(statement).append(';');
      }

      return sb.toString();
   }

   public ScriptEngine getScriptEngine() {
      try {
         return new NashornScriptEngine(this, DEFAULT_OPTIONS, getAppClassLoader(), (ClassFilter)null);
      } catch (RuntimeException var2) {
         if (Context.DEBUG) {
            var2.printStackTrace();
         }

         throw var2;
      }
   }

   public ScriptEngine getScriptEngine(ClassLoader appLoader) {
      return this.newEngine(DEFAULT_OPTIONS, appLoader, (ClassFilter)null);
   }

   public ScriptEngine getScriptEngine(ClassFilter classFilter) {
      return this.newEngine(DEFAULT_OPTIONS, getAppClassLoader(), (ClassFilter)Objects.requireNonNull(classFilter));
   }

   public ScriptEngine getScriptEngine(String... args) {
      return this.newEngine((String[])Objects.requireNonNull(args), getAppClassLoader(), (ClassFilter)null);
   }

   public ScriptEngine getScriptEngine(String[] args, ClassLoader appLoader) {
      return this.newEngine((String[])Objects.requireNonNull(args), appLoader, (ClassFilter)null);
   }

   public ScriptEngine getScriptEngine(String[] args, ClassLoader appLoader, ClassFilter classFilter) {
      return this.newEngine((String[])Objects.requireNonNull(args), appLoader, (ClassFilter)Objects.requireNonNull(classFilter));
   }

   private ScriptEngine newEngine(String[] args, ClassLoader appLoader, ClassFilter classFilter) {
      checkConfigPermission();

      try {
         return new NashornScriptEngine(this, args, appLoader, classFilter);
      } catch (RuntimeException var5) {
         if (Context.DEBUG) {
            var5.printStackTrace();
         }

         throw var5;
      }
   }

   private static void checkConfigPermission() {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new RuntimePermission("nashorn.setConfig"));
      }

   }

   private static List<String> immutableList(String... elements) {
      return Collections.unmodifiableList(Arrays.asList(elements));
   }

   private static ClassLoader getAppClassLoader() {
      ClassLoader ccl = Thread.currentThread().getContextClassLoader();
      return ccl == null ? NashornScriptEngineFactory.class.getClassLoader() : ccl;
   }
}
