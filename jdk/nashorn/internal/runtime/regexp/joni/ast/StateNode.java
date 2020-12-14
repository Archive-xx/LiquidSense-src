package jdk.nashorn.internal.runtime.regexp.joni.ast;

import jdk.nashorn.internal.runtime.regexp.joni.constants.NodeStatus;

public abstract class StateNode extends Node implements NodeStatus {
   protected int state;

   public String toString(int level) {
      return "\n  state: " + this.stateToString();
   }

   public String stateToString() {
      StringBuilder states = new StringBuilder();
      if (this.isMinFixed()) {
         states.append("MIN_FIXED ");
      }

      if (this.isMaxFixed()) {
         states.append("MAX_FIXED ");
      }

      if (this.isMark1()) {
         states.append("MARK1 ");
      }

      if (this.isMark2()) {
         states.append("MARK2 ");
      }

      if (this.isMemBackrefed()) {
         states.append("MEM_BACKREFED ");
      }

      if (this.isStopBtSimpleRepeat()) {
         states.append("STOP_BT_SIMPLE_REPEAT ");
      }

      if (this.isRecursion()) {
         states.append("RECURSION ");
      }

      if (this.isCalled()) {
         states.append("CALLED ");
      }

      if (this.isAddrFixed()) {
         states.append("ADDR_FIXED ");
      }

      if (this.isInRepeat()) {
         states.append("IN_REPEAT ");
      }

      if (this.isNestLevel()) {
         states.append("NEST_LEVEL ");
      }

      if (this.isByNumber()) {
         states.append("BY_NUMBER ");
      }

      return states.toString();
   }

   public boolean isMinFixed() {
      return (this.state & 1) != 0;
   }

   public void setMinFixed() {
      this.state |= 1;
   }

   public boolean isMaxFixed() {
      return (this.state & 2) != 0;
   }

   public void setMaxFixed() {
      this.state |= 2;
   }

   public boolean isCLenFixed() {
      return (this.state & 4) != 0;
   }

   public void setCLenFixed() {
      this.state |= 4;
   }

   public boolean isMark1() {
      return (this.state & 8) != 0;
   }

   public void setMark1() {
      this.state |= 8;
   }

   public boolean isMark2() {
      return (this.state & 16) != 0;
   }

   public void setMark2() {
      this.state |= 16;
   }

   public void clearMark2() {
      this.state &= -17;
   }

   public boolean isMemBackrefed() {
      return (this.state & 32) != 0;
   }

   public void setMemBackrefed() {
      this.state |= 32;
   }

   public boolean isStopBtSimpleRepeat() {
      return (this.state & 64) != 0;
   }

   public void setStopBtSimpleRepeat() {
      this.state |= 64;
   }

   public boolean isRecursion() {
      return (this.state & 128) != 0;
   }

   public void setRecursion() {
      this.state |= 128;
   }

   public boolean isCalled() {
      return (this.state & 256) != 0;
   }

   public void setCalled() {
      this.state |= 256;
   }

   public boolean isAddrFixed() {
      return (this.state & 512) != 0;
   }

   public void setAddrFixed() {
      this.state |= 512;
   }

   public boolean isInRepeat() {
      return (this.state & 4096) != 0;
   }

   public void setInRepeat() {
      this.state |= 4096;
   }

   public boolean isNestLevel() {
      return (this.state & 8192) != 0;
   }

   public void setNestLevel() {
      this.state |= 8192;
   }

   public boolean isByNumber() {
      return (this.state & 16384) != 0;
   }

   public void setByNumber() {
      this.state |= 16384;
   }
}
