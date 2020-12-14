package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import jdk.nashorn.internal.runtime.linker.NashornGuards;

public final class WithObject extends Scope {
   private static final MethodHandle WITHEXPRESSIONGUARD;
   private static final MethodHandle WITHEXPRESSIONFILTER;
   private static final MethodHandle WITHSCOPEFILTER;
   private static final MethodHandle BIND_TO_EXPRESSION_OBJ;
   private static final MethodHandle BIND_TO_EXPRESSION_FN;
   private final ScriptObject expression;

   WithObject(ScriptObject scope, ScriptObject expression) {
      super(scope, (PropertyMap)null);
      this.expression = expression;
   }

   public boolean delete(Object key, boolean strict) {
      ScriptObject self = this.expression;
      String propName = JSType.toString(key);
      FindProperty find = self.findProperty(propName, true);
      return find != null ? self.delete(propName, strict) : false;
   }

   public GuardedInvocation lookup(CallSiteDescriptor desc, LinkRequest request) {
      if (request.isCallSiteUnstable()) {
         return super.lookup(desc, request);
      } else {
         NashornCallSiteDescriptor ndesc = (NashornCallSiteDescriptor)desc;
         FindProperty find = null;
         GuardedInvocation link = null;
         boolean isNamedOperation;
         String name;
         if (desc.getNameTokenCount() > 2) {
            isNamedOperation = true;
            name = desc.getNameToken(2);
         } else {
            isNamedOperation = false;
            name = null;
         }

         ScriptObject self = this.expression;
         if (isNamedOperation) {
            find = self.findProperty(name, true);
         }

         if (find != null) {
            link = self.lookup(desc, request);
            if (link != null) {
               return fixExpressionCallSite(ndesc, link);
            }
         }

         ScriptObject scope = this.getProto();
         if (isNamedOperation) {
            find = scope.findProperty(name, true);
         }

         if (find != null) {
            return this.fixScopeCallSite(scope.lookup(desc, request), name, find.getOwner());
         } else {
            if (self != null) {
               String operator = (String)CallSiteDescriptorFactory.tokenizeOperators(desc).get(0);
               byte var13 = -1;
               switch(operator.hashCode()) {
               case -75566075:
                  if (operator.equals("getElem")) {
                     var13 = 3;
                  }
                  break;
               case -75232295:
                  if (operator.equals("getProp")) {
                     var13 = 2;
                  }
                  break;
               case 618460119:
                  if (operator.equals("getMethod")) {
                     var13 = 1;
                  }
                  break;
               case 1402960095:
                  if (operator.equals("callMethod")) {
                     var13 = 0;
                  }
               }

               String fallBack;
               switch(var13) {
               case 0:
                  throw new AssertionError();
               case 1:
                  fallBack = "__noSuchMethod__";
                  break;
               case 2:
               case 3:
                  fallBack = "__noSuchProperty__";
                  break;
               default:
                  fallBack = null;
               }

               if (fallBack != null) {
                  find = self.findProperty(fallBack, true);
                  if (find != null) {
                     var13 = -1;
                     switch(operator.hashCode()) {
                     case -75566075:
                        if (operator.equals("getElem")) {
                           var13 = 2;
                        }
                        break;
                     case -75232295:
                        if (operator.equals("getProp")) {
                           var13 = 1;
                        }
                        break;
                     case 618460119:
                        if (operator.equals("getMethod")) {
                           var13 = 0;
                        }
                     }

                     switch(var13) {
                     case 0:
                        link = self.noSuchMethod(desc, request);
                        break;
                     case 1:
                     case 2:
                        link = self.noSuchProperty(desc, request);
                     }
                  }
               }

               if (link != null) {
                  return fixExpressionCallSite(ndesc, link);
               }
            }

            link = scope.lookup(desc, request);
            return link != null ? this.fixScopeCallSite(link, name, (ScriptObject)null) : null;
         }
      }
   }

   protected FindProperty findProperty(String key, boolean deep, ScriptObject start) {
      FindProperty exprProperty = this.expression.findProperty(key, true, this.expression);
      return exprProperty != null ? exprProperty : super.findProperty(key, deep, start);
   }

   protected Object invokeNoSuchProperty(String name, boolean isScope, int programPoint) {
      FindProperty find = this.expression.findProperty("__noSuchProperty__", true);
      if (find != null) {
         Object func = find.getObjectValue();
         if (func instanceof ScriptFunction) {
            ScriptFunction sfunc = (ScriptFunction)func;
            Object self = isScope && sfunc.isStrict() ? ScriptRuntime.UNDEFINED : this.expression;
            return ScriptRuntime.apply(sfunc, self, name);
         }
      }

      return this.getProto().invokeNoSuchProperty(name, isScope, programPoint);
   }

   public void setSplitState(int state) {
      ((Scope)this.getNonWithParent()).setSplitState(state);
   }

   public int getSplitState() {
      return ((Scope)this.getNonWithParent()).getSplitState();
   }

   public void addBoundProperties(ScriptObject source, Property[] properties) {
      this.getNonWithParent().addBoundProperties(source, properties);
   }

   private ScriptObject getNonWithParent() {
      ScriptObject proto;
      for(proto = this.getProto(); proto != null && proto instanceof WithObject; proto = proto.getProto()) {
      }

      return proto;
   }

   private static GuardedInvocation fixReceiverType(GuardedInvocation link, MethodHandle filter) {
      MethodType invType = link.getInvocation().type();
      MethodType newInvType = invType.changeParameterType(0, filter.type().returnType());
      return link.asType(newInvType);
   }

   private static GuardedInvocation fixExpressionCallSite(NashornCallSiteDescriptor desc, GuardedInvocation link) {
      if (!"getMethod".equals(desc.getFirstOperator())) {
         return fixReceiverType(link, WITHEXPRESSIONFILTER).filterArguments(0, WITHEXPRESSIONFILTER);
      } else {
         MethodHandle linkInvocation = link.getInvocation();
         MethodType linkType = linkInvocation.type();
         boolean linkReturnsFunction = ScriptFunction.class.isAssignableFrom(linkType.returnType());
         return link.replaceMethods(Lookup.MH.foldArguments(linkReturnsFunction ? BIND_TO_EXPRESSION_FN : BIND_TO_EXPRESSION_OBJ, filterReceiver(linkInvocation.asType(linkType.changeReturnType(linkReturnsFunction ? ScriptFunction.class : Object.class).changeParameterType(0, Object.class)), WITHEXPRESSIONFILTER)), filterGuardReceiver(link, WITHEXPRESSIONFILTER));
      }
   }

   private GuardedInvocation fixScopeCallSite(GuardedInvocation link, String name, ScriptObject owner) {
      GuardedInvocation newLink = fixReceiverType(link, WITHSCOPEFILTER);
      MethodHandle expressionGuard = this.expressionGuard(name, owner);
      MethodHandle filterGuardReceiver = filterGuardReceiver(newLink, WITHSCOPEFILTER);
      return link.replaceMethods(filterReceiver(newLink.getInvocation(), WITHSCOPEFILTER), NashornGuards.combineGuards(expressionGuard, filterGuardReceiver));
   }

   private static MethodHandle filterGuardReceiver(GuardedInvocation link, MethodHandle receiverFilter) {
      MethodHandle test = link.getGuard();
      if (test == null) {
         return null;
      } else {
         Class<?> receiverType = test.type().parameterType(0);
         MethodHandle filter = Lookup.MH.asType(receiverFilter, receiverFilter.type().changeParameterType(0, receiverType).changeReturnType(receiverType));
         return filterReceiver(test, filter);
      }
   }

   private static MethodHandle filterReceiver(MethodHandle mh, MethodHandle receiverFilter) {
      return Lookup.MH.filterArguments(mh, 0, receiverFilter.asType(receiverFilter.type().changeReturnType(mh.type().parameterType(0))));
   }

   public static Object withFilterExpression(Object receiver) {
      return ((WithObject)receiver).expression;
   }

   private static Object bindToExpression(Object fn, final Object receiver) {
      if (fn instanceof ScriptFunction) {
         return bindToExpression((ScriptFunction)fn, receiver);
      } else {
         if (fn instanceof ScriptObjectMirror) {
            final ScriptObjectMirror mirror = (ScriptObjectMirror)fn;
            if (mirror.isFunction()) {
               return new AbstractJSObject() {
                  public Object call(Object thiz, Object... args) {
                     return mirror.call(WithObject.withFilterExpression(receiver), args);
                  }
               };
            }
         }

         return fn;
      }
   }

   private static Object bindToExpression(ScriptFunction fn, Object receiver) {
      return fn.createBound(withFilterExpression(receiver), ScriptRuntime.EMPTY_ARRAY);
   }

   private MethodHandle expressionGuard(String name, ScriptObject owner) {
      PropertyMap map = this.expression.getMap();
      SwitchPoint[] sp = this.expression.getProtoSwitchPoints(name, owner);
      return Lookup.MH.insertArguments(WITHEXPRESSIONGUARD, 1, map, sp);
   }

   private static boolean withExpressionGuard(Object receiver, PropertyMap map, SwitchPoint[] sp) {
      return ((WithObject)receiver).expression.getMap() == map && !hasBeenInvalidated(sp);
   }

   private static boolean hasBeenInvalidated(SwitchPoint[] switchPoints) {
      if (switchPoints != null) {
         SwitchPoint[] var1 = switchPoints;
         int var2 = switchPoints.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            SwitchPoint switchPoint = var1[var3];
            if (switchPoint.hasBeenInvalidated()) {
               return true;
            }
         }
      }

      return false;
   }

   public static Object withFilterScope(Object receiver) {
      return ((WithObject)receiver).getProto();
   }

   public ScriptObject getExpression() {
      return this.expression;
   }

   private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), WithObject.class, name, Lookup.MH.type(rtype, types));
   }

   static {
      WITHEXPRESSIONGUARD = findOwnMH("withExpressionGuard", Boolean.TYPE, Object.class, PropertyMap.class, SwitchPoint[].class);
      WITHEXPRESSIONFILTER = findOwnMH("withFilterExpression", Object.class, Object.class);
      WITHSCOPEFILTER = findOwnMH("withFilterScope", Object.class, Object.class);
      BIND_TO_EXPRESSION_OBJ = findOwnMH("bindToExpression", Object.class, Object.class, Object.class);
      BIND_TO_EXPRESSION_FN = findOwnMH("bindToExpression", Object.class, ScriptFunction.class, Object.class);
   }
}
