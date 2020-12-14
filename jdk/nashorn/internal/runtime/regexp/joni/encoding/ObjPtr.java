package jdk.nashorn.internal.runtime.regexp.joni.encoding;

public final class ObjPtr<T> {
   public T p;

   public ObjPtr() {
      this((Object)null);
   }

   public ObjPtr(T p) {
      this.p = p;
   }
}
