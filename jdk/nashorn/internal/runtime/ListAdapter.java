package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.util.AbstractList;
import java.util.Deque;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.concurrent.Callable;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

public class ListAdapter extends AbstractList<Object> implements RandomAccess, Deque<Object> {
   private static final Callable<MethodHandle> ADD_INVOKER_CREATOR;
   private static final Object PUSH;
   private static final Object UNSHIFT;
   private static final Callable<MethodHandle> REMOVE_INVOKER_CREATOR;
   private static final Object POP;
   private static final Object SHIFT;
   private static final Object SPLICE_ADD;
   private static final Callable<MethodHandle> SPLICE_ADD_INVOKER_CREATOR;
   private static final Object SPLICE_REMOVE;
   private static final Callable<MethodHandle> SPLICE_REMOVE_INVOKER_CREATOR;
   final JSObject obj;
   private final Global global;

   ListAdapter(JSObject obj, Global global) {
      if (global == null) {
         throw new IllegalStateException(ECMAErrors.getMessage("list.adapter.null.global"));
      } else {
         this.obj = obj;
         this.global = global;
      }
   }

   public static ListAdapter create(Object obj) {
      Global global = Context.getGlobal();
      return new ListAdapter(getJSObject(obj, global), global);
   }

   private static JSObject getJSObject(Object obj, Global global) {
      if (obj instanceof ScriptObject) {
         return (JSObject)ScriptObjectMirror.wrap(obj, global);
      } else if (obj instanceof JSObject) {
         return (JSObject)obj;
      } else {
         throw new IllegalArgumentException("ScriptObject or JSObject expected");
      }
   }

   public final Object get(int index) {
      this.checkRange(index);
      return this.getAt(index);
   }

   private Object getAt(int index) {
      return this.obj.getSlot(index);
   }

   public Object set(int index, Object element) {
      this.checkRange(index);
      Object prevValue = this.getAt(index);
      this.obj.setSlot(index, element);
      return prevValue;
   }

   private void checkRange(int index) {
      if (index < 0 || index >= this.size()) {
         throw invalidIndex(index);
      }
   }

   public int size() {
      return JSType.toInt32(this.obj.getMember("length"));
   }

   public final void push(Object e) {
      this.addFirst(e);
   }

   public final boolean add(Object e) {
      this.addLast(e);
      return true;
   }

   public final void addFirst(Object e) {
      try {
         this.getDynamicInvoker(UNSHIFT, ADD_INVOKER_CREATOR).invokeExact(this.getFunction("unshift"), this.obj, e);
      } catch (Error | RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RuntimeException(var4);
      }
   }

   public final void addLast(Object e) {
      try {
         this.getDynamicInvoker(PUSH, ADD_INVOKER_CREATOR).invokeExact(this.getFunction("push"), this.obj, e);
      } catch (Error | RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RuntimeException(var4);
      }
   }

   public final void add(int index, Object e) {
      try {
         if (index < 0) {
            throw invalidIndex(index);
         } else {
            if (index == 0) {
               this.addFirst(e);
            } else {
               int size = this.size();
               if (index < size) {
                  this.getDynamicInvoker(SPLICE_ADD, SPLICE_ADD_INVOKER_CREATOR).invokeExact(this.obj.getMember("splice"), this.obj, index, 0, e);
               } else {
                  if (index != size) {
                     throw invalidIndex(index);
                  }

                  this.addLast(e);
               }
            }

         }
      } catch (Error | RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RuntimeException(var5);
      }
   }

   private Object getFunction(String name) {
      Object fn = this.obj.getMember(name);
      if (!Bootstrap.isCallable(fn)) {
         throw new UnsupportedOperationException("The script object doesn't have a function named " + name);
      } else {
         return fn;
      }
   }

   private static IndexOutOfBoundsException invalidIndex(int index) {
      return new IndexOutOfBoundsException(String.valueOf(index));
   }

   public final boolean offer(Object e) {
      return this.offerLast(e);
   }

   public final boolean offerFirst(Object e) {
      this.addFirst(e);
      return true;
   }

   public final boolean offerLast(Object e) {
      this.addLast(e);
      return true;
   }

   public final Object pop() {
      return this.removeFirst();
   }

   public final Object remove() {
      return this.removeFirst();
   }

   public final Object removeFirst() {
      this.checkNonEmpty();
      return this.invokeShift();
   }

   public final Object removeLast() {
      this.checkNonEmpty();
      return this.invokePop();
   }

   private void checkNonEmpty() {
      if (this.isEmpty()) {
         throw new NoSuchElementException();
      }
   }

   public final Object remove(int index) {
      if (index < 0) {
         throw invalidIndex(index);
      } else if (index == 0) {
         return this.invokeShift();
      } else {
         int maxIndex = this.size() - 1;
         if (index < maxIndex) {
            Object prevValue = this.get(index);
            this.invokeSpliceRemove(index, 1);
            return prevValue;
         } else if (index == maxIndex) {
            return this.invokePop();
         } else {
            throw invalidIndex(index);
         }
      }
   }

   private Object invokeShift() {
      try {
         return this.getDynamicInvoker(SHIFT, REMOVE_INVOKER_CREATOR).invokeExact(this.getFunction("shift"), this.obj);
      } catch (Error | RuntimeException var2) {
         throw var2;
      } catch (Throwable var3) {
         throw new RuntimeException(var3);
      }
   }

   private Object invokePop() {
      try {
         return this.getDynamicInvoker(POP, REMOVE_INVOKER_CREATOR).invokeExact(this.getFunction("pop"), this.obj);
      } catch (Error | RuntimeException var2) {
         throw var2;
      } catch (Throwable var3) {
         throw new RuntimeException(var3);
      }
   }

   protected final void removeRange(int fromIndex, int toIndex) {
      this.invokeSpliceRemove(fromIndex, toIndex - fromIndex);
   }

   private void invokeSpliceRemove(int fromIndex, int count) {
      try {
         this.getDynamicInvoker(SPLICE_REMOVE, SPLICE_REMOVE_INVOKER_CREATOR).invokeExact(this.getFunction("splice"), this.obj, fromIndex, count);
      } catch (Error | RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RuntimeException(var5);
      }
   }

   public final Object poll() {
      return this.pollFirst();
   }

   public final Object pollFirst() {
      return this.isEmpty() ? null : this.invokeShift();
   }

   public final Object pollLast() {
      return this.isEmpty() ? null : this.invokePop();
   }

   public final Object peek() {
      return this.peekFirst();
   }

   public final Object peekFirst() {
      return this.isEmpty() ? null : this.get(0);
   }

   public final Object peekLast() {
      return this.isEmpty() ? null : this.get(this.size() - 1);
   }

   public final Object element() {
      return this.getFirst();
   }

   public final Object getFirst() {
      this.checkNonEmpty();
      return this.get(0);
   }

   public final Object getLast() {
      this.checkNonEmpty();
      return this.get(this.size() - 1);
   }

   public final Iterator<Object> descendingIterator() {
      final ListIterator<Object> it = this.listIterator(this.size());
      return new Iterator<Object>() {
         public boolean hasNext() {
            return it.hasPrevious();
         }

         public Object next() {
            return it.previous();
         }

         public void remove() {
            it.remove();
         }
      };
   }

   public final boolean removeFirstOccurrence(Object o) {
      return removeOccurrence(o, this.iterator());
   }

   public final boolean removeLastOccurrence(Object o) {
      return removeOccurrence(o, this.descendingIterator());
   }

   private static boolean removeOccurrence(Object o, Iterator<Object> it) {
      while(true) {
         if (it.hasNext()) {
            if (!Objects.equals(o, it.next())) {
               continue;
            }

            it.remove();
            return true;
         }

         return false;
      }
   }

   private static Callable<MethodHandle> invokerCreator(final Class<?> rtype, final Class<?>... ptypes) {
      return new Callable<MethodHandle>() {
         public MethodHandle call() {
            return Bootstrap.createDynamicInvoker("dyn:call", rtype, ptypes);
         }
      };
   }

   private MethodHandle getDynamicInvoker(Object key, Callable<MethodHandle> creator) {
      return this.global.getDynamicInvoker(key, creator);
   }

   static {
      ADD_INVOKER_CREATOR = invokerCreator(Void.TYPE, Object.class, JSObject.class, Object.class);
      PUSH = new Object();
      UNSHIFT = new Object();
      REMOVE_INVOKER_CREATOR = invokerCreator(Object.class, Object.class, JSObject.class);
      POP = new Object();
      SHIFT = new Object();
      SPLICE_ADD = new Object();
      SPLICE_ADD_INVOKER_CREATOR = invokerCreator(Void.TYPE, Object.class, JSObject.class, Integer.TYPE, Integer.TYPE, Object.class);
      SPLICE_REMOVE = new Object();
      SPLICE_REMOVE_INVOKER_CREATOR = invokerCreator(Void.TYPE, Object.class, JSObject.class, Integer.TYPE, Integer.TYPE);
   }
}
