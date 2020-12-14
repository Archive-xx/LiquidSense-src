package jdk.nashorn.internal.ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.types.ArrayType;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
import jdk.nashorn.internal.objects.NativeArray;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Undefined;

@Immutable
public abstract class LiteralNode<T> extends Expression implements PropertyKey {
   private static final long serialVersionUID = 1L;
   protected final T value;
   public static final Object POSTSET_MARKER = new Object();

   protected LiteralNode(long token, int finish, T value) {
      super(token, finish);
      this.value = value;
   }

   protected LiteralNode(LiteralNode<T> literalNode) {
      this(literalNode, literalNode.value);
   }

   protected LiteralNode(LiteralNode<T> literalNode, T newValue) {
      super(literalNode);
      this.value = newValue;
   }

   public LiteralNode<?> initialize(LexicalContext lc) {
      return this;
   }

   public boolean isNull() {
      return this.value == null;
   }

   public Type getType() {
      return Type.typeFor(this.value.getClass());
   }

   public String getPropertyName() {
      return JSType.toString(this.getObject());
   }

   public boolean getBoolean() {
      return JSType.toBoolean(this.value);
   }

   public int getInt32() {
      return JSType.toInt32(this.value);
   }

   public long getUint32() {
      return JSType.toUint32(this.value);
   }

   public long getLong() {
      return JSType.toLong(this.value);
   }

   public double getNumber() {
      return JSType.toNumber(this.value);
   }

   public String getString() {
      return JSType.toString(this.value);
   }

   public Object getObject() {
      return this.value;
   }

   public boolean isString() {
      return this.value instanceof String;
   }

   public boolean isNumeric() {
      return this.value instanceof Number;
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterLiteralNode(this) ? visitor.leaveLiteralNode(this) : this);
   }

   public void toString(StringBuilder sb, boolean printType) {
      if (this.value == null) {
         sb.append("null");
      } else {
         sb.append(this.value.toString());
      }

   }

   public final T getValue() {
      return this.value;
   }

   private static Expression[] valueToArray(List<Expression> value) {
      return (Expression[])value.toArray(new Expression[value.size()]);
   }

   public static LiteralNode<Object> newInstance(long token, int finish) {
      return new LiteralNode.NullLiteralNode(token, finish);
   }

   public static LiteralNode<Object> newInstance(Node parent) {
      return new LiteralNode.NullLiteralNode(parent.getToken(), parent.getFinish());
   }

   public static LiteralNode<Boolean> newInstance(long token, int finish, boolean value) {
      return new LiteralNode.BooleanLiteralNode(token, finish, value);
   }

   public static LiteralNode<?> newInstance(Node parent, boolean value) {
      return new LiteralNode.BooleanLiteralNode(parent.getToken(), parent.getFinish(), value);
   }

   public static LiteralNode<Number> newInstance(long token, int finish, Number value) {
      assert !(value instanceof Long);

      return new LiteralNode.NumberLiteralNode(token, finish, value);
   }

   public static LiteralNode<?> newInstance(Node parent, Number value) {
      return new LiteralNode.NumberLiteralNode(parent.getToken(), parent.getFinish(), value);
   }

   public static LiteralNode<Undefined> newInstance(long token, int finish, Undefined value) {
      return new LiteralNode.UndefinedLiteralNode(token, finish);
   }

   public static LiteralNode<?> newInstance(Node parent, Undefined value) {
      return new LiteralNode.UndefinedLiteralNode(parent.getToken(), parent.getFinish());
   }

   public static LiteralNode<String> newInstance(long token, int finish, String value) {
      return new LiteralNode.StringLiteralNode(token, finish, value);
   }

   public static LiteralNode<?> newInstance(Node parent, String value) {
      return new LiteralNode.StringLiteralNode(parent.getToken(), parent.getFinish(), value);
   }

   public static LiteralNode<Lexer.LexerToken> newInstance(long token, int finish, Lexer.LexerToken value) {
      return new LiteralNode.LexerTokenLiteralNode(token, finish, value);
   }

   public static LiteralNode<?> newInstance(Node parent, Lexer.LexerToken value) {
      return new LiteralNode.LexerTokenLiteralNode(parent.getToken(), parent.getFinish(), value);
   }

   public static Object objectAsConstant(Object object) {
      if (object == null) {
         return null;
      } else if (!(object instanceof Number) && !(object instanceof String) && !(object instanceof Boolean)) {
         return object instanceof LiteralNode ? objectAsConstant(((LiteralNode)object).getValue()) : POSTSET_MARKER;
      } else {
         return object;
      }
   }

   public static boolean isConstant(Object object) {
      return objectAsConstant(object) != POSTSET_MARKER;
   }

   public static LiteralNode<Expression[]> newInstance(long token, int finish, List<Expression> value) {
      return new LiteralNode.ArrayLiteralNode(token, finish, valueToArray(value));
   }

   public static LiteralNode<?> newInstance(Node parent, List<Expression> value) {
      return new LiteralNode.ArrayLiteralNode(parent.getToken(), parent.getFinish(), valueToArray(value));
   }

   public static LiteralNode<Expression[]> newInstance(long token, int finish, Expression[] value) {
      return new LiteralNode.ArrayLiteralNode(token, finish, value);
   }

   @Immutable
   public static final class ArrayLiteralNode extends LiteralNode<Expression[]> implements LexicalContextNode, Splittable {
      private static final long serialVersionUID = 1L;
      private final Type elementType;
      private final Object presets;
      private final int[] postsets;
      private final List<Splittable.SplitRange> splitRanges;

      protected ArrayLiteralNode(long token, int finish, Expression[] value) {
         super(Token.recast(token, TokenType.ARRAY), finish, value);
         this.elementType = Type.UNKNOWN;
         this.presets = null;
         this.postsets = null;
         this.splitRanges = null;
      }

      private ArrayLiteralNode(LiteralNode.ArrayLiteralNode node, Expression[] value, Type elementType, int[] postsets, Object presets, List<Splittable.SplitRange> splitRanges) {
         super(node, value);
         this.elementType = elementType;
         this.postsets = postsets;
         this.presets = presets;
         this.splitRanges = splitRanges;
      }

      public List<Expression> getElementExpressions() {
         return Collections.unmodifiableList(Arrays.asList((Object[])this.value));
      }

      public LiteralNode.ArrayLiteralNode initialize(LexicalContext lc) {
         return (LiteralNode.ArrayLiteralNode)Node.replaceInLexicalContext(lc, this, LiteralNode.ArrayLiteralNode.ArrayLiteralInitializer.initialize(this));
      }

      public ArrayType getArrayType() {
         return getArrayType(this.getElementType());
      }

      private static ArrayType getArrayType(Type elementType) {
         if (elementType.isInteger()) {
            return Type.INT_ARRAY;
         } else {
            return elementType.isNumeric() ? Type.NUMBER_ARRAY : Type.OBJECT_ARRAY;
         }
      }

      public Type getType() {
         return Type.typeFor(NativeArray.class);
      }

      public Type getElementType() {
         assert !this.elementType.isUnknown() : this + " has elementType=unknown";

         return this.elementType;
      }

      public int[] getPostsets() {
         assert this.postsets != null : this + " elementType=" + this.elementType + " has no postsets";

         return this.postsets;
      }

      private boolean presetsMatchElementType() {
         if (this.elementType == Type.INT) {
            return this.presets instanceof int[];
         } else {
            return this.elementType == Type.NUMBER ? this.presets instanceof double[] : this.presets instanceof Object[];
         }
      }

      public Object getPresets() {
         assert this.presets != null && this.presetsMatchElementType() : this + " doesn't have presets, or invalid preset type: " + this.presets;

         return this.presets;
      }

      public List<Splittable.SplitRange> getSplitRanges() {
         return this.splitRanges == null ? null : Collections.unmodifiableList(this.splitRanges);
      }

      public LiteralNode.ArrayLiteralNode setSplitRanges(LexicalContext lc, List<Splittable.SplitRange> splitRanges) {
         return this.splitRanges == splitRanges ? this : (LiteralNode.ArrayLiteralNode)Node.replaceInLexicalContext(lc, this, new LiteralNode.ArrayLiteralNode(this, (Expression[])this.value, this.elementType, this.postsets, this.presets, splitRanges));
      }

      public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
         return LexicalContextNode.Acceptor.accept(this, visitor);
      }

      public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
         if (visitor.enterLiteralNode(this)) {
            List<Expression> oldValue = Arrays.asList((Object[])this.value);
            List<Expression> newValue = Node.accept(visitor, oldValue);
            return visitor.leaveLiteralNode(oldValue != newValue ? this.setValue(lc, newValue) : this);
         } else {
            return this;
         }
      }

      private LiteralNode.ArrayLiteralNode setValue(LexicalContext lc, Expression[] value) {
         return this.value == value ? this : (LiteralNode.ArrayLiteralNode)Node.replaceInLexicalContext(lc, this, new LiteralNode.ArrayLiteralNode(this, value, this.elementType, this.postsets, this.presets, this.splitRanges));
      }

      private LiteralNode.ArrayLiteralNode setValue(LexicalContext lc, List<Expression> value) {
         return this.setValue(lc, (Expression[])value.toArray(new Expression[value.size()]));
      }

      public void toString(StringBuilder sb, boolean printType) {
         sb.append('[');
         boolean first = true;
         Expression[] var4 = (Expression[])this.value;
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Node node = var4[var6];
            if (!first) {
               sb.append(',');
               sb.append(' ');
            }

            if (node == null) {
               sb.append("undefined");
            } else {
               node.toString(sb, printType);
            }

            first = false;
         }

         sb.append(']');
      }

      // $FF: synthetic method
      ArrayLiteralNode(LiteralNode.ArrayLiteralNode x0, Expression[] x1, Type x2, int[] x3, Object x4, List x5, Object x6) {
         this(x0, x1, x2, x3, x4, x5);
      }

      private static final class ArrayLiteralInitializer {
         static LiteralNode.ArrayLiteralNode initialize(LiteralNode.ArrayLiteralNode node) {
            Type elementType = computeElementType((Expression[])node.value);
            int[] postsets = computePostsets((Expression[])node.value);
            Object presets = computePresets((Expression[])node.value, elementType, postsets);
            return new LiteralNode.ArrayLiteralNode(node, (Expression[])node.value, elementType, postsets, presets, node.splitRanges);
         }

         private static Type computeElementType(Expression[] value) {
            Type widestElementType = Type.INT;
            Expression[] var2 = value;
            int var3 = value.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Expression elem = var2[var4];
               if (elem == null) {
                  widestElementType = ((Type)widestElementType).widest(Type.OBJECT);
                  break;
               }

               Type type = elem.getType().isUnknown() ? Type.OBJECT : elem.getType();
               if (type.isBoolean()) {
                  widestElementType = ((Type)widestElementType).widest(Type.OBJECT);
                  break;
               }

               widestElementType = ((Type)widestElementType).widest(type);
               if (((Type)widestElementType).isObject()) {
                  break;
               }
            }

            return (Type)widestElementType;
         }

         private static int[] computePostsets(Expression[] value) {
            int[] computed = new int[value.length];
            int nComputed = 0;

            for(int i = 0; i < value.length; ++i) {
               Expression element = value[i];
               if (element == null || !LiteralNode.isConstant(element)) {
                  computed[nComputed++] = i;
               }
            }

            return Arrays.copyOf(computed, nComputed);
         }

         private static boolean setArrayElement(int[] array, int i, Object n) {
            if (n instanceof Number) {
               array[i] = ((Number)n).intValue();
               return true;
            } else {
               return false;
            }
         }

         private static boolean setArrayElement(long[] array, int i, Object n) {
            if (n instanceof Number) {
               array[i] = ((Number)n).longValue();
               return true;
            } else {
               return false;
            }
         }

         private static boolean setArrayElement(double[] array, int i, Object n) {
            if (n instanceof Number) {
               array[i] = ((Number)n).doubleValue();
               return true;
            } else {
               return false;
            }
         }

         private static int[] presetIntArray(Expression[] value, int[] postsets) {
            int[] array = new int[value.length];
            int nComputed = 0;

            for(int i = 0; i < value.length; ++i) {
               assert setArrayElement(array, i, LiteralNode.objectAsConstant(value[i])) || postsets[nComputed++] == i;
            }

            assert postsets.length == nComputed;

            return array;
         }

         private static long[] presetLongArray(Expression[] value, int[] postsets) {
            long[] array = new long[value.length];
            int nComputed = 0;

            for(int i = 0; i < value.length; ++i) {
               assert setArrayElement(array, i, LiteralNode.objectAsConstant(value[i])) || postsets[nComputed++] == i;
            }

            assert postsets.length == nComputed;

            return array;
         }

         private static double[] presetDoubleArray(Expression[] value, int[] postsets) {
            double[] array = new double[value.length];
            int nComputed = 0;

            for(int i = 0; i < value.length; ++i) {
               assert setArrayElement(array, i, LiteralNode.objectAsConstant(value[i])) || postsets[nComputed++] == i;
            }

            assert postsets.length == nComputed;

            return array;
         }

         private static Object[] presetObjectArray(Expression[] value, int[] postsets) {
            Object[] array = new Object[value.length];
            int nComputed = 0;

            for(int i = 0; i < value.length; ++i) {
               Node node = value[i];
               if (node == null) {
                  assert postsets[nComputed++] == i;
               } else {
                  Object element = LiteralNode.objectAsConstant(node);
                  if (element != LiteralNode.POSTSET_MARKER) {
                     array[i] = element;
                  } else {
                     assert postsets[nComputed++] == i;
                  }
               }
            }

            assert postsets.length == nComputed;

            return array;
         }

         static Object computePresets(Expression[] value, Type elementType, int[] postsets) {
            assert !elementType.isUnknown();

            if (elementType.isInteger()) {
               return presetIntArray(value, postsets);
            } else {
               return elementType.isNumeric() ? presetDoubleArray(value, postsets) : presetObjectArray(value, postsets);
            }
         }
      }
   }

   private static final class NullLiteralNode extends LiteralNode.PrimitiveLiteralNode<Object> {
      private static final long serialVersionUID = 1L;

      private NullLiteralNode(long token, int finish) {
         super(Token.recast(token, TokenType.OBJECT), finish, (Object)null, null);
      }

      public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
         return (Node)(visitor.enterLiteralNode(this) ? visitor.leaveLiteralNode(this) : this);
      }

      public Type getType() {
         return Type.OBJECT;
      }

      public Type getWidestOperationType() {
         return Type.OBJECT;
      }

      // $FF: synthetic method
      NullLiteralNode(long x0, int x1, Object x2) {
         this(x0, x1);
      }
   }

   @Immutable
   private static class LexerTokenLiteralNode extends LiteralNode<Lexer.LexerToken> {
      private static final long serialVersionUID = 1L;

      private LexerTokenLiteralNode(long token, int finish, Lexer.LexerToken value) {
         super(Token.recast(token, TokenType.STRING), finish, value);
      }

      private LexerTokenLiteralNode(LiteralNode.LexerTokenLiteralNode literalNode) {
         super(literalNode);
      }

      public Type getType() {
         return Type.OBJECT;
      }

      public void toString(StringBuilder sb, boolean printType) {
         sb.append(((Lexer.LexerToken)this.value).toString());
      }

      // $FF: synthetic method
      LexerTokenLiteralNode(long x0, int x1, Lexer.LexerToken x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   @Immutable
   private static class StringLiteralNode extends LiteralNode.PrimitiveLiteralNode<String> {
      private static final long serialVersionUID = 1L;

      private StringLiteralNode(long token, int finish, String value) {
         super(Token.recast(token, TokenType.STRING), finish, value, null);
      }

      private StringLiteralNode(LiteralNode.StringLiteralNode literalNode) {
         super(literalNode, null);
      }

      public void toString(StringBuilder sb, boolean printType) {
         sb.append('"');
         sb.append((String)this.value);
         sb.append('"');
      }

      // $FF: synthetic method
      StringLiteralNode(long x0, int x1, String x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static class UndefinedLiteralNode extends LiteralNode.PrimitiveLiteralNode<Undefined> {
      private static final long serialVersionUID = 1L;

      private UndefinedLiteralNode(long token, int finish) {
         super(Token.recast(token, TokenType.OBJECT), finish, ScriptRuntime.UNDEFINED, null);
      }

      private UndefinedLiteralNode(LiteralNode.UndefinedLiteralNode literalNode) {
         super(literalNode, null);
      }

      // $FF: synthetic method
      UndefinedLiteralNode(long x0, int x1, Object x2) {
         this(x0, x1);
      }
   }

   @Immutable
   private static final class NumberLiteralNode extends LiteralNode.PrimitiveLiteralNode<Number> {
      private static final long serialVersionUID = 1L;
      private final Type type;

      private NumberLiteralNode(long token, int finish, Number value) {
         super(Token.recast(token, TokenType.DECIMAL), finish, value, null);
         this.type = numberGetType((Number)this.value);
      }

      private NumberLiteralNode(LiteralNode.NumberLiteralNode literalNode) {
         super(literalNode, null);
         this.type = numberGetType((Number)this.value);
      }

      private static Type numberGetType(Number number) {
         if (number instanceof Integer) {
            return Type.INT;
         } else if (number instanceof Double) {
            return Type.NUMBER;
         } else {
            assert false;

            return null;
         }
      }

      public Type getType() {
         return this.type;
      }

      public Type getWidestOperationType() {
         return this.getType();
      }

      // $FF: synthetic method
      NumberLiteralNode(long x0, int x1, Number x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   @Immutable
   private static final class BooleanLiteralNode extends LiteralNode.PrimitiveLiteralNode<Boolean> {
      private static final long serialVersionUID = 1L;

      private BooleanLiteralNode(long token, int finish, boolean value) {
         super(Token.recast(token, value ? TokenType.TRUE : TokenType.FALSE), finish, value, null);
      }

      private BooleanLiteralNode(LiteralNode.BooleanLiteralNode literalNode) {
         super(literalNode, null);
      }

      public boolean isTrue() {
         return (Boolean)this.value;
      }

      public Type getType() {
         return Type.BOOLEAN;
      }

      public Type getWidestOperationType() {
         return Type.BOOLEAN;
      }

      // $FF: synthetic method
      BooleanLiteralNode(long x0, int x1, boolean x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   public static class PrimitiveLiteralNode<T> extends LiteralNode<T> {
      private static final long serialVersionUID = 1L;

      private PrimitiveLiteralNode(long token, int finish, T value) {
         super(token, finish, value);
      }

      private PrimitiveLiteralNode(LiteralNode.PrimitiveLiteralNode<T> literalNode) {
         super(literalNode);
      }

      public boolean isTrue() {
         return JSType.toBoolean(this.value);
      }

      public boolean isLocal() {
         return true;
      }

      public boolean isAlwaysFalse() {
         return !this.isTrue();
      }

      public boolean isAlwaysTrue() {
         return this.isTrue();
      }

      // $FF: synthetic method
      PrimitiveLiteralNode(long x0, int x1, Object x2, Object x3) {
         this(x0, x1, x2);
      }

      // $FF: synthetic method
      PrimitiveLiteralNode(LiteralNode.PrimitiveLiteralNode x0, Object x1) {
         this(x0);
      }
   }
}
