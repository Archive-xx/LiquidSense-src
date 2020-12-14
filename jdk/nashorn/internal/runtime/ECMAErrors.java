package jdk.nashorn.internal.runtime;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.scripts.JS;

public final class ECMAErrors {
   private static final String MESSAGES_RESOURCE = "jdk.nashorn.internal.runtime.resources.Messages";
   private static final ResourceBundle MESSAGES_BUNDLE = ResourceBundle.getBundle("jdk.nashorn.internal.runtime.resources.Messages", Locale.getDefault());
   private static final String scriptPackage;

   private ECMAErrors() {
   }

   private static ECMAException error(Object thrown, Throwable cause) {
      return new ECMAException(thrown, cause);
   }

   public static ECMAException asEcmaException(ParserException e) {
      return asEcmaException(Context.getGlobal(), e);
   }

   public static ECMAException asEcmaException(Global global, ParserException e) {
      JSErrorType errorType = e.getErrorType();

      assert errorType != null : "error type for " + e + " was null";

      String msg = e.getMessage();
      switch(errorType) {
      case ERROR:
         return error(global.newError(msg), e);
      case EVAL_ERROR:
         return error(global.newEvalError(msg), e);
      case RANGE_ERROR:
         return error(global.newRangeError(msg), e);
      case REFERENCE_ERROR:
         return error(global.newReferenceError(msg), e);
      case SYNTAX_ERROR:
         return error(global.newSyntaxError(msg), e);
      case TYPE_ERROR:
         return error(global.newTypeError(msg), e);
      case URI_ERROR:
         return error(global.newURIError(msg), e);
      default:
         throw new AssertionError(e.getMessage());
      }
   }

   public static ECMAException syntaxError(String msgId, String... args) {
      return syntaxError(Context.getGlobal(), msgId, args);
   }

   public static ECMAException syntaxError(Global global, String msgId, String... args) {
      return syntaxError(global, (Throwable)null, msgId, args);
   }

   public static ECMAException syntaxError(Throwable cause, String msgId, String... args) {
      return syntaxError(Context.getGlobal(), cause, msgId, args);
   }

   public static ECMAException syntaxError(Global global, Throwable cause, String msgId, String... args) {
      String msg = getMessage("syntax.error." + msgId, args);
      return error(global.newSyntaxError(msg), cause);
   }

   public static ECMAException typeError(String msgId, String... args) {
      return typeError(Context.getGlobal(), msgId, args);
   }

   public static ECMAException typeError(Global global, String msgId, String... args) {
      return typeError(global, (Throwable)null, msgId, args);
   }

   public static ECMAException typeError(Throwable cause, String msgId, String... args) {
      return typeError(Context.getGlobal(), cause, msgId, args);
   }

   public static ECMAException typeError(Global global, Throwable cause, String msgId, String... args) {
      String msg = getMessage("type.error." + msgId, args);
      return error(global.newTypeError(msg), cause);
   }

   public static ECMAException rangeError(String msgId, String... args) {
      return rangeError(Context.getGlobal(), msgId, args);
   }

   public static ECMAException rangeError(Global global, String msgId, String... args) {
      return rangeError(global, (Throwable)null, msgId, args);
   }

   public static ECMAException rangeError(Throwable cause, String msgId, String... args) {
      return rangeError(Context.getGlobal(), cause, msgId, args);
   }

   public static ECMAException rangeError(Global global, Throwable cause, String msgId, String... args) {
      String msg = getMessage("range.error." + msgId, args);
      return error(global.newRangeError(msg), cause);
   }

   public static ECMAException referenceError(String msgId, String... args) {
      return referenceError(Context.getGlobal(), msgId, args);
   }

   public static ECMAException referenceError(Global global, String msgId, String... args) {
      return referenceError(global, (Throwable)null, msgId, args);
   }

   public static ECMAException referenceError(Throwable cause, String msgId, String... args) {
      return referenceError(Context.getGlobal(), cause, msgId, args);
   }

   public static ECMAException referenceError(Global global, Throwable cause, String msgId, String... args) {
      String msg = getMessage("reference.error." + msgId, args);
      return error(global.newReferenceError(msg), cause);
   }

   public static ECMAException uriError(String msgId, String... args) {
      return uriError(Context.getGlobal(), msgId, args);
   }

   public static ECMAException uriError(Global global, String msgId, String... args) {
      return uriError(global, (Throwable)null, msgId, args);
   }

   public static ECMAException uriError(Throwable cause, String msgId, String... args) {
      return uriError(Context.getGlobal(), cause, msgId, args);
   }

   public static ECMAException uriError(Global global, Throwable cause, String msgId, String... args) {
      String msg = getMessage("uri.error." + msgId, args);
      return error(global.newURIError(msg), cause);
   }

   public static String getMessage(String msgId, String... args) {
      try {
         return (new MessageFormat(MESSAGES_BUNDLE.getString(msgId))).format(args);
      } catch (MissingResourceException var3) {
         throw new RuntimeException("no message resource found for message id: " + msgId);
      }
   }

   public static boolean isScriptFrame(StackTraceElement frame) {
      String className = frame.getClassName();
      if (className.startsWith(scriptPackage) && !CompilerConstants.isInternalMethodName(frame.getMethodName())) {
         String source = frame.getFileName();
         return source != null && !source.endsWith(".java");
      } else {
         return false;
      }
   }

   static {
      String name = JS.class.getName();
      scriptPackage = name.substring(0, name.lastIndexOf(46));
   }
}
