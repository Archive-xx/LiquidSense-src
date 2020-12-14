package jdk.nashorn.internal.runtime;

import javax.script.ScriptException;
import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.internal.codegen.CompilerConstants;

public final class ECMAException extends NashornException {
   public static final CompilerConstants.Call CREATE;
   public static final CompilerConstants.FieldAccess THROWN;
   private static final String EXCEPTION_PROPERTY = "nashornException";
   public final Object thrown;

   private ECMAException(Object thrown, String fileName, int line, int column) {
      super(ScriptRuntime.safeToString(thrown), asThrowable(thrown), fileName, line, column);
      this.thrown = thrown;
      this.setExceptionToThrown();
   }

   public ECMAException(Object thrown, Throwable cause) {
      super(ScriptRuntime.safeToString(thrown), cause);
      this.thrown = thrown;
      this.setExceptionToThrown();
   }

   public static ECMAException create(Object thrown, String fileName, int line, int column) {
      if (thrown instanceof ScriptObject) {
         Object exception = getException((ScriptObject)thrown);
         if (exception instanceof ECMAException) {
            ECMAException ee = (ECMAException)exception;
            if (ee.getThrown() == thrown) {
               ee.setFileName(fileName);
               ee.setLineNumber(line);
               ee.setColumnNumber(column);
               return ee;
            }
         }
      }

      return new ECMAException(thrown, fileName, line, column);
   }

   public Object getThrown() {
      return this.thrown;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      String fileName = this.getFileName();
      int line = this.getLineNumber();
      int column = this.getColumnNumber();
      if (fileName != null) {
         sb.append(fileName);
         if (line >= 0) {
            sb.append(':');
            sb.append(line);
         }

         if (column >= 0) {
            sb.append(':');
            sb.append(column);
         }

         sb.append(' ');
      } else {
         sb.append("ECMAScript Exception: ");
      }

      sb.append(this.getMessage());
      return sb.toString();
   }

   public static Object getException(ScriptObject errObj) {
      return errObj.hasOwnProperty("nashornException") ? errObj.get("nashornException") : null;
   }

   public static Object printStackTrace(ScriptObject errObj) {
      Object exception = getException(errObj);
      if (exception instanceof Throwable) {
         ((Throwable)exception).printStackTrace(Context.getCurrentErr());
      } else {
         Context.err("<stack trace not available>");
      }

      return ScriptRuntime.UNDEFINED;
   }

   public static Object getLineNumber(ScriptObject errObj) {
      Object e = getException(errObj);
      if (e instanceof NashornException) {
         return ((NashornException)e).getLineNumber();
      } else {
         return e instanceof ScriptException ? ((ScriptException)e).getLineNumber() : ScriptRuntime.UNDEFINED;
      }
   }

   public static Object getColumnNumber(ScriptObject errObj) {
      Object e = getException(errObj);
      if (e instanceof NashornException) {
         return ((NashornException)e).getColumnNumber();
      } else {
         return e instanceof ScriptException ? ((ScriptException)e).getColumnNumber() : ScriptRuntime.UNDEFINED;
      }
   }

   public static Object getFileName(ScriptObject errObj) {
      Object e = getException(errObj);
      if (e instanceof NashornException) {
         return ((NashornException)e).getFileName();
      } else {
         return e instanceof ScriptException ? ((ScriptException)e).getFileName() : ScriptRuntime.UNDEFINED;
      }
   }

   public static String safeToString(ScriptObject errObj) {
      Object name = ScriptRuntime.UNDEFINED;

      try {
         name = errObj.get("name");
      } catch (Exception var5) {
      }

      String name;
      if (name == ScriptRuntime.UNDEFINED) {
         name = "Error";
      } else {
         name = ScriptRuntime.safeToString(name);
      }

      Object msg = ScriptRuntime.UNDEFINED;

      try {
         msg = errObj.get("message");
      } catch (Exception var4) {
      }

      String msg;
      if (msg == ScriptRuntime.UNDEFINED) {
         msg = "";
      } else {
         msg = ScriptRuntime.safeToString(msg);
      }

      if (((String)name).isEmpty()) {
         return (String)msg;
      } else {
         return ((String)msg).isEmpty() ? (String)name : name + ": " + msg;
      }
   }

   private static Throwable asThrowable(Object obj) {
      return obj instanceof Throwable ? (Throwable)obj : null;
   }

   private void setExceptionToThrown() {
      if (this.thrown instanceof ScriptObject) {
         ScriptObject sobj = (ScriptObject)this.thrown;
         if (!sobj.has("nashornException")) {
            sobj.addOwnProperty("nashornException", 2, this);
         } else {
            sobj.set("nashornException", this, 0);
         }
      }

   }

   static {
      CREATE = CompilerConstants.staticCallNoLookup(ECMAException.class, "create", ECMAException.class, Object.class, String.class, Integer.TYPE, Integer.TYPE);
      THROWN = CompilerConstants.virtualField(ECMAException.class, "thrown", Object.class);
   }
}
