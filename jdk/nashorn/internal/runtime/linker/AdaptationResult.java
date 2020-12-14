package jdk.nashorn.internal.runtime.linker;

import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ECMAException;

final class AdaptationResult {
   static final AdaptationResult SUCCESSFUL_RESULT;
   private final AdaptationResult.Outcome outcome;
   private final String[] messageArgs;

   AdaptationResult(AdaptationResult.Outcome outcome, String... messageArgs) {
      this.outcome = outcome;
      this.messageArgs = messageArgs;
   }

   AdaptationResult.Outcome getOutcome() {
      return this.outcome;
   }

   ECMAException typeError() {
      return ECMAErrors.typeError("extend." + this.outcome, this.messageArgs);
   }

   static {
      SUCCESSFUL_RESULT = new AdaptationResult(AdaptationResult.Outcome.SUCCESS, new String[]{""});
   }

   static enum Outcome {
      SUCCESS,
      ERROR_FINAL_CLASS,
      ERROR_NON_PUBLIC_CLASS,
      ERROR_NO_ACCESSIBLE_CONSTRUCTOR,
      ERROR_MULTIPLE_SUPERCLASSES,
      ERROR_NO_COMMON_LOADER,
      ERROR_FINAL_FINALIZER,
      ERROR_OTHER;
   }
}
