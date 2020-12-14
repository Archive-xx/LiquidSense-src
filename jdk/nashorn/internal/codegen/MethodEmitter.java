package jdk.nashorn.internal.codegen;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jdk.internal.dynalink.support.NameCodec;
import jdk.internal.org.objectweb.asm.Handle;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.types.ArrayType;
import jdk.nashorn.internal.codegen.types.BitwiseType;
import jdk.nashorn.internal.codegen.types.NumericType;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.JoinPredecessor;
import jdk.nashorn.internal.ir.LiteralNode;
import jdk.nashorn.internal.ir.LocalVariableConversion;
import jdk.nashorn.internal.ir.Symbol;
import jdk.nashorn.internal.ir.TryNode;
import jdk.nashorn.internal.objects.NativeArray;
import jdk.nashorn.internal.runtime.ArgumentSetter;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Debug;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.RewriteException;
import jdk.nashorn.internal.runtime.Scope;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.options.Options;

public class MethodEmitter {
   private final MethodVisitor method;
   private final ClassEmitter classEmitter;
   protected FunctionNode functionNode;
   private Label.Stack stack;
   private boolean preventUndefinedLoad;
   private final Map<Symbol, MethodEmitter.LocalVariableDef> localVariableDefs;
   private final Context context;
   static final int LARGE_STRING_THRESHOLD = 32768;
   private final DebugLogger log;
   private final boolean debug;
   private static final int DEBUG_TRACE_LINE;
   private static final Handle LINKERBOOTSTRAP;
   private static final Handle POPULATE_ARRAY_BOOTSTRAP;
   private final CompilerConstants.FieldAccess ERR_STREAM;
   private final CompilerConstants.Call PRINT;
   private final CompilerConstants.Call PRINTLN;
   private final CompilerConstants.Call PRINT_STACKTRACE;
   private static int linePrefix;

   MethodEmitter(ClassEmitter classEmitter, MethodVisitor method) {
      this(classEmitter, method, (FunctionNode)null);
   }

   MethodEmitter(ClassEmitter classEmitter, MethodVisitor method, FunctionNode functionNode) {
      this.localVariableDefs = new IdentityHashMap();
      this.ERR_STREAM = CompilerConstants.staticField(System.class, "err", PrintStream.class);
      this.PRINT = CompilerConstants.virtualCallNoLookup(PrintStream.class, "print", Void.TYPE, Object.class);
      this.PRINTLN = CompilerConstants.virtualCallNoLookup(PrintStream.class, "println", Void.TYPE, Object.class);
      this.PRINT_STACKTRACE = CompilerConstants.virtualCallNoLookup(Throwable.class, "printStackTrace", Void.TYPE);
      this.context = classEmitter.getContext();
      this.classEmitter = classEmitter;
      this.method = method;
      this.functionNode = functionNode;
      this.stack = null;
      this.log = this.context.getLogger(CodeGenerator.class);
      this.debug = this.log.isEnabled();
   }

   public void begin() {
      this.classEmitter.beginMethod(this);
      this.newStack();
      this.method.visitCode();
   }

   public void end() {
      this.method.visitMaxs(0, 0);
      this.method.visitEnd();
      this.classEmitter.endMethod(this);
   }

   boolean isReachable() {
      return this.stack != null;
   }

   private void doesNotContinueSequentially() {
      this.stack = null;
   }

   private void newStack() {
      this.stack = new Label.Stack();
   }

   public String toString() {
      return "methodEmitter: " + (this.functionNode == null ? this.method : this.functionNode.getName()).toString() + ' ' + Debug.id(this);
   }

   void pushType(Type type) {
      if (type != null) {
         this.stack.push(type);
      }

   }

   private Type popType(Type expected) {
      Type type = this.popType();

      assert type.isEquivalentTo(expected) : type + " is not compatible with " + expected;

      return type;
   }

   private Type popType() {
      return this.stack.pop();
   }

   private NumericType popNumeric() {
      Type type = this.popType();
      if (type.isBoolean()) {
         return Type.INT;
      } else {
         assert type.isNumeric();

         return (NumericType)type;
      }
   }

   private BitwiseType popBitwise() {
      Type type = this.popType();
      return type == Type.BOOLEAN ? Type.INT : (BitwiseType)type;
   }

   private BitwiseType popInteger() {
      Type type = this.popType();
      if (type == Type.BOOLEAN) {
         return Type.INT;
      } else {
         assert type == Type.INT;

         return (BitwiseType)type;
      }
   }

   private ArrayType popArray() {
      Type type = this.popType();

      assert type.isArray() : type;

      return (ArrayType)type;
   }

   final Type peekType(int pos) {
      return this.stack.peek(pos);
   }

   final Type peekType() {
      return this.stack.peek();
   }

   MethodEmitter _new(String classDescriptor, Type type) {
      this.debug("new", classDescriptor);
      this.method.visitTypeInsn(187, classDescriptor);
      this.pushType(type);
      return this;
   }

   MethodEmitter _new(Class<?> clazz) {
      return this._new(CompilerConstants.className(clazz), Type.typeFor(clazz));
   }

   MethodEmitter newInstance(Class<?> clazz) {
      return this.invoke(CompilerConstants.constructorNoLookup(clazz));
   }

   MethodEmitter dup(int depth) {
      if (this.peekType().dup(this.method, depth) == null) {
         return null;
      } else {
         this.debug("dup", depth);
         int l0;
         Type p0;
         int l1;
         Type p1;
         switch(depth) {
         case 0:
            l0 = this.stack.getTopLocalLoad();
            this.pushType(this.peekType());
            this.stack.markLocalLoad(l0);
            break;
         case 1:
            l0 = this.stack.getTopLocalLoad();
            p0 = this.popType();
            l1 = this.stack.getTopLocalLoad();
            p1 = this.popType();
            this.pushType(p0);
            this.stack.markLocalLoad(l0);
            this.pushType(p1);
            this.stack.markLocalLoad(l1);
            this.pushType(p0);
            this.stack.markLocalLoad(l0);
            break;
         case 2:
            l0 = this.stack.getTopLocalLoad();
            p0 = this.popType();
            l1 = this.stack.getTopLocalLoad();
            p1 = this.popType();
            int l2 = this.stack.getTopLocalLoad();
            Type p2 = this.popType();
            this.pushType(p0);
            this.stack.markLocalLoad(l0);
            this.pushType(p2);
            this.stack.markLocalLoad(l2);
            this.pushType(p1);
            this.stack.markLocalLoad(l1);
            this.pushType(p0);
            this.stack.markLocalLoad(l0);
            break;
         default:
            assert false : "illegal dup depth = " + depth;

            return null;
         }

         return this;
      }
   }

   MethodEmitter dup2() {
      this.debug("dup2");
      int l0;
      if (this.peekType().isCategory2()) {
         l0 = this.stack.getTopLocalLoad();
         this.pushType(this.peekType());
         this.stack.markLocalLoad(l0);
      } else {
         l0 = this.stack.getTopLocalLoad();
         Type p0 = this.popType();
         int l1 = this.stack.getTopLocalLoad();
         Type p1 = this.popType();
         this.pushType(p0);
         this.stack.markLocalLoad(l0);
         this.pushType(p1);
         this.stack.markLocalLoad(l1);
         this.pushType(p0);
         this.stack.markLocalLoad(l0);
         this.pushType(p1);
         this.stack.markLocalLoad(l1);
      }

      this.method.visitInsn(92);
      return this;
   }

   MethodEmitter dup() {
      return this.dup(0);
   }

   MethodEmitter pop() {
      this.debug("pop", this.peekType());
      this.popType().pop(this.method);
      return this;
   }

   MethodEmitter pop2() {
      if (this.peekType().isCategory2()) {
         this.popType();
      } else {
         this.get2n();
      }

      return this;
   }

   MethodEmitter swap() {
      this.debug("swap");
      int l0 = this.stack.getTopLocalLoad();
      Type p0 = this.popType();
      int l1 = this.stack.getTopLocalLoad();
      Type p1 = this.popType();
      p0.swap(this.method, p1);
      this.pushType(p0);
      this.stack.markLocalLoad(l0);
      this.pushType(p1);
      this.stack.markLocalLoad(l1);
      return this;
   }

   void pack() {
      Type type = this.peekType();
      if (type.isInteger()) {
         this.convert(ObjectClassGenerator.PRIMITIVE_FIELD_TYPE);
      } else if (!type.isLong()) {
         if (type.isNumber()) {
            this.invokestatic("java/lang/Double", "doubleToRawLongBits", "(D)J");
         } else {
            assert false : type + " cannot be packed!";
         }
      }

   }

   void initializeMethodParameter(Symbol symbol, Type type, Label start) {
      assert symbol.isBytecodeLocal();

      this.localVariableDefs.put(symbol, new MethodEmitter.LocalVariableDef(start.getLabel(), type));
   }

   MethodEmitter newStringBuilder() {
      return this.invoke(CompilerConstants.constructorNoLookup(StringBuilder.class)).dup();
   }

   MethodEmitter stringBuilderAppend() {
      this.convert(Type.STRING);
      return this.invoke(CompilerConstants.virtualCallNoLookup(StringBuilder.class, "append", StringBuilder.class, String.class));
   }

   MethodEmitter and() {
      this.debug("and");
      this.pushType(this.get2i().and(this.method));
      return this;
   }

   MethodEmitter or() {
      this.debug("or");
      this.pushType(this.get2i().or(this.method));
      return this;
   }

   MethodEmitter xor() {
      this.debug("xor");
      this.pushType(this.get2i().xor(this.method));
      return this;
   }

   MethodEmitter shr() {
      this.debug("shr");
      this.popInteger();
      this.pushType(this.popBitwise().shr(this.method));
      return this;
   }

   MethodEmitter shl() {
      this.debug("shl");
      this.popInteger();
      this.pushType(this.popBitwise().shl(this.method));
      return this;
   }

   MethodEmitter sar() {
      this.debug("sar");
      this.popInteger();
      this.pushType(this.popBitwise().sar(this.method));
      return this;
   }

   MethodEmitter neg(int programPoint) {
      this.debug("neg");
      this.pushType(this.popNumeric().neg(this.method, programPoint));
      return this;
   }

   void _catch(Label recovery) {
      assert this.stack == null;

      recovery.onCatch();
      this.label(recovery);
      this.beginCatchBlock();
   }

   void _catch(Collection<Label> recoveries) {
      assert this.stack == null;

      Iterator var2 = recoveries.iterator();

      while(var2.hasNext()) {
         Label l = (Label)var2.next();
         this.label(l);
      }

      this.beginCatchBlock();
   }

   private void beginCatchBlock() {
      if (!this.isReachable()) {
         this.newStack();
      }

      this.pushType(Type.typeFor(Throwable.class));
   }

   private void _try(Label entry, Label exit, Label recovery, String typeDescriptor, boolean isOptimismHandler) {
      recovery.joinFromTry(entry.getStack(), isOptimismHandler);
      this.method.visitTryCatchBlock(entry.getLabel(), exit.getLabel(), recovery.getLabel(), typeDescriptor);
   }

   void _try(Label entry, Label exit, Label recovery, Class<?> clazz) {
      this._try(entry, exit, recovery, CompilerConstants.className(clazz), clazz == UnwarrantedOptimismException.class);
   }

   void _try(Label entry, Label exit, Label recovery) {
      this._try(entry, exit, recovery, (String)null, false);
   }

   void markLabelAsOptimisticCatchHandler(Label label, int liveLocalCount) {
      label.markAsOptimisticCatchHandler(this.stack, liveLocalCount);
   }

   MethodEmitter loadConstants() {
      this.getStatic(this.classEmitter.getUnitClassName(), CompilerConstants.CONSTANTS.symbolName(), CompilerConstants.CONSTANTS.descriptor());

      assert this.peekType().isArray() : this.peekType();

      return this;
   }

   MethodEmitter loadUndefined(Type type) {
      this.debug("load undefined ", type);
      this.pushType(type.loadUndefined(this.method));
      return this;
   }

   MethodEmitter loadForcedInitializer(Type type) {
      this.debug("load forced initializer ", type);
      this.pushType(type.loadForcedInitializer(this.method));
      return this;
   }

   MethodEmitter loadEmpty(Type type) {
      this.debug("load empty ", type);
      this.pushType(type.loadEmpty(this.method));
      return this;
   }

   MethodEmitter loadNull() {
      this.debug("aconst_null");
      this.pushType(Type.OBJECT.ldc(this.method, (Object)null));
      return this;
   }

   MethodEmitter loadType(String className) {
      this.debug("load type", className);
      this.method.visitLdcInsn(jdk.internal.org.objectweb.asm.Type.getObjectType(className));
      this.pushType(Type.OBJECT);
      return this;
   }

   MethodEmitter load(boolean b) {
      this.debug("load boolean", b);
      this.pushType(Type.BOOLEAN.ldc(this.method, b));
      return this;
   }

   MethodEmitter load(int i) {
      this.debug("load int", i);
      this.pushType(Type.INT.ldc(this.method, i));
      return this;
   }

   MethodEmitter load(double d) {
      this.debug("load double", d);
      this.pushType(Type.NUMBER.ldc(this.method, d));
      return this;
   }

   MethodEmitter load(long l) {
      this.debug("load long", l);
      this.pushType(Type.LONG.ldc(this.method, l));
      return this;
   }

   MethodEmitter arraylength() {
      this.debug("arraylength");
      this.popType(Type.OBJECT);
      this.pushType(Type.OBJECT_ARRAY.arraylength(this.method));
      return this;
   }

   MethodEmitter load(String s) {
      this.debug("load string", s);
      if (s == null) {
         this.loadNull();
         return this;
      } else {
         int length = s.length();
         if (length <= 32768) {
            this.pushType(Type.OBJECT.ldc(this.method, s));
            return this;
         } else {
            this._new(StringBuilder.class);
            this.dup();
            this.load(length);
            this.invoke(CompilerConstants.constructorNoLookup(StringBuilder.class, Integer.TYPE));

            for(int n = 0; n < length; n += 32768) {
               String part = s.substring(n, Math.min(n + '耀', length));
               this.load(part);
               this.stringBuilderAppend();
            }

            this.invoke(CompilerConstants.virtualCallNoLookup(StringBuilder.class, "toString", String.class));
            return this;
         }
      }
   }

   MethodEmitter load(IdentNode ident) {
      return this.load(ident.getSymbol(), ident.getType());
   }

   MethodEmitter load(Symbol symbol, Type type) {
      assert symbol != null;

      int index;
      if (symbol.hasSlot()) {
         index = symbol.getSlot(type);
         this.debug("load symbol", symbol.getName(), " slot=", index, "type=", type);
         this.load(type, index);
      } else if (symbol.isParam()) {
         assert this.functionNode.isVarArg() : "Non-vararg functions have slotted parameters";

         index = symbol.getFieldIndex();
         if (this.functionNode.needsArguments()) {
            this.debug("load symbol", symbol.getName(), " arguments index=", index);
            this.loadCompilerConstant(CompilerConstants.ARGUMENTS);
            this.load(index);
            ScriptObject.GET_ARGUMENT.invoke(this);
         } else {
            this.debug("load symbol", symbol.getName(), " array index=", index);
            this.loadCompilerConstant(CompilerConstants.VARARGS);
            this.load(symbol.getFieldIndex());
            this.arrayload();
         }
      }

      return this;
   }

   MethodEmitter load(Type type, int slot) {
      this.debug("explicit load", type, slot);
      Type loadType = type.load(this.method, slot);

      assert loadType != null;

      this.pushType(loadType == Type.OBJECT && this.isThisSlot(slot) ? Type.THIS : loadType);

      assert !this.preventUndefinedLoad || slot < this.stack.localVariableTypes.size() && this.stack.localVariableTypes.get(slot) != Type.UNKNOWN : "Attempted load of uninitialized slot " + slot + " (as type " + type + ")";

      this.stack.markLocalLoad(slot);
      return this;
   }

   private boolean isThisSlot(int slot) {
      if (this.functionNode == null) {
         return slot == CompilerConstants.JAVA_THIS.slot();
      } else {
         int thisSlot = this.getCompilerConstantSymbol(CompilerConstants.THIS).getSlot(Type.OBJECT);

         assert !this.functionNode.needsCallee() || thisSlot == 1;

         assert this.functionNode.needsCallee() || thisSlot == 0;

         return slot == thisSlot;
      }
   }

   MethodEmitter loadHandle(String className, String methodName, String descName, EnumSet<ClassEmitter.Flag> flags) {
      this.debug("load handle ");
      this.pushType(Type.OBJECT.ldc(this.method, new Handle(ClassEmitter.Flag.getValue(flags), className, methodName, descName)));
      return this;
   }

   private Symbol getCompilerConstantSymbol(CompilerConstants cc) {
      return this.functionNode.getBody().getExistingSymbol(cc.symbolName());
   }

   boolean hasScope() {
      return this.getCompilerConstantSymbol(CompilerConstants.SCOPE).hasSlot();
   }

   MethodEmitter loadCompilerConstant(CompilerConstants cc) {
      return this.loadCompilerConstant(cc, (Type)null);
   }

   MethodEmitter loadCompilerConstant(CompilerConstants cc, Type type) {
      if (cc == CompilerConstants.SCOPE && this.peekType() == Type.SCOPE) {
         this.dup();
         return this;
      } else {
         return this.load(this.getCompilerConstantSymbol(cc), type != null ? type : getCompilerConstantType(cc));
      }
   }

   MethodEmitter loadScope() {
      return this.loadCompilerConstant(CompilerConstants.SCOPE).checkcast(Scope.class);
   }

   MethodEmitter setSplitState(int state) {
      return this.loadScope().load(state).invoke(Scope.SET_SPLIT_STATE);
   }

   void storeCompilerConstant(CompilerConstants cc) {
      this.storeCompilerConstant(cc, (Type)null);
   }

   void storeCompilerConstant(CompilerConstants cc, Type type) {
      Symbol symbol = this.getCompilerConstantSymbol(cc);
      if (symbol.hasSlot()) {
         this.debug("store compiler constant ", symbol);
         this.store(symbol, type != null ? type : getCompilerConstantType(cc));
      }
   }

   private static Type getCompilerConstantType(CompilerConstants cc) {
      Class<?> constantType = cc.type();

      assert constantType != null;

      return Type.typeFor(constantType);
   }

   MethodEmitter arrayload() {
      this.debug("Xaload");
      this.popType(Type.INT);
      this.pushType(this.popArray().aload(this.method));
      return this;
   }

   void arraystore() {
      this.debug("Xastore");
      Type value = this.popType();
      Type index = this.popType(Type.INT);

      assert index.isInteger() : "array index is not integer, but " + index;

      ArrayType array = this.popArray();

      assert value.isEquivalentTo(array.getElementType()) : "Storing " + value + " into " + array;

      assert array.isObject();

      array.astore(this.method);
   }

   void store(IdentNode ident) {
      Type type = ident.getType();
      Symbol symbol = ident.getSymbol();
      if (type == Type.UNDEFINED) {
         assert this.peekType() == Type.UNDEFINED;

         this.store(symbol, Type.OBJECT);
      } else {
         this.store(symbol, type);
      }

   }

   void closeLocalVariable(Symbol symbol, Label label) {
      MethodEmitter.LocalVariableDef def = (MethodEmitter.LocalVariableDef)this.localVariableDefs.get(symbol);
      if (def != null) {
         this.endLocalValueDef(symbol, def, label.getLabel());
      }

      if (this.isReachable()) {
         this.markDeadLocalVariable(symbol);
      }

   }

   void markDeadLocalVariable(Symbol symbol) {
      if (!symbol.isDead()) {
         this.markDeadSlots(symbol.getFirstSlot(), symbol.slotCount());
      }

   }

   void markDeadSlots(int firstSlot, int slotCount) {
      this.stack.markDeadLocalVariables(firstSlot, slotCount);
   }

   private void endLocalValueDef(Symbol symbol, MethodEmitter.LocalVariableDef def, jdk.internal.org.objectweb.asm.Label label) {
      String name = symbol.getName();
      if (name.equals(CompilerConstants.THIS.symbolName())) {
         name = CompilerConstants.THIS_DEBUGGER.symbolName();
      }

      this.method.visitLocalVariable(name, def.type.getDescriptor(), (String)null, def.label, label, symbol.getSlot(def.type));
   }

   void store(Symbol symbol, Type type) {
      this.store(symbol, type, true);
   }

   void store(Symbol symbol, Type type, boolean onlySymbolLiveValue) {
      assert symbol != null : "No symbol to store";

      if (symbol.hasSlot()) {
         boolean isLiveType = symbol.hasSlotFor(type);
         MethodEmitter.LocalVariableDef existingDef = (MethodEmitter.LocalVariableDef)this.localVariableDefs.get(symbol);
         if (existingDef == null || existingDef.type != type) {
            jdk.internal.org.objectweb.asm.Label here = new jdk.internal.org.objectweb.asm.Label();
            if (isLiveType) {
               MethodEmitter.LocalVariableDef newDef = new MethodEmitter.LocalVariableDef(here, type);
               this.localVariableDefs.put(symbol, newDef);
            }

            this.method.visitLabel(here);
            if (existingDef != null) {
               this.endLocalValueDef(symbol, existingDef, here);
            }
         }

         if (isLiveType) {
            int slot = symbol.getSlot(type);
            this.debug("store symbol", symbol.getName(), " type=", type, " slot=", slot);
            this.storeHidden(type, slot, onlySymbolLiveValue);
         } else {
            if (onlySymbolLiveValue) {
               this.markDeadLocalVariable(symbol);
            }

            this.debug("dead store symbol ", symbol.getName(), " type=", type);
            this.pop();
         }
      } else if (symbol.isParam()) {
         assert !symbol.isScope();

         assert this.functionNode.isVarArg() : "Non-vararg functions have slotted parameters";

         int index = symbol.getFieldIndex();
         if (this.functionNode.needsArguments()) {
            this.convert(Type.OBJECT);
            this.debug("store symbol", symbol.getName(), " arguments index=", index);
            this.loadCompilerConstant(CompilerConstants.ARGUMENTS);
            this.load(index);
            ArgumentSetter.SET_ARGUMENT.invoke(this);
         } else {
            this.convert(Type.OBJECT);
            this.debug("store symbol", symbol.getName(), " array index=", index);
            this.loadCompilerConstant(CompilerConstants.VARARGS);
            this.load(index);
            ArgumentSetter.SET_ARRAY_ELEMENT.invoke(this);
         }
      } else {
         this.debug("dead store symbol ", symbol.getName(), " type=", type);
         this.pop();
      }

   }

   void storeHidden(Type type, int slot) {
      this.storeHidden(type, slot, true);
   }

   void storeHidden(Type type, int slot, boolean onlyLiveSymbolValue) {
      this.explicitStore(type, slot);
      this.stack.onLocalStore(type, slot, onlyLiveSymbolValue);
   }

   void storeTemp(Type type, int slot) {
      this.explicitStore(type, slot);
      this.defineTemporaryLocalVariable(slot, slot + type.getSlots());
      this.onLocalStore(type, slot);
   }

   void onLocalStore(Type type, int slot) {
      this.stack.onLocalStore(type, slot, true);
   }

   private void explicitStore(Type type, int slot) {
      assert slot != -1;

      this.debug("explicit store", type, slot);
      this.popType(type);
      type.store(this.method, slot);
   }

   void defineBlockLocalVariable(int fromSlot, int toSlot) {
      this.stack.defineBlockLocalVariable(fromSlot, toSlot);
   }

   void defineTemporaryLocalVariable(int fromSlot, int toSlot) {
      this.stack.defineTemporaryLocalVariable(fromSlot, toSlot);
   }

   int defineTemporaryLocalVariable(int width) {
      return this.stack.defineTemporaryLocalVariable(width);
   }

   void undefineLocalVariables(int fromSlot, boolean canTruncateSymbol) {
      if (this.isReachable()) {
         this.stack.undefineLocalVariables(fromSlot, canTruncateSymbol);
      }

   }

   List<Type> getLocalVariableTypes() {
      return this.stack.localVariableTypes;
   }

   List<Type> getWidestLiveLocals(List<Type> localTypes) {
      return this.stack.getWidestLiveLocals(localTypes);
   }

   String markSymbolBoundariesInLvarTypesDescriptor(String lvarDescriptor) {
      return this.stack.markSymbolBoundariesInLvarTypesDescriptor(lvarDescriptor);
   }

   void iinc(int slot, int increment) {
      this.debug("iinc");
      this.method.visitIincInsn(slot, increment);
   }

   public void athrow() {
      this.debug("athrow");
      Type receiver = this.popType(Type.OBJECT);

      assert Throwable.class.isAssignableFrom(receiver.getTypeClass()) : receiver.getTypeClass();

      this.method.visitInsn(191);
      this.doesNotContinueSequentially();
   }

   MethodEmitter _instanceof(String classDescriptor) {
      this.debug("instanceof", classDescriptor);
      this.popType(Type.OBJECT);
      this.method.visitTypeInsn(193, classDescriptor);
      this.pushType(Type.INT);
      return this;
   }

   MethodEmitter _instanceof(Class<?> clazz) {
      return this._instanceof(CompilerConstants.className(clazz));
   }

   MethodEmitter checkcast(String classDescriptor) {
      this.debug("checkcast", classDescriptor);

      assert this.peekType().isObject();

      this.method.visitTypeInsn(192, classDescriptor);
      return this;
   }

   MethodEmitter checkcast(Class<?> clazz) {
      return this.checkcast(CompilerConstants.className(clazz));
   }

   MethodEmitter newarray(ArrayType arrayType) {
      this.debug("newarray ", "arrayType=", arrayType);
      this.popType(Type.INT);
      this.pushType(arrayType.newarray(this.method));
      return this;
   }

   MethodEmitter multinewarray(ArrayType arrayType, int dims) {
      this.debug("multianewarray ", arrayType, dims);

      for(int i = 0; i < dims; ++i) {
         this.popType(Type.INT);
      }

      this.pushType(arrayType.newarray(this.method, dims));
      return this;
   }

   private Type fixParamStack(String signature) {
      Type[] params = Type.getMethodArguments(signature);

      for(int i = params.length - 1; i >= 0; --i) {
         this.popType(params[i]);
      }

      Type returnType = Type.getMethodReturnType(signature);
      return returnType;
   }

   MethodEmitter invoke(CompilerConstants.Call call) {
      return call.invoke(this);
   }

   private MethodEmitter invoke(int opcode, String className, String methodName, String methodDescriptor, boolean hasReceiver) {
      Type returnType = this.fixParamStack(methodDescriptor);
      if (hasReceiver) {
         this.popType(Type.OBJECT);
      }

      this.method.visitMethodInsn(opcode, className, methodName, methodDescriptor, opcode == 185);
      if (returnType != null) {
         this.pushType(returnType);
      }

      return this;
   }

   MethodEmitter invokespecial(String className, String methodName, String methodDescriptor) {
      this.debug("invokespecial", className, ".", methodName, methodDescriptor);
      return this.invoke(183, className, methodName, methodDescriptor, true);
   }

   MethodEmitter invokevirtual(String className, String methodName, String methodDescriptor) {
      this.debug("invokevirtual", className, ".", methodName, methodDescriptor, " ", this.stack);
      return this.invoke(182, className, methodName, methodDescriptor, true);
   }

   MethodEmitter invokestatic(String className, String methodName, String methodDescriptor) {
      this.debug("invokestatic", className, ".", methodName, methodDescriptor);
      this.invoke(184, className, methodName, methodDescriptor, false);
      return this;
   }

   MethodEmitter invokestatic(String className, String methodName, String methodDescriptor, Type returnType) {
      this.invokestatic(className, methodName, methodDescriptor);
      this.popType();
      this.pushType(returnType);
      return this;
   }

   MethodEmitter invokeinterface(String className, String methodName, String methodDescriptor) {
      this.debug("invokeinterface", className, ".", methodName, methodDescriptor);
      return this.invoke(185, className, methodName, methodDescriptor, true);
   }

   static jdk.internal.org.objectweb.asm.Label[] getLabels(Label... table) {
      jdk.internal.org.objectweb.asm.Label[] internalLabels = new jdk.internal.org.objectweb.asm.Label[table.length];

      for(int i = 0; i < table.length; ++i) {
         internalLabels[i] = table[i].getLabel();
      }

      return internalLabels;
   }

   void lookupswitch(Label defaultLabel, int[] values, Label... table) {
      this.debug("lookupswitch", this.peekType());
      this.adjustStackForSwitch(defaultLabel, table);
      this.method.visitLookupSwitchInsn(defaultLabel.getLabel(), values, getLabels(table));
      this.doesNotContinueSequentially();
   }

   void tableswitch(int lo, int hi, Label defaultLabel, Label... table) {
      this.debug("tableswitch", this.peekType());
      this.adjustStackForSwitch(defaultLabel, table);
      this.method.visitTableSwitchInsn(lo, hi, defaultLabel.getLabel(), getLabels(table));
      this.doesNotContinueSequentially();
   }

   private void adjustStackForSwitch(Label defaultLabel, Label... table) {
      this.popType(Type.INT);
      this.joinTo(defaultLabel);
      Label[] var3 = table;
      int var4 = table.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Label label = var3[var5];
         this.joinTo(label);
      }

   }

   void conditionalJump(Condition cond, Label trueLabel) {
      this.conditionalJump(cond, cond != Condition.GT && cond != Condition.GE, trueLabel);
   }

   void conditionalJump(Condition cond, boolean isCmpG, Label trueLabel) {
      if (this.peekType().isCategory2()) {
         this.debug("[ld]cmp isCmpG=", isCmpG);
         this.pushType(this.get2n().cmp(this.method, isCmpG));
         this.jump(Condition.toUnary(cond), trueLabel, 1);
      } else {
         this.debug("if", cond);
         this.jump(Condition.toBinary(cond, this.peekType().isObject()), trueLabel, 2);
      }

   }

   void _return(Type type) {
      this.debug("return", type);

      assert this.stack.size() == 1 : "Only return value on stack allowed at return point - depth=" + this.stack.size() + " stack = " + this.stack;

      Type stackType = this.peekType();
      if (!Type.areEquivalent(type, stackType)) {
         this.convert(type);
      }

      this.popType(type)._return(this.method);
      this.doesNotContinueSequentially();
   }

   void _return() {
      this._return(this.peekType());
   }

   void returnVoid() {
      this.debug("return [void]");

      assert this.stack.isEmpty() : this.stack;

      this.method.visitInsn(177);
      this.doesNotContinueSequentially();
   }

   MethodEmitter cmp(boolean isCmpG) {
      this.pushType(this.get2n().cmp(this.method, isCmpG));
      return this;
   }

   private void jump(int opcode, Label label, int n) {
      for(int i = 0; i < n; ++i) {
         assert this.peekType().isInteger() || this.peekType().isBoolean() || this.peekType().isObject() : "expecting integer type or object for jump, but found " + this.peekType();

         this.popType();
      }

      this.joinTo(label);
      this.method.visitJumpInsn(opcode, label.getLabel());
   }

   void if_acmpeq(Label label) {
      this.debug("if_acmpeq", label);
      this.jump(165, label, 2);
   }

   void if_acmpne(Label label) {
      this.debug("if_acmpne", label);
      this.jump(166, label, 2);
   }

   void ifnull(Label label) {
      this.debug("ifnull", label);
      this.jump(198, label, 1);
   }

   void ifnonnull(Label label) {
      this.debug("ifnonnull", label);
      this.jump(199, label, 1);
   }

   void ifeq(Label label) {
      this.debug("ifeq ", label);
      this.jump(153, label, 1);
   }

   void if_icmpeq(Label label) {
      this.debug("if_icmpeq", label);
      this.jump(159, label, 2);
   }

   void ifne(Label label) {
      this.debug("ifne", label);
      this.jump(154, label, 1);
   }

   void if_icmpne(Label label) {
      this.debug("if_icmpne", label);
      this.jump(160, label, 2);
   }

   void iflt(Label label) {
      this.debug("iflt", label);
      this.jump(155, label, 1);
   }

   void if_icmplt(Label label) {
      this.debug("if_icmplt", label);
      this.jump(161, label, 2);
   }

   void ifle(Label label) {
      this.debug("ifle", label);
      this.jump(158, label, 1);
   }

   void if_icmple(Label label) {
      this.debug("if_icmple", label);
      this.jump(164, label, 2);
   }

   void ifgt(Label label) {
      this.debug("ifgt", label);
      this.jump(157, label, 1);
   }

   void if_icmpgt(Label label) {
      this.debug("if_icmpgt", label);
      this.jump(163, label, 2);
   }

   void ifge(Label label) {
      this.debug("ifge", label);
      this.jump(156, label, 1);
   }

   void if_icmpge(Label label) {
      this.debug("if_icmpge", label);
      this.jump(162, label, 2);
   }

   void _goto(Label label) {
      this.debug("goto", label);
      this.jump(167, label, 0);
      this.doesNotContinueSequentially();
   }

   void gotoLoopStart(Label loopStart) {
      this.debug("goto (loop)", loopStart);
      this.jump(167, loopStart, 0);
   }

   void uncheckedGoto(Label target) {
      this.method.visitJumpInsn(167, target.getLabel());
   }

   void canThrow(Label catchLabel) {
      catchLabel.joinFromTry(this.stack, false);
   }

   private void joinTo(Label label) {
      assert this.isReachable();

      label.joinFrom(this.stack);
   }

   void label(Label label) {
      this.breakLabel(label, -1);
   }

   void breakLabel(Label label, int liveLocals) {
      if (!this.isReachable()) {
         assert label.getStack() == null != label.isReachable();
      } else {
         this.joinTo(label);
      }

      Label.Stack labelStack = label.getStack();
      this.stack = labelStack == null ? null : labelStack.clone();
      if (this.stack != null && label.isBreakTarget() && liveLocals != -1) {
         assert this.stack.firstTemp >= liveLocals;

         this.stack.firstTemp = liveLocals;
      }

      this.debug_label(label);
      this.method.visitLabel(label.getLabel());
   }

   MethodEmitter convert(Type to) {
      Type from = this.peekType();
      Type type = from.convert(this.method, to);
      if (type != null) {
         if (!from.isEquivalentTo(to)) {
            this.debug("convert", from, "->", to);
         }

         if (type != from) {
            int l0 = this.stack.getTopLocalLoad();
            this.popType();
            this.pushType(type);
            if (!from.isObject()) {
               this.stack.markLocalLoad(l0);
            }
         }
      }

      return this;
   }

   private Type get2() {
      Type p0 = this.popType();
      Type p1 = this.popType();

      assert p0.isEquivalentTo(p1) : "expecting equivalent types on stack but got " + p0 + " and " + p1;

      return p0;
   }

   private BitwiseType get2i() {
      BitwiseType p0 = this.popBitwise();
      BitwiseType p1 = this.popBitwise();

      assert p0.isEquivalentTo(p1) : "expecting equivalent types on stack but got " + p0 + " and " + p1;

      return p0;
   }

   private NumericType get2n() {
      NumericType p0 = this.popNumeric();
      NumericType p1 = this.popNumeric();

      assert p0.isEquivalentTo(p1) : "expecting equivalent types on stack but got " + p0 + " and " + p1;

      return p0;
   }

   MethodEmitter add(int programPoint) {
      this.debug("add");
      this.pushType(this.get2().add(this.method, programPoint));
      return this;
   }

   MethodEmitter sub(int programPoint) {
      this.debug("sub");
      this.pushType(this.get2n().sub(this.method, programPoint));
      return this;
   }

   MethodEmitter mul(int programPoint) {
      this.debug("mul ");
      this.pushType(this.get2n().mul(this.method, programPoint));
      return this;
   }

   MethodEmitter div(int programPoint) {
      this.debug("div");
      this.pushType(this.get2n().div(this.method, programPoint));
      return this;
   }

   MethodEmitter rem(int programPoint) {
      this.debug("rem");
      this.pushType(this.get2n().rem(this.method, programPoint));
      return this;
   }

   protected Type[] getTypesFromStack(int count) {
      return this.stack.getTopTypes(count);
   }

   int[] getLocalLoadsOnStack(int from, int to) {
      return this.stack.getLocalLoads(from, to);
   }

   int getStackSize() {
      return this.stack.size();
   }

   int getFirstTemp() {
      return this.stack.firstTemp;
   }

   int getUsedSlotsWithLiveTemporaries() {
      return this.stack.getUsedSlotsWithLiveTemporaries();
   }

   private String getDynamicSignature(Type returnType, int argCount) {
      Type[] paramTypes = new Type[argCount];
      int pos = 0;

      for(int i = argCount - 1; i >= 0; --i) {
         Type pt = this.stack.peek(pos++);
         if (ScriptObject.class.isAssignableFrom(pt.getTypeClass()) && !NativeArray.class.isAssignableFrom(pt.getTypeClass())) {
            pt = Type.SCRIPT_OBJECT;
         }

         paramTypes[i] = pt;
      }

      String descriptor = Type.getMethodDescriptor(returnType, paramTypes);

      for(int i = 0; i < argCount; ++i) {
         this.popType(paramTypes[argCount - i - 1]);
      }

      return descriptor;
   }

   MethodEmitter invalidateSpecialName(String name) {
      byte var3 = -1;
      switch(name.hashCode()) {
      case 3045982:
         if (name.equals("call")) {
            var3 = 1;
         }
         break;
      case 93029230:
         if (name.equals("apply")) {
            var3 = 0;
         }
      }

      switch(var3) {
      case 0:
      case 1:
         this.debug("invalidate_name", "name=", name);
         this.load("Function");
         this.invoke(ScriptRuntime.INVALIDATE_RESERVED_BUILTIN_NAME);
      default:
         return this;
      }
   }

   MethodEmitter dynamicNew(int argCount, int flags) {
      return this.dynamicNew(argCount, flags, (String)null);
   }

   MethodEmitter dynamicNew(int argCount, int flags, String msg) {
      assert !isOptimistic(flags);

      this.debug("dynamic_new", "argcount=", argCount);
      String signature = this.getDynamicSignature(Type.OBJECT, argCount);
      this.method.visitInvokeDynamicInsn(msg != null && msg.length() < 32768 ? "dyn:new:" + NameCodec.encode(msg) : "dyn:new", signature, LINKERBOOTSTRAP, new Object[]{flags});
      this.pushType(Type.OBJECT);
      return this;
   }

   MethodEmitter dynamicCall(Type returnType, int argCount, int flags) {
      return this.dynamicCall(returnType, argCount, flags, (String)null);
   }

   MethodEmitter dynamicCall(Type returnType, int argCount, int flags, String msg) {
      this.debug("dynamic_call", "args=", argCount, "returnType=", returnType);
      String signature = this.getDynamicSignature(returnType, argCount);
      this.debug("   signature", signature);
      this.method.visitInvokeDynamicInsn(msg != null && msg.length() < 32768 ? "dyn:call:" + NameCodec.encode(msg) : "dyn:call", signature, LINKERBOOTSTRAP, new Object[]{flags});
      this.pushType(returnType);
      return this;
   }

   MethodEmitter dynamicArrayPopulatorCall(int argCount, int startIndex) {
      this.debug("populate_array", "args=", argCount, "startIndex=", startIndex);
      String signature = this.getDynamicSignature(Type.OBJECT_ARRAY, argCount);
      this.method.visitInvokeDynamicInsn("populateArray", signature, POPULATE_ARRAY_BOOTSTRAP, new Object[]{startIndex});
      this.pushType(Type.OBJECT_ARRAY);
      return this;
   }

   MethodEmitter dynamicGet(Type valueType, String name, int flags, boolean isMethod, boolean isIndex) {
      if (name.length() > 32768) {
         return this.load(name).dynamicGetIndex(valueType, flags, isMethod);
      } else {
         this.debug("dynamic_get", name, valueType, getProgramPoint(flags));
         Type type = valueType;
         if (valueType.isObject() || valueType.isBoolean()) {
            type = Type.OBJECT;
         }

         this.popType(Type.SCOPE);
         this.method.visitInvokeDynamicInsn(dynGetOperation(isMethod, isIndex) + ':' + NameCodec.encode(name), Type.getMethodDescriptor(type, Type.OBJECT), LINKERBOOTSTRAP, new Object[]{flags});
         this.pushType(type);
         this.convert(valueType);
         return this;
      }
   }

   void dynamicSet(String name, int flags, boolean isIndex) {
      if (name.length() > 32768) {
         this.load(name).swap().dynamicSetIndex(flags);
      } else {
         assert !isOptimistic(flags);

         this.debug("dynamic_set", name, this.peekType());
         Type type = this.peekType();
         if (type.isObject() || type.isBoolean()) {
            type = Type.OBJECT;
            this.convert(Type.OBJECT);
         }

         this.popType(type);
         this.popType(Type.SCOPE);
         this.method.visitInvokeDynamicInsn(dynSetOperation(isIndex) + ':' + NameCodec.encode(name), CompilerConstants.methodDescriptor(Void.TYPE, Object.class, type.getTypeClass()), LINKERBOOTSTRAP, new Object[]{flags});
      }
   }

   MethodEmitter dynamicGetIndex(Type result, int flags, boolean isMethod) {
      assert result.getTypeClass().isPrimitive() || result.getTypeClass() == Object.class;

      this.debug("dynamic_get_index", this.peekType(1), "[", this.peekType(), "]", getProgramPoint(flags));
      Type resultType = result;
      if (result.isBoolean()) {
         resultType = Type.OBJECT;
      }

      Type index = this.peekType();
      if (index.isObject() || index.isBoolean()) {
         index = Type.OBJECT;
         this.convert(Type.OBJECT);
      }

      this.popType();
      this.popType(Type.OBJECT);
      String signature = Type.getMethodDescriptor(resultType, Type.OBJECT, index);
      this.method.visitInvokeDynamicInsn(dynGetOperation(isMethod, true), signature, LINKERBOOTSTRAP, new Object[]{flags});
      this.pushType(resultType);
      if (result.isBoolean()) {
         this.convert(Type.BOOLEAN);
      }

      return this;
   }

   private static String getProgramPoint(int flags) {
      return (flags & 8) == 0 ? "" : "pp=" + String.valueOf((flags & -2048) >> 11);
   }

   void dynamicSetIndex(int flags) {
      assert !isOptimistic(flags);

      this.debug("dynamic_set_index", this.peekType(2), "[", this.peekType(1), "] =", this.peekType());
      Type value = this.peekType();
      if (value.isObject() || value.isBoolean()) {
         value = Type.OBJECT;
         this.convert(Type.OBJECT);
      }

      this.popType();
      Type index = this.peekType();
      if (index.isObject() || index.isBoolean()) {
         index = Type.OBJECT;
         this.convert(Type.OBJECT);
      }

      this.popType(index);
      Type receiver = this.popType(Type.OBJECT);

      assert receiver.isObject();

      this.method.visitInvokeDynamicInsn("dyn:setElem|setProp", CompilerConstants.methodDescriptor(Void.TYPE, receiver.getTypeClass(), index.getTypeClass(), value.getTypeClass()), LINKERBOOTSTRAP, new Object[]{flags});
   }

   MethodEmitter loadKey(Object key) {
      if (key instanceof IdentNode) {
         this.method.visitLdcInsn(((IdentNode)key).getName());
      } else if (key instanceof LiteralNode) {
         this.method.visitLdcInsn(((LiteralNode)key).getString());
      } else {
         this.method.visitLdcInsn(JSType.toString(key));
      }

      this.pushType(Type.OBJECT);
      return this;
   }

   private static Type fieldType(String desc) {
      byte var2 = -1;
      switch(desc.hashCode()) {
      case 66:
         if (desc.equals("B")) {
            var2 = 1;
         }
         break;
      case 67:
         if (desc.equals("C")) {
            var2 = 2;
         }
         break;
      case 68:
         if (desc.equals("D")) {
            var2 = 6;
         }
      case 69:
      case 71:
      case 72:
      case 75:
      case 76:
      case 77:
      case 78:
      case 79:
      case 80:
      case 81:
      case 82:
      case 84:
      case 85:
      case 86:
      case 87:
      case 88:
      case 89:
      default:
         break;
      case 70:
         if (desc.equals("F")) {
            var2 = 5;
         }
         break;
      case 73:
         if (desc.equals("I")) {
            var2 = 4;
         }
         break;
      case 74:
         if (desc.equals("J")) {
            var2 = 7;
         }
         break;
      case 83:
         if (desc.equals("S")) {
            var2 = 3;
         }
         break;
      case 90:
         if (desc.equals("Z")) {
            var2 = 0;
         }
      }

      switch(var2) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
         return Type.INT;
      case 5:
         assert false;
      case 6:
         return Type.NUMBER;
      case 7:
         return Type.LONG;
      default:
         assert desc.startsWith("[") || desc.startsWith("L") : desc + " is not an object type";

         switch(desc.charAt(0)) {
         case 'L':
            return Type.OBJECT;
         case '[':
            return Type.typeFor(Array.newInstance(fieldType(desc.substring(1)).getTypeClass(), 0).getClass());
         default:
            assert false;

            return Type.OBJECT;
         }
      }
   }

   MethodEmitter getField(CompilerConstants.FieldAccess fa) {
      return fa.get(this);
   }

   void putField(CompilerConstants.FieldAccess fa) {
      fa.put(this);
   }

   MethodEmitter getField(String className, String fieldName, String fieldDescriptor) {
      this.debug("getfield", "receiver=", this.peekType(), className, ".", fieldName, fieldDescriptor);
      Type receiver = this.popType();

      assert receiver.isObject();

      this.method.visitFieldInsn(180, className, fieldName, fieldDescriptor);
      this.pushType(fieldType(fieldDescriptor));
      return this;
   }

   MethodEmitter getStatic(String className, String fieldName, String fieldDescriptor) {
      this.debug("getstatic", className, ".", fieldName, ".", fieldDescriptor);
      this.method.visitFieldInsn(178, className, fieldName, fieldDescriptor);
      this.pushType(fieldType(fieldDescriptor));
      return this;
   }

   void putField(String className, String fieldName, String fieldDescriptor) {
      this.debug("putfield", "receiver=", this.peekType(1), "value=", this.peekType());
      this.popType(fieldType(fieldDescriptor));
      this.popType(Type.OBJECT);
      this.method.visitFieldInsn(181, className, fieldName, fieldDescriptor);
   }

   void putStatic(String className, String fieldName, String fieldDescriptor) {
      this.debug("putfield", "value=", this.peekType());
      this.popType(fieldType(fieldDescriptor));
      this.method.visitFieldInsn(179, className, fieldName, fieldDescriptor);
   }

   void lineNumber(int line) {
      if (this.context.getEnv()._debug_lines) {
         this.debug_label("[LINE]", line);
         jdk.internal.org.objectweb.asm.Label l = new jdk.internal.org.objectweb.asm.Label();
         this.method.visitLabel(l);
         this.method.visitLineNumber(line, l);
      }

   }

   void beforeJoinPoint(JoinPredecessor joinPredecessor) {
      for(LocalVariableConversion next = joinPredecessor.getLocalVariableConversion(); next != null; next = next.getNext()) {
         Symbol symbol = next.getSymbol();
         if (next.isLive()) {
            this.emitLocalVariableConversion(next, true);
         } else {
            this.markDeadLocalVariable(symbol);
         }
      }

   }

   void beforeTry(TryNode tryNode, Label recovery) {
      for(LocalVariableConversion next = tryNode.getLocalVariableConversion(); next != null; next = next.getNext()) {
         if (next.isLive()) {
            Type to = this.emitLocalVariableConversion(next, false);
            recovery.getStack().onLocalStore(to, next.getSymbol().getSlot(to), true);
         }
      }

   }

   private static String dynGetOperation(boolean isMethod, boolean isIndex) {
      if (isMethod) {
         return isIndex ? "dyn:getMethod|getElem|getProp" : "dyn:getMethod|getProp|getElem";
      } else {
         return isIndex ? "dyn:getElem|getProp|getMethod" : "dyn:getProp|getElem|getMethod";
      }
   }

   private static String dynSetOperation(boolean isIndex) {
      return isIndex ? "dyn:setElem|setProp" : "dyn:setProp|setElem";
   }

   private Type emitLocalVariableConversion(LocalVariableConversion conversion, boolean onlySymbolLiveValue) {
      Type from = conversion.getFrom();
      Type to = conversion.getTo();
      Symbol symbol = conversion.getSymbol();

      assert symbol.isBytecodeLocal();

      if (from == Type.UNDEFINED) {
         this.loadUndefined(to);
      } else {
         this.load(symbol, from).convert(to);
      }

      this.store(symbol, to, onlySymbolLiveValue);
      return to;
   }

   void print() {
      this.getField(this.ERR_STREAM);
      this.swap();
      this.convert(Type.OBJECT);
      this.invoke(this.PRINT);
   }

   void println() {
      this.getField(this.ERR_STREAM);
      this.swap();
      this.convert(Type.OBJECT);
      this.invoke(this.PRINTLN);
   }

   void print(String string) {
      this.getField(this.ERR_STREAM);
      this.load(string);
      this.invoke(this.PRINT);
   }

   void println(String string) {
      this.getField(this.ERR_STREAM);
      this.load(string);
      this.invoke(this.PRINTLN);
   }

   void stacktrace() {
      this._new(Throwable.class);
      this.dup();
      this.invoke(CompilerConstants.constructorNoLookup(Throwable.class));
      this.invoke(this.PRINT_STACKTRACE);
   }

   private void debug(Object... args) {
      if (this.debug) {
         this.debug(30, args);
      }

   }

   private void debug(String arg) {
      if (this.debug) {
         this.debug(30, arg);
      }

   }

   private void debug(Object arg0, Object arg1) {
      if (this.debug) {
         this.debug(30, arg0, arg1);
      }

   }

   private void debug(Object arg0, Object arg1, Object arg2) {
      if (this.debug) {
         this.debug(30, arg0, arg1, arg2);
      }

   }

   private void debug(Object arg0, Object arg1, Object arg2, Object arg3) {
      if (this.debug) {
         this.debug(30, arg0, arg1, arg2, arg3);
      }

   }

   private void debug(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
      if (this.debug) {
         this.debug(30, arg0, arg1, arg2, arg3, arg4);
      }

   }

   private void debug(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
      if (this.debug) {
         this.debug(30, arg0, arg1, arg2, arg3, arg4, arg5);
      }

   }

   private void debug(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) {
      if (this.debug) {
         this.debug(30, arg0, arg1, arg2, arg3, arg4, arg5, arg6);
      }

   }

   private void debug_label(Object... args) {
      if (this.debug) {
         this.debug(22, args);
      }

   }

   private void debug(int padConstant, Object... args) {
      if (this.debug) {
         StringBuilder sb = new StringBuilder();
         sb.append('#');
         sb.append(++linePrefix);

         int pad;
         for(pad = 5 - sb.length(); pad > 0; --pad) {
            sb.append(' ');
         }

         int loadIndex;
         if (this.isReachable() && !this.stack.isEmpty()) {
            sb.append("{");
            sb.append(this.stack.size());
            sb.append(":");
            int pos = 0;

            while(true) {
               if (pos >= this.stack.size()) {
                  sb.append('}');
                  sb.append(' ');
                  break;
               }

               Type t = this.stack.peek(pos);
               if (t == Type.SCOPE) {
                  sb.append("scope");
               } else if (t == Type.THIS) {
                  sb.append("this");
               } else if (!t.isObject()) {
                  sb.append(t.getDescriptor());
               } else {
                  String desc = t.getDescriptor();

                  int i;
                  for(i = 0; desc.charAt(i) == '[' && i < desc.length(); ++i) {
                     sb.append('[');
                  }

                  desc = desc.substring(i);
                  int slash = desc.lastIndexOf(47);
                  if (slash != -1) {
                     desc = desc.substring(slash + 1, desc.length() - 1);
                  }

                  if ("Object".equals(desc)) {
                     sb.append('O');
                  } else {
                     sb.append(desc);
                  }
               }

               loadIndex = this.stack.localLoads[this.stack.sp - 1 - pos];
               if (loadIndex != -1) {
                  sb.append('(').append(loadIndex).append(')');
               }

               if (pos + 1 < this.stack.size()) {
                  sb.append(' ');
               }

               ++pos;
            }
         }

         for(pad = padConstant - sb.length(); pad > 0; --pad) {
            sb.append(' ');
         }

         Object[] var10 = args;
         int var11 = args.length;

         for(loadIndex = 0; loadIndex < var11; ++loadIndex) {
            Object arg = var10[loadIndex];
            sb.append(arg);
            sb.append(' ');
         }

         if (this.context.getEnv() != null) {
            this.log.info(sb);
            if (DEBUG_TRACE_LINE == linePrefix) {
               (new Throwable()).printStackTrace(this.log.getOutputStream());
            }
         }
      }

   }

   void setFunctionNode(FunctionNode functionNode) {
      this.functionNode = functionNode;
   }

   void setPreventUndefinedLoad() {
      this.preventUndefinedLoad = true;
   }

   private static boolean isOptimistic(int flags) {
      return (flags & 8) != 0;
   }

   static {
      String tl = Options.getStringProperty("nashorn.codegen.debug.trace", "-1");
      int line = -1;

      try {
         line = Integer.parseInt(tl);
      } catch (NumberFormatException var3) {
      }

      DEBUG_TRACE_LINE = line;
      LINKERBOOTSTRAP = new Handle(6, Bootstrap.BOOTSTRAP.className(), Bootstrap.BOOTSTRAP.name(), Bootstrap.BOOTSTRAP.descriptor());
      POPULATE_ARRAY_BOOTSTRAP = new Handle(6, RewriteException.BOOTSTRAP.className(), RewriteException.BOOTSTRAP.name(), RewriteException.BOOTSTRAP.descriptor());
      linePrefix = 0;
   }

   private static class LocalVariableDef {
      private final jdk.internal.org.objectweb.asm.Label label;
      private final Type type;

      LocalVariableDef(jdk.internal.org.objectweb.asm.Label label, Type type) {
         this.label = label;
         this.type = type;
      }
   }
}
