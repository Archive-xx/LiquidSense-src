package kotlin.jvm.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class SpreadBuilder {
   private final ArrayList<Object> list;

   public SpreadBuilder(int size) {
      this.list = new ArrayList(size);
   }

   public void addSpread(Object container) {
      if (container != null) {
         if (container instanceof Object[]) {
            Object[] array = (Object[])((Object[])container);
            if (array.length > 0) {
               this.list.ensureCapacity(this.list.size() + array.length);
               Collections.addAll(this.list, array);
            }
         } else if (container instanceof Collection) {
            this.list.addAll((Collection)container);
         } else {
            Iterator iterator;
            if (container instanceof Iterable) {
               iterator = ((Iterable)container).iterator();

               while(iterator.hasNext()) {
                  Object element = iterator.next();
                  this.list.add(element);
               }
            } else {
               if (!(container instanceof Iterator)) {
                  throw new UnsupportedOperationException("Don't know how to spread " + container.getClass());
               }

               iterator = (Iterator)container;

               while(iterator.hasNext()) {
                  this.list.add(iterator.next());
               }
            }
         }

      }
   }

   public int size() {
      return this.list.size();
   }

   public void add(Object element) {
      this.list.add(element);
   }

   public Object[] toArray(Object[] a) {
      return this.list.toArray(a);
   }
}
