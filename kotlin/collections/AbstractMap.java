package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010&\n\u0002\b\b\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\b'\u0018\u0000 )*\u0004\b\u0000\u0010\u0001*\u0006\b\u0001\u0010\u0002 \u00012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003:\u0001)B\u0007\b\u0004¢\u0006\u0002\u0010\u0004J\u001f\u0010\u0013\u001a\u00020\u00142\u0010\u0010\u0015\u001a\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010\u0016H\u0000¢\u0006\u0002\b\u0017J\u0015\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001aJ\u0015\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001aJ\u0013\u0010\u001d\u001a\u00020\u00142\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0096\u0002J\u0018\u0010 \u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0019\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010!J\b\u0010\"\u001a\u00020\rH\u0016J#\u0010#\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00162\u0006\u0010\u0019\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010$J\b\u0010%\u001a\u00020\u0014H\u0016J\b\u0010&\u001a\u00020'H\u0016J\u0012\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010\u001fH\u0002J\u001c\u0010&\u001a\u00020'2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0016H\bR\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\bX\u0088\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006*"},
   d2 = {"Lkotlin/collections/AbstractMap;", "K", "V", "", "()V", "_keys", "", "_values", "", "keys", "getKeys", "()Ljava/util/Set;", "size", "", "getSize", "()I", "values", "getValues", "()Ljava/util/Collection;", "containsEntry", "", "entry", "", "containsEntry$kotlin_stdlib", "containsKey", "key", "(Ljava/lang/Object;)Z", "containsValue", "value", "equals", "other", "", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "hashCode", "implFindEntry", "(Ljava/lang/Object;)Ljava/util/Map$Entry;", "isEmpty", "toString", "", "o", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.1"
)
public abstract class AbstractMap<K, V> implements Map<K, V>, KMappedMarker {
   private volatile Set<? extends K> _keys;
   private volatile Collection<? extends V> _values;
   public static final AbstractMap.Companion Companion = new AbstractMap.Companion((DefaultConstructorMarker)null);

   public boolean containsKey(Object key) {
      return this.implFindEntry(key) != null;
   }

   public boolean containsValue(Object value) {
      Iterable $this$any$iv = (Iterable)this.entrySet();
      int $i$f$any = false;
      boolean var10000;
      if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
         var10000 = false;
      } else {
         Iterator var4 = $this$any$iv.iterator();

         while(true) {
            if (!var4.hasNext()) {
               var10000 = false;
               break;
            }

            Object element$iv = var4.next();
            Entry it = (Entry)element$iv;
            int var7 = false;
            if (Intrinsics.areEqual(it.getValue(), value)) {
               var10000 = true;
               break;
            }
         }
      }

      return var10000;
   }

   public final boolean containsEntry$kotlin_stdlib(@Nullable Entry<?, ?> entry) {
      if (!(entry instanceof Entry)) {
         return false;
      } else {
         Object key = entry.getKey();
         Object value = entry.getValue();
         boolean var6 = false;
         if (this == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
         } else {
            Object ourValue = ((Map)this).get(key);
            if (Intrinsics.areEqual(value, ourValue) ^ true) {
               return false;
            } else {
               if (ourValue == null) {
                  var6 = false;
                  if (this == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
                  }

                  if (!((Map)this).containsKey(key)) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   public boolean equals(@Nullable Object other) {
      if (other == (AbstractMap)this) {
         return true;
      } else if (!(other instanceof Map)) {
         return false;
      } else if (this.size() != ((Map)other).size()) {
         return false;
      } else {
         Iterable $this$all$iv = (Iterable)((Map)other).entrySet();
         int $i$f$all = false;
         boolean var10000;
         if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
            var10000 = true;
         } else {
            Iterator var4 = $this$all$iv.iterator();

            while(true) {
               if (!var4.hasNext()) {
                  var10000 = true;
                  break;
               }

               Object element$iv = var4.next();
               Entry it = (Entry)element$iv;
               int var7 = false;
               if (!this.containsEntry$kotlin_stdlib(it)) {
                  var10000 = false;
                  break;
               }
            }
         }

         return var10000;
      }
   }

   @Nullable
   public V get(Object key) {
      Entry var10000 = this.implFindEntry(key);
      return var10000 != null ? var10000.getValue() : null;
   }

   public int hashCode() {
      return this.entrySet().hashCode();
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public int getSize() {
      return this.entrySet().size();
   }

   @NotNull
   public Set<K> getKeys() {
      if (this._keys == null) {
         this._keys = (Set)(new AbstractSet<K>() {
            public boolean contains(Object element) {
               return AbstractMap.this.containsKey(element);
            }

            @NotNull
            public Iterator<K> iterator() {
               final Iterator entryIterator = AbstractMap.this.entrySet().iterator();
               return (Iterator)(new Iterator<K>() {
                  public boolean hasNext() {
                     return entryIterator.hasNext();
                  }

                  public K next() {
                     return ((Entry)entryIterator.next()).getKey();
                  }

                  public void remove() {
                     throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                  }
               });
            }

            public int getSize() {
               return AbstractMap.this.size();
            }
         });
      }

      Set var10000 = this._keys;
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return var10000;
   }

   @NotNull
   public String toString() {
      return CollectionsKt.joinToString$default((Iterable)this.entrySet(), (CharSequence)", ", (CharSequence)"{", (CharSequence)"}", 0, (CharSequence)null, (Function1)(new Function1<Entry<? extends K, ? extends V>, String>() {
         @NotNull
         public final String invoke(@NotNull Entry<? extends K, ? extends V> it) {
            Intrinsics.checkParameterIsNotNull(it, "it");
            return AbstractMap.this.toString(it);
         }
      }), 24, (Object)null);
   }

   private final String toString(Entry<? extends K, ? extends V> entry) {
      return this.toString(entry.getKey()) + "=" + this.toString(entry.getValue());
   }

   private final String toString(Object o) {
      return o == (AbstractMap)this ? "(this Map)" : String.valueOf(o);
   }

   @NotNull
   public Collection<V> getValues() {
      if (this._values == null) {
         this._values = (Collection)(new AbstractCollection<V>() {
            public boolean contains(Object element) {
               return AbstractMap.this.containsValue(element);
            }

            @NotNull
            public Iterator<V> iterator() {
               final Iterator entryIterator = AbstractMap.this.entrySet().iterator();
               return (Iterator)(new Iterator<V>() {
                  public boolean hasNext() {
                     return entryIterator.hasNext();
                  }

                  public V next() {
                     return ((Entry)entryIterator.next()).getValue();
                  }

                  public void remove() {
                     throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                  }
               });
            }

            public int getSize() {
               return AbstractMap.this.size();
            }
         });
      }

      Collection var10000 = this._values;
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return var10000;
   }

   private final Entry<K, V> implFindEntry(K key) {
      Iterable $this$firstOrNull$iv = (Iterable)this.entrySet();
      int $i$f$firstOrNull = false;
      Iterator var4 = $this$firstOrNull$iv.iterator();

      Object var10000;
      while(true) {
         if (var4.hasNext()) {
            Object element$iv = var4.next();
            Entry it = (Entry)element$iv;
            int var7 = false;
            if (!Intrinsics.areEqual(it.getKey(), key)) {
               continue;
            }

            var10000 = element$iv;
            break;
         }

         var10000 = null;
         break;
      }

      return (Entry)var10000;
   }

   protected AbstractMap() {
   }

   public abstract Set getEntries();

   public void clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public V put(K var1, V var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void putAll(Map<? extends K, ? extends V> var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public V remove(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010&\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0000¢\u0006\u0002\b\bJ\u001d\u0010\t\u001a\u00020\n2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\b\u000bJ\u001d\u0010\f\u001a\u00020\r2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\b\u000e¨\u0006\u000f"},
      d2 = {"Lkotlin/collections/AbstractMap$Companion;", "", "()V", "entryEquals", "", "e", "", "other", "entryEquals$kotlin_stdlib", "entryHashCode", "", "entryHashCode$kotlin_stdlib", "entryToString", "", "entryToString$kotlin_stdlib", "kotlin-stdlib"}
   )
   public static final class Companion {
      public final int entryHashCode$kotlin_stdlib(@NotNull Entry<?, ?> e) {
         Intrinsics.checkParameterIsNotNull(e, "e");
         boolean var2 = false;
         boolean var3 = false;
         int var5 = false;
         Object var10000 = e.getKey();
         int var6 = var10000 != null ? var10000.hashCode() : 0;
         Object var10001 = e.getValue();
         return var6 ^ (var10001 != null ? var10001.hashCode() : 0);
      }

      @NotNull
      public final String entryToString$kotlin_stdlib(@NotNull Entry<?, ?> e) {
         Intrinsics.checkParameterIsNotNull(e, "e");
         boolean var2 = false;
         boolean var3 = false;
         int var5 = false;
         return "" + e.getKey() + '=' + e.getValue();
      }

      public final boolean entryEquals$kotlin_stdlib(@NotNull Entry<?, ?> e, @Nullable Object other) {
         Intrinsics.checkParameterIsNotNull(e, "e");
         if (!(other instanceof Entry)) {
            return false;
         } else {
            return Intrinsics.areEqual(e.getKey(), ((Entry)other).getKey()) && Intrinsics.areEqual(e.getValue(), ((Entry)other).getValue());
         }
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
