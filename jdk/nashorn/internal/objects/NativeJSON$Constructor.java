package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;

final class NativeJSON$Constructor extends ScriptObject {
   private Object parse = ScriptFunction.createBuiltin("parse", "parse");
   private Object stringify = ScriptFunction.createBuiltin("stringify", "stringify");
   private static final PropertyMap $nasgenmap$;

   public Object G$parse() {
      return this.parse;
   }

   public void S$parse(Object var1) {
      this.parse = var1;
   }

   public Object G$stringify() {
      return this.stringify;
   }

   public void S$stringify(Object var1) {
      this.stringify = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(2);
      var10000.add(AccessorProperty.create("parse", 2, "G$parse", "S$parse"));
      var10000.add(AccessorProperty.create("stringify", 2, "G$stringify", "S$stringify"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeJSON$Constructor() {
      super($nasgenmap$);
   }

   public String getClassName() {
      return "JSON";
   }
}
