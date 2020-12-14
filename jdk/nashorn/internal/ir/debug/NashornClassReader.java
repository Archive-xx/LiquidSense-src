package jdk.nashorn.internal.ir.debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Label;

public class NashornClassReader extends ClassReader {
   private final Map<String, List<Label>> labelMap = new HashMap();
   private static String[] type = new String[]{"<error>", "UTF8", "<error>", "Integer", "Float", "Long", "Double", "Class", "String", "Fieldref", "Methodref", "InterfaceMethodRef", "NameAndType", "<error>", "<error>", "MethodHandle", "MethodType", "<error>", "Invokedynamic"};

   public NashornClassReader(byte[] bytecode) {
      super(bytecode);
      this.parse(bytecode);
   }

   List<Label> getExtraLabels(String className, String methodName, String methodDesc) {
      String key = fullyQualifiedName(className, methodName, methodDesc);
      return (List)this.labelMap.get(key);
   }

   private static int readByte(byte[] bytecode, int index) {
      return (byte)(bytecode[index] & 255);
   }

   private static int readShort(byte[] bytecode, int index) {
      return (short)((bytecode[index] & 255) << 8) | bytecode[index + 1] & 255;
   }

   private static int readInt(byte[] bytecode, int index) {
      return (bytecode[index] & 255) << 24 | (bytecode[index + 1] & 255) << 16 | (bytecode[index + 2] & 255) << 8 | bytecode[index + 3] & 255;
   }

   private static long readLong(byte[] bytecode, int index) {
      int hi = readInt(bytecode, index);
      int lo = readInt(bytecode, index + 4);
      return (long)hi << 32 | (long)lo;
   }

   private static String readUTF(int index, int utfLen, byte[] bytecode) {
      int endIndex = index + utfLen;
      char[] buf = new char[utfLen * 2];
      int strLen = 0;
      int st = 0;
      char cc = 0;
      int i = index;

      while(true) {
         while(i < endIndex) {
            int c = bytecode[i++];
            switch(st) {
            case 0:
               int c = c & 255;
               if (c < 128) {
                  buf[strLen++] = (char)c;
               } else {
                  if (c < 224 && c > 191) {
                     cc = (char)(c & 31);
                     st = 1;
                     continue;
                  }

                  cc = (char)(c & 15);
                  st = 2;
               }
               break;
            case 1:
               buf[strLen++] = (char)(cc << 6 | c & 63);
               st = 0;
               break;
            case 2:
               cc = (char)(cc << 6 | c & 63);
               st = 1;
            }
         }

         return new String(buf, 0, strLen);
      }
   }

   private String parse(byte[] bytecode) {
      int u = 0;
      int magic = readInt(bytecode, u);
      int u = u + 4;

      assert magic == -889275714 : Integer.toHexString(magic);

      readShort(bytecode, u);
      u += 2;
      readShort(bytecode, u);
      u += 2;
      int cpc = readShort(bytecode, u);
      u += 2;
      ArrayList<NashornClassReader.Constant> cp = new ArrayList(cpc);
      cp.add((Object)null);

      int cls;
      int tag;
      int fc;
      int mc;
      for(cls = 1; cls < cpc; ++cls) {
         tag = readByte(bytecode, u);
         ++u;
         switch(tag) {
         case 1:
            fc = readShort(bytecode, u);
            u += 2;
            cp.add(new NashornClassReader.DirectInfo(cp, tag, readUTF(u, fc, bytecode)));
            u += fc;
            break;
         case 2:
         case 13:
         case 14:
         case 17:
         default:
            assert false : tag;
            break;
         case 3:
            cp.add(new NashornClassReader.DirectInfo(cp, tag, readInt(bytecode, u)));
            u += 4;
            break;
         case 4:
            cp.add(new NashornClassReader.DirectInfo(cp, tag, Float.intBitsToFloat(readInt(bytecode, u))));
            u += 4;
            break;
         case 5:
            cp.add(new NashornClassReader.DirectInfo(cp, tag, readLong(bytecode, u)));
            cp.add((Object)null);
            ++cls;
            u += 8;
            break;
         case 6:
            cp.add(new NashornClassReader.DirectInfo(cp, tag, Double.longBitsToDouble(readLong(bytecode, u))));
            cp.add((Object)null);
            ++cls;
            u += 8;
            break;
         case 7:
            cp.add(new NashornClassReader.IndexInfo(cp, tag, readShort(bytecode, u)));
            u += 2;
            break;
         case 8:
            cp.add(new NashornClassReader.IndexInfo(cp, tag, readShort(bytecode, u)));
            u += 2;
            break;
         case 9:
         case 10:
         case 11:
            cp.add(new NashornClassReader.IndexInfo2(cp, tag, readShort(bytecode, u), readShort(bytecode, u + 2)));
            u += 4;
            break;
         case 12:
            cp.add(new NashornClassReader.IndexInfo2(cp, tag, readShort(bytecode, u), readShort(bytecode, u + 2)));
            u += 4;
            break;
         case 15:
            mc = readByte(bytecode, u);

            assert mc >= 1 && mc <= 9 : mc;

            cp.add(new NashornClassReader.IndexInfo2(cp, tag, mc, readShort(bytecode, u + 1)) {
               public String toString() {
                  return "#" + this.index + ' ' + ((NashornClassReader.Constant)this.cp.get(this.index2)).toString();
               }
            });
            u += 3;
            break;
         case 16:
            cp.add(new NashornClassReader.IndexInfo(cp, tag, readShort(bytecode, u)));
            u += 2;
            break;
         case 18:
            cp.add(new NashornClassReader.IndexInfo2(cp, tag, readShort(bytecode, u), readShort(bytecode, u + 2)) {
               public String toString() {
                  return "#" + this.index + ' ' + ((NashornClassReader.Constant)this.cp.get(this.index2)).toString();
               }
            });
            u += 4;
         }
      }

      readShort(bytecode, u);
      u += 2;
      cls = readShort(bytecode, u);
      u += 2;
      String thisClassName = ((NashornClassReader.Constant)cp.get(cls)).toString();
      u += 2;
      tag = readShort(bytecode, u);
      u += 2;
      u += tag * 2;
      fc = readShort(bytecode, u);
      u += 2;

      int ac;
      int i;
      int len;
      for(mc = 0; mc < fc; ++mc) {
         u += 2;
         readShort(bytecode, u);
         u += 2;
         u += 2;
         ac = readShort(bytecode, u);
         u += 2;

         for(i = 0; i < ac; ++i) {
            u += 2;
            len = readInt(bytecode, u);
            u += 4;
            u += len;
         }
      }

      mc = readShort(bytecode, u);
      u += 2;

      for(ac = 0; ac < mc; ++ac) {
         readShort(bytecode, u);
         u += 2;
         i = readShort(bytecode, u);
         u += 2;
         String methodName = ((NashornClassReader.Constant)cp.get(i)).toString();
         int methodDescIndex = readShort(bytecode, u);
         u += 2;
         String methodDesc = ((NashornClassReader.Constant)cp.get(methodDescIndex)).toString();
         int ac = readShort(bytecode, u);
         u += 2;

         for(int j = 0; j < ac; ++j) {
            int nameIndex = readShort(bytecode, u);
            u += 2;
            String attrName = ((NashornClassReader.Constant)cp.get(nameIndex)).toString();
            int attrLen = readInt(bytecode, u);
            u += 4;
            if ("Code".equals(attrName)) {
               readShort(bytecode, u);
               u += 2;
               readShort(bytecode, u);
               u += 2;
               int len = readInt(bytecode, u);
               u += 4;
               this.parseCode(bytecode, u, len, fullyQualifiedName(thisClassName, methodName, methodDesc));
               u += len;
               int elen = readShort(bytecode, u);
               u += 2;
               u += elen * 8;
               int ac2 = readShort(bytecode, u);
               u += 2;

               for(int k = 0; k < ac2; ++k) {
                  u += 2;
                  int aclen = readInt(bytecode, u);
                  u += 4;
                  u += aclen;
               }
            } else {
               u += attrLen;
            }
         }
      }

      ac = readShort(bytecode, u);
      u += 2;

      for(i = 0; i < ac; ++i) {
         readShort(bytecode, u);
         u += 2;
         len = readInt(bytecode, u);
         u += 4;
         u += len;
      }

      return thisClassName;
   }

   private static String fullyQualifiedName(String className, String methodName, String methodDesc) {
      return className + '.' + methodName + methodDesc;
   }

   private void parseCode(byte[] bytecode, int index, int len, String desc) {
      List<Label> labels = new ArrayList();
      this.labelMap.put(desc, labels);
      boolean wide = false;
      int i = index;

      while(i < index + len) {
         int opcode = bytecode[i];
         labels.add(new NashornTextifier.NashornLabel(opcode, i - index));
         switch(opcode & 255) {
         case 16:
         case 18:
         case 188:
            i += 2;
            break;
         case 17:
         case 19:
         case 20:
         case 153:
         case 154:
         case 155:
         case 156:
         case 157:
         case 158:
         case 159:
         case 160:
         case 161:
         case 162:
         case 163:
         case 164:
         case 165:
         case 166:
         case 167:
         case 168:
         case 178:
         case 179:
         case 180:
         case 181:
         case 182:
         case 183:
         case 184:
         case 187:
         case 189:
         case 192:
         case 193:
         case 198:
         case 199:
            i += 3;
            break;
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
            i += wide ? 3 : 2;
            break;
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 59:
         case 60:
         case 61:
         case 62:
         case 63:
         case 64:
         case 65:
         case 66:
         case 67:
         case 68:
         case 69:
         case 70:
         case 71:
         case 72:
         case 73:
         case 74:
         case 75:
         case 76:
         case 77:
         case 78:
         case 79:
         case 80:
         case 81:
         case 82:
         case 83:
         case 84:
         case 85:
         case 86:
         case 87:
         case 88:
         case 89:
         case 90:
         case 91:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         case 97:
         case 98:
         case 99:
         case 100:
         case 101:
         case 102:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 110:
         case 111:
         case 112:
         case 113:
         case 114:
         case 115:
         case 116:
         case 117:
         case 118:
         case 119:
         case 120:
         case 121:
         case 122:
         case 123:
         case 124:
         case 125:
         case 126:
         case 127:
         case 128:
         case 129:
         case 130:
         case 131:
         case 133:
         case 134:
         case 135:
         case 136:
         case 137:
         case 138:
         case 139:
         case 140:
         case 141:
         case 142:
         case 143:
         case 144:
         case 145:
         case 146:
         case 147:
         case 148:
         case 149:
         case 150:
         case 151:
         case 152:
         case 172:
         case 173:
         case 174:
         case 175:
         case 176:
         case 177:
         case 190:
         case 191:
         case 194:
         case 195:
         default:
            ++i;
            break;
         case 132:
            i += wide ? 5 : 3;
            break;
         case 169:
            i += wide ? 4 : 2;
            break;
         case 170:
            ++i;

            while((i - index & 3) != 0) {
               ++i;
            }

            readInt(bytecode, i);
            i += 4;
            int lo = readInt(bytecode, i);
            i += 4;
            int hi = readInt(bytecode, i);
            i += 4;
            i += 4 * (hi - lo + 1);
            break;
         case 171:
            ++i;

            while((i - index & 3) != 0) {
               ++i;
            }

            readInt(bytecode, i);
            i += 4;
            int npairs = readInt(bytecode, i);
            i += 4;
            i += 8 * npairs;
            break;
         case 185:
         case 186:
         case 200:
         case 201:
            i += 5;
            break;
         case 196:
            wide = true;
            ++i;
            break;
         case 197:
            i += 4;
         }

         if (wide) {
            wide = false;
         }
      }

   }

   public void accept(ClassVisitor classVisitor, Attribute[] attrs, int flags) {
      super.accept(classVisitor, attrs, flags);
   }

   protected Label readLabel(int offset, Label[] labels) {
      Label label = super.readLabel(offset, labels);
      label.info = offset;
      return label;
   }

   private static class DirectInfo<T> extends NashornClassReader.Constant {
      protected final T info;

      DirectInfo(ArrayList<NashornClassReader.Constant> cp, int tag, T info) {
         super(cp, tag);
         this.info = info;
      }

      public String toString() {
         return this.info.toString();
      }
   }

   private static class IndexInfo2 extends NashornClassReader.IndexInfo {
      protected final int index2;

      IndexInfo2(ArrayList<NashornClassReader.Constant> cp, int tag, int index, int index2) {
         super(cp, tag, index);
         this.index2 = index2;
      }

      public String toString() {
         return super.toString() + ' ' + ((NashornClassReader.Constant)this.cp.get(this.index2)).toString();
      }
   }

   private static class IndexInfo extends NashornClassReader.Constant {
      protected final int index;

      IndexInfo(ArrayList<NashornClassReader.Constant> cp, int tag, int index) {
         super(cp, tag);
         this.index = index;
      }

      public String toString() {
         return ((NashornClassReader.Constant)this.cp.get(this.index)).toString();
      }
   }

   private abstract static class Constant {
      protected ArrayList<NashornClassReader.Constant> cp;
      protected int tag;

      protected Constant(ArrayList<NashornClassReader.Constant> cp, int tag) {
         this.cp = cp;
         this.tag = tag;
      }

      final String getType() {
         String str;
         for(str = NashornClassReader.type[this.tag]; str.length() < 16; str = str + " ") {
         }

         return str;
      }
   }
}
