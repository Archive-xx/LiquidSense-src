package jdk.nashorn.internal.parser;

import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.codegen.ObjectClassGenerator;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.JSErrorType;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.SpillProperty;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import jdk.nashorn.internal.scripts.JD;
import jdk.nashorn.internal.scripts.JO;

public class JSONParser {
   private final String source;
   private final Global global;
   private final boolean dualFields;
   final int length;
   int pos = 0;
   private static final int EOF = -1;
   private static final String TRUE = "true";
   private static final String FALSE = "false";
   private static final String NULL = "null";
   private static final int STATE_EMPTY = 0;
   private static final int STATE_ELEMENT_PARSED = 1;
   private static final int STATE_COMMA_PARSED = 2;

   public JSONParser(String source, Global global, boolean dualFields) {
      this.source = source;
      this.global = global;
      this.length = source.length();
      this.dualFields = dualFields;
   }

   public static String quote(String value) {
      StringBuilder product = new StringBuilder();
      product.append("\"");
      char[] var2 = value.toCharArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char ch = var2[var4];
         switch(ch) {
         case '\b':
            product.append("\\b");
            break;
         case '\t':
            product.append("\\t");
            break;
         case '\n':
            product.append("\\n");
            break;
         case '\f':
            product.append("\\f");
            break;
         case '\r':
            product.append("\\r");
            break;
         case '"':
            product.append("\\\"");
            break;
         case '\\':
            product.append("\\\\");
            break;
         default:
            if (ch < ' ') {
               product.append(Lexer.unicodeEscape(ch));
            } else {
               product.append(ch);
            }
         }
      }

      product.append("\"");
      return product.toString();
   }

   public Object parse() {
      Object value = this.parseLiteral();
      this.skipWhiteSpace();
      if (this.pos < this.length) {
         throw this.expectedError(this.pos, "eof", toString(this.peek()));
      } else {
         return value;
      }
   }

   private Object parseLiteral() {
      this.skipWhiteSpace();
      int c = this.peek();
      if (c == -1) {
         throw this.expectedError(this.pos, "json literal", "eof");
      } else {
         switch(c) {
         case 34:
            return this.parseString();
         case 91:
            return this.parseArray();
         case 102:
            return this.parseKeyword("false", Boolean.FALSE);
         case 110:
            return this.parseKeyword("null", (Object)null);
         case 116:
            return this.parseKeyword("true", Boolean.TRUE);
         case 123:
            return this.parseObject();
         default:
            if (!this.isDigit(c) && c != 45) {
               if (c == 46) {
                  throw this.numberError(this.pos);
               } else {
                  throw this.expectedError(this.pos, "json literal", toString(c));
               }
            } else {
               return this.parseNumber();
            }
         }
      }
   }

   private Object parseObject() {
      PropertyMap propertyMap = this.dualFields ? JD.getInitialMap() : JO.getInitialMap();
      ArrayData arrayData = ArrayData.EMPTY_ARRAY;
      ArrayList<Object> values = new ArrayList();
      int state = 0;

      assert this.peek() == 123;

      ++this.pos;

      while(this.pos < this.length) {
         this.skipWhiteSpace();
         int c = this.peek();
         switch(c) {
         case 34:
            if (state == 1) {
               throw this.expectedError(this.pos - 1, ", or }", toString(c));
            }

            String id = this.parseString();
            this.expectColon();
            Object value = this.parseLiteral();
            int index = ArrayIndex.getArrayIndex(id);
            if (ArrayIndex.isValidArrayIndex(index)) {
               arrayData = addArrayElement(arrayData, index, value);
            } else {
               propertyMap = this.addObjectProperty(propertyMap, values, id, value);
            }

            state = 1;
            break;
         case 44:
            if (state != 1) {
               throw this.error(AbstractParser.message("trailing.comma.in.json"), this.pos);
            }

            state = 2;
            ++this.pos;
            break;
         case 125:
            if (state == 2) {
               throw this.error(AbstractParser.message("trailing.comma.in.json"), this.pos);
            }

            ++this.pos;
            return this.createObject(propertyMap, values, arrayData);
         default:
            throw this.expectedError(this.pos, ", or }", toString(c));
         }
      }

      throw this.expectedError(this.pos, ", or }", "eof");
   }

   private static ArrayData addArrayElement(ArrayData arrayData, int index, Object value) {
      long oldLength = arrayData.length();
      long longIndex = ArrayIndex.toLongIndex(index);
      ArrayData newArrayData = arrayData;
      if (longIndex >= oldLength) {
         newArrayData = arrayData.ensure(longIndex);
         if (longIndex > oldLength) {
            newArrayData = newArrayData.delete(oldLength, longIndex - 1L);
         }
      }

      return newArrayData.set(index, value, false);
   }

   private PropertyMap addObjectProperty(PropertyMap propertyMap, List<Object> values, String id, Object value) {
      Property oldProperty = propertyMap.findProperty(id);
      Class type;
      short flags;
      if (this.dualFields) {
         type = getType(value);
         flags = 2048;
      } else {
         type = Object.class;
         flags = 0;
      }

      PropertyMap newMap;
      if (oldProperty != null) {
         values.set(oldProperty.getSlot(), value);
         newMap = propertyMap.replaceProperty(oldProperty, new SpillProperty(id, flags, oldProperty.getSlot(), type));
      } else {
         values.add(value);
         newMap = propertyMap.addProperty(new SpillProperty(id, flags, propertyMap.size(), type));
      }

      return newMap;
   }

   private Object createObject(PropertyMap propertyMap, List<Object> values, ArrayData arrayData) {
      long[] primitiveSpill = this.dualFields ? new long[values.size()] : null;
      Object[] objectSpill = new Object[values.size()];
      Property[] var6 = propertyMap.getProperties();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Property property = var6[var8];
         if (this.dualFields && property.getType() != Object.class) {
            primitiveSpill[property.getSlot()] = ObjectClassGenerator.pack((Number)values.get(property.getSlot()));
         } else {
            objectSpill[property.getSlot()] = values.get(property.getSlot());
         }
      }

      ScriptObject object = this.dualFields ? new JD(propertyMap, primitiveSpill, objectSpill) : new JO(propertyMap, (long[])null, objectSpill);
      ((ScriptObject)object).setInitialProto(this.global.getObjectPrototype());
      ((ScriptObject)object).setArray(arrayData);
      return object;
   }

   private static Class<?> getType(Object value) {
      if (value instanceof Integer) {
         return Integer.TYPE;
      } else {
         return value instanceof Double ? Double.TYPE : Object.class;
      }
   }

   private void expectColon() {
      this.skipWhiteSpace();
      int n = this.next();
      if (n != 58) {
         throw this.expectedError(this.pos - 1, ":", toString(n));
      }
   }

   private Object parseArray() {
      ArrayData arrayData = ArrayData.EMPTY_ARRAY;
      int state = 0;

      assert this.peek() == 91;

      ++this.pos;

      while(this.pos < this.length) {
         this.skipWhiteSpace();
         int c = this.peek();
         switch(c) {
         case 44:
            if (state != 1) {
               throw this.error(AbstractParser.message("trailing.comma.in.json"), this.pos);
            }

            state = 2;
            ++this.pos;
            break;
         case 93:
            if (state == 2) {
               throw this.error(AbstractParser.message("trailing.comma.in.json"), this.pos);
            }

            ++this.pos;
            return this.global.wrapAsObject(arrayData);
         default:
            if (state == 1) {
               throw this.expectedError(this.pos, ", or ]", toString(c));
            }

            long index = arrayData.length();
            arrayData = arrayData.ensure(index).set((int)index, this.parseLiteral(), true);
            state = 1;
         }
      }

      throw this.expectedError(this.pos, ", or ]", "eof");
   }

   private String parseString() {
      int start = ++this.pos;
      StringBuilder sb = null;

      while(this.pos < this.length) {
         int c = this.next();
         if (c <= 31) {
            throw this.syntaxError(this.pos, "String contains control character");
         }

         if (c == 92) {
            if (sb == null) {
               sb = new StringBuilder(this.pos - start + 16);
            }

            sb.append(this.source, start, this.pos - 1);
            sb.append(this.parseEscapeSequence());
            start = this.pos;
         } else if (c == 34) {
            if (sb != null) {
               sb.append(this.source, start, this.pos - 1);
               return sb.toString();
            }

            return this.source.substring(start, this.pos - 1);
         }
      }

      throw this.error(Lexer.message("missing.close.quote"), this.pos, this.length);
   }

   private char parseEscapeSequence() {
      int c = this.next();
      switch(c) {
      case 34:
         return '"';
      case 47:
         return '/';
      case 92:
         return '\\';
      case 98:
         return '\b';
      case 102:
         return '\f';
      case 110:
         return '\n';
      case 114:
         return '\r';
      case 116:
         return '\t';
      case 117:
         return this.parseUnicodeEscape();
      default:
         throw this.error(Lexer.message("invalid.escape.char"), this.pos - 1, this.length);
      }
   }

   private char parseUnicodeEscape() {
      return (char)(this.parseHexDigit() << 12 | this.parseHexDigit() << 8 | this.parseHexDigit() << 4 | this.parseHexDigit());
   }

   private int parseHexDigit() {
      int c = this.next();
      if (c >= 48 && c <= 57) {
         return c - 48;
      } else if (c >= 65 && c <= 70) {
         return c + 10 - 65;
      } else if (c >= 97 && c <= 102) {
         return c + 10 - 97;
      } else {
         throw this.error(Lexer.message("invalid.hex"), this.pos - 1, this.length);
      }
   }

   private boolean isDigit(int c) {
      return c >= 48 && c <= 57;
   }

   private void skipDigits() {
      while(true) {
         if (this.pos < this.length) {
            int c = this.peek();
            if (this.isDigit(c)) {
               ++this.pos;
               continue;
            }
         }

         return;
      }
   }

   private Number parseNumber() {
      int start = this.pos;
      int c = this.next();
      if (c == 45) {
         c = this.next();
      }

      if (!this.isDigit(c)) {
         throw this.numberError(start);
      } else {
         if (c != 48) {
            this.skipDigits();
         }

         if (this.peek() == 46) {
            ++this.pos;
            if (!this.isDigit(this.next())) {
               throw this.numberError(this.pos - 1);
            }

            this.skipDigits();
         }

         c = this.peek();
         if (c == 101 || c == 69) {
            ++this.pos;
            c = this.next();
            if (c == 45 || c == 43) {
               c = this.next();
            }

            if (!this.isDigit(c)) {
               throw this.numberError(this.pos - 1);
            }

            this.skipDigits();
         }

         double d = Double.parseDouble(this.source.substring(start, this.pos));
         return (Number)(JSType.isRepresentableAsInt(d) ? (int)d : d);
      }
   }

   private Object parseKeyword(String keyword, Object value) {
      if (!this.source.regionMatches(this.pos, keyword, 0, keyword.length())) {
         throw this.expectedError(this.pos, "json literal", "ident");
      } else {
         this.pos += keyword.length();
         return value;
      }
   }

   private int peek() {
      return this.pos >= this.length ? -1 : this.source.charAt(this.pos);
   }

   private int next() {
      int next = this.peek();
      ++this.pos;
      return next;
   }

   private void skipWhiteSpace() {
      while(this.pos < this.length) {
         switch(this.peek()) {
         case 9:
         case 10:
         case 13:
         case 32:
            ++this.pos;
            break;
         default:
            return;
         }
      }

   }

   private static String toString(int c) {
      return c == -1 ? "eof" : String.valueOf((char)c);
   }

   ParserException error(String message, int start, int length) throws ParserException {
      long token = Token.toDesc(TokenType.STRING, start, length);
      int pos = Token.descPosition(token);
      Source src = Source.sourceFor("<json>", this.source);
      int lineNum = src.getLine(pos);
      int columnNum = src.getColumn(pos);
      String formatted = ErrorManager.format(message, src, lineNum, columnNum, token);
      return new ParserException(JSErrorType.SYNTAX_ERROR, formatted, src, lineNum, columnNum, token);
   }

   private ParserException error(String message, int start) {
      return this.error(message, start, this.length);
   }

   private ParserException numberError(int start) {
      return this.error(Lexer.message("json.invalid.number"), start);
   }

   private ParserException expectedError(int start, String expected, String found) {
      return this.error(AbstractParser.message("expected", expected, found), start);
   }

   private ParserException syntaxError(int start, String reason) {
      String message = ECMAErrors.getMessage("syntax.error.invalid.json", reason);
      return this.error(message, start);
   }
}
