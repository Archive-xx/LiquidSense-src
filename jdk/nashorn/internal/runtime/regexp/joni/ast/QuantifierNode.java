package jdk.nashorn.internal.runtime.regexp.joni.ast;

import jdk.nashorn.internal.runtime.regexp.joni.ScanEnvironment;

public final class QuantifierNode extends StateNode {
   public Node target;
   public int lower;
   public int upper;
   public boolean greedy;
   public int targetEmptyInfo;
   public Node headExact;
   public Node nextHeadExact;
   public boolean isRefered;
   private static final QuantifierNode.ReduceType[][] REDUCE_TABLE;
   private static final String[] PopularQStr;
   private static final String[] ReduceQStr;
   public static final int REPEAT_INFINITE = -1;

   public QuantifierNode(int lower, int upper, boolean byNumber) {
      this.lower = lower;
      this.upper = upper;
      this.greedy = true;
      this.targetEmptyInfo = 0;
      if (byNumber) {
         this.setByNumber();
      }

   }

   public int getType() {
      return 5;
   }

   protected void setChild(Node newChild) {
      this.target = newChild;
   }

   protected Node getChild() {
      return this.target;
   }

   public void setTarget(Node tgt) {
      this.target = tgt;
      tgt.parent = this;
   }

   public StringNode convertToString(int flag) {
      StringNode sn = new StringNode();
      sn.flag = flag;
      sn.swap(this);
      return sn;
   }

   public String getName() {
      return "Quantifier";
   }

   public String toString(int level) {
      StringBuilder value = new StringBuilder(super.toString(level));
      value.append("\n  target: " + pad(this.target, level + 1));
      value.append("\n  lower: " + this.lower);
      value.append("\n  upper: " + this.upper);
      value.append("\n  greedy: " + this.greedy);
      value.append("\n  targetEmptyInfo: " + this.targetEmptyInfo);
      value.append("\n  headExact: " + pad(this.headExact, level + 1));
      value.append("\n  nextHeadExact: " + pad(this.nextHeadExact, level + 1));
      value.append("\n  isRefered: " + this.isRefered);
      return value.toString();
   }

   public boolean isAnyCharStar() {
      return this.greedy && isRepeatInfinite(this.upper) && this.target.getType() == 3;
   }

   protected int popularNum() {
      if (this.greedy) {
         if (this.lower == 0) {
            if (this.upper == 1) {
               return 0;
            }

            if (isRepeatInfinite(this.upper)) {
               return 1;
            }
         } else if (this.lower == 1 && isRepeatInfinite(this.upper)) {
            return 2;
         }
      } else if (this.lower == 0) {
         if (this.upper == 1) {
            return 3;
         }

         if (isRepeatInfinite(this.upper)) {
            return 4;
         }
      } else if (this.lower == 1 && isRepeatInfinite(this.upper)) {
         return 5;
      }

      return -1;
   }

   protected void set(QuantifierNode other) {
      this.setTarget(other.target);
      other.target = null;
      this.lower = other.lower;
      this.upper = other.upper;
      this.greedy = other.greedy;
      this.targetEmptyInfo = other.targetEmptyInfo;
      this.headExact = other.headExact;
      this.nextHeadExact = other.nextHeadExact;
      this.isRefered = other.isRefered;
   }

   public void reduceNestedQuantifier(QuantifierNode other) {
      int pnum = this.popularNum();
      int cnum = other.popularNum();
      if (pnum >= 0 && cnum >= 0) {
         switch(REDUCE_TABLE[cnum][pnum]) {
         case DEL:
            this.set(other);
            break;
         case A:
            this.setTarget(other.target);
            this.lower = 0;
            this.upper = -1;
            this.greedy = true;
            break;
         case AQ:
            this.setTarget(other.target);
            this.lower = 0;
            this.upper = -1;
            this.greedy = false;
            break;
         case QQ:
            this.setTarget(other.target);
            this.lower = 0;
            this.upper = 1;
            this.greedy = false;
            break;
         case P_QQ:
            this.setTarget(other);
            this.lower = 0;
            this.upper = 1;
            this.greedy = false;
            other.lower = 1;
            other.upper = -1;
            other.greedy = true;
            return;
         case PQ_Q:
            this.setTarget(other);
            this.lower = 0;
            this.upper = 1;
            this.greedy = true;
            other.lower = 1;
            other.upper = -1;
            other.greedy = false;
            return;
         case ASIS:
            this.setTarget(other);
            return;
         }

         other.target = null;
      }
   }

   public int setQuantifier(Node tgt, boolean group, ScanEnvironment env, char[] chars, int p, int end) {
      if (this.lower == 1 && this.upper == 1) {
         return 1;
      } else {
         switch(tgt.getType()) {
         case 0:
            if (!group) {
               StringNode sn = (StringNode)tgt;
               if (sn.canBeSplit()) {
                  StringNode n = sn.splitLastChar();
                  if (n != null) {
                     this.setTarget(n);
                     return 2;
                  }
               }
            }
            break;
         case 5:
            QuantifierNode qnt = (QuantifierNode)tgt;
            int nestQNum = this.popularNum();
            int targetQNum = qnt.popularNum();
            if (targetQNum >= 0) {
               if (nestQNum >= 0) {
                  this.reduceNestedQuantifier(qnt);
                  return 0;
               }

               if ((targetQNum == 1 || targetQNum == 2) && !isRepeatInfinite(this.upper) && this.upper > 1 && this.greedy) {
                  this.upper = this.lower == 0 ? 1 : this.lower;
               }
            }
         }

         this.setTarget(tgt);
         return 0;
      }
   }

   public static boolean isRepeatInfinite(int n) {
      return n == -1;
   }

   static {
      REDUCE_TABLE = new QuantifierNode.ReduceType[][]{{QuantifierNode.ReduceType.DEL, QuantifierNode.ReduceType.A, QuantifierNode.ReduceType.A, QuantifierNode.ReduceType.QQ, QuantifierNode.ReduceType.AQ, QuantifierNode.ReduceType.ASIS}, {QuantifierNode.ReduceType.DEL, QuantifierNode.ReduceType.DEL, QuantifierNode.ReduceType.DEL, QuantifierNode.ReduceType.P_QQ, QuantifierNode.ReduceType.P_QQ, QuantifierNode.ReduceType.DEL}, {QuantifierNode.ReduceType.A, QuantifierNode.ReduceType.A, QuantifierNode.ReduceType.DEL, QuantifierNode.ReduceType.ASIS, QuantifierNode.ReduceType.P_QQ, QuantifierNode.ReduceType.DEL}, {QuantifierNode.ReduceType.DEL, QuantifierNode.ReduceType.AQ, QuantifierNode.ReduceType.AQ, QuantifierNode.ReduceType.DEL, QuantifierNode.ReduceType.AQ, QuantifierNode.ReduceType.AQ}, {QuantifierNode.ReduceType.DEL, QuantifierNode.ReduceType.DEL, QuantifierNode.ReduceType.DEL, QuantifierNode.ReduceType.DEL, QuantifierNode.ReduceType.DEL, QuantifierNode.ReduceType.DEL}, {QuantifierNode.ReduceType.ASIS, QuantifierNode.ReduceType.PQ_Q, QuantifierNode.ReduceType.DEL, QuantifierNode.ReduceType.AQ, QuantifierNode.ReduceType.AQ, QuantifierNode.ReduceType.DEL}};
      PopularQStr = new String[]{"?", "*", "+", "??", "*?", "+?"};
      ReduceQStr = new String[]{"", "", "*", "*?", "??", "+ and ??", "+? and ?"};
   }

   static enum ReduceType {
      ASIS,
      DEL,
      A,
      AQ,
      QQ,
      P_QQ,
      PQ_Q;
   }
}
