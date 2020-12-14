package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.codegen.types.Type;

public final class LocalVariableConversion {
   private final Symbol symbol;
   private final Type from;
   private final Type to;
   private final LocalVariableConversion next;

   public LocalVariableConversion(Symbol symbol, Type from, Type to, LocalVariableConversion next) {
      this.symbol = symbol;
      this.from = from;
      this.to = to;
      this.next = next;
   }

   public Type getFrom() {
      return this.from;
   }

   public Type getTo() {
      return this.to;
   }

   public LocalVariableConversion getNext() {
      return this.next;
   }

   public Symbol getSymbol() {
      return this.symbol;
   }

   public boolean isLive() {
      return this.symbol.hasSlotFor(this.to);
   }

   public boolean isAnyLive() {
      return this.isLive() || isAnyLive(this.next);
   }

   public static boolean hasLiveConversion(JoinPredecessor jp) {
      return isAnyLive(jp.getLocalVariableConversion());
   }

   private static boolean isAnyLive(LocalVariableConversion conv) {
      return conv != null && conv.isAnyLive();
   }

   public String toString() {
      return this.toString(new StringBuilder()).toString();
   }

   public StringBuilder toString(StringBuilder sb) {
      if (this.isLive()) {
         return this.toStringNext(sb.append('⟦'), true).append("⟧ ");
      } else {
         return this.next == null ? sb : this.next.toString(sb);
      }
   }

   public static StringBuilder toString(LocalVariableConversion conv, StringBuilder sb) {
      return conv == null ? sb : conv.toString(sb);
   }

   private StringBuilder toStringNext(StringBuilder sb, boolean first) {
      if (this.isLive()) {
         if (!first) {
            sb.append(", ");
         }

         sb.append(this.symbol.getName()).append(':').append(getTypeChar(this.from)).append('→').append(getTypeChar(this.to));
         return this.next == null ? sb : this.next.toStringNext(sb, false);
      } else {
         return this.next == null ? sb : this.next.toStringNext(sb, first);
      }
   }

   private static char getTypeChar(Type type) {
      if (type == Type.UNDEFINED) {
         return 'U';
      } else if (type.isObject()) {
         return 'O';
      } else {
         return type == Type.BOOLEAN ? 'Z' : type.getBytecodeStackType();
      }
   }
}
