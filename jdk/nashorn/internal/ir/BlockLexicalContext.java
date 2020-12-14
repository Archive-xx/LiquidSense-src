package jdk.nashorn.internal.ir;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BlockLexicalContext extends LexicalContext {
   private final Deque<List<Statement>> sstack = new ArrayDeque();
   protected Statement lastStatement;

   public <T extends LexicalContextNode> T push(T node) {
      T pushed = super.push(node);
      if (node instanceof Block) {
         this.sstack.push(new ArrayList());
      }

      return pushed;
   }

   protected List<Statement> popStatements() {
      return (List)this.sstack.pop();
   }

   protected Block afterSetStatements(Block block) {
      return block;
   }

   public <T extends Node> T pop(T node) {
      T expected = node;
      if (node instanceof Block) {
         List<Statement> newStatements = this.popStatements();
         T expected = ((Block)node).setStatements(this, newStatements);
         expected = this.afterSetStatements((Block)expected);
         if (!this.sstack.isEmpty()) {
            this.lastStatement = lastStatement((List)this.sstack.peek());
         }
      }

      return super.pop((Node)expected);
   }

   public void appendStatement(Statement statement) {
      assert statement != null;

      ((List)this.sstack.peek()).add(statement);
      this.lastStatement = statement;
   }

   public Node prependStatement(Statement statement) {
      assert statement != null;

      ((List)this.sstack.peek()).add(0, statement);
      return statement;
   }

   public void prependStatements(List<Statement> statements) {
      assert statements != null;

      ((List)this.sstack.peek()).addAll(0, statements);
   }

   public Statement getLastStatement() {
      return this.lastStatement;
   }

   private static Statement lastStatement(List<Statement> statements) {
      int s = statements.size();
      return s == 0 ? null : (Statement)statements.get(s - 1);
   }
}
