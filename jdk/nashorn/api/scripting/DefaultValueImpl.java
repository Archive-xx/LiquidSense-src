package jdk.nashorn.api.scripting;

import jdk.nashorn.internal.runtime.JSType;

class DefaultValueImpl {
   private static final String[] DEFAULT_VALUE_FNS_NUMBER = new String[]{"valueOf", "toString"};
   private static final String[] DEFAULT_VALUE_FNS_STRING = new String[]{"toString", "valueOf"};

   static Object getDefaultValue(JSObject jsobj, Class<?> hint) throws UnsupportedOperationException {
      boolean isNumber = hint == null || hint == Number.class;
      String[] var3 = isNumber ? DEFAULT_VALUE_FNS_NUMBER : DEFAULT_VALUE_FNS_STRING;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String methodName = var3[var5];
         Object objMember = jsobj.getMember(methodName);
         if (objMember instanceof JSObject) {
            JSObject member = (JSObject)objMember;
            if (member.isFunction()) {
               Object value = member.call(jsobj);
               if (JSType.isPrimitive(value)) {
                  return value;
               }
            }
         }
      }

      throw new UnsupportedOperationException(isNumber ? "cannot.get.default.number" : "cannot.get.default.string");
   }
}
