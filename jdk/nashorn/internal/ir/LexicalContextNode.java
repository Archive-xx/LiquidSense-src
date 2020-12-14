package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.ir.visitor.NodeVisitor;

public interface LexicalContextNode {
   Node accept(LexicalContext var1, NodeVisitor<? extends LexicalContext> var2);

   public static class Acceptor {
      static Node accept(LexicalContextNode node, NodeVisitor<? extends LexicalContext> visitor) {
         LexicalContext lc = visitor.getLexicalContext();
         lc.push(node);
         Node newNode = node.accept(lc, visitor);
         return lc.pop(newNode);
      }
   }
}
