package kotlin.jvm.internal;

import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.KVariance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0017\u001a\u00020\u0013H\u0002J\u0013\u0010\u0018\u001a\u00020\b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0096\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0013H\u0016J\f\u0010\u0017\u001a\u00020\u0013*\u00020\u0006H\u0002R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0011R\u001c\u0010\u0012\u001a\u00020\u0013*\u0006\u0012\u0002\b\u00030\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001e"},
   d2 = {"Lkotlin/jvm/internal/TypeReference;", "Lkotlin/reflect/KType;", "classifier", "Lkotlin/reflect/KClassifier;", "arguments", "", "Lkotlin/reflect/KTypeProjection;", "isMarkedNullable", "", "(Lkotlin/reflect/KClassifier;Ljava/util/List;Z)V", "annotations", "", "getAnnotations", "()Ljava/util/List;", "getArguments", "getClassifier", "()Lkotlin/reflect/KClassifier;", "()Z", "arrayClassName", "", "Ljava/lang/Class;", "getArrayClassName", "(Ljava/lang/Class;)Ljava/lang/String;", "asString", "equals", "other", "", "hashCode", "", "toString", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.4"
)
public final class TypeReference implements KType {
   @NotNull
   private final KClassifier classifier;
   @NotNull
   private final List<KTypeProjection> arguments;
   private final boolean isMarkedNullable;

   @NotNull
   public List<Annotation> getAnnotations() {
      return CollectionsKt.emptyList();
   }

   public boolean equals(@Nullable Object other) {
      return other instanceof TypeReference && Intrinsics.areEqual((Object)this.getClassifier(), (Object)((TypeReference)other).getClassifier()) && Intrinsics.areEqual((Object)this.getArguments(), (Object)((TypeReference)other).getArguments()) && this.isMarkedNullable() == ((TypeReference)other).isMarkedNullable();
   }

   public int hashCode() {
      return (this.getClassifier().hashCode() * 31 + this.getArguments().hashCode()) * 31 + Boolean.valueOf(this.isMarkedNullable()).hashCode();
   }

   @NotNull
   public String toString() {
      return this.asString() + " (Kotlin reflection is not available)";
   }

   private final String asString() {
      KClassifier var10000 = this.getClassifier();
      if (!(var10000 instanceof KClass)) {
         var10000 = null;
      }

      Class javaClass = (KClass)var10000 != null ? JvmClassMappingKt.getJavaClass((KClass)var10000) : null;
      String klass = javaClass == null ? this.getClassifier().toString() : (javaClass.isArray() ? this.getArrayClassName(javaClass) : javaClass.getName());
      String args = this.getArguments().isEmpty() ? "" : CollectionsKt.joinToString$default((Iterable)this.getArguments(), (CharSequence)", ", (CharSequence)"<", (CharSequence)">", 0, (CharSequence)null, (Function1)(new Function1<KTypeProjection, String>() {
         @NotNull
         public final String invoke(@NotNull KTypeProjection it) {
            Intrinsics.checkParameterIsNotNull(it, "it");
            return TypeReference.this.asString(it);
         }
      }), 24, (Object)null);
      String nullable = this.isMarkedNullable() ? "?" : "";
      return klass + args + nullable;
   }

   private final String getArrayClassName(@NotNull Class<?> $this$arrayClassName) {
      return Intrinsics.areEqual((Object)$this$arrayClassName, (Object)boolean[].class) ? "kotlin.BooleanArray" : (Intrinsics.areEqual((Object)$this$arrayClassName, (Object)char[].class) ? "kotlin.CharArray" : (Intrinsics.areEqual((Object)$this$arrayClassName, (Object)byte[].class) ? "kotlin.ByteArray" : (Intrinsics.areEqual((Object)$this$arrayClassName, (Object)short[].class) ? "kotlin.ShortArray" : (Intrinsics.areEqual((Object)$this$arrayClassName, (Object)int[].class) ? "kotlin.IntArray" : (Intrinsics.areEqual((Object)$this$arrayClassName, (Object)float[].class) ? "kotlin.FloatArray" : (Intrinsics.areEqual((Object)$this$arrayClassName, (Object)long[].class) ? "kotlin.LongArray" : (Intrinsics.areEqual((Object)$this$arrayClassName, (Object)double[].class) ? "kotlin.DoubleArray" : "kotlin.Array")))))));
   }

   private final String asString(@NotNull KTypeProjection $this$asString) {
      // $FF: Couldn't be decompiled
   }

   @NotNull
   public KClassifier getClassifier() {
      return this.classifier;
   }

   @NotNull
   public List<KTypeProjection> getArguments() {
      return this.arguments;
   }

   public boolean isMarkedNullable() {
      return this.isMarkedNullable;
   }

   public TypeReference(@NotNull KClassifier classifier, @NotNull List<KTypeProjection> arguments, boolean isMarkedNullable) {
      Intrinsics.checkParameterIsNotNull(classifier, "classifier");
      Intrinsics.checkParameterIsNotNull(arguments, "arguments");
      super();
      this.classifier = classifier;
      this.arguments = arguments;
      this.isMarkedNullable = isMarkedNullable;
   }
}
