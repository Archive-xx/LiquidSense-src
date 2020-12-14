package jdk.nashorn.internal.runtime.arrays;

import java.lang.invoke.MethodHandle;
import java.nio.Buffer;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;

public abstract class TypedArrayData<T extends Buffer> extends ContinuousArrayData {
   protected final T nb;

   protected TypedArrayData(T nb, int elementLength) {
      super((long)elementLength);
      this.nb = nb;
   }

   public final int getElementLength() {
      return (int)this.length();
   }

   public boolean isUnsigned() {
      return false;
   }

   public boolean isClamped() {
      return false;
   }

   public boolean canDelete(int index, boolean strict) {
      return false;
   }

   public boolean canDelete(long longIndex, boolean strict) {
      return false;
   }

   public TypedArrayData<T> copy() {
      throw new UnsupportedOperationException();
   }

   public Object[] asObjectArray() {
      throw new UnsupportedOperationException();
   }

   public ArrayData shiftLeft(int by) {
      throw new UnsupportedOperationException();
   }

   public ArrayData shiftRight(int by) {
      throw new UnsupportedOperationException();
   }

   public ArrayData ensure(long safeIndex) {
      return this;
   }

   public ArrayData shrink(long newLength) {
      throw new UnsupportedOperationException();
   }

   public final boolean has(int index) {
      return 0 <= index && (long)index < this.length();
   }

   public ArrayData delete(int index) {
      return this;
   }

   public ArrayData delete(long fromIndex, long toIndex) {
      return this;
   }

   public TypedArrayData<T> convert(Class<?> type) {
      throw new UnsupportedOperationException();
   }

   public Object pop() {
      throw new UnsupportedOperationException();
   }

   public ArrayData slice(long from, long to) {
      throw new UnsupportedOperationException();
   }

   protected abstract MethodHandle getGetElem();

   protected abstract MethodHandle getSetElem();

   public MethodHandle getElementGetter(Class<?> returnType, int programPoint) {
      MethodHandle getter = this.getContinuousElementGetter(this.getClass(), this.getGetElem(), returnType, programPoint);
      return getter != null ? Lookup.filterReturnType(getter, returnType) : getter;
   }

   public MethodHandle getElementSetter(Class<?> elementType) {
      return this.getContinuousElementSetter(this.getClass(), Lookup.filterArgumentType(this.getSetElem(), 2, elementType), elementType);
   }

   protected MethodHandle getContinuousElementSetter(Class<? extends ContinuousArrayData> clazz, MethodHandle setHas, Class<?> elementType) {
      MethodHandle mh = Lookup.filterArgumentType(setHas, 2, elementType);
      return Lookup.MH.asType(mh, mh.type().changeParameterType(0, clazz));
   }

   public GuardedInvocation findFastGetIndexMethod(Class<? extends ArrayData> clazz, CallSiteDescriptor desc, LinkRequest request) {
      GuardedInvocation inv = super.findFastGetIndexMethod(clazz, desc, request);
      return inv != null ? inv : null;
   }

   public GuardedInvocation findFastSetIndexMethod(Class<? extends ArrayData> clazz, CallSiteDescriptor desc, LinkRequest request) {
      GuardedInvocation inv = super.findFastSetIndexMethod(clazz, desc, request);
      return inv != null ? inv : null;
   }
}
