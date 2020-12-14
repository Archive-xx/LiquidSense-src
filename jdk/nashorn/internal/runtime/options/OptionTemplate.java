package jdk.nashorn.internal.runtime.options;

import java.util.Locale;
import java.util.TimeZone;
import jdk.nashorn.internal.runtime.QuotedStringTokenizer;

public final class OptionTemplate implements Comparable<OptionTemplate> {
   private final String resource;
   private final String key;
   private final boolean isHelp;
   private final boolean isXHelp;
   private String name;
   private String shortName;
   private String params;
   private String type;
   private String defaultValue;
   private String dependency;
   private String conflict;
   private boolean isUndocumented;
   private String description;
   private boolean valueNextArg;
   private static final int LINE_BREAK = 64;

   OptionTemplate(String resource, String key, String value, boolean isHelp, boolean isXHelp) {
      this.resource = resource;
      this.key = key;
      this.isHelp = isHelp;
      this.isXHelp = isXHelp;
      this.parse(value);
   }

   public boolean isHelp() {
      return this.isHelp;
   }

   public boolean isXHelp() {
      return this.isXHelp;
   }

   public String getResource() {
      return this.resource;
   }

   public String getType() {
      return this.type;
   }

   public String getKey() {
      return this.key;
   }

   public String getDefaultValue() {
      String var1 = this.getType();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -2076227591:
         if (var1.equals("timezone")) {
            var2 = 2;
         }
         break;
      case -1097462182:
         if (var1.equals("locale")) {
            var2 = 3;
         }
         break;
      case 64711720:
         if (var1.equals("boolean")) {
            var2 = 0;
         }
         break;
      case 1958052158:
         if (var1.equals("integer")) {
            var2 = 1;
         }
      }

      switch(var2) {
      case 0:
         if (this.defaultValue == null) {
            this.defaultValue = "false";
         }
         break;
      case 1:
         if (this.defaultValue == null) {
            this.defaultValue = "0";
         }
         break;
      case 2:
         this.defaultValue = TimeZone.getDefault().getID();
         break;
      case 3:
         this.defaultValue = Locale.getDefault().toLanguageTag();
      }

      return this.defaultValue;
   }

   public String getDependency() {
      return this.dependency;
   }

   public String getConflict() {
      return this.conflict;
   }

   public boolean isUndocumented() {
      return this.isUndocumented;
   }

   public String getShortName() {
      return this.shortName;
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public boolean isValueNextArg() {
      return this.valueNextArg;
   }

   private static String strip(String value, char start, char end) {
      int len = value.length();
      return len > 1 && value.charAt(0) == start && value.charAt(len - 1) == end ? value.substring(1, len - 1) : null;
   }

   private void parse(String origValue) {
      String value = origValue.trim();

      try {
         value = strip(value, '{', '}');
         QuotedStringTokenizer keyValuePairs = new QuotedStringTokenizer(value, ",");

         while(keyValuePairs.hasMoreTokens()) {
            String keyValue = keyValuePairs.nextToken();
            QuotedStringTokenizer st = new QuotedStringTokenizer(keyValue, "=");
            String keyToken = st.nextToken();
            String arg = st.nextToken();
            byte var9 = -1;
            switch(keyToken.hashCode()) {
            case -2103299560:
               if (keyToken.equals("value_next_arg")) {
                  var9 = 9;
               }
               break;
            case -995427962:
               if (keyToken.equals("params")) {
                  var9 = 4;
               }
               break;
            case -580047918:
               if (keyToken.equals("conflict")) {
                  var9 = 8;
               }
               break;
            case -26291381:
               if (keyToken.equals("dependency")) {
                  var9 = 7;
               }
               break;
            case 3079825:
               if (keyToken.equals("desc")) {
                  var9 = 3;
               }
               break;
            case 3373707:
               if (keyToken.equals("name")) {
                  var9 = 1;
               }
               break;
            case 3575610:
               if (keyToken.equals("type")) {
                  var9 = 5;
               }
               break;
            case 572651336:
               if (keyToken.equals("is_undocumented")) {
                  var9 = 0;
               }
               break;
            case 1544803905:
               if (keyToken.equals("default")) {
                  var9 = 6;
               }
               break;
            case 1565793390:
               if (keyToken.equals("short_name")) {
                  var9 = 2;
               }
            }

            switch(var9) {
            case 0:
               this.isUndocumented = Boolean.parseBoolean(arg);
               break;
            case 1:
               if (!arg.startsWith("-")) {
                  throw new IllegalArgumentException(arg);
               }

               this.name = arg;
               break;
            case 2:
               if (!arg.startsWith("-")) {
                  throw new IllegalArgumentException(arg);
               }

               this.shortName = arg;
               break;
            case 3:
               this.description = arg;
               break;
            case 4:
               this.params = arg;
               break;
            case 5:
               this.type = arg.toLowerCase(Locale.ENGLISH);
               break;
            case 6:
               this.defaultValue = arg;
               break;
            case 7:
               this.dependency = arg;
               break;
            case 8:
               this.conflict = arg;
               break;
            case 9:
               this.valueNextArg = Boolean.parseBoolean(arg);
               break;
            default:
               throw new IllegalArgumentException(keyToken);
            }
         }

         if (this.type == null) {
            this.type = "boolean";
         }

         if (this.params == null && "boolean".equals(this.type)) {
            this.params = "[true|false]";
         }
      } catch (Exception var10) {
         throw new IllegalArgumentException(origValue);
      }

      if (this.name == null && this.shortName == null) {
         throw new IllegalArgumentException(origValue);
      }
   }

   boolean nameMatches(String aName) {
      return aName.equals(this.shortName) || aName.equals(this.name);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append('\t');
      if (this.shortName != null) {
         sb.append(this.shortName);
         if (this.name != null) {
            sb.append(", ");
         }
      }

      if (this.name != null) {
         sb.append(this.name);
      }

      if (this.description != null) {
         int indent = sb.length();
         sb.append(' ');
         sb.append('(');
         int pos = 0;
         char[] var4 = this.description.toCharArray();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            char c = var4[var6];
            sb.append(c);
            ++pos;
            if (pos >= 64 && Character.isWhitespace(c)) {
               pos = 0;
               sb.append("\n\t");

               for(int i = 0; i < indent; ++i) {
                  sb.append(' ');
               }
            }
         }

         sb.append(')');
      }

      if (this.params != null) {
         sb.append('\n');
         sb.append('\t');
         sb.append('\t');
         sb.append(Options.getMsg("nashorn.options.param")).append(": ");
         sb.append(this.params);
         sb.append("   ");
         Object def = this.getDefaultValue();
         if (def != null) {
            sb.append(Options.getMsg("nashorn.options.default")).append(": ");
            sb.append(this.getDefaultValue());
         }
      }

      return sb.toString();
   }

   public int compareTo(OptionTemplate o) {
      return this.getKey().compareTo(o.getKey());
   }
}
