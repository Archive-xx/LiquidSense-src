package kotlin;

import java.io.Serializable;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u0001*\u0006\b\u0001\u0010\u0002 \u0001*\u0006\b\u0002\u0010\u0003 \u00012\u00060\u0004j\u0002`\u0005B\u001d\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00028\u0001\u0012\u0006\u0010\b\u001a\u00028\u0002¢\u0006\u0002\u0010\tJ\u000e\u0010\u000f\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u000bJ\u000e\u0010\u0010\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u000bJ\u000e\u0010\u0011\u001a\u00028\u0002HÆ\u0003¢\u0006\u0002\u0010\u000bJ>\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u00002\b\b\u0002\u0010\u0006\u001a\u00028\u00002\b\b\u0002\u0010\u0007\u001a\u00028\u00012\b\b\u0002\u0010\b\u001a\u00028\u0002HÆ\u0001¢\u0006\u0002\u0010\u0013J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0013\u0010\u0006\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0007\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\r\u0010\u000bR\u0013\u0010\b\u001a\u00028\u0002¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\u000e\u0010\u000b¨\u0006\u001c"},
   d2 = {"Lkotlin/Triple;", "A", "B", "C", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "first", "second", "third", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", "getFirst", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getSecond", "getThird", "component1", "component2", "component3", "copy", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Triple;", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlin-stdlib"}
)
public final class Triple<A, B, C> implements Serializable {
   private final A first;
   private final B second;
   private final C third;

   @NotNull
   public String toString() {
      return "" + '(' + this.first + ", " + this.second + ", " + this.third + ')';
   }

   public final A getFirst() {
      return this.first;
   }

   public final B getSecond() {
      return this.second;
   }

   public final C getThird() {
      return this.third;
   }

   public Triple(A first, B second, C third) {
      this.first = first;
      this.second = second;
      this.third = third;
   }

   public final A component1() {
      return this.first;
   }

   public final B component2() {
      return this.second;
   }

   public final C component3() {
      return this.third;
   }

   @NotNull
   public final Triple<A, B, C> copy(A first, B second, C third) {
      return new Triple(first, second, third);
   }

   // $FF: synthetic method
   public static Triple copy$default(Triple var0, Object var1, Object var2, Object var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = var0.first;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.second;
      }

      if ((var4 & 4) != 0) {
         var3 = var0.third;
      }

      return var0.copy(var1, var2, var3);
   }

   public int hashCode() {
      Object var10000 = this.first;
      int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
      Object var10001 = this.second;
      var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
      var10001 = this.third;
      return var1 + (var10001 != null ? var10001.hashCode() : 0);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof Triple) {
            Triple var2 = (Triple)var1;
            if (Intrinsics.areEqual(this.first, var2.first) && Intrinsics.areEqual(this.second, var2.second) && Intrinsics.areEqual(this.third, var2.third)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
