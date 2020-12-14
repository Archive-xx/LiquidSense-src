package jdk.internal.dynalink;

public class NoSuchDynamicMethodException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public NoSuchDynamicMethodException(String message) {
      super(message);
   }
}
