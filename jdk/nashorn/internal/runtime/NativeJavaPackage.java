package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.support.Guards;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.lookup.MethodHandleFunctionality;
import jdk.nashorn.internal.objects.annotations.Function;

public final class NativeJavaPackage extends ScriptObject {
   private static final MethodHandleFunctionality MH = MethodHandleFactory.getFunctionality();
   private static final MethodHandle CLASS_NOT_FOUND;
   private static final MethodHandle TYPE_GUARD;
   private final String name;

   public NativeJavaPackage(String name, ScriptObject proto) {
      super(proto, (PropertyMap)null);
      Context.checkPackageAccess(name);
      this.name = name;
   }

   public String getClassName() {
      return "JavaPackage";
   }

   public boolean equals(Object other) {
      return other instanceof NativeJavaPackage ? this.name.equals(((NativeJavaPackage)other).name) : false;
   }

   public int hashCode() {
      return this.name == null ? 0 : this.name.hashCode();
   }

   public String getName() {
      return this.name;
   }

   public String safeToString() {
      return this.toString();
   }

   public String toString() {
      return "[JavaPackage " + this.name + "]";
   }

   public Object getDefaultValue(Class<?> hint) {
      return hint == String.class ? this.toString() : super.getDefaultValue(hint);
   }

   protected GuardedInvocation findNewMethod(CallSiteDescriptor desc, LinkRequest request) {
      return createClassNotFoundInvocation(desc);
   }

   protected GuardedInvocation findCallMethod(CallSiteDescriptor desc, LinkRequest request) {
      return createClassNotFoundInvocation(desc);
   }

   private static GuardedInvocation createClassNotFoundInvocation(CallSiteDescriptor desc) {
      MethodType type = desc.getMethodType();
      return new GuardedInvocation(MH.dropArguments(CLASS_NOT_FOUND, 1, (List)type.parameterList().subList(1, type.parameterCount())), type.parameterType(0) == NativeJavaPackage.class ? null : TYPE_GUARD);
   }

   private static void classNotFound(NativeJavaPackage pkg) throws ClassNotFoundException {
      throw new ClassNotFoundException(pkg.name);
   }

   @Function(
      attributes = 2
   )
   public static Object __noSuchProperty__(Object self, Object name) {
      throw new AssertionError("__noSuchProperty__ placeholder called");
   }

   @Function(
      attributes = 2
   )
   public static Object __noSuchMethod__(Object self, Object... args) {
      throw new AssertionError("__noSuchMethod__ placeholder called");
   }

   public GuardedInvocation noSuchProperty(CallSiteDescriptor desc, LinkRequest request) {
      String propertyName = desc.getNameToken(2);
      this.createProperty(propertyName);
      return super.lookup(desc, request);
   }

   protected Object invokeNoSuchProperty(String key, boolean isScope, int programPoint) {
      Object retval = this.createProperty(key);
      if (UnwarrantedOptimismException.isValid(programPoint)) {
         throw new UnwarrantedOptimismException(retval, programPoint);
      } else {
         return retval;
      }
   }

   public GuardedInvocation noSuchMethod(CallSiteDescriptor desc, LinkRequest request) {
      return this.noSuchProperty(desc, request);
   }

   private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
      return MH.findStatic(MethodHandles.lookup(), NativeJavaPackage.class, name, MH.type(rtype, types));
   }

   private Object createProperty(String propertyName) {
      String fullName = this.name.isEmpty() ? propertyName : this.name + "." + propertyName;
      Context context = Context.getContextTrusted();
      Class javaClass = null;

      try {
         javaClass = context.findClass(fullName);
      } catch (ClassNotFoundException | NoClassDefFoundError var11) {
      }

      int openBrace = propertyName.indexOf(40);
      int closeBrace = propertyName.lastIndexOf(41);
      if (openBrace == -1 && closeBrace == -1) {
         Object propertyValue;
         if (javaClass == null) {
            propertyValue = new NativeJavaPackage(fullName, this.getProto());
         } else {
            propertyValue = StaticClass.forClass(javaClass);
         }

         this.set(propertyName, propertyValue, 0);
         return propertyValue;
      } else {
         int lastChar = propertyName.length() - 1;
         if (openBrace != -1 && closeBrace == lastChar) {
            String className = this.name + "." + propertyName.substring(0, openBrace);

            try {
               javaClass = context.findClass(className);
            } catch (ClassNotFoundException | NoClassDefFoundError var10) {
               throw ECMAErrors.typeError((Throwable)var10, "no.such.java.class", className);
            }

            Object constructor = BeansLinker.getConstructorMethod(javaClass, propertyName.substring(openBrace + 1, lastChar));
            if (constructor != null) {
               this.set(propertyName, constructor, 0);
               return constructor;
            } else {
               throw ECMAErrors.typeError("no.such.java.constructor", propertyName);
            }
         } else {
            throw ECMAErrors.typeError("improper.constructor.signature", propertyName);
         }
      }
   }

   static {
      CLASS_NOT_FOUND = findOwnMH("classNotFound", Void.TYPE, NativeJavaPackage.class);
      TYPE_GUARD = Guards.getClassGuard(NativeJavaPackage.class);
   }
}
