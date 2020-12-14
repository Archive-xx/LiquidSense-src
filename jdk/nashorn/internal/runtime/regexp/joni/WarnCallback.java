package jdk.nashorn.internal.runtime.regexp.joni;

public interface WarnCallback {
   WarnCallback DEFAULT = new WarnCallback() {
      public void warn(String message) {
         System.err.println(message);
      }
   };

   void warn(String var1);
}
