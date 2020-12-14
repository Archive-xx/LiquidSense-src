package kotlin.jvm.internal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u00002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a#\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0007¢\u0006\u0004\b\t\u0010\n\u001a5\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0010\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001H\u0007¢\u0006\u0004\b\t\u0010\f\u001a~\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0014\u0010\u000e\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u000f2\u001a\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u00112(\u0010\u0012\u001a$\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u0013H\u0082\b¢\u0006\u0002\u0010\u0014\"\u0018\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0015"},
   d2 = {"EMPTY", "", "", "[Ljava/lang/Object;", "MAX_SIZE", "", "collectionToArray", "collection", "", "toArray", "(Ljava/util/Collection;)[Ljava/lang/Object;", "a", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", "toArrayImpl", "empty", "Lkotlin/Function0;", "alloc", "Lkotlin/Function1;", "trim", "Lkotlin/Function2;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)[Ljava/lang/Object;", "kotlin-stdlib"}
)
@JvmName(
   name = "CollectionToArray"
)
public final class CollectionToArray {
   private static final Object[] EMPTY = new Object[0];
   private static final int MAX_SIZE = 2147483645;

   @JvmName(
      name = "toArray"
   )
   @NotNull
   public static final Object[] toArray(@NotNull Collection<?> collection) {
      Intrinsics.checkParameterIsNotNull(collection, "collection");
      int $i$f$toArrayImpl = false;
      int size$iv = collection.size();
      Object[] var10000;
      if (size$iv == 0) {
         int var3 = false;
         var10000 = EMPTY;
      } else {
         Iterator iter$iv = collection.iterator();
         if (!iter$iv.hasNext()) {
            int var4 = false;
            var10000 = EMPTY;
         } else {
            int var5 = false;
            Object[] result$iv = new Object[size$iv];
            int i$iv = 0;

            while(true) {
               while(true) {
                  result$iv[i$iv++] = iter$iv.next();
                  if (i$iv >= result$iv.length) {
                     if (!iter$iv.hasNext()) {
                        var10000 = result$iv;
                        return var10000;
                     }

                     int newSize$iv = i$iv * 3 + 1 >>> 1;
                     if (newSize$iv <= i$iv) {
                        if (i$iv >= 2147483645) {
                           throw (Throwable)(new OutOfMemoryError());
                        }

                        newSize$iv = 2147483645;
                     }

                     var10000 = Arrays.copyOf(result$iv, newSize$iv);
                     Intrinsics.checkExpressionValueIsNotNull(var10000, "Arrays.copyOf(result, newSize)");
                     result$iv = var10000;
                  } else if (!iter$iv.hasNext()) {
                     int var9 = false;
                     var10000 = Arrays.copyOf(result$iv, i$iv);
                     Intrinsics.checkExpressionValueIsNotNull(var10000, "Arrays.copyOf(result, size)");
                     return var10000;
                  }
               }
            }
         }
      }

      return var10000;
   }

   @JvmName(
      name = "toArray"
   )
   @NotNull
   public static final Object[] toArray(@NotNull Collection<?> collection, @Nullable Object[] a) {
      Intrinsics.checkParameterIsNotNull(collection, "collection");
      if (a == null) {
         throw (Throwable)(new NullPointerException());
      } else {
         int $i$f$toArrayImpl = false;
         int size$iv = collection.size();
         boolean var4;
         Object[] var10000;
         if (size$iv == 0) {
            var4 = false;
            if (a.length > 0) {
               a[0] = null;
            }

            var10000 = a;
         } else {
            Iterator iter$iv = collection.iterator();
            if (!iter$iv.hasNext()) {
               var4 = false;
               if (a.length > 0) {
                  a[0] = null;
               }

               var10000 = a;
            } else {
               int var6 = false;
               if (size$iv <= a.length) {
                  var10000 = a;
               } else {
                  Object var13 = Array.newInstance(a.getClass().getComponentType(), size$iv);
                  if (var13 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
                  }

                  var10000 = (Object[])var13;
               }

               Object[] result$iv = var10000;
               int i$iv = 0;

               while(true) {
                  while(true) {
                     result$iv[i$iv++] = iter$iv.next();
                     if (i$iv >= result$iv.length) {
                        if (!iter$iv.hasNext()) {
                           var10000 = result$iv;
                           return var10000;
                        }

                        int newSize$iv = i$iv * 3 + 1 >>> 1;
                        if (newSize$iv <= i$iv) {
                           if (i$iv >= 2147483645) {
                              throw (Throwable)(new OutOfMemoryError());
                           }

                           newSize$iv = 2147483645;
                        }

                        var10000 = Arrays.copyOf(result$iv, newSize$iv);
                        Intrinsics.checkExpressionValueIsNotNull(var10000, "Arrays.copyOf(result, newSize)");
                        result$iv = var10000;
                     } else if (!iter$iv.hasNext()) {
                        int var10 = false;
                        if (result$iv == a) {
                           a[i$iv] = null;
                           var10000 = a;
                        } else {
                           var10000 = Arrays.copyOf(result$iv, i$iv);
                           Intrinsics.checkExpressionValueIsNotNull(var10000, "Arrays.copyOf(result, size)");
                        }

                        return var10000;
                     }
                  }
               }
            }
         }

         return var10000;
      }
   }

   private static final Object[] toArrayImpl(Collection<?> collection, Function0<Object[]> empty, Function1<? super Integer, Object[]> alloc, Function2<? super Object[], ? super Integer, Object[]> trim) {
      int $i$f$toArrayImpl = 0;
      int size = collection.size();
      if (size == 0) {
         return (Object[])empty.invoke();
      } else {
         Iterator iter = collection.iterator();
         if (!iter.hasNext()) {
            return (Object[])empty.invoke();
         } else {
            Object[] result = (Object[])alloc.invoke(size);
            int i = 0;

            while(true) {
               while(true) {
                  result[i++] = iter.next();
                  if (i >= result.length) {
                     if (!iter.hasNext()) {
                        return result;
                     }

                     int newSize = i * 3 + 1 >>> 1;
                     if (newSize <= i) {
                        if (i >= 2147483645) {
                           throw (Throwable)(new OutOfMemoryError());
                        }

                        newSize = 2147483645;
                     }

                     Object[] var10000 = Arrays.copyOf(result, newSize);
                     Intrinsics.checkExpressionValueIsNotNull(var10000, "Arrays.copyOf(result, newSize)");
                     result = var10000;
                  } else if (!iter.hasNext()) {
                     return (Object[])trim.invoke(result, i);
                  }
               }
            }
         }
      }
   }
}
