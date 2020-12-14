package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

public final class NativeRangeError extends ScriptObject {
   public Object instMessage;
   public Object nashornException;
   private static PropertyMap $nasgenmap$;

   private NativeRangeError(Object msg, ScriptObject proto, PropertyMap map) {
      super(proto, map);
      if (msg != ScriptRuntime.UNDEFINED) {
         this.instMessage = JSType.toString(msg);
      } else {
         this.delete("message", false);
      }

      NativeError.initException(this);
   }

   NativeRangeError(Object msg, Global global) {
      this(msg, global.getRangeErrorPrototype(), $nasgenmap$);
   }

   private NativeRangeError(Object msg) {
      this(msg, Global.instance());
   }

   public String getClassName() {
      return "Error";
   }

   public static NativeRangeError constructor(boolean newObj, Object self, Object msg) {
      return new NativeRangeError(msg);
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      ArrayList var10000 = new ArrayList(2);
      var10000.add(AccessorProperty.create("message", 2, "G$instMessage", "S$instMessage"));
      var10000.add(AccessorProperty.create("nashornException", 2, "G$nashornException", "S$nashornException"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   public Object G$instMessage() {
      return this.instMessage;
   }

   public void S$instMessage(Object var1) {
      this.instMessage = var1;
   }

   public Object G$nashornException() {
      return this.nashornException;
   }

   public void S$nashornException(Object var1) {
      this.nashornException = var1;
   }
}
