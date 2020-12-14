package jdk.internal.dynalink.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class TypeUtilities {
   static final Class<Object> OBJECT_CLASS = Object.class;
   private static final Map<Class<?>, Class<?>> WRAPPER_TYPES = createWrapperTypes();
   private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPES;
   private static final Map<String, Class<?>> PRIMITIVE_TYPES_BY_NAME;
   private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPES;
   private static final Set<Class<?>> PRIMITIVE_WRAPPER_TYPES;

   private TypeUtilities() {
   }

   public static Class<?> getCommonLosslessConversionType(Class<?> c1, Class<?> c2) {
      if (c1 == c2) {
         return c1;
      } else if (c1 != Void.TYPE && c2 != Void.TYPE) {
         if (isConvertibleWithoutLoss(c2, c1)) {
            return c1;
         } else if (isConvertibleWithoutLoss(c1, c2)) {
            return c2;
         } else {
            if (c1.isPrimitive() && c2.isPrimitive()) {
               if (c1 == Byte.TYPE && c2 == Character.TYPE || c1 == Character.TYPE && c2 == Byte.TYPE) {
                  return Integer.TYPE;
               }

               if (c1 == Short.TYPE && c2 == Character.TYPE || c1 == Character.TYPE && c2 == Short.TYPE) {
                  return Integer.TYPE;
               }

               if (c1 == Integer.TYPE && c2 == Float.TYPE || c1 == Float.TYPE && c2 == Integer.TYPE) {
                  return Double.TYPE;
               }
            }

            return getMostSpecificCommonTypeUnequalNonprimitives(c1, c2);
         }
      } else {
         return Object.class;
      }
   }

   private static Class<?> getMostSpecificCommonTypeUnequalNonprimitives(Class<?> c1, Class<?> c2) {
      Class<?> npc1 = c1.isPrimitive() ? getWrapperType(c1) : c1;
      Class<?> npc2 = c2.isPrimitive() ? getWrapperType(c2) : c2;
      Set<Class<?>> a1 = getAssignables(npc1, npc2);
      Set<Class<?>> a2 = getAssignables(npc2, npc1);
      a1.retainAll(a2);
      if (a1.isEmpty()) {
         return Object.class;
      } else {
         List<Class<?>> max = new ArrayList();
         Iterator var7 = a1.iterator();

         while(true) {
            label43:
            while(var7.hasNext()) {
               Class<?> clazz = (Class)var7.next();
               Iterator maxiter = max.iterator();

               while(maxiter.hasNext()) {
                  Class<?> maxClazz = (Class)maxiter.next();
                  if (isSubtype(maxClazz, clazz)) {
                     continue label43;
                  }

                  if (isSubtype(clazz, maxClazz)) {
                     maxiter.remove();
                  }
               }

               max.add(clazz);
            }

            if (max.size() > 1) {
               return Object.class;
            }

            return (Class)max.get(0);
         }
      }
   }

   private static Set<Class<?>> getAssignables(Class<?> c1, Class<?> c2) {
      Set<Class<?>> s = new HashSet();
      collectAssignables(c1, c2, s);
      return s;
   }

   private static void collectAssignables(Class<?> c1, Class<?> c2, Set<Class<?>> s) {
      if (c1.isAssignableFrom(c2)) {
         s.add(c1);
      }

      Class<?> sc = c1.getSuperclass();
      if (sc != null) {
         collectAssignables(sc, c2, s);
      }

      Class<?>[] itf = c1.getInterfaces();

      for(int i = 0; i < itf.length; ++i) {
         collectAssignables(itf[i], c2, s);
      }

   }

   private static Map<Class<?>, Class<?>> createWrapperTypes() {
      Map<Class<?>, Class<?>> wrapperTypes = new IdentityHashMap(8);
      wrapperTypes.put(Boolean.TYPE, Boolean.class);
      wrapperTypes.put(Byte.TYPE, Byte.class);
      wrapperTypes.put(Character.TYPE, Character.class);
      wrapperTypes.put(Short.TYPE, Short.class);
      wrapperTypes.put(Integer.TYPE, Integer.class);
      wrapperTypes.put(Long.TYPE, Long.class);
      wrapperTypes.put(Float.TYPE, Float.class);
      wrapperTypes.put(Double.TYPE, Double.class);
      return Collections.unmodifiableMap(wrapperTypes);
   }

   private static Map<String, Class<?>> createClassNameMapping(Collection<Class<?>> classes) {
      Map<String, Class<?>> map = new HashMap();
      Iterator var2 = classes.iterator();

      while(var2.hasNext()) {
         Class<?> clazz = (Class)var2.next();
         map.put(clazz.getName(), clazz);
      }

      return map;
   }

   private static <K, V> Map<V, K> invertMap(Map<K, V> map) {
      Map<V, K> inverted = new IdentityHashMap(map.size());
      Iterator var2 = map.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<K, V> entry = (Entry)var2.next();
         inverted.put(entry.getValue(), entry.getKey());
      }

      return Collections.unmodifiableMap(inverted);
   }

   public static boolean isMethodInvocationConvertible(Class<?> sourceType, Class<?> targetType) {
      if (targetType.isAssignableFrom(sourceType)) {
         return true;
      } else if (sourceType.isPrimitive()) {
         if (targetType.isPrimitive()) {
            return isProperPrimitiveSubtype(sourceType, targetType);
         } else {
            assert WRAPPER_TYPES.get(sourceType) != null : sourceType.getName();

            return targetType.isAssignableFrom((Class)WRAPPER_TYPES.get(sourceType));
         }
      } else if (!targetType.isPrimitive()) {
         return false;
      } else {
         Class<?> unboxedCallSiteType = (Class)PRIMITIVE_TYPES.get(sourceType);
         return unboxedCallSiteType != null && (unboxedCallSiteType == targetType || isProperPrimitiveSubtype(unboxedCallSiteType, targetType));
      }
   }

   public static boolean isConvertibleWithoutLoss(Class<?> sourceType, Class<?> targetType) {
      if (!targetType.isAssignableFrom(sourceType) && targetType != Void.TYPE) {
         if (sourceType.isPrimitive()) {
            if (sourceType == Void.TYPE) {
               return targetType == Object.class;
            } else if (targetType.isPrimitive()) {
               return isProperPrimitiveLosslessSubtype(sourceType, targetType);
            } else {
               assert WRAPPER_TYPES.get(sourceType) != null : sourceType.getName();

               return targetType.isAssignableFrom((Class)WRAPPER_TYPES.get(sourceType));
            }
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean isPotentiallyConvertible(Class<?> callSiteType, Class<?> methodType) {
      if (areAssignable(callSiteType, methodType)) {
         return true;
      } else if (!callSiteType.isPrimitive()) {
         return methodType.isPrimitive() ? isAssignableFromBoxedPrimitive(callSiteType) : false;
      } else {
         return methodType.isPrimitive() || isAssignableFromBoxedPrimitive(methodType);
      }
   }

   public static boolean areAssignable(Class<?> c1, Class<?> c2) {
      return c1.isAssignableFrom(c2) || c2.isAssignableFrom(c1);
   }

   public static boolean isSubtype(Class<?> subType, Class<?> superType) {
      if (superType.isAssignableFrom(subType)) {
         return true;
      } else {
         return superType.isPrimitive() && subType.isPrimitive() ? isProperPrimitiveSubtype(subType, superType) : false;
      }
   }

   private static boolean isProperPrimitiveSubtype(Class<?> subType, Class<?> superType) {
      if (superType != Boolean.TYPE && subType != Boolean.TYPE) {
         if (subType == Byte.TYPE) {
            return superType != Character.TYPE;
         } else if (subType == Character.TYPE) {
            return superType != Short.TYPE && superType != Byte.TYPE;
         } else if (subType == Short.TYPE) {
            return superType != Character.TYPE && superType != Byte.TYPE;
         } else if (subType == Integer.TYPE) {
            return superType == Long.TYPE || superType == Float.TYPE || superType == Double.TYPE;
         } else if (subType != Long.TYPE) {
            if (subType == Float.TYPE) {
               return superType == Double.TYPE;
            } else {
               return false;
            }
         } else {
            return superType == Float.TYPE || superType == Double.TYPE;
         }
      } else {
         return false;
      }
   }

   private static boolean isProperPrimitiveLosslessSubtype(Class<?> subType, Class<?> superType) {
      if (superType != Boolean.TYPE && subType != Boolean.TYPE) {
         if (superType != Character.TYPE && subType != Character.TYPE) {
            if (subType == Byte.TYPE) {
               return true;
            } else if (subType == Short.TYPE) {
               return superType != Byte.TYPE;
            } else if (subType != Integer.TYPE) {
               if (subType == Float.TYPE) {
                  return superType == Double.TYPE;
               } else {
                  return false;
               }
            } else {
               return superType == Long.TYPE || superType == Double.TYPE;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static Map<Class<?>, Class<?>> createWrapperToPrimitiveTypes() {
      Map<Class<?>, Class<?>> classes = new IdentityHashMap();
      classes.put(Void.class, Void.TYPE);
      classes.put(Boolean.class, Boolean.TYPE);
      classes.put(Byte.class, Byte.TYPE);
      classes.put(Character.class, Character.TYPE);
      classes.put(Short.class, Short.TYPE);
      classes.put(Integer.class, Integer.TYPE);
      classes.put(Long.class, Long.TYPE);
      classes.put(Float.class, Float.TYPE);
      classes.put(Double.class, Double.TYPE);
      return classes;
   }

   private static Set<Class<?>> createPrimitiveWrapperTypes() {
      Map<Class<?>, Class<?>> classes = new IdentityHashMap();
      addClassHierarchy(classes, Boolean.class);
      addClassHierarchy(classes, Byte.class);
      addClassHierarchy(classes, Character.class);
      addClassHierarchy(classes, Short.class);
      addClassHierarchy(classes, Integer.class);
      addClassHierarchy(classes, Long.class);
      addClassHierarchy(classes, Float.class);
      addClassHierarchy(classes, Double.class);
      return classes.keySet();
   }

   private static void addClassHierarchy(Map<Class<?>, Class<?>> map, Class<?> clazz) {
      if (clazz != null) {
         map.put(clazz, clazz);
         addClassHierarchy(map, clazz.getSuperclass());
         Class[] var2 = clazz.getInterfaces();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class<?> itf = var2[var4];
            addClassHierarchy(map, itf);
         }

      }
   }

   private static boolean isAssignableFromBoxedPrimitive(Class<?> clazz) {
      return PRIMITIVE_WRAPPER_TYPES.contains(clazz);
   }

   public static Class<?> getPrimitiveTypeByName(String name) {
      return (Class)PRIMITIVE_TYPES_BY_NAME.get(name);
   }

   public static Class<?> getPrimitiveType(Class<?> wrapperType) {
      return (Class)WRAPPER_TO_PRIMITIVE_TYPES.get(wrapperType);
   }

   public static Class<?> getWrapperType(Class<?> primitiveType) {
      return (Class)WRAPPER_TYPES.get(primitiveType);
   }

   public static boolean isWrapperType(Class<?> type) {
      return PRIMITIVE_TYPES.containsKey(type);
   }

   static {
      PRIMITIVE_TYPES = invertMap(WRAPPER_TYPES);
      PRIMITIVE_TYPES_BY_NAME = createClassNameMapping(WRAPPER_TYPES.keySet());
      WRAPPER_TO_PRIMITIVE_TYPES = createWrapperToPrimitiveTypes();
      PRIMITIVE_WRAPPER_TYPES = createPrimitiveWrapperTypes();
   }
}
