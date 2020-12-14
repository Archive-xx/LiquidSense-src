package jdk.nashorn.internal.codegen.types;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import jdk.internal.org.objectweb.asm.Handle;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Undefined;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

public abstract class Type implements Comparable<Type>, BytecodeOps, Serializable {
   private static final long serialVersionUID = 1L;
   private final transient String name;
   private final transient String descriptor;
   private final transient int weight;
   private final transient int slots;
   private final Class<?> clazz;
   private static final Map<Class<?>, jdk.internal.org.objectweb.asm.Type> INTERNAL_TYPE_CACHE = Collections.synchronizedMap(new WeakHashMap());
   private final transient jdk.internal.org.objectweb.asm.Type internalType;
   protected static final int MIN_WEIGHT = -1;
   protected static final int MAX_WEIGHT = 20;
   static final CompilerConstants.Call BOOTSTRAP;
   static final Handle MATHBOOTSTRAP;
   private static final ConcurrentMap<Class<?>, Type> cache;
   public static final Type BOOLEAN;
   public static final BitwiseType INT;
   public static final NumericType NUMBER;
   public static final Type LONG;
   public static final Type STRING;
   public static final Type CHARSEQUENCE;
   public static final Type OBJECT;
   public static final Type UNDEFINED;
   public static final Type SCRIPT_OBJECT;
   public static final ArrayType INT_ARRAY;
   public static final ArrayType LONG_ARRAY;
   public static final ArrayType NUMBER_ARRAY;
   public static final ArrayType OBJECT_ARRAY;
   public static final Type THIS;
   public static final Type SCOPE;
   public static final Type UNKNOWN;
   public static final Type SLOT_2;

   Type(String name, Class<?> clazz, int weight, int slots) {
      this.name = name;
      this.clazz = clazz;
      this.descriptor = jdk.internal.org.objectweb.asm.Type.getDescriptor(clazz);
      this.weight = weight;

      assert weight >= -1 && weight <= 20 : "illegal type weight: " + weight;

      this.slots = slots;
      this.internalType = getInternalType(clazz);
   }

   public int getWeight() {
      return this.weight;
   }

   public Class<?> getTypeClass() {
      return this.clazz;
   }

   public Type nextWider() {
      return null;
   }

   public Class<?> getBoxedType() {
      assert !this.getTypeClass().isPrimitive();

      return null;
   }

   public abstract char getBytecodeStackType();

   public static String getMethodDescriptor(Type returnType, Type... types) {
      jdk.internal.org.objectweb.asm.Type[] itypes = new jdk.internal.org.objectweb.asm.Type[types.length];

      for(int i = 0; i < types.length; ++i) {
         itypes[i] = types[i].getInternalType();
      }

      return jdk.internal.org.objectweb.asm.Type.getMethodDescriptor(returnType.getInternalType(), itypes);
   }

   public static String getMethodDescriptor(Class<?> returnType, Class<?>... types) {
      jdk.internal.org.objectweb.asm.Type[] itypes = new jdk.internal.org.objectweb.asm.Type[types.length];

      for(int i = 0; i < types.length; ++i) {
         itypes[i] = getInternalType(types[i]);
      }

      return jdk.internal.org.objectweb.asm.Type.getMethodDescriptor(getInternalType(returnType), itypes);
   }

   public static char getShortSignatureDescriptor(Type type) {
      return type instanceof BooleanType ? 'Z' : type.getBytecodeStackType();
   }

   static Type typeFor(jdk.internal.org.objectweb.asm.Type itype) {
      switch(itype.getSort()) {
      case 0:
         return null;
      case 1:
         return BOOLEAN;
      case 2:
      case 3:
      case 4:
      case 6:
      default:
         assert false : "Unknown itype : " + itype + " sort " + itype.getSort();

         return null;
      case 5:
         return INT;
      case 7:
         return LONG;
      case 8:
         return NUMBER;
      case 9:
         switch(itype.getElementType().getSort()) {
         case 5:
            return INT_ARRAY;
         case 6:
         case 9:
         default:
            assert false;
         case 10:
            return OBJECT_ARRAY;
         case 7:
            return LONG_ARRAY;
         case 8:
            return NUMBER_ARRAY;
         }
      case 10:
         if (Context.isStructureClass(itype.getClassName())) {
            return SCRIPT_OBJECT;
         } else {
            try {
               return typeFor(Class.forName(itype.getClassName()));
            } catch (ClassNotFoundException var2) {
               throw new AssertionError(var2);
            }
         }
      }
   }

   public static Type getMethodReturnType(String methodDescriptor) {
      return typeFor(jdk.internal.org.objectweb.asm.Type.getReturnType(methodDescriptor));
   }

   public static Type[] getMethodArguments(String methodDescriptor) {
      jdk.internal.org.objectweb.asm.Type[] itypes = jdk.internal.org.objectweb.asm.Type.getArgumentTypes(methodDescriptor);
      Type[] types = new Type[itypes.length];

      for(int i = 0; i < itypes.length; ++i) {
         types[i] = typeFor(itypes[i]);
      }

      return types;
   }

   public static void writeTypeMap(Map<Integer, Type> typeMap, DataOutput output) throws IOException {
      if (typeMap == null) {
         output.writeInt(0);
      } else {
         output.writeInt(typeMap.size());

         byte typeChar;
         for(Iterator var2 = typeMap.entrySet().iterator(); var2.hasNext(); output.writeByte(typeChar)) {
            Entry<Integer, Type> e = (Entry)var2.next();
            output.writeInt((Integer)e.getKey());
            Type type = (Type)e.getValue();
            if (type == OBJECT) {
               typeChar = 76;
            } else if (type == NUMBER) {
               typeChar = 68;
            } else {
               if (type != LONG) {
                  throw new AssertionError();
               }

               typeChar = 74;
            }
         }
      }

   }

   public static Map<Integer, Type> readTypeMap(DataInput input) throws IOException {
      int size = input.readInt();
      if (size <= 0) {
         return null;
      } else {
         Map<Integer, Type> map = new TreeMap();

         for(int i = 0; i < size; ++i) {
            int pp = input.readInt();
            int typeChar = input.readByte();
            Object type;
            switch(typeChar) {
            case 68:
               type = NUMBER;
               break;
            case 74:
               type = LONG;
               break;
            case 76:
               type = OBJECT;
               break;
            default:
               continue;
            }

            map.put(pp, type);
         }

         return map;
      }
   }

   static jdk.internal.org.objectweb.asm.Type getInternalType(String className) {
      return jdk.internal.org.objectweb.asm.Type.getType(className);
   }

   private jdk.internal.org.objectweb.asm.Type getInternalType() {
      return this.internalType;
   }

   private static jdk.internal.org.objectweb.asm.Type lookupInternalType(Class<?> type) {
      Map<Class<?>, jdk.internal.org.objectweb.asm.Type> c = INTERNAL_TYPE_CACHE;
      jdk.internal.org.objectweb.asm.Type itype = (jdk.internal.org.objectweb.asm.Type)c.get(type);
      if (itype != null) {
         return itype;
      } else {
         itype = jdk.internal.org.objectweb.asm.Type.getType(type);
         c.put(type, itype);
         return itype;
      }
   }

   private static jdk.internal.org.objectweb.asm.Type getInternalType(Class<?> type) {
      return lookupInternalType(type);
   }

   static void invokestatic(MethodVisitor method, CompilerConstants.Call call) {
      method.visitMethodInsn(184, call.className(), call.name(), call.descriptor(), false);
   }

   public String getInternalName() {
      return jdk.internal.org.objectweb.asm.Type.getInternalName(this.getTypeClass());
   }

   public static String getInternalName(Class<?> clazz) {
      return jdk.internal.org.objectweb.asm.Type.getInternalName(clazz);
   }

   public boolean isUnknown() {
      return this.equals(UNKNOWN);
   }

   public boolean isJSPrimitive() {
      return !this.isObject() || this.isString();
   }

   public boolean isBoolean() {
      return this.equals(BOOLEAN);
   }

   public boolean isInteger() {
      return this.equals(INT);
   }

   public boolean isLong() {
      return this.equals(LONG);
   }

   public boolean isNumber() {
      return this.equals(NUMBER);
   }

   public boolean isNumeric() {
      return this instanceof NumericType;
   }

   public boolean isArray() {
      return this instanceof ArrayType;
   }

   public boolean isCategory2() {
      return this.getSlots() == 2;
   }

   public boolean isObject() {
      return this instanceof ObjectType;
   }

   public boolean isPrimitive() {
      return !this.isObject();
   }

   public boolean isString() {
      return this.equals(STRING);
   }

   public boolean isCharSequence() {
      return this.equals(CHARSEQUENCE);
   }

   public boolean isEquivalentTo(Type type) {
      return this.weight() == type.weight() || this.isObject() && type.isObject();
   }

   public static boolean isAssignableFrom(Type type0, Type type1) {
      if (type0.isObject() && type1.isObject()) {
         return type0.weight() >= type1.weight();
      } else {
         return type0.weight() == type1.weight();
      }
   }

   public boolean isAssignableFrom(Type type) {
      return isAssignableFrom(this, type);
   }

   public static boolean areEquivalent(Type type0, Type type1) {
      return type0.isEquivalentTo(type1);
   }

   public int getSlots() {
      return this.slots;
   }

   public static Type widest(Type type0, Type type1) {
      if (type0.isArray() && type1.isArray()) {
         return ((ArrayType)type0).getElementType() == ((ArrayType)type1).getElementType() ? type0 : OBJECT;
      } else if (type0.isArray() != type1.isArray()) {
         return OBJECT;
      } else if (type0.isObject() && type1.isObject() && type0.getTypeClass() != type1.getTypeClass()) {
         return OBJECT;
      } else {
         return type0.weight() > type1.weight() ? type0 : type1;
      }
   }

   public static Class<?> widest(Class<?> type0, Class<?> type1) {
      return widest(typeFor(type0), typeFor(type1)).getTypeClass();
   }

   public static Type widestReturnType(Type t1, Type t2) {
      if (t1.isUnknown()) {
         return t2;
      } else if (t2.isUnknown()) {
         return t1;
      } else {
         return t1.isBoolean() == t2.isBoolean() && t1.isNumeric() == t2.isNumeric() ? widest(t1, t2) : OBJECT;
      }
   }

   public static Type generic(Type type) {
      return type.isObject() ? OBJECT : type;
   }

   public static Type narrowest(Type type0, Type type1) {
      return type0.narrowerThan(type1) ? type0 : type1;
   }

   public boolean narrowerThan(Type type) {
      return this.weight() < type.weight();
   }

   public boolean widerThan(Type type) {
      return this.weight() > type.weight();
   }

   public static Type widest(Type type0, Type type1, Type limit) {
      Type type = widest(type0, type1);
      return type.weight() > limit.weight() ? limit : type;
   }

   public static Type narrowest(Type type0, Type type1, Type limit) {
      Type type = type0.weight() < type1.weight() ? type0 : type1;
      return type.weight() < limit.weight() ? limit : type;
   }

   public Type narrowest(Type other) {
      return narrowest(this, other);
   }

   public Type widest(Type other) {
      return widest(this, other);
   }

   int weight() {
      return this.weight;
   }

   public String getDescriptor() {
      return this.descriptor;
   }

   public String getShortDescriptor() {
      return this.descriptor;
   }

   public String toString() {
      return this.name;
   }

   public static Type typeFor(Class<?> clazz) {
      Type type = (Type)cache.get(clazz);
      if (type != null) {
         return type;
      } else {
         assert !clazz.isPrimitive() || clazz == Void.TYPE;

         Object newType;
         if (clazz.isArray()) {
            newType = new ArrayType(clazz);
         } else {
            newType = new ObjectType(clazz);
         }

         Type existingType = (Type)cache.putIfAbsent(clazz, newType);
         return (Type)(existingType == null ? newType : existingType);
      }
   }

   public int compareTo(Type o) {
      return o.weight() - this.weight();
   }

   public Type dup(MethodVisitor method, int depth) {
      return dup(method, this, depth);
   }

   public Type swap(MethodVisitor method, Type other) {
      swap(method, this, other);
      return other;
   }

   public Type pop(MethodVisitor method) {
      pop(method, this);
      return this;
   }

   public Type loadEmpty(MethodVisitor method) {
      assert false : "unsupported operation";

      return null;
   }

   protected static void pop(MethodVisitor method, Type type) {
      method.visitInsn(type.isCategory2() ? 88 : 87);
   }

   private static Type dup(MethodVisitor method, Type type, int depth) {
      boolean cat2 = type.isCategory2();
      switch(depth) {
      case 0:
         method.visitInsn(cat2 ? 92 : 89);
         break;
      case 1:
         method.visitInsn(cat2 ? 93 : 90);
         break;
      case 2:
         method.visitInsn(cat2 ? 94 : 91);
         break;
      default:
         return null;
      }

      return type;
   }

   private static void swap(MethodVisitor method, Type above, Type below) {
      if (below.isCategory2()) {
         if (above.isCategory2()) {
            method.visitInsn(94);
            method.visitInsn(88);
         } else {
            method.visitInsn(91);
            method.visitInsn(87);
         }
      } else if (above.isCategory2()) {
         method.visitInsn(93);
         method.visitInsn(88);
      } else {
         method.visitInsn(95);
      }

   }

   private static <T extends Type> T putInCache(T type) {
      cache.put(type.getTypeClass(), type);
      return type;
   }

   protected final Object readResolve() {
      return typeFor(this.clazz);
   }

   static {
      BOOTSTRAP = CompilerConstants.staticCallNoLookup(Bootstrap.class, "mathBootstrap", CallSite.class, Lookup.class, String.class, MethodType.class, Integer.TYPE);
      MATHBOOTSTRAP = new Handle(6, BOOTSTRAP.className(), "mathBootstrap", BOOTSTRAP.descriptor());
      cache = new ConcurrentHashMap();
      BOOLEAN = putInCache(new BooleanType());
      INT = (BitwiseType)putInCache(new IntType());
      NUMBER = (NumericType)putInCache(new NumberType());
      LONG = putInCache(new LongType());
      STRING = putInCache(new ObjectType(String.class));
      CHARSEQUENCE = putInCache(new ObjectType(CharSequence.class));
      OBJECT = putInCache(new ObjectType());
      UNDEFINED = putInCache(new ObjectType(Undefined.class));
      SCRIPT_OBJECT = putInCache(new ObjectType(ScriptObject.class));
      INT_ARRAY = (ArrayType)putInCache(new ArrayType(int[].class) {
         private static final long serialVersionUID = 1L;

         public void astore(MethodVisitor method) {
            method.visitInsn(79);
         }

         public Type aload(MethodVisitor method) {
            method.visitInsn(46);
            return INT;
         }

         public Type newarray(MethodVisitor method) {
            method.visitIntInsn(188, 10);
            return this;
         }

         public Type getElementType() {
            return INT;
         }
      });
      LONG_ARRAY = (ArrayType)putInCache(new ArrayType(long[].class) {
         private static final long serialVersionUID = 1L;

         public void astore(MethodVisitor method) {
            method.visitInsn(80);
         }

         public Type aload(MethodVisitor method) {
            method.visitInsn(47);
            return LONG;
         }

         public Type newarray(MethodVisitor method) {
            method.visitIntInsn(188, 11);
            return this;
         }

         public Type getElementType() {
            return LONG;
         }
      });
      NUMBER_ARRAY = (ArrayType)putInCache(new ArrayType(double[].class) {
         private static final long serialVersionUID = 1L;

         public void astore(MethodVisitor method) {
            method.visitInsn(82);
         }

         public Type aload(MethodVisitor method) {
            method.visitInsn(49);
            return NUMBER;
         }

         public Type newarray(MethodVisitor method) {
            method.visitIntInsn(188, 7);
            return this;
         }

         public Type getElementType() {
            return NUMBER;
         }
      });
      OBJECT_ARRAY = (ArrayType)putInCache(new ArrayType(Object[].class));
      THIS = new ObjectType() {
         private static final long serialVersionUID = 1L;

         public String toString() {
            return "this";
         }
      };
      SCOPE = new ObjectType() {
         private static final long serialVersionUID = 1L;

         public String toString() {
            return "scope";
         }
      };
      UNKNOWN = new Type.ValueLessType("<unknown>") {
         private static final long serialVersionUID = 1L;

         public String getDescriptor() {
            return "<unknown>";
         }

         public char getBytecodeStackType() {
            return 'U';
         }
      };
      SLOT_2 = new Type.ValueLessType("<slot_2>") {
         private static final long serialVersionUID = 1L;

         public String getDescriptor() {
            return "<slot_2>";
         }

         public char getBytecodeStackType() {
            throw new UnsupportedOperationException("getBytecodeStackType");
         }
      };
   }

   private abstract static class ValueLessType extends Type {
      private static final long serialVersionUID = 1L;

      ValueLessType(String name) {
         super(name, Type.Unknown.class, -1, 1);
      }

      public Type load(MethodVisitor method, int slot) {
         throw new UnsupportedOperationException("load " + slot);
      }

      public void store(MethodVisitor method, int slot) {
         throw new UnsupportedOperationException("store " + slot);
      }

      public Type ldc(MethodVisitor method, Object c) {
         throw new UnsupportedOperationException("ldc " + c);
      }

      public Type loadUndefined(MethodVisitor method) {
         throw new UnsupportedOperationException("load undefined");
      }

      public Type loadForcedInitializer(MethodVisitor method) {
         throw new UnsupportedOperationException("load forced initializer");
      }

      public Type convert(MethodVisitor method, Type to) {
         throw new UnsupportedOperationException("convert => " + to);
      }

      public void _return(MethodVisitor method) {
         throw new UnsupportedOperationException("return");
      }

      public Type add(MethodVisitor method, int programPoint) {
         throw new UnsupportedOperationException("add");
      }
   }

   private interface Unknown {
   }
}
