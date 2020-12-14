package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Lookup {
   private final java.lang.invoke.MethodHandles.Lookup lookup;
   public static final Lookup PUBLIC = new Lookup(MethodHandles.publicLookup());

   public Lookup(java.lang.invoke.MethodHandles.Lookup lookup) {
      this.lookup = lookup;
   }

   public MethodHandle unreflect(Method m) {
      return unreflect(this.lookup, m);
   }

   public static MethodHandle unreflect(java.lang.invoke.MethodHandles.Lookup lookup, Method m) {
      try {
         return lookup.unreflect(m);
      } catch (IllegalAccessException var4) {
         IllegalAccessError ee = new IllegalAccessError("Failed to unreflect method " + m);
         ee.initCause(var4);
         throw ee;
      }
   }

   public MethodHandle unreflectGetter(Field f) {
      try {
         return this.lookup.unreflectGetter(f);
      } catch (IllegalAccessException var4) {
         IllegalAccessError ee = new IllegalAccessError("Failed to unreflect getter for field " + f);
         ee.initCause(var4);
         throw ee;
      }
   }

   public MethodHandle findGetter(Class<?> refc, String name, Class<?> type) {
      try {
         return this.lookup.findGetter(refc, name, type);
      } catch (IllegalAccessException var6) {
         IllegalAccessError ee = new IllegalAccessError("Failed to access getter for field " + refc.getName() + "." + name + " of type " + type.getName());
         ee.initCause(var6);
         throw ee;
      } catch (NoSuchFieldException var7) {
         NoSuchFieldError ee = new NoSuchFieldError("Failed to find getter for field " + refc.getName() + "." + name + " of type " + type.getName());
         ee.initCause(var7);
         throw ee;
      }
   }

   public MethodHandle unreflectSetter(Field f) {
      try {
         return this.lookup.unreflectSetter(f);
      } catch (IllegalAccessException var4) {
         IllegalAccessError ee = new IllegalAccessError("Failed to unreflect setter for field " + f);
         ee.initCause(var4);
         throw ee;
      }
   }

   public MethodHandle unreflectConstructor(Constructor<?> c) {
      return unreflectConstructor(this.lookup, c);
   }

   public static MethodHandle unreflectConstructor(java.lang.invoke.MethodHandles.Lookup lookup, Constructor<?> c) {
      try {
         return lookup.unreflectConstructor(c);
      } catch (IllegalAccessException var4) {
         IllegalAccessError ee = new IllegalAccessError("Failed to unreflect constructor " + c);
         ee.initCause(var4);
         throw ee;
      }
   }

   public MethodHandle findSpecial(Class<?> declaringClass, String name, MethodType type) {
      try {
         return this.lookup.findSpecial(declaringClass, name, type, declaringClass);
      } catch (IllegalAccessException var6) {
         IllegalAccessError ee = new IllegalAccessError("Failed to access special method " + methodDescription(declaringClass, name, type));
         ee.initCause(var6);
         throw ee;
      } catch (NoSuchMethodException var7) {
         NoSuchMethodError ee = new NoSuchMethodError("Failed to find special method " + methodDescription(declaringClass, name, type));
         ee.initCause(var7);
         throw ee;
      }
   }

   private static String methodDescription(Class<?> declaringClass, String name, MethodType type) {
      return declaringClass.getName() + "#" + name + type;
   }

   public MethodHandle findStatic(Class<?> declaringClass, String name, MethodType type) {
      try {
         return this.lookup.findStatic(declaringClass, name, type);
      } catch (IllegalAccessException var6) {
         IllegalAccessError ee = new IllegalAccessError("Failed to access static method " + methodDescription(declaringClass, name, type));
         ee.initCause(var6);
         throw ee;
      } catch (NoSuchMethodException var7) {
         NoSuchMethodError ee = new NoSuchMethodError("Failed to find static method " + methodDescription(declaringClass, name, type));
         ee.initCause(var7);
         throw ee;
      }
   }

   public MethodHandle findVirtual(Class<?> declaringClass, String name, MethodType type) {
      try {
         return this.lookup.findVirtual(declaringClass, name, type);
      } catch (IllegalAccessException var6) {
         IllegalAccessError ee = new IllegalAccessError("Failed to access virtual method " + methodDescription(declaringClass, name, type));
         ee.initCause(var6);
         throw ee;
      } catch (NoSuchMethodException var7) {
         NoSuchMethodError ee = new NoSuchMethodError("Failed to find virtual method " + methodDescription(declaringClass, name, type));
         ee.initCause(var7);
         throw ee;
      }
   }

   public static MethodHandle findOwnSpecial(java.lang.invoke.MethodHandles.Lookup lookup, String name, Class<?> rtype, Class<?>... ptypes) {
      return (new Lookup(lookup)).findOwnSpecial(name, rtype, ptypes);
   }

   public MethodHandle findOwnSpecial(String name, Class<?> rtype, Class<?>... ptypes) {
      return this.findSpecial(this.lookup.lookupClass(), name, MethodType.methodType(rtype, ptypes));
   }

   public static MethodHandle findOwnStatic(java.lang.invoke.MethodHandles.Lookup lookup, String name, Class<?> rtype, Class<?>... ptypes) {
      return (new Lookup(lookup)).findOwnStatic(name, rtype, ptypes);
   }

   public MethodHandle findOwnStatic(String name, Class<?> rtype, Class<?>... ptypes) {
      return this.findStatic(this.lookup.lookupClass(), name, MethodType.methodType(rtype, ptypes));
   }
}
