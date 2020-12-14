package jdk.nashorn.internal.ir;

public interface JoinPredecessor {
   JoinPredecessor setLocalVariableConversion(LexicalContext var1, LocalVariableConversion var2);

   LocalVariableConversion getLocalVariableConversion();
}
