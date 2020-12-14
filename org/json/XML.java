package org.json;

import java.util.Iterator;
import java.util.Map.Entry;

public class XML {
   public static final Character AMP = '&';
   public static final Character APOS = '\'';
   public static final Character BANG = '!';
   public static final Character EQ = '=';
   public static final Character GT = '>';
   public static final Character LT = '<';
   public static final Character QUEST = '?';
   public static final Character QUOT = '"';
   public static final Character SLASH = '/';

   private static Iterable<Integer> codePointIterator(final String string) {
      return new Iterable<Integer>() {
         public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {
               private int nextIndex = 0;
               private int length = string.length();

               public boolean hasNext() {
                  return this.nextIndex < this.length;
               }

               public Integer next() {
                  int result = string.codePointAt(this.nextIndex);
                  this.nextIndex += Character.charCount(result);
                  return result;
               }

               public void remove() {
                  throw new UnsupportedOperationException();
               }
            };
         }
      };
   }

   public static String escape(String string) {
      StringBuilder sb = new StringBuilder(string.length());
      Iterator var2 = codePointIterator(string).iterator();

      while(var2.hasNext()) {
         int cp = (Integer)var2.next();
         switch(cp) {
         case 34:
            sb.append("&quot;");
            break;
         case 38:
            sb.append("&amp;");
            break;
         case 39:
            sb.append("&apos;");
            break;
         case 60:
            sb.append("&lt;");
            break;
         case 62:
            sb.append("&gt;");
            break;
         default:
            if (mustEscape(cp)) {
               sb.append("&#x");
               sb.append(Integer.toHexString(cp));
               sb.append(';');
            } else {
               sb.appendCodePoint(cp);
            }
         }
      }

      return sb.toString();
   }

   private static boolean mustEscape(int cp) {
      return Character.isISOControl(cp) && cp != 9 && cp != 10 && cp != 13 || (cp < 32 || cp > 55295) && (cp < 57344 || cp > 65533) && (cp < 65536 || cp > 1114111);
   }

   public static String unescape(String string) {
      StringBuilder sb = new StringBuilder(string.length());
      int i = 0;

      for(int length = string.length(); i < length; ++i) {
         char c = string.charAt(i);
         if (c == '&') {
            int semic = string.indexOf(59, i);
            if (semic > i) {
               String entity = string.substring(i + 1, semic);
               sb.append(XMLTokener.unescapeEntity(entity));
               i += entity.length() + 1;
            } else {
               sb.append(c);
            }
         } else {
            sb.append(c);
         }
      }

      return sb.toString();
   }

   public static void noSpace(String string) throws JSONException {
      int length = string.length();
      if (length == 0) {
         throw new JSONException("Empty string.");
      } else {
         for(int i = 0; i < length; ++i) {
            if (Character.isWhitespace(string.charAt(i))) {
               throw new JSONException("'" + string + "' contains a space character.");
            }
         }

      }
   }

   private static boolean parse(XMLTokener x, JSONObject context, String name, boolean keepStrings) throws JSONException {
      JSONObject jsonobject = null;
      Object token = x.nextToken();
      String string;
      if (token == BANG) {
         char c = x.next();
         if (c == '-') {
            if (x.next() == '-') {
               x.skipPast("-->");
               return false;
            }

            x.back();
         } else if (c == '[') {
            token = x.nextToken();
            if ("CDATA".equals(token) && x.next() == '[') {
               string = x.nextCDATA();
               if (string.length() > 0) {
                  context.accumulate("content", string);
               }

               return false;
            }

            throw x.syntaxError("Expected 'CDATA['");
         }

         int i = 1;

         do {
            token = x.nextMeta();
            if (token == null) {
               throw x.syntaxError("Missing '>' after '<!'.");
            }

            if (token == LT) {
               ++i;
            } else if (token == GT) {
               --i;
            }
         } while(i > 0);

         return false;
      } else if (token == QUEST) {
         x.skipPast("?>");
         return false;
      } else if (token == SLASH) {
         token = x.nextToken();
         if (name == null) {
            throw x.syntaxError("Mismatched close tag " + token);
         } else if (!token.equals(name)) {
            throw x.syntaxError("Mismatched " + name + " and " + token);
         } else if (x.nextToken() != GT) {
            throw x.syntaxError("Misshaped close tag");
         } else {
            return true;
         }
      } else if (token instanceof Character) {
         throw x.syntaxError("Misshaped tag");
      } else {
         String tagName = (String)token;
         token = null;
         jsonobject = new JSONObject();

         while(true) {
            if (token == null) {
               token = x.nextToken();
            }

            if (!(token instanceof String)) {
               if (token == SLASH) {
                  if (x.nextToken() != GT) {
                     throw x.syntaxError("Misshaped tag");
                  }

                  if (jsonobject.length() > 0) {
                     context.accumulate(tagName, jsonobject);
                  } else {
                     context.accumulate(tagName, "");
                  }

                  return false;
               }

               if (token != GT) {
                  throw x.syntaxError("Misshaped tag");
               }

               while(true) {
                  token = x.nextContent();
                  if (token == null) {
                     if (tagName != null) {
                        throw x.syntaxError("Unclosed tag " + tagName);
                     }

                     return false;
                  }

                  if (token instanceof String) {
                     string = (String)token;
                     if (string.length() > 0) {
                        jsonobject.accumulate("content", keepStrings ? string : stringToValue(string));
                     }
                  } else if (token == LT && parse(x, jsonobject, tagName, keepStrings)) {
                     if (jsonobject.length() == 0) {
                        context.accumulate(tagName, "");
                     } else if (jsonobject.length() == 1 && jsonobject.opt("content") != null) {
                        context.accumulate(tagName, jsonobject.opt("content"));
                     } else {
                        context.accumulate(tagName, jsonobject);
                     }

                     return false;
                  }
               }
            }

            string = (String)token;
            token = x.nextToken();
            if (token == EQ) {
               token = x.nextToken();
               if (!(token instanceof String)) {
                  throw x.syntaxError("Missing value");
               }

               jsonobject.accumulate(string, keepStrings ? (String)token : stringToValue((String)token));
               token = null;
            } else {
               jsonobject.accumulate(string, "");
            }
         }
      }
   }

   public static Object stringToValue(String string) {
      return JSONObject.stringToValue(string);
   }

   public static JSONObject toJSONObject(String string) throws JSONException {
      return toJSONObject(string, false);
   }

   public static JSONObject toJSONObject(String string, boolean keepStrings) throws JSONException {
      JSONObject jo = new JSONObject();
      XMLTokener x = new XMLTokener(string);

      while(x.more() && x.skipPast("<")) {
         parse(x, jo, (String)null, keepStrings);
      }

      return jo;
   }

   public static String toString(Object object) throws JSONException {
      return toString(object, (String)null);
   }

   public static String toString(Object object, String tagName) throws JSONException {
      StringBuilder sb = new StringBuilder();
      JSONArray ja;
      Iterator var6;
      if (object instanceof JSONObject) {
         if (tagName != null) {
            sb.append('<');
            sb.append(tagName);
            sb.append('>');
         }

         JSONObject jo = (JSONObject)object;
         var6 = jo.entrySet().iterator();

         while(true) {
            while(true) {
               while(var6.hasNext()) {
                  Entry<String, ?> entry = (Entry)var6.next();
                  String key = (String)entry.getKey();
                  Object value = entry.getValue();
                  if (value == null) {
                     value = "";
                  } else if (value.getClass().isArray()) {
                     value = new JSONArray(value);
                  }

                  if ("content".equals(key)) {
                     if (value instanceof JSONArray) {
                        ja = (JSONArray)value;
                        int i = 0;

                        for(Iterator var15 = ja.iterator(); var15.hasNext(); ++i) {
                           Object val = var15.next();
                           if (i > 0) {
                              sb.append('\n');
                           }

                           sb.append(escape(val.toString()));
                        }
                     } else {
                        sb.append(escape(value.toString()));
                     }
                  } else if (value instanceof JSONArray) {
                     ja = (JSONArray)value;
                     Iterator var10 = ja.iterator();

                     while(var10.hasNext()) {
                        Object val = var10.next();
                        if (val instanceof JSONArray) {
                           sb.append('<');
                           sb.append(key);
                           sb.append('>');
                           sb.append(toString(val));
                           sb.append("</");
                           sb.append(key);
                           sb.append('>');
                        } else {
                           sb.append(toString(val, key));
                        }
                     }
                  } else if ("".equals(value)) {
                     sb.append('<');
                     sb.append(key);
                     sb.append("/>");
                  } else {
                     sb.append(toString(value, key));
                  }
               }

               if (tagName != null) {
                  sb.append("</");
                  sb.append(tagName);
                  sb.append('>');
               }

               return sb.toString();
            }
         }
      } else if (object != null && (object instanceof JSONArray || object.getClass().isArray())) {
         if (object.getClass().isArray()) {
            ja = new JSONArray(object);
         } else {
            ja = (JSONArray)object;
         }

         var6 = ja.iterator();

         while(var6.hasNext()) {
            Object val = var6.next();
            sb.append(toString(val, tagName == null ? "array" : tagName));
         }

         return sb.toString();
      } else {
         String string = object == null ? "null" : escape(object.toString());
         return tagName == null ? "\"" + string + "\"" : (string.length() == 0 ? "<" + tagName + "/>" : "<" + tagName + ">" + string + "</" + tagName + ">");
      }
   }
}
