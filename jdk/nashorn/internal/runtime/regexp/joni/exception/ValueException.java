package jdk.nashorn.internal.runtime.regexp.joni.exception;

public class ValueException extends SyntaxException {
   private static final long serialVersionUID = -196013852479929134L;

   public ValueException(String message) {
      super(message);
   }

   public ValueException(String message, String str) {
      super(message.replaceAll("%n", str));
   }
}
