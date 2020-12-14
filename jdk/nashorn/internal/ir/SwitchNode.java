package jdk.nashorn.internal.ir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class SwitchNode extends BreakableStatement {
   private static final long serialVersionUID = 1L;
   private final Expression expression;
   private final List<CaseNode> cases;
   private final int defaultCaseIndex;
   private final boolean uniqueInteger;
   private final Symbol tag;

   public SwitchNode(int lineNumber, long token, int finish, Expression expression, List<CaseNode> cases, CaseNode defaultCase) {
      super(lineNumber, token, finish, new Label("switch_break"));
      this.expression = expression;
      this.cases = cases;
      this.defaultCaseIndex = defaultCase == null ? -1 : cases.indexOf(defaultCase);
      this.uniqueInteger = false;
      this.tag = null;
   }

   private SwitchNode(SwitchNode switchNode, Expression expression, List<CaseNode> cases, int defaultCaseIndex, LocalVariableConversion conversion, boolean uniqueInteger, Symbol tag) {
      super(switchNode, conversion);
      this.expression = expression;
      this.cases = cases;
      this.defaultCaseIndex = defaultCaseIndex;
      this.tag = tag;
      this.uniqueInteger = uniqueInteger;
   }

   public Node ensureUniqueLabels(LexicalContext lc) {
      List<CaseNode> newCases = new ArrayList();
      Iterator var3 = this.cases.iterator();

      while(var3.hasNext()) {
         CaseNode caseNode = (CaseNode)var3.next();
         newCases.add(new CaseNode(caseNode, caseNode.getTest(), caseNode.getBody(), caseNode.getLocalVariableConversion()));
      }

      return (Node)Node.replaceInLexicalContext(lc, this, new SwitchNode(this, this.expression, newCases, this.defaultCaseIndex, this.conversion, this.uniqueInteger, this.tag));
   }

   public boolean isTerminal() {
      if (!this.cases.isEmpty() && this.defaultCaseIndex != -1) {
         Iterator var1 = this.cases.iterator();

         CaseNode caseNode;
         do {
            if (!var1.hasNext()) {
               return true;
            }

            caseNode = (CaseNode)var1.next();
         } while(caseNode.isTerminal());

         return false;
      } else {
         return false;
      }
   }

   public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterSwitchNode(this) ? visitor.leaveSwitchNode(this.setExpression(lc, (Expression)this.expression.accept(visitor)).setCases(lc, Node.accept(visitor, this.cases), this.defaultCaseIndex)) : this);
   }

   public void toString(StringBuilder sb, boolean printType) {
      sb.append("switch (");
      this.expression.toString(sb, printType);
      sb.append(')');
   }

   public CaseNode getDefaultCase() {
      return this.defaultCaseIndex == -1 ? null : (CaseNode)this.cases.get(this.defaultCaseIndex);
   }

   public List<CaseNode> getCases() {
      return Collections.unmodifiableList(this.cases);
   }

   public SwitchNode setCases(LexicalContext lc, List<CaseNode> cases) {
      return this.setCases(lc, cases, this.defaultCaseIndex);
   }

   private SwitchNode setCases(LexicalContext lc, List<CaseNode> cases, int defaultCaseIndex) {
      return this.cases == cases ? this : (SwitchNode)Node.replaceInLexicalContext(lc, this, new SwitchNode(this, this.expression, cases, defaultCaseIndex, this.conversion, this.uniqueInteger, this.tag));
   }

   public SwitchNode setCases(LexicalContext lc, List<CaseNode> cases, CaseNode defaultCase) {
      return this.setCases(lc, cases, defaultCase == null ? -1 : cases.indexOf(defaultCase));
   }

   public Expression getExpression() {
      return this.expression;
   }

   public SwitchNode setExpression(LexicalContext lc, Expression expression) {
      return this.expression == expression ? this : (SwitchNode)Node.replaceInLexicalContext(lc, this, new SwitchNode(this, expression, this.cases, this.defaultCaseIndex, this.conversion, this.uniqueInteger, this.tag));
   }

   public Symbol getTag() {
      return this.tag;
   }

   public SwitchNode setTag(LexicalContext lc, Symbol tag) {
      return this.tag == tag ? this : (SwitchNode)Node.replaceInLexicalContext(lc, this, new SwitchNode(this, this.expression, this.cases, this.defaultCaseIndex, this.conversion, this.uniqueInteger, tag));
   }

   public boolean isUniqueInteger() {
      return this.uniqueInteger;
   }

   public SwitchNode setUniqueInteger(LexicalContext lc, boolean uniqueInteger) {
      return this.uniqueInteger == uniqueInteger ? this : (SwitchNode)Node.replaceInLexicalContext(lc, this, new SwitchNode(this, this.expression, this.cases, this.defaultCaseIndex, this.conversion, uniqueInteger, this.tag));
   }

   JoinPredecessor setLocalVariableConversionChanged(LexicalContext lc, LocalVariableConversion conversion) {
      return (JoinPredecessor)Node.replaceInLexicalContext(lc, this, new SwitchNode(this, this.expression, this.cases, this.defaultCaseIndex, conversion, this.uniqueInteger, this.tag));
   }
}
