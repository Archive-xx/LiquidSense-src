package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a;\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u00022\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001aY\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u000226\u0010\u0007\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u00050\b\"\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005¢\u0006\u0002\u0010\t\u001aW\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001a;\u0010\f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u00022\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001aW\u0010\f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001a-\u0010\r\u001a\u00020\u000e\"\f\b\u0000\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00062\b\u0010\u000f\u001a\u0004\u0018\u0001H\u00022\b\u0010\u0010\u001a\u0004\u0018\u0001H\u0002¢\u0006\u0002\u0010\u0011\u001a>\u0010\u0012\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u00022\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b¢\u0006\u0002\u0010\u0013\u001aY\u0010\u0012\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u000226\u0010\u0007\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u00050\b\"\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005¢\u0006\u0002\u0010\u0014\u001aZ\u0010\u0012\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n2\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u00022\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b¢\u0006\u0002\u0010\u0015\u001aG\u0010\u0016\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u00022 \u0010\u0007\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u00050\bH\u0002¢\u0006\u0004\b\u0017\u0010\u0014\u001a&\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006\u001a-\u0010\u0019\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0087\b\u001a@\u0010\u0019\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\b\b\u0000\u0010\u0002*\u00020\u001a2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003\u001a-\u0010\u001b\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0087\b\u001a@\u0010\u001b\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\b\b\u0000\u0010\u0002*\u00020\u001a2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003\u001a&\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006\u001a0\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\u001aO\u0010\u001e\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003H\u0086\u0004\u001aO\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001ak\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001aO\u0010 \u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001ak\u0010 \u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001am\u0010!\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u000328\b\u0004\u0010\"\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u000e0#H\u0087\b\u001aO\u0010&\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003H\u0086\u0004¨\u0006'"},
   d2 = {"compareBy", "Ljava/util/Comparator;", "T", "Lkotlin/Comparator;", "selector", "Lkotlin/Function1;", "", "selectors", "", "([Lkotlin/jvm/functions/Function1;)Ljava/util/Comparator;", "K", "comparator", "compareByDescending", "compareValues", "", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)I", "compareValuesBy", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)I", "(Ljava/lang/Object;Ljava/lang/Object;[Lkotlin/jvm/functions/Function1;)I", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;Lkotlin/jvm/functions/Function1;)I", "compareValuesByImpl", "compareValuesByImpl$ComparisonsKt__ComparisonsKt", "naturalOrder", "nullsFirst", "", "nullsLast", "reverseOrder", "reversed", "then", "thenBy", "thenByDescending", "thenComparator", "comparison", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "thenDescending", "kotlin-stdlib"},
   xs = "kotlin/comparisons/ComparisonsKt"
)
class ComparisonsKt__ComparisonsKt {
   public static final <T> int compareValuesBy(T a, T b, @NotNull Function1<? super T, ? extends Comparable<?>>... selectors) {
      Intrinsics.checkParameterIsNotNull(selectors, "selectors");
      boolean var3 = selectors.length > 0;
      boolean var4 = false;
      boolean var5 = false;
      var5 = false;
      boolean var6 = false;
      if (!var3) {
         boolean var7 = false;
         String var8 = "Failed requirement.";
         throw (Throwable)(new IllegalArgumentException(var8.toString()));
      } else {
         return compareValuesByImpl$ComparisonsKt__ComparisonsKt(a, b, selectors);
      }
   }

   private static final <T> int compareValuesByImpl$ComparisonsKt__ComparisonsKt(T a, T b, Function1<? super T, ? extends Comparable<?>>[] selectors) {
      Function1[] var5 = selectors;
      int var6 = selectors.length;

      for(int var4 = 0; var4 < var6; ++var4) {
         Function1 fn = var5[var4];
         Comparable v1 = (Comparable)fn.invoke(a);
         Comparable v2 = (Comparable)fn.invoke(b);
         int diff = ComparisonsKt.compareValues(v1, v2);
         if (diff != 0) {
            return diff;
         }
      }

      return 0;
   }

   @InlineOnly
   private static final <T> int compareValuesBy(T a, T b, Function1<? super T, ? extends Comparable<?>> selector) {
      int $i$f$compareValuesBy = 0;
      return ComparisonsKt.compareValues((Comparable)selector.invoke(a), (Comparable)selector.invoke(b));
   }

   @InlineOnly
   private static final <T, K> int compareValuesBy(T a, T b, Comparator<? super K> comparator, Function1<? super T, ? extends K> selector) {
      int $i$f$compareValuesBy = 0;
      return comparator.compare(selector.invoke(a), selector.invoke(b));
   }

   public static final <T extends Comparable<?>> int compareValues(@Nullable T a, @Nullable T b) {
      if (a == b) {
         return 0;
      } else if (a == null) {
         return -1;
      } else {
         return b == null ? 1 : a.compareTo(b);
      }
   }

   @NotNull
   public static final <T> Comparator<T> compareBy(@NotNull final Function1<? super T, ? extends Comparable<?>>... selectors) {
      Intrinsics.checkParameterIsNotNull(selectors, "selectors");
      boolean var1 = selectors.length > 0;
      boolean var2 = false;
      boolean var3 = false;
      var3 = false;
      boolean var4 = false;
      if (!var1) {
         boolean var5 = false;
         String var6 = "Failed requirement.";
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      } else {
         return (Comparator)(new Comparator<T>() {
            public final int compare(T a, T b) {
               return ComparisonsKt__ComparisonsKt.compareValuesByImpl$ComparisonsKt__ComparisonsKt(a, b, selectors);
            }
         });
      }
   }

   @InlineOnly
   private static final <T> Comparator<T> compareBy(final Function1<? super T, ? extends Comparable<?>> selector) {
      int $i$f$compareBy = 0;
      return (Comparator)(new Comparator<T>() {
         public final int compare(T a, T b) {
            boolean var3 = false;
            return ComparisonsKt.compareValues((Comparable)selector.invoke(a), (Comparable)selector.invoke(b));
         }
      });
   }

   @InlineOnly
   private static final <T, K> Comparator<T> compareBy(final Comparator<? super K> comparator, final Function1<? super T, ? extends K> selector) {
      int $i$f$compareBy = 0;
      return (Comparator)(new Comparator<T>() {
         public final int compare(T a, T b) {
            Comparator var3 = comparator;
            boolean var4 = false;
            return var3.compare(selector.invoke(a), selector.invoke(b));
         }
      });
   }

   @InlineOnly
   private static final <T> Comparator<T> compareByDescending(final Function1<? super T, ? extends Comparable<?>> selector) {
      int $i$f$compareByDescending = 0;
      return (Comparator)(new Comparator<T>() {
         public final int compare(T a, T b) {
            boolean var3 = false;
            return ComparisonsKt.compareValues((Comparable)selector.invoke(b), (Comparable)selector.invoke(a));
         }
      });
   }

   @InlineOnly
   private static final <T, K> Comparator<T> compareByDescending(final Comparator<? super K> comparator, final Function1<? super T, ? extends K> selector) {
      int $i$f$compareByDescending = 0;
      return (Comparator)(new Comparator<T>() {
         public final int compare(T a, T b) {
            Comparator var3 = comparator;
            boolean var4 = false;
            return var3.compare(selector.invoke(b), selector.invoke(a));
         }
      });
   }

   @InlineOnly
   private static final <T> Comparator<T> thenBy(@NotNull final Comparator<T> $this$thenBy, final Function1<? super T, ? extends Comparable<?>> selector) {
      int $i$f$thenBy = 0;
      return (Comparator)(new Comparator<T>() {
         public final int compare(T a, T b) {
            int previousCompare = $this$thenBy.compare(a, b);
            int var10000;
            if (previousCompare != 0) {
               var10000 = previousCompare;
            } else {
               boolean var4 = false;
               var10000 = ComparisonsKt.compareValues((Comparable)selector.invoke(a), (Comparable)selector.invoke(b));
            }

            return var10000;
         }
      });
   }

   @InlineOnly
   private static final <T, K> Comparator<T> thenBy(@NotNull final Comparator<T> $this$thenBy, final Comparator<? super K> comparator, final Function1<? super T, ? extends K> selector) {
      int $i$f$thenBy = 0;
      return (Comparator)(new Comparator<T>() {
         public final int compare(T a, T b) {
            int previousCompare = $this$thenBy.compare(a, b);
            int var10000;
            if (previousCompare != 0) {
               var10000 = previousCompare;
            } else {
               Comparator var4 = comparator;
               boolean var5 = false;
               var10000 = var4.compare(selector.invoke(a), selector.invoke(b));
            }

            return var10000;
         }
      });
   }

   @InlineOnly
   private static final <T> Comparator<T> thenByDescending(@NotNull final Comparator<T> $this$thenByDescending, final Function1<? super T, ? extends Comparable<?>> selector) {
      int $i$f$thenByDescending = 0;
      return (Comparator)(new Comparator<T>() {
         public final int compare(T a, T b) {
            int previousCompare = $this$thenByDescending.compare(a, b);
            int var10000;
            if (previousCompare != 0) {
               var10000 = previousCompare;
            } else {
               boolean var4 = false;
               var10000 = ComparisonsKt.compareValues((Comparable)selector.invoke(b), (Comparable)selector.invoke(a));
            }

            return var10000;
         }
      });
   }

   @InlineOnly
   private static final <T, K> Comparator<T> thenByDescending(@NotNull final Comparator<T> $this$thenByDescending, final Comparator<? super K> comparator, final Function1<? super T, ? extends K> selector) {
      int $i$f$thenByDescending = 0;
      return (Comparator)(new Comparator<T>() {
         public final int compare(T a, T b) {
            int previousCompare = $this$thenByDescending.compare(a, b);
            int var10000;
            if (previousCompare != 0) {
               var10000 = previousCompare;
            } else {
               Comparator var4 = comparator;
               boolean var5 = false;
               var10000 = var4.compare(selector.invoke(b), selector.invoke(a));
            }

            return var10000;
         }
      });
   }

   @InlineOnly
   private static final <T> Comparator<T> thenComparator(@NotNull final Comparator<T> $this$thenComparator, final Function2<? super T, ? super T, Integer> comparison) {
      int $i$f$thenComparator = 0;
      return (Comparator)(new Comparator<T>() {
         public final int compare(T a, T b) {
            int previousCompare = $this$thenComparator.compare(a, b);
            return previousCompare != 0 ? previousCompare : ((Number)comparison.invoke(a, b)).intValue();
         }
      });
   }

   @NotNull
   public static final <T> Comparator<T> then(@NotNull final Comparator<T> $this$then, @NotNull final Comparator<? super T> comparator) {
      Intrinsics.checkParameterIsNotNull($this$then, "$this$then");
      Intrinsics.checkParameterIsNotNull(comparator, "comparator");
      return (Comparator)(new Comparator<T>() {
         public final int compare(T a, T b) {
            int previousCompare = $this$then.compare(a, b);
            return previousCompare != 0 ? previousCompare : comparator.compare(a, b);
         }
      });
   }

   @NotNull
   public static final <T> Comparator<T> thenDescending(@NotNull final Comparator<T> $this$thenDescending, @NotNull final Comparator<? super T> comparator) {
      Intrinsics.checkParameterIsNotNull($this$thenDescending, "$this$thenDescending");
      Intrinsics.checkParameterIsNotNull(comparator, "comparator");
      return (Comparator)(new Comparator<T>() {
         public final int compare(T a, T b) {
            int previousCompare = $this$thenDescending.compare(a, b);
            return previousCompare != 0 ? previousCompare : comparator.compare(b, a);
         }
      });
   }

   @NotNull
   public static final <T> Comparator<T> nullsFirst(@NotNull final Comparator<? super T> comparator) {
      Intrinsics.checkParameterIsNotNull(comparator, "comparator");
      return (Comparator)(new Comparator<T>() {
         public final int compare(@Nullable T a, @Nullable T b) {
            return a == b ? 0 : (a == null ? -1 : (b == null ? 1 : comparator.compare(a, b)));
         }
      });
   }

   @InlineOnly
   private static final <T extends Comparable<? super T>> Comparator<T> nullsFirst() {
      int $i$f$nullsFirst = 0;
      return ComparisonsKt.nullsFirst(ComparisonsKt.naturalOrder());
   }

   @NotNull
   public static final <T> Comparator<T> nullsLast(@NotNull final Comparator<? super T> comparator) {
      Intrinsics.checkParameterIsNotNull(comparator, "comparator");
      return (Comparator)(new Comparator<T>() {
         public final int compare(@Nullable T a, @Nullable T b) {
            return a == b ? 0 : (a == null ? 1 : (b == null ? -1 : comparator.compare(a, b)));
         }
      });
   }

   @InlineOnly
   private static final <T extends Comparable<? super T>> Comparator<T> nullsLast() {
      int $i$f$nullsLast = 0;
      return ComparisonsKt.nullsLast(ComparisonsKt.naturalOrder());
   }

   @NotNull
   public static final <T extends Comparable<? super T>> Comparator<T> naturalOrder() {
      NaturalOrderComparator var10000 = NaturalOrderComparator.INSTANCE;
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
      } else {
         return (Comparator)var10000;
      }
   }

   @NotNull
   public static final <T extends Comparable<? super T>> Comparator<T> reverseOrder() {
      ReverseOrderComparator var10000 = ReverseOrderComparator.INSTANCE;
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
      } else {
         return (Comparator)var10000;
      }
   }

   @NotNull
   public static final <T> Comparator<T> reversed(@NotNull Comparator<T> $this$reversed) {
      Intrinsics.checkParameterIsNotNull($this$reversed, "$this$reversed");
      Comparator var10000;
      if ($this$reversed instanceof ReversedComparator) {
         var10000 = ((ReversedComparator)$this$reversed).getComparator();
      } else if (Intrinsics.areEqual((Object)$this$reversed, (Object)NaturalOrderComparator.INSTANCE)) {
         ReverseOrderComparator var2 = ReverseOrderComparator.INSTANCE;
         if (var2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
         }

         var10000 = (Comparator)var2;
      } else if (Intrinsics.areEqual((Object)$this$reversed, (Object)ReverseOrderComparator.INSTANCE)) {
         NaturalOrderComparator var3 = NaturalOrderComparator.INSTANCE;
         if (var3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
         }

         var10000 = (Comparator)var3;
      } else {
         var10000 = (Comparator)(new ReversedComparator($this$reversed));
      }

      return var10000;
   }

   public ComparisonsKt__ComparisonsKt() {
   }
}
