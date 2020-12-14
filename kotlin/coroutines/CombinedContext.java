package kotlin.coroutines;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0001\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u0003:\u0001!B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u0000H\u0002J\u0013\u0010\f\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J5\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u00102\u0006\u0010\u0011\u001a\u0002H\u00102\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u0002H\u00100\u0013H\u0016¢\u0006\u0002\u0010\u0014J(\u0010\u0015\u001a\u0004\u0018\u0001H\u0016\"\b\b\u0000\u0010\u0016*\u00020\u00062\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0018H\u0096\u0002¢\u0006\u0002\u0010\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0014\u0010\u001c\u001a\u00020\u00012\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0018H\u0016J\b\u0010\u001d\u001a\u00020\u001bH\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""},
   d2 = {"Lkotlin/coroutines/CombinedContext;", "Lkotlin/coroutines/CoroutineContext;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "left", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/CoroutineContext$Element;)V", "contains", "", "containsAll", "context", "equals", "other", "", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "hashCode", "", "minusKey", "size", "toString", "", "writeReplace", "Serialized", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
public final class CombinedContext implements CoroutineContext, Serializable {
   private final CoroutineContext left;
   private final CoroutineContext.Element element;

   @Nullable
   public <E extends CoroutineContext.Element> E get(@NotNull CoroutineContext.Key<E> key) {
      Intrinsics.checkParameterIsNotNull(key, "key");
      CombinedContext cur = (CombinedContext)this;

      while(true) {
         CoroutineContext.Element var10000 = cur.element.get(key);
         if (var10000 != null) {
            CoroutineContext.Element var8 = var10000;
            boolean var4 = false;
            boolean var5 = false;
            int var7 = false;
            return var8;
         }

         CoroutineContext next = cur.left;
         if (!(next instanceof CombinedContext)) {
            return next.get(key);
         }

         cur = (CombinedContext)next;
      }
   }

   public <R> R fold(R initial, @NotNull Function2<? super R, ? super CoroutineContext.Element, ? extends R> operation) {
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      return operation.invoke(this.left.fold(initial, operation), this.element);
   }

   @NotNull
   public CoroutineContext minusKey(@NotNull CoroutineContext.Key<?> key) {
      Intrinsics.checkParameterIsNotNull(key, "key");
      if (this.element.get(key) != null) {
         boolean var3 = false;
         boolean var4 = false;
         int var6 = false;
         return this.left;
      } else {
         CoroutineContext newLeft = this.left.minusKey(key);
         return newLeft == this.left ? (CoroutineContext)this : (newLeft == EmptyCoroutineContext.INSTANCE ? (CoroutineContext)this.element : (CoroutineContext)(new CombinedContext(newLeft, this.element)));
      }
   }

   private final int size() {
      CombinedContext cur = (CombinedContext)this;
      int size = 2;

      while(true) {
         CoroutineContext var10000 = cur.left;
         if (!(var10000 instanceof CombinedContext)) {
            var10000 = null;
         }

         CombinedContext var3 = (CombinedContext)var10000;
         if ((CombinedContext)var10000 == null) {
            return size;
         }

         cur = var3;
         ++size;
      }
   }

   private final boolean contains(CoroutineContext.Element element) {
      return Intrinsics.areEqual((Object)this.get(element.getKey()), (Object)element);
   }

   private final boolean containsAll(CombinedContext context) {
      CoroutineContext next;
      for(CombinedContext cur = context; this.contains(cur.element); cur = (CombinedContext)next) {
         next = cur.left;
         if (!(next instanceof CombinedContext)) {
            if (next == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.CoroutineContext.Element");
            }

            return this.contains((CoroutineContext.Element)next);
         }
      }

      return false;
   }

   public boolean equals(@Nullable Object other) {
      return (CombinedContext)this == other || other instanceof CombinedContext && ((CombinedContext)other).size() == this.size() && ((CombinedContext)other).containsAll(this);
   }

   public int hashCode() {
      return this.left.hashCode() + this.element.hashCode();
   }

   @NotNull
   public String toString() {
      return "[" + (String)this.fold("", (Function2)null.INSTANCE) + "]";
   }

   private final Object writeReplace() {
      int n = this.size();
      final CoroutineContext[] elements = new CoroutineContext[n];
      final Ref.IntRef index = new Ref.IntRef();
      index.element = 0;
      this.fold(Unit.INSTANCE, (Function2)(new Function2<Unit, CoroutineContext.Element, Unit>() {
         public final void invoke(@NotNull Unit $noName_0, @NotNull CoroutineContext.Element element) {
            Intrinsics.checkParameterIsNotNull($noName_0, "<anonymous parameter 0>");
            Intrinsics.checkParameterIsNotNull(element, "element");
            CoroutineContext[] var10000 = elements;
            Ref.IntRef var10001 = index;
            int var3;
            var10001.element = (var3 = var10001.element) + 1;
            var10000[var3] = (CoroutineContext)element;
         }
      }));
      boolean var4 = index.element == n;
      boolean var5 = false;
      boolean var6 = false;
      var6 = false;
      boolean var7 = false;
      if (!var4) {
         boolean var8 = false;
         String var9 = "Check failed.";
         throw (Throwable)(new IllegalStateException(var9.toString()));
      } else {
         return new CombinedContext.Serialized(elements);
      }
   }

   public CombinedContext(@NotNull CoroutineContext left, @NotNull CoroutineContext.Element element) {
      Intrinsics.checkParameterIsNotNull(left, "left");
      Intrinsics.checkParameterIsNotNull(element, "element");
      super();
      this.left = left;
      this.element = element;
   }

   @NotNull
   public CoroutineContext plus(@NotNull CoroutineContext context) {
      Intrinsics.checkParameterIsNotNull(context, "context");
      return CoroutineContext.DefaultImpls.plus(this, context);
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u0000 \f2\u00060\u0001j\u0002`\u0002:\u0001\fB\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\n\u001a\u00020\u000bH\u0002R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\b¨\u0006\r"},
      d2 = {"Lkotlin/coroutines/CombinedContext$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "elements", "", "Lkotlin/coroutines/CoroutineContext;", "([Lkotlin/coroutines/CoroutineContext;)V", "getElements", "()[Lkotlin/coroutines/CoroutineContext;", "[Lkotlin/coroutines/CoroutineContext;", "readResolve", "", "Companion", "kotlin-stdlib"}
   )
   private static final class Serialized implements Serializable {
      @NotNull
      private final CoroutineContext[] elements;
      private static final long serialVersionUID = 0L;
      public static final CombinedContext.Serialized.Companion Companion = new CombinedContext.Serialized.Companion((DefaultConstructorMarker)null);

      private final Object readResolve() {
         CoroutineContext[] $this$fold$iv = this.elements;
         Object initial$iv = EmptyCoroutineContext.INSTANCE;
         int $i$f$fold = false;
         Object accumulator$iv = initial$iv;
         CoroutineContext[] var5 = $this$fold$iv;
         int var6 = $this$fold$iv.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Object element$iv = var5[var7];
            CoroutineContext p1 = (CoroutineContext)accumulator$iv;
            int var11 = false;
            accumulator$iv = p1.plus(element$iv);
         }

         return accumulator$iv;
      }

      @NotNull
      public final CoroutineContext[] getElements() {
         return this.elements;
      }

      public Serialized(@NotNull CoroutineContext[] elements) {
         Intrinsics.checkParameterIsNotNull(elements, "elements");
         super();
         this.elements = elements;
      }

      @Metadata(
         mv = {1, 1, 15},
         bv = {1, 0, 3},
         k = 1,
         d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"},
         d2 = {"Lkotlin/coroutines/CombinedContext$Serialized$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"}
      )
      public static final class Companion {
         private Companion() {
         }

         // $FF: synthetic method
         public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
         }
      }
   }
}
