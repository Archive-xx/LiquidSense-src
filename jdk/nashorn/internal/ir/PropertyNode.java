package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class PropertyNode extends Node {
   private static final long serialVersionUID = 1L;
   private final PropertyKey key;
   private final Expression value;
   private final FunctionNode getter;
   private final FunctionNode setter;

   public PropertyNode(long token, int finish, PropertyKey key, Expression value, FunctionNode getter, FunctionNode setter) {
      super(token, finish);
      this.key = key;
      this.value = value;
      this.getter = getter;
      this.setter = setter;
   }

   private PropertyNode(PropertyNode propertyNode, PropertyKey key, Expression value, FunctionNode getter, FunctionNode setter) {
      super(propertyNode);
      this.key = key;
      this.value = value;
      this.getter = getter;
      this.setter = setter;
   }

   public String getKeyName() {
      return this.key.getPropertyName();
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterPropertyNode(this) ? visitor.leavePropertyNode(this.setKey((PropertyKey)((Node)this.key).accept(visitor)).setValue(this.value == null ? null : (Expression)this.value.accept(visitor)).setGetter(this.getter == null ? null : (FunctionNode)this.getter.accept(visitor)).setSetter(this.setter == null ? null : (FunctionNode)this.setter.accept(visitor))) : this);
   }

   public void toString(StringBuilder sb, boolean printType) {
      if (this.value instanceof FunctionNode && ((FunctionNode)this.value).getIdent() != null) {
         this.value.toString(sb);
      }

      if (this.value != null) {
         ((Node)this.key).toString(sb, printType);
         sb.append(": ");
         this.value.toString(sb, printType);
      }

      if (this.getter != null) {
         sb.append(' ');
         this.getter.toString(sb, printType);
      }

      if (this.setter != null) {
         sb.append(' ');
         this.setter.toString(sb, printType);
      }

   }

   public FunctionNode getGetter() {
      return this.getter;
   }

   public PropertyNode setGetter(FunctionNode getter) {
      return this.getter == getter ? this : new PropertyNode(this, this.key, this.value, getter, this.setter);
   }

   public Expression getKey() {
      return (Expression)this.key;
   }

   private PropertyNode setKey(PropertyKey key) {
      return this.key == key ? this : new PropertyNode(this, key, this.value, this.getter, this.setter);
   }

   public FunctionNode getSetter() {
      return this.setter;
   }

   public PropertyNode setSetter(FunctionNode setter) {
      return this.setter == setter ? this : new PropertyNode(this, this.key, this.value, this.getter, setter);
   }

   public Expression getValue() {
      return this.value;
   }

   public PropertyNode setValue(Expression value) {
      return this.value == value ? this : new PropertyNode(this, this.key, value, this.getter, this.setter);
   }
}
