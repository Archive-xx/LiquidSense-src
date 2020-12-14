package jdk.internal.dynalink.beans;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import sun.reflect.CallerSensitive;

public class CallerSensitiveDetector {
   private static final CallerSensitiveDetector.DetectionStrategy DETECTION_STRATEGY = getDetectionStrategy();

   static boolean isCallerSensitive(AccessibleObject ao) {
      return DETECTION_STRATEGY.isCallerSensitive(ao);
   }

   private static CallerSensitiveDetector.DetectionStrategy getDetectionStrategy() {
      try {
         return new CallerSensitiveDetector.PrivilegedDetectionStrategy();
      } catch (Throwable var1) {
         return new CallerSensitiveDetector.UnprivilegedDetectionStrategy();
      }
   }

   private static class UnprivilegedDetectionStrategy extends CallerSensitiveDetector.DetectionStrategy {
      private static final String CALLER_SENSITIVE_ANNOTATION_STRING = "@sun.reflect.CallerSensitive()";

      private UnprivilegedDetectionStrategy() {
         super(null);
      }

      boolean isCallerSensitive(AccessibleObject o) {
         Annotation[] var2 = o.getAnnotations();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Annotation a = var2[var4];
            if (String.valueOf(a).equals("@sun.reflect.CallerSensitive()")) {
               return true;
            }
         }

         return false;
      }

      // $FF: synthetic method
      UnprivilegedDetectionStrategy(Object x0) {
         this();
      }
   }

   private static class PrivilegedDetectionStrategy extends CallerSensitiveDetector.DetectionStrategy {
      private static final Class<? extends Annotation> CALLER_SENSITIVE_ANNOTATION_CLASS = CallerSensitive.class;

      private PrivilegedDetectionStrategy() {
         super(null);
      }

      boolean isCallerSensitive(AccessibleObject ao) {
         return ao.getAnnotation(CALLER_SENSITIVE_ANNOTATION_CLASS) != null;
      }

      // $FF: synthetic method
      PrivilegedDetectionStrategy(Object x0) {
         this();
      }
   }

   private abstract static class DetectionStrategy {
      private DetectionStrategy() {
      }

      abstract boolean isCallerSensitive(AccessibleObject var1);

      // $FF: synthetic method
      DetectionStrategy(Object x0) {
         this();
      }
   }
}
