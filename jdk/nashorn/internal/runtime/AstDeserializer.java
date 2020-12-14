package jdk.nashorn.internal.runtime;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.InflaterInputStream;
import jdk.nashorn.internal.ir.FunctionNode;

final class AstDeserializer {
   static FunctionNode deserialize(byte[] serializedAst) {
      try {
         return (FunctionNode)(new ObjectInputStream(new InflaterInputStream(new ByteArrayInputStream(serializedAst)))).readObject();
      } catch (IOException | ClassNotFoundException var2) {
         throw new AssertionError("Unexpected exception deserializing function", var2);
      }
   }
}
