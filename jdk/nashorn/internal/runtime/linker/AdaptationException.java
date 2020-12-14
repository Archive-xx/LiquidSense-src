package jdk.nashorn.internal.runtime.linker;

final class AdaptationException extends Exception {
   private final AdaptationResult adaptationResult;

   AdaptationException(AdaptationResult.Outcome outcome, String classList) {
      super((String)null, (Throwable)null, false, false);
      this.adaptationResult = new AdaptationResult(outcome, new String[]{classList});
   }

   AdaptationResult getAdaptationResult() {
      return this.adaptationResult;
   }
}
