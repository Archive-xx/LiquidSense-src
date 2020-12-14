package jdk.nashorn.internal.runtime.regexp.joni.exception;

public class JOniException extends RuntimeException {
   private static final long serialVersionUID = -6027192180014164667L;

   public JOniException(String message) {
      super(message, (Throwable)null, false, false);
   }
}
