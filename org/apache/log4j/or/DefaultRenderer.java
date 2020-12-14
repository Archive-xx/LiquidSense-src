package org.apache.log4j.or;

class DefaultRenderer implements ObjectRenderer {
   public String doRender(Object o) {
      try {
         return o.toString();
      } catch (Exception var3) {
         return var3.toString();
      }
   }
}
