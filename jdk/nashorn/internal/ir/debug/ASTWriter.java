package jdk.nashorn.internal.ir.debug;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.nashorn.internal.ir.BinaryNode;
import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.Statement;
import jdk.nashorn.internal.ir.Symbol;
import jdk.nashorn.internal.ir.Terminal;
import jdk.nashorn.internal.ir.TernaryNode;
import jdk.nashorn.internal.ir.annotations.Ignore;
import jdk.nashorn.internal.ir.annotations.Reference;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Debug;

public final class ASTWriter {
   private final Node root;
   private static final int TABWIDTH = 4;

   public ASTWriter(Node root) {
      this.root = root;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      this.printAST(sb, (List)null, (Field)null, "root", this.root, 0);
      return sb.toString();
   }

   public Node[] toArray() {
      List<Node> preorder = new ArrayList();
      this.printAST(new StringBuilder(), preorder, (Field)null, "root", this.root, 0);
      return (Node[])preorder.toArray(new Node[preorder.size()]);
   }

   private void printAST(StringBuilder sb, List<Node> preorder, Field field, String name, Node node, int indent) {
      indent(sb, indent);
      if (node == null) {
         sb.append("[Object ");
         sb.append(name);
         sb.append(" null]\n");
      } else {
         if (preorder != null) {
            preorder.add(node);
         }

         boolean isReference = field != null && field.isAnnotationPresent(Reference.class);
         Class<?> clazz = node.getClass();
         String type = clazz.getName();
         type = type.substring(type.lastIndexOf(46) + 1, type.length());
         int truncate = type.indexOf("Node");
         if (truncate == -1) {
            truncate = type.indexOf("Statement");
         }

         if (truncate != -1) {
            type = type.substring(0, truncate);
         }

         type = type.toLowerCase();
         if (isReference) {
            type = "ref: " + type;
         }

         Symbol symbol;
         if (node instanceof IdentNode) {
            symbol = ((IdentNode)node).getSymbol();
         } else {
            symbol = null;
         }

         if (symbol != null) {
            type = type + ">" + symbol;
         }

         if (node instanceof Block && ((Block)node).needsScope()) {
            type = type + " <scope>";
         }

         List<Field> children = new LinkedList();
         if (!isReference) {
            enqueueChildren(node, clazz, children);
         }

         String status = "";
         if (node instanceof Terminal && ((Terminal)node).isTerminal()) {
            status = status + " Terminal";
         }

         if (node instanceof Statement && ((Statement)node).hasGoto()) {
            status = status + " Goto ";
         }

         if (symbol != null) {
            status = status + symbol;
         }

         status = status.trim();
         if (!"".equals(status)) {
            status = " [" + status + "]";
         }

         if (symbol != null) {
            String tname = ((Expression)node).getType().toString();
            if (tname.indexOf(46) != -1) {
               tname = tname.substring(tname.lastIndexOf(46) + 1, tname.length());
            }

            status = status + " (" + tname + ")";
         }

         status = status + " @" + Debug.id(node);
         if (children.isEmpty()) {
            sb.append("[").append(type).append(' ').append(name).append(" = '").append(node).append("'").append(status).append("] ").append('\n');
         } else {
            sb.append("[").append(type).append(' ').append(name).append(' ').append(Token.toString(node.getToken())).append(status).append("]").append('\n');
            Iterator var21 = children.iterator();

            while(true) {
               while(true) {
                  Field child;
                  do {
                     if (!var21.hasNext()) {
                        return;
                     }

                     child = (Field)var21.next();
                  } while(child.isAnnotationPresent(Ignore.class));

                  Object value;
                  try {
                     value = child.get(node);
                  } catch (IllegalAccessException | IllegalArgumentException var20) {
                     Context.printStackTrace(var20);
                     return;
                  }

                  if (value instanceof Node) {
                     this.printAST(sb, preorder, child, child.getName(), (Node)value, indent + 1);
                  } else if (value instanceof Collection) {
                     int pos = 0;
                     indent(sb, indent + 1);
                     sb.append('[').append(child.getName()).append("[0..").append(((Collection)value).size()).append("]]").append('\n');
                     Iterator var18 = ((Collection)value).iterator();

                     while(var18.hasNext()) {
                        Node member = (Node)var18.next();
                        this.printAST(sb, preorder, child, child.getName() + "[" + pos++ + "]", member, indent + 2);
                     }
                  }
               }
            }
         }
      }
   }

   private static void enqueueChildren(Node node, Class<?> nodeClass, List<Field> children) {
      Deque<Class<?>> stack = new ArrayDeque();
      Class clazz = nodeClass;

      do {
         stack.push(clazz);
         clazz = clazz.getSuperclass();
      } while(clazz != null);

      if (node instanceof TernaryNode) {
         stack.push(stack.removeLast());
      }

      Iterator iter = node instanceof BinaryNode ? stack.descendingIterator() : stack.iterator();

      while(iter.hasNext()) {
         Class<?> c = (Class)iter.next();
         Field[] var7 = c.getDeclaredFields();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Field f = var7[var9];

            try {
               f.setAccessible(true);
               Object child = f.get(node);
               if (child != null) {
                  if (child instanceof Node) {
                     children.add(f);
                  } else if (child instanceof Collection && !((Collection)child).isEmpty()) {
                     children.add(f);
                  }
               }
            } catch (IllegalAccessException | IllegalArgumentException var12) {
               return;
            }
         }
      }

   }

   private static void indent(StringBuilder sb, int indent) {
      for(int i = 0; i < indent; ++i) {
         for(int j = 0; j < 4; ++j) {
            sb.append(' ');
         }
      }

   }
}
