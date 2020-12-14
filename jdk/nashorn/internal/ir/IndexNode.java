package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class IndexNode extends BaseNode {
   private static final long serialVersionUID = 1L;
   private final Expression index;

   public IndexNode(long token, int finish, Expression base, Expression index) {
      super(token, finish, base, false);
      this.index = index;
   }

   private IndexNode(IndexNode indexNode, Expression base, Expression index, boolean isFunction, Type type, int programPoint) {
      super(indexNode, base, isFunction, type, programPoint);
      this.index = index;
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterIndexNode(this) ? visitor.leaveIndexNode(this.setBase((Expression)this.base.accept(visitor)).setIndex((Expression)this.index.accept(visitor))) : this);
   }

   public void toString(StringBuilder sb, boolean printType) {
      boolean needsParen = this.tokenType().needsParens(this.base.tokenType(), true);
      if (needsParen) {
         sb.append('(');
      }

      if (printType) {
         this.optimisticTypeToString(sb);
      }

      this.base.toString(sb, printType);
      if (needsParen) {
         sb.append(')');
      }

      sb.append('[');
      this.index.toString(sb, printType);
      sb.append(']');
   }

   public Expression getIndex() {
      return this.index;
   }

   private IndexNode setBase(Expression base) {
      return this.base == base ? this : new IndexNode(this, base, this.index, this.isFunction(), this.type, this.programPoint);
   }

   public IndexNode setIndex(Expression index) {
      return this.index == index ? this : new IndexNode(this, this.base, index, this.isFunction(), this.type, this.programPoint);
   }

   public IndexNode setType(Type type) {
      return this.type == type ? this : new IndexNode(this, this.base, this.index, this.isFunction(), type, this.programPoint);
   }

   public IndexNode setIsFunction() {
      return this.isFunction() ? this : new IndexNode(this, this.base, this.index, true, this.type, this.programPoint);
   }

   public IndexNode setProgramPoint(int programPoint) {
      return this.programPoint == programPoint ? this : new IndexNode(this, this.base, this.index, this.isFunction(), this.type, programPoint);
   }
}
