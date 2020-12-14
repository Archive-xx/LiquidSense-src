package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.regexp.RegExpResult;

public final class NativeRegExpExecResult extends ScriptObject {
   public Object index;
   public Object input;
   private static PropertyMap $nasgenmap$;

   NativeRegExpExecResult(RegExpResult result, Global global) {
      super(global.getArrayPrototype(), $nasgenmap$);
      this.setIsArray();
      this.setArray(ArrayData.allocate((Object[])result.getGroups().clone()));
      this.index = result.getIndex();
      this.input = result.getInput();
   }

   public String getClassName() {
      return "Array";
   }

   public static Object length(Object self) {
      return self instanceof ScriptObject ? (double)JSType.toUint32((double)((ScriptObject)self).getArray().length()) : 0;
   }

   public static void length(Object self, Object length) {
      if (self instanceof ScriptObject) {
         ((ScriptObject)self).setLength(NativeArray.validLength(length));
      }

   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      ArrayList var10000 = new ArrayList(3);
      var10000.add(AccessorProperty.create("index", 0, "G$index", "S$index"));
      var10000.add(AccessorProperty.create("input", 0, "G$input", "S$input"));
      var10000.add(AccessorProperty.create("length", 6, "length", "length"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   public Object G$index() {
      return this.index;
   }

   public void S$index(Object var1) {
      this.index = var1;
   }

   public Object G$input() {
      return this.input;
   }

   public void S$input(Object var1) {
      this.input = var1;
   }
}
