package jdk.nashorn.internal.runtime.regexp.joni.ast;

import java.util.Set;
import jdk.nashorn.internal.runtime.regexp.joni.WarnCallback;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

public final class ConsAltNode extends Node {
   public Node car;
   public ConsAltNode cdr;
   private int type;

   private ConsAltNode(Node car, ConsAltNode cdr, int type) {
      this.car = car;
      if (car != null) {
         car.parent = this;
      }

      this.cdr = cdr;
      if (cdr != null) {
         cdr.parent = this;
      }

      this.type = type;
   }

   public static ConsAltNode newAltNode(Node left, ConsAltNode right) {
      return new ConsAltNode(left, right, 9);
   }

   public static ConsAltNode newListNode(Node left, ConsAltNode right) {
      return new ConsAltNode(left, right, 8);
   }

   public static ConsAltNode listAdd(ConsAltNode listp, Node x) {
      ConsAltNode n = newListNode(x, (ConsAltNode)null);
      ConsAltNode list = listp;
      if (listp != null) {
         while(true) {
            if (list.cdr == null) {
               list.setCdr(n);
               break;
            }

            list = list.cdr;
         }
      }

      return n;
   }

   public void toListNode() {
      this.type = 8;
   }

   public void toAltNode() {
      this.type = 9;
   }

   public int getType() {
      return this.type;
   }

   protected void setChild(Node newChild) {
      this.car = newChild;
   }

   protected Node getChild() {
      return this.car;
   }

   public void swap(Node with) {
      if (this.cdr != null) {
         this.cdr.parent = with;
         if (with instanceof ConsAltNode) {
            ConsAltNode withCan = (ConsAltNode)with;
            withCan.cdr.parent = this;
            ConsAltNode tmp = this.cdr;
            this.cdr = withCan.cdr;
            withCan.cdr = tmp;
         }
      }

      super.swap(with);
   }

   public void verifyTree(Set<Node> set, WarnCallback warnings) {
      if (!set.contains(this)) {
         set.add(this);
         if (this.car != null) {
            if (this.car.parent != this) {
               warnings.warn("broken list car: " + this.getAddressName() + " -> " + this.car.getAddressName());
            }

            this.car.verifyTree(set, warnings);
         }

         if (this.cdr != null) {
            if (this.cdr.parent != this) {
               warnings.warn("broken list cdr: " + this.getAddressName() + " -> " + this.cdr.getAddressName());
            }

            this.cdr.verifyTree(set, warnings);
         }
      }

   }

   public Node setCar(Node ca) {
      this.car = ca;
      ca.parent = this;
      return this.car;
   }

   public ConsAltNode setCdr(ConsAltNode cd) {
      this.cdr = cd;
      cd.parent = this;
      return this.cdr;
   }

   public String getName() {
      switch(this.type) {
      case 8:
         return "List";
      case 9:
         return "Alt";
      default:
         throw new InternalException("internal parser error (bug)");
      }
   }

   public String toString(int level) {
      StringBuilder value = new StringBuilder();
      value.append("\n  car: " + pad(this.car, level + 1));
      value.append("\n  cdr: " + (this.cdr == null ? "NULL" : this.cdr.toString()));
      return value.toString();
   }
}
