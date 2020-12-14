package jdk.nashorn.internal.ir;

public interface Flags<T extends LexicalContextNode> {
   int getFlags();

   boolean getFlag(int var1);

   T clearFlag(LexicalContext var1, int var2);

   T setFlag(LexicalContext var1, int var2);

   T setFlags(LexicalContext var1, int var2);
}
