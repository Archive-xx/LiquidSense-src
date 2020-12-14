package jdk.nashorn.internal.runtime;

import java.security.CodeSource;
import java.security.ProtectionDomain;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.ObjectClassGenerator;

final class StructureLoader extends NashornLoader {
   private static final String SINGLE_FIELD_PREFIX;
   private static final String DUAL_FIELD_PREFIX;

   StructureLoader(ClassLoader parent) {
      super(parent);
   }

   private static boolean isDualFieldStructure(String name) {
      return name.startsWith(DUAL_FIELD_PREFIX);
   }

   static boolean isSingleFieldStructure(String name) {
      return name.startsWith(SINGLE_FIELD_PREFIX);
   }

   static boolean isStructureClass(String name) {
      return isDualFieldStructure(name) || isSingleFieldStructure(name);
   }

   protected Class<?> findClass(String name) throws ClassNotFoundException {
      if (isDualFieldStructure(name)) {
         return this.generateClass(name, name.substring(DUAL_FIELD_PREFIX.length()), true);
      } else {
         return isSingleFieldStructure(name) ? this.generateClass(name, name.substring(SINGLE_FIELD_PREFIX.length()), false) : super.findClass(name);
      }
   }

   private Class<?> generateClass(String name, String descriptor, boolean dualFields) {
      Context context = Context.getContextTrusted();
      byte[] code = (new ObjectClassGenerator(context, dualFields)).generate(descriptor);
      return this.defineClass(name, code, 0, code.length, new ProtectionDomain((CodeSource)null, this.getPermissions((CodeSource)null)));
   }

   static {
      SINGLE_FIELD_PREFIX = Compiler.binaryName("jdk/nashorn/internal/scripts") + '.' + CompilerConstants.JS_OBJECT_SINGLE_FIELD_PREFIX.symbolName();
      DUAL_FIELD_PREFIX = Compiler.binaryName("jdk/nashorn/internal/scripts") + '.' + CompilerConstants.JS_OBJECT_DUAL_FIELD_PREFIX.symbolName();
   }
}
