package jdk.nashorn.internal;

public final class AssertsEnabled {
   private static boolean assertsEnabled = false;
   // $FF: synthetic field
   static final boolean $assertionsDisabled = !AssertsEnabled.class.desiredAssertionStatus();

   public static boolean assertsEnabled() {
      return assertsEnabled;
   }

   static {
      if (!$assertionsDisabled) {
         assertsEnabled = true;
         if (false) {
            throw new AssertionError();
         }
      }

   }
}
