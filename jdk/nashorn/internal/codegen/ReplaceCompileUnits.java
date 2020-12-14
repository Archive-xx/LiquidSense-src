package jdk.nashorn.internal.codegen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.ir.CompileUnitHolder;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.LiteralNode;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.ObjectNode;
import jdk.nashorn.internal.ir.Splittable;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;

abstract class ReplaceCompileUnits extends SimpleNodeVisitor {
   abstract CompileUnit getReplacement(CompileUnit var1);

   CompileUnit getExistingReplacement(CompileUnitHolder node) {
      CompileUnit oldUnit = node.getCompileUnit();

      assert oldUnit != null;

      CompileUnit newUnit = this.getReplacement(oldUnit);

      assert newUnit != null;

      return newUnit;
   }

   public Node leaveFunctionNode(FunctionNode node) {
      return node.setCompileUnit(this.lc, this.getExistingReplacement(node));
   }

   public Node leaveLiteralNode(LiteralNode<?> node) {
      if (!(node instanceof LiteralNode.ArrayLiteralNode)) {
         return node;
      } else {
         LiteralNode.ArrayLiteralNode aln = (LiteralNode.ArrayLiteralNode)node;
         if (aln.getSplitRanges() == null) {
            return node;
         } else {
            List<Splittable.SplitRange> newArrayUnits = new ArrayList();
            Iterator var4 = aln.getSplitRanges().iterator();

            while(var4.hasNext()) {
               Splittable.SplitRange au = (Splittable.SplitRange)var4.next();
               newArrayUnits.add(new Splittable.SplitRange(this.getExistingReplacement(au), au.getLow(), au.getHigh()));
            }

            return aln.setSplitRanges(this.lc, newArrayUnits);
         }
      }
   }

   public Node leaveObjectNode(ObjectNode objectNode) {
      List<Splittable.SplitRange> ranges = objectNode.getSplitRanges();
      if (ranges == null) {
         return super.leaveObjectNode(objectNode);
      } else {
         List<Splittable.SplitRange> newRanges = new ArrayList();
         Iterator var4 = ranges.iterator();

         while(var4.hasNext()) {
            Splittable.SplitRange range = (Splittable.SplitRange)var4.next();
            newRanges.add(new Splittable.SplitRange(this.getExistingReplacement(range), range.getLow(), range.getHigh()));
         }

         return objectNode.setSplitRanges(this.lc, newRanges);
      }
   }
}
