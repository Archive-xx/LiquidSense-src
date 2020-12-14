package kotlin.reflect;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J!\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"},
   d2 = {"Lkotlin/reflect/KTypeProjection;", "", "variance", "Lkotlin/reflect/KVariance;", "type", "Lkotlin/reflect/KType;", "(Lkotlin/reflect/KVariance;Lkotlin/reflect/KType;)V", "getType", "()Lkotlin/reflect/KType;", "getVariance", "()Lkotlin/reflect/KVariance;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.1"
)
public final class KTypeProjection {
   @Nullable
   private final KVariance variance;
   @Nullable
   private final KType type;
   @NotNull
   private static final KTypeProjection STAR = new KTypeProjection((KVariance)null, (KType)null);
   public static final KTypeProjection.Companion Companion = new KTypeProjection.Companion((DefaultConstructorMarker)null);

   @Nullable
   public final KVariance getVariance() {
      return this.variance;
   }

   @Nullable
   public final KType getType() {
      return this.type;
   }

   public KTypeProjection(@Nullable KVariance variance, @Nullable KType type) {
      this.variance = variance;
      this.type = type;
   }

   @Nullable
   public final KVariance component1() {
      return this.variance;
   }

   @Nullable
   public final KType component2() {
      return this.type;
   }

   @NotNull
   public final KTypeProjection copy(@Nullable KVariance variance, @Nullable KType type) {
      return new KTypeProjection(variance, type);
   }

   // $FF: synthetic method
   public static KTypeProjection copy$default(KTypeProjection var0, KVariance var1, KType var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = var0.variance;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.type;
      }

      return var0.copy(var1, var2);
   }

   @NotNull
   public String toString() {
      return "KTypeProjection(variance=" + this.variance + ", type=" + this.type + ")";
   }

   public int hashCode() {
      KVariance var10000 = this.variance;
      int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
      KType var10001 = this.type;
      return var1 + (var10001 != null ? var10001.hashCode() : 0);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof KTypeProjection) {
            KTypeProjection var2 = (KTypeProjection)var1;
            if (Intrinsics.areEqual((Object)this.variance, (Object)var2.variance) && Intrinsics.areEqual((Object)this.type, (Object)var2.type)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"},
      d2 = {"Lkotlin/reflect/KTypeProjection$Companion;", "", "()V", "STAR", "Lkotlin/reflect/KTypeProjection;", "getSTAR", "()Lkotlin/reflect/KTypeProjection;", "contravariant", "type", "Lkotlin/reflect/KType;", "covariant", "invariant", "kotlin-stdlib"}
   )
   public static final class Companion {
      @NotNull
      public final KTypeProjection getSTAR() {
         return KTypeProjection.STAR;
      }

      @NotNull
      public final KTypeProjection invariant(@NotNull KType type) {
         Intrinsics.checkParameterIsNotNull(type, "type");
         return new KTypeProjection(KVariance.INVARIANT, type);
      }

      @NotNull
      public final KTypeProjection contravariant(@NotNull KType type) {
         Intrinsics.checkParameterIsNotNull(type, "type");
         return new KTypeProjection(KVariance.IN, type);
      }

      @NotNull
      public final KTypeProjection covariant(@NotNull KType type) {
         Intrinsics.checkParameterIsNotNull(type, "type");
         return new KTypeProjection(KVariance.OUT, type);
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
