package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandles;
import java.util.Objects;
import jdk.internal.dynalink.CallSiteDescriptor;

public abstract class AbstractCallSiteDescriptor implements CallSiteDescriptor {
   public String getName() {
      return this.appendName(new StringBuilder(this.getNameLength())).toString();
   }

   public java.lang.invoke.MethodHandles.Lookup getLookup() {
      return MethodHandles.publicLookup();
   }

   public boolean equals(Object obj) {
      return obj instanceof CallSiteDescriptor && this.equals((CallSiteDescriptor)obj);
   }

   public boolean equals(CallSiteDescriptor csd) {
      if (csd == null) {
         return false;
      } else if (csd == this) {
         return true;
      } else {
         int ntc = this.getNameTokenCount();
         if (ntc != csd.getNameTokenCount()) {
            return false;
         } else {
            int i = ntc;

            do {
               if (i-- <= 0) {
                  if (!this.getMethodType().equals(csd.getMethodType())) {
                     return false;
                  }

                  return lookupsEqual(this.getLookup(), csd.getLookup());
               }
            } while(Objects.equals(this.getNameToken(i), csd.getNameToken(i)));

            return false;
         }
      }
   }

   public int hashCode() {
      java.lang.invoke.MethodHandles.Lookup lookup = this.getLookup();
      int h = lookup.lookupClass().hashCode() + 31 * lookup.lookupModes();
      int c = this.getNameTokenCount();

      for(int i = 0; i < c; ++i) {
         h = h * 31 + this.getNameToken(i).hashCode();
      }

      return h * 31 + this.getMethodType().hashCode();
   }

   public String toString() {
      String mt = this.getMethodType().toString();
      String l = this.getLookup().toString();
      StringBuilder b = new StringBuilder(l.length() + 1 + mt.length() + this.getNameLength());
      return this.appendName(b).append(mt).append("@").append(l).toString();
   }

   private int getNameLength() {
      int c = this.getNameTokenCount();
      int l = 0;

      for(int i = 0; i < c; ++i) {
         l += this.getNameToken(i).length();
      }

      return l + c - 1;
   }

   private StringBuilder appendName(StringBuilder b) {
      b.append(this.getNameToken(0));
      int c = this.getNameTokenCount();

      for(int i = 1; i < c; ++i) {
         b.append(':').append(this.getNameToken(i));
      }

      return b;
   }

   private static boolean lookupsEqual(java.lang.invoke.MethodHandles.Lookup l1, java.lang.invoke.MethodHandles.Lookup l2) {
      if (l1 == l2) {
         return true;
      } else if (l1.lookupClass() != l2.lookupClass()) {
         return false;
      } else {
         return l1.lookupModes() == l2.lookupModes();
      }
   }
}
