package kotlin.jvm.internal;

import java.util.List;
import kotlin.SinceKotlin;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KProperty2;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;

public class ReflectionFactory {
   private static final String KOTLIN_JVM_FUNCTIONS = "kotlin.jvm.functions.";

   public KClass createKotlinClass(Class javaClass) {
      return new ClassReference(javaClass);
   }

   public KClass createKotlinClass(Class javaClass, String internalName) {
      return new ClassReference(javaClass);
   }

   public KDeclarationContainer getOrCreateKotlinPackage(Class javaClass, String moduleName) {
      return new PackageReference(javaClass, moduleName);
   }

   public KClass getOrCreateKotlinClass(Class javaClass) {
      return new ClassReference(javaClass);
   }

   public KClass getOrCreateKotlinClass(Class javaClass, String internalName) {
      return new ClassReference(javaClass);
   }

   @SinceKotlin(
      version = "1.1"
   )
   public String renderLambdaToString(Lambda lambda) {
      return this.renderLambdaToString((FunctionBase)lambda);
   }

   @SinceKotlin(
      version = "1.3"
   )
   public String renderLambdaToString(FunctionBase lambda) {
      String result = lambda.getClass().getGenericInterfaces()[0].toString();
      return result.startsWith("kotlin.jvm.functions.") ? result.substring(21) : result;
   }

   public KFunction function(FunctionReference f) {
      return f;
   }

   public KProperty0 property0(PropertyReference0 p) {
      return p;
   }

   public KMutableProperty0 mutableProperty0(MutablePropertyReference0 p) {
      return p;
   }

   public KProperty1 property1(PropertyReference1 p) {
      return p;
   }

   public KMutableProperty1 mutableProperty1(MutablePropertyReference1 p) {
      return p;
   }

   public KProperty2 property2(PropertyReference2 p) {
      return p;
   }

   public KMutableProperty2 mutableProperty2(MutablePropertyReference2 p) {
      return p;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public KType typeOf(KClassifier klass, List<KTypeProjection> arguments, boolean isMarkedNullable) {
      return new TypeReference(klass, arguments, isMarkedNullable);
   }
}
