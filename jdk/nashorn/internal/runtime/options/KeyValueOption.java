package jdk.nashorn.internal.runtime.options;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class KeyValueOption extends Option<String> {
   protected Map<String, String> map;

   KeyValueOption(String value) {
      super(value);
      this.initialize();
   }

   Map<String, String> getValues() {
      return Collections.unmodifiableMap(this.map);
   }

   public boolean hasValue(String key) {
      return this.map != null && this.map.get(key) != null;
   }

   String getValue(String key) {
      if (this.map == null) {
         return null;
      } else {
         String val = (String)this.map.get(key);
         return "".equals(val) ? null : val;
      }
   }

   private void initialize() {
      if (this.getValue() != null) {
         this.map = new LinkedHashMap();
         StringTokenizer st = new StringTokenizer((String)this.getValue(), ",");

         while(st.hasMoreElements()) {
            String token = st.nextToken();
            String[] keyValue = token.split(":");
            if (keyValue.length == 1) {
               this.map.put(keyValue[0], "");
            } else {
               if (keyValue.length != 2) {
                  throw new IllegalArgumentException(token);
               }

               this.map.put(keyValue[0], keyValue[1]);
            }
         }

      }
   }
}
