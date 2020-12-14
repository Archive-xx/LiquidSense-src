package jdk.nashorn.internal.ir.debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.Handle;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.signature.SignatureReader;
import jdk.internal.org.objectweb.asm.util.Printer;
import jdk.internal.org.objectweb.asm.util.TraceSignatureVisitor;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;

public final class NashornTextifier extends Printer {
   private String currentClassName;
   private Iterator<Label> labelIter;
   private NashornTextifier.Graph graph;
   private String currentBlock;
   private boolean lastWasNop;
   private boolean lastWasEllipse;
   private static final int INTERNAL_NAME = 0;
   private static final int FIELD_DESCRIPTOR = 1;
   private static final int FIELD_SIGNATURE = 2;
   private static final int METHOD_DESCRIPTOR = 3;
   private static final int METHOD_SIGNATURE = 4;
   private static final int CLASS_SIGNATURE = 5;
   private final String tab;
   private final String tab2;
   private final String tab3;
   private Map<Label, String> labelNames;
   private boolean localVarsStarted;
   private NashornClassReader cr;
   private ScriptEnvironment env;

   public NashornTextifier(ScriptEnvironment env, NashornClassReader cr) {
      this(327680);
      this.env = env;
      this.cr = cr;
   }

   private NashornTextifier(ScriptEnvironment env, NashornClassReader cr, Iterator<Label> labelIter, NashornTextifier.Graph graph) {
      this(env, cr);
      this.labelIter = labelIter;
      this.graph = graph;
   }

   protected NashornTextifier(int api) {
      super(api);
      this.lastWasNop = false;
      this.lastWasEllipse = false;
      this.tab = "  ";
      this.tab2 = "    ";
      this.tab3 = "      ";
      this.localVarsStarted = false;
   }

   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      int major = version & '\uffff';
      int minor = version >>> 16;
      this.currentClassName = name;
      StringBuilder sb = new StringBuilder();
      sb.append("// class version ").append(major).append('.').append(minor).append(" (").append(version).append(")\n");
      if ((access & 131072) != 0) {
         sb.append("// DEPRECATED\n");
      }

      sb.append("// access flags 0x").append(Integer.toHexString(access).toUpperCase()).append('\n');
      appendDescriptor(sb, 5, signature);
      if (signature != null) {
         TraceSignatureVisitor sv = new TraceSignatureVisitor(access);
         SignatureReader r = new SignatureReader(signature);
         r.accept(sv);
         sb.append("// declaration: ").append(name).append(sv.getDeclaration()).append('\n');
      }

      appendAccess(sb, access & -33);
      if ((access & 8192) != 0) {
         sb.append("@interface ");
      } else if ((access & 512) != 0) {
         sb.append("interface ");
      } else if ((access & 16384) == 0) {
         sb.append("class ");
      }

      appendDescriptor(sb, 0, name);
      if (superName != null && !"java/lang/Object".equals(superName)) {
         sb.append(" extends ");
         appendDescriptor(sb, 0, superName);
         sb.append(' ');
      }

      if (interfaces != null && interfaces.length > 0) {
         sb.append(" implements ");
         String[] var14 = interfaces;
         int var15 = interfaces.length;

         for(int var12 = 0; var12 < var15; ++var12) {
            String interface1 = var14[var12];
            appendDescriptor(sb, 0, interface1);
            sb.append(' ');
         }
      }

      sb.append(" {\n");
      this.addText(sb);
   }

   public void visitSource(String file, String debug) {
      StringBuilder sb = new StringBuilder();
      if (file != null) {
         sb.append("  ").append("// compiled from: ").append(file).append('\n');
      }

      if (debug != null) {
         sb.append("  ").append("// debug info: ").append(debug).append('\n');
      }

      if (sb.length() > 0) {
         this.addText(sb);
      }

   }

   public void visitOuterClass(String owner, String name, String desc) {
      StringBuilder sb = new StringBuilder();
      sb.append("  ").append("outer class ");
      appendDescriptor(sb, 0, owner);
      sb.append(' ');
      if (name != null) {
         sb.append(name).append(' ');
      }

      appendDescriptor(sb, 3, desc);
      sb.append('\n');
      this.addText(sb);
   }

   public NashornTextifier visitField(int access, String name, String desc, String signature, Object value) {
      StringBuilder sb = new StringBuilder();
      if ((access & 131072) != 0) {
         sb.append("  ").append("// DEPRECATED\n");
      }

      if (signature != null) {
         sb.append("  ");
         appendDescriptor(sb, 2, signature);
         TraceSignatureVisitor sv = new TraceSignatureVisitor(0);
         SignatureReader r = new SignatureReader(signature);
         r.acceptType(sv);
         sb.append("  ").append("// declaration: ").append(sv.getDeclaration()).append('\n');
      }

      sb.append("  ");
      appendAccess(sb, access);
      String prunedDesc = desc.endsWith(";") ? desc.substring(0, desc.length() - 1) : desc;
      appendDescriptor(sb, 1, prunedDesc);
      sb.append(' ').append(name);
      if (value != null) {
         sb.append(" = ");
         if (value instanceof String) {
            sb.append('"').append(value).append('"');
         } else {
            sb.append(value);
         }
      }

      sb.append(";\n");
      this.addText(sb);
      NashornTextifier t = this.createNashornTextifier();
      this.addText(t.getText());
      return t;
   }

   public NashornTextifier visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
      this.graph = new NashornTextifier.Graph(name);
      List<Label> extraLabels = this.cr.getExtraLabels(this.currentClassName, name, desc);
      this.labelIter = extraLabels == null ? null : extraLabels.iterator();
      StringBuilder sb = new StringBuilder();
      sb.append('\n');
      if ((access & 131072) != 0) {
         sb.append("  ").append("// DEPRECATED\n");
      }

      sb.append("  ").append("// access flags 0x").append(Integer.toHexString(access).toUpperCase()).append('\n');
      String exception;
      if (signature != null) {
         sb.append("  ");
         appendDescriptor(sb, 4, signature);
         TraceSignatureVisitor v = new TraceSignatureVisitor(0);
         SignatureReader r = new SignatureReader(signature);
         r.accept(v);
         String genericDecl = v.getDeclaration();
         exception = v.getReturnType();
         String genericExceptions = v.getExceptions();
         sb.append("  ").append("// declaration: ").append(exception).append(' ').append(name).append(genericDecl);
         if (genericExceptions != null) {
            sb.append(" throws ").append(genericExceptions);
         }

         sb.append('\n');
      }

      sb.append("  ");
      appendAccess(sb, access);
      if ((access & 256) != 0) {
         sb.append("native ");
      }

      if ((access & 128) != 0) {
         sb.append("varargs ");
      }

      if ((access & 64) != 0) {
         sb.append("bridge ");
      }

      sb.append(name);
      appendDescriptor(sb, 3, desc);
      if (exceptions != null && exceptions.length > 0) {
         sb.append(" throws ");
         String[] var13 = exceptions;
         int var15 = exceptions.length;

         for(int var16 = 0; var16 < var15; ++var16) {
            exception = var13[var16];
            appendDescriptor(sb, 0, exception);
            sb.append(' ');
         }
      }

      sb.append('\n');
      this.addText(sb);
      NashornTextifier t = this.createNashornTextifier();
      this.addText(t.getText());
      return t;
   }

   public void visitClassEnd() {
      this.addText("}\n");
   }

   public void visitFieldEnd() {
   }

   public void visitParameter(String name, int access) {
      StringBuilder sb = new StringBuilder();
      sb.append("    ").append("// parameter ");
      appendAccess(sb, access);
      sb.append(' ').append(name == null ? "<no name>" : name).append('\n');
      this.addText(sb);
   }

   public void visitCode() {
   }

   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
      StringBuilder sb = new StringBuilder();
      sb.append("frame ");
      switch(type) {
      case -1:
      case 0:
         sb.append("full [");
         this.appendFrameTypes(sb, nLocal, local);
         sb.append("] [");
         this.appendFrameTypes(sb, nStack, stack);
         sb.append(']');
         break;
      case 1:
         sb.append("append [");
         this.appendFrameTypes(sb, nLocal, local);
         sb.append(']');
         break;
      case 2:
         sb.append("chop ").append(nLocal);
         break;
      case 3:
         sb.append("same");
         break;
      case 4:
         sb.append("same1 ");
         this.appendFrameTypes(sb, 1, stack);
         break;
      default:
         assert false;
      }

      sb.append('\n');
      sb.append('\n');
      this.addText(sb);
   }

   private StringBuilder appendOpcode(StringBuilder sb, int opcode) {
      Label next = this.getNextLabel();
      if (next instanceof NashornTextifier.NashornLabel) {
         int bci = next.getOffset();
         if (bci != -1) {
            String bcis = "" + bci;

            for(int i = 0; i < 5 - bcis.length(); ++i) {
               sb.append(' ');
            }

            sb.append(bcis);
            sb.append(' ');
         } else {
            sb.append("       ");
         }
      }

      return sb.append("    ").append(OPCODES[opcode].toLowerCase());
   }

   private Label getNextLabel() {
      return this.labelIter == null ? null : (Label)this.labelIter.next();
   }

   public void visitInsn(int opcode) {
      if (opcode == 0) {
         if (this.lastWasEllipse) {
            this.getNextLabel();
            return;
         }

         if (this.lastWasNop) {
            this.getNextLabel();
            this.addText("          ...\n");
            this.lastWasEllipse = true;
            return;
         }

         this.lastWasNop = true;
      } else {
         this.lastWasNop = this.lastWasEllipse = false;
      }

      StringBuilder sb = new StringBuilder();
      this.appendOpcode(sb, opcode).append('\n');
      this.addText(sb);
      this.checkNoFallThru(opcode, (String)null);
   }

   public void visitIntInsn(int opcode, int operand) {
      StringBuilder sb = new StringBuilder();
      this.appendOpcode(sb, opcode).append(' ').append(opcode == 188 ? TYPES[operand] : Integer.toString(operand)).append('\n');
      this.addText(sb);
   }

   public void visitVarInsn(int opcode, int var) {
      StringBuilder sb = new StringBuilder();
      this.appendOpcode(sb, opcode).append(' ').append(var).append('\n');
      this.addText(sb);
   }

   public void visitTypeInsn(int opcode, String type) {
      StringBuilder sb = new StringBuilder();
      this.appendOpcode(sb, opcode).append(' ');
      appendDescriptor(sb, 0, type);
      sb.append('\n');
      this.addText(sb);
   }

   public void visitFieldInsn(int opcode, String owner, String name, String desc) {
      StringBuilder sb = new StringBuilder();
      this.appendOpcode(sb, opcode).append(' ');
      appendDescriptor(sb, 0, owner);
      sb.append('.').append(name).append(" : ");
      appendDescriptor(sb, 1, desc);
      sb.append('\n');
      this.addText(sb);
   }

   public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
      StringBuilder sb = new StringBuilder();
      this.appendOpcode(sb, opcode).append(' ');
      appendDescriptor(sb, 0, owner);
      sb.append('.').append(name);
      appendDescriptor(sb, 3, desc);
      sb.append('\n');
      this.addText(sb);
   }

   public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
      StringBuilder sb = new StringBuilder();
      this.appendOpcode(sb, 186).append(' ');
      sb.append(name);
      appendDescriptor(sb, 3, desc);
      int len = sb.length();

      for(int i = 0; i < 80 - len; ++i) {
         sb.append(' ');
      }

      sb.append(" [");
      appendHandle(sb, bsm);
      if (bsmArgs.length == 0) {
         sb.append("none");
      } else {
         Object[] var13 = bsmArgs;
         int var8 = bsmArgs.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Object cst = var13[var9];
            if (cst instanceof String) {
               appendStr(sb, (String)cst);
            } else if (cst instanceof Type) {
               sb.append(((Type)cst).getDescriptor()).append(".class");
            } else if (cst instanceof Handle) {
               appendHandle(sb, (Handle)cst);
            } else if (cst instanceof Integer) {
               int c = (Integer)cst;
               int pp = c >> 11;
               if (pp != 0) {
                  sb.append(" pp=").append(pp);
               }

               sb.append(NashornCallSiteDescriptor.toString(c & 2047));
            } else {
               sb.append(cst);
            }

            sb.append(", ");
         }

         sb.setLength(sb.length() - 2);
      }

      sb.append("]\n");
      this.addText(sb);
   }

   private static final boolean noFallThru(int opcode) {
      switch(opcode) {
      case 167:
      case 172:
      case 173:
      case 174:
      case 175:
      case 176:
      case 191:
         return true;
      case 168:
      case 169:
      case 170:
      case 171:
      case 177:
      case 178:
      case 179:
      case 180:
      case 181:
      case 182:
      case 183:
      case 184:
      case 185:
      case 186:
      case 187:
      case 188:
      case 189:
      case 190:
      default:
         return false;
      }
   }

   private void checkNoFallThru(int opcode, String to) {
      if (noFallThru(opcode)) {
         this.graph.setNoFallThru(this.currentBlock);
      }

      if (this.currentBlock != null && to != null) {
         this.graph.addEdge(this.currentBlock, to);
      }

   }

   public void visitJumpInsn(int opcode, Label label) {
      StringBuilder sb = new StringBuilder();
      this.appendOpcode(sb, opcode).append(' ');
      String to = this.appendLabel(sb, label);
      sb.append('\n');
      this.addText(sb);
      this.checkNoFallThru(opcode, to);
   }

   private void addText(Object t) {
      this.text.add(t);
      if (this.currentBlock != null) {
         this.graph.addText(this.currentBlock, t.toString());
      }

   }

   public void visitLabel(Label label) {
      StringBuilder sb = new StringBuilder();
      sb.append("\n");
      String name = this.appendLabel(sb, label);
      sb.append(" [bci=");
      sb.append(label.info);
      sb.append("]");
      sb.append("\n");
      this.graph.addNode(name);
      if (this.currentBlock != null && !this.graph.isNoFallThru(this.currentBlock)) {
         this.graph.addEdge(this.currentBlock, name);
      }

      this.currentBlock = name;
      this.addText(sb);
   }

   public void visitLdcInsn(Object cst) {
      StringBuilder sb = new StringBuilder();
      this.appendOpcode(sb, 18).append(' ');
      if (cst instanceof String) {
         appendStr(sb, (String)cst);
      } else if (cst instanceof Type) {
         sb.append(((Type)cst).getDescriptor()).append(".class");
      } else {
         sb.append(cst);
      }

      sb.append('\n');
      this.addText(sb);
   }

   public void visitIincInsn(int var, int increment) {
      StringBuilder sb = new StringBuilder();
      this.appendOpcode(sb, 132).append(' ');
      sb.append(var).append(' ').append(increment).append('\n');
      this.addText(sb);
   }

   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
      StringBuilder sb = new StringBuilder();
      this.appendOpcode(sb, 170).append(' ');

      for(int i = 0; i < labels.length; ++i) {
         sb.append("      ").append(min + i).append(": ");
         String to = this.appendLabel(sb, labels[i]);
         this.graph.addEdge(this.currentBlock, to);
         sb.append('\n');
      }

      sb.append("      ").append("default: ");
      this.appendLabel(sb, dflt);
      sb.append('\n');
      this.addText(sb);
   }

   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
      StringBuilder sb = new StringBuilder();
      this.appendOpcode(sb, 171).append(' ');

      for(int i = 0; i < labels.length; ++i) {
         sb.append("      ").append(keys[i]).append(": ");
         String to = this.appendLabel(sb, labels[i]);
         this.graph.addEdge(this.currentBlock, to);
         sb.append('\n');
      }

      sb.append("      ").append("default: ");
      String to = this.appendLabel(sb, dflt);
      this.graph.addEdge(this.currentBlock, to);
      sb.append('\n');
      this.addText(sb.toString());
   }

   public void visitMultiANewArrayInsn(String desc, int dims) {
      StringBuilder sb = new StringBuilder();
      this.appendOpcode(sb, 197).append(' ');
      appendDescriptor(sb, 1, desc);
      sb.append(' ').append(dims).append('\n');
      this.addText(sb);
   }

   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
      StringBuilder sb = new StringBuilder();
      sb.append("    ").append("try ");
      String from = this.appendLabel(sb, start);
      sb.append(' ');
      this.appendLabel(sb, end);
      sb.append(' ');
      String to = this.appendLabel(sb, handler);
      sb.append(' ');
      appendDescriptor(sb, 0, type);
      sb.append('\n');
      this.addText(sb);
      this.graph.setIsCatch(to, type);
      this.graph.addTryCatch(from, to);
   }

   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
      StringBuilder sb = new StringBuilder();
      if (!this.localVarsStarted) {
         this.text.add("\n");
         this.localVarsStarted = true;
         this.graph.addNode("vars");
         this.currentBlock = "vars";
      }

      sb.append("    ").append("local ").append(name).append(' ');
      int len = sb.length();

      for(int i = 0; i < 25 - len; ++i) {
         sb.append(' ');
      }

      String label = this.appendLabel(sb, start);

      int i;
      for(i = 0; i < 5 - label.length(); ++i) {
         sb.append(' ');
      }

      label = this.appendLabel(sb, end);

      for(i = 0; i < 5 - label.length(); ++i) {
         sb.append(' ');
      }

      sb.append(index).append("    ");
      appendDescriptor(sb, 1, desc);
      sb.append('\n');
      if (signature != null) {
         sb.append("    ");
         appendDescriptor(sb, 2, signature);
         TraceSignatureVisitor sv = new TraceSignatureVisitor(0);
         SignatureReader r = new SignatureReader(signature);
         r.acceptType(sv);
         sb.append("    ").append("// declaration: ").append(sv.getDeclaration()).append('\n');
      }

      this.addText(sb.toString());
   }

   public void visitLineNumber(int line, Label start) {
      StringBuilder sb = new StringBuilder();
      sb.append("<line ");
      sb.append(line);
      sb.append(">\n");
      this.addText(sb.toString());
   }

   public void visitMaxs(int maxStack, int maxLocals) {
      StringBuilder sb = new StringBuilder();
      sb.append('\n');
      sb.append("    ").append("max stack  = ").append(maxStack);
      sb.append(", max locals = ").append(maxLocals).append('\n');
      this.addText(sb.toString());
   }

   private void printToDir(NashornTextifier.Graph g) {
      if (this.env._print_code_dir != null) {
         File dir = new File(this.env._print_code_dir);
         if (!dir.exists() && !dir.mkdirs()) {
            throw new RuntimeException(dir.toString());
         }

         int uniqueId = 0;

         File file;
         do {
            String fileName = g.getName() + (uniqueId == 0 ? "" : "_" + uniqueId) + ".dot";
            file = new File(dir, fileName);
            ++uniqueId;
         } while(file.exists());

         try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(file));
            Throwable var6 = null;

            try {
               pw.println(g);
            } catch (Throwable var16) {
               var6 = var16;
               throw var16;
            } finally {
               if (pw != null) {
                  if (var6 != null) {
                     try {
                        pw.close();
                     } catch (Throwable var15) {
                        var6.addSuppressed(var15);
                     }
                  } else {
                     pw.close();
                  }
               }

            }
         } catch (FileNotFoundException var18) {
            throw new RuntimeException(var18);
         }
      }

   }

   public void visitMethodEnd() {
      if ((this.env._print_code_func == null || this.env._print_code_func.equals(this.graph.getName())) && this.env._print_code_dir != null) {
         this.printToDir(this.graph);
      }

   }

   protected NashornTextifier createNashornTextifier() {
      return new NashornTextifier(this.env, this.cr, this.labelIter, this.graph);
   }

   private static void appendDescriptor(StringBuilder sb, int type, String desc) {
      if (desc != null) {
         if (type != 5 && type != 2 && type != 4) {
            appendShortDescriptor(sb, desc);
         } else {
            sb.append("// signature ").append(desc).append('\n');
         }
      }

   }

   private String appendLabel(StringBuilder sb, Label l) {
      if (this.labelNames == null) {
         this.labelNames = new HashMap();
      }

      String name = (String)this.labelNames.get(l);
      if (name == null) {
         name = "L" + this.labelNames.size();
         this.labelNames.put(l, name);
      }

      sb.append(name);
      return name;
   }

   private static void appendHandle(StringBuilder sb, Handle h) {
      switch(h.getTag()) {
      case 1:
         sb.append("getfield");
         break;
      case 2:
         sb.append("getstatic");
         break;
      case 3:
         sb.append("putfield");
         break;
      case 4:
         sb.append("putstatic");
         break;
      case 5:
         sb.append("virtual");
         break;
      case 6:
         sb.append("static");
         break;
      case 7:
         sb.append("special");
         break;
      case 8:
         sb.append("new_special");
         break;
      case 9:
         sb.append("interface");
         break;
      default:
         assert false;
      }

      sb.append(" '");
      sb.append(h.getName());
      sb.append("'");
   }

   private static void appendAccess(StringBuilder sb, int access) {
      if ((access & 1) != 0) {
         sb.append("public ");
      }

      if ((access & 2) != 0) {
         sb.append("private ");
      }

      if ((access & 4) != 0) {
         sb.append("protected ");
      }

      if ((access & 16) != 0) {
         sb.append("final ");
      }

      if ((access & 8) != 0) {
         sb.append("static ");
      }

      if ((access & 32) != 0) {
         sb.append("synchronized ");
      }

      if ((access & 64) != 0) {
         sb.append("volatile ");
      }

      if ((access & 128) != 0) {
         sb.append("transient ");
      }

      if ((access & 1024) != 0) {
         sb.append("abstract ");
      }

      if ((access & 2048) != 0) {
         sb.append("strictfp ");
      }

      if ((access & 4096) != 0) {
         sb.append("synthetic ");
      }

      if ((access & '耀') != 0) {
         sb.append("mandated ");
      }

      if ((access & 16384) != 0) {
         sb.append("enum ");
      }

   }

   private void appendFrameTypes(StringBuilder sb, int n, Object[] o) {
      for(int i = 0; i < n; ++i) {
         if (i > 0) {
            sb.append(' ');
         }

         if (o[i] instanceof String) {
            String desc = (String)o[i];
            if (desc.startsWith("[")) {
               appendDescriptor(sb, 1, desc);
            } else {
               appendDescriptor(sb, 0, desc);
            }
         } else if (o[i] instanceof Integer) {
            switch((Integer)o[i]) {
            case 0:
               appendDescriptor(sb, 1, "T");
               break;
            case 1:
               appendDescriptor(sb, 1, "I");
               break;
            case 2:
               appendDescriptor(sb, 1, "F");
               break;
            case 3:
               appendDescriptor(sb, 1, "D");
               break;
            case 4:
               appendDescriptor(sb, 1, "J");
               break;
            case 5:
               appendDescriptor(sb, 1, "N");
               break;
            case 6:
               appendDescriptor(sb, 1, "U");
               break;
            default:
               assert false;
            }
         } else {
            this.appendLabel(sb, (Label)o[i]);
         }
      }

   }

   private static void appendShortDescriptor(StringBuilder sb, String desc) {
      int i;
      int slash;
      if (desc.charAt(0) == '(') {
         for(i = 0; i < desc.length(); ++i) {
            if (desc.charAt(i) != 'L') {
               sb.append(desc.charAt(i));
            } else {
               slash = i;

               while(desc.charAt(i) != ';') {
                  ++i;
                  if (desc.charAt(i) == '/') {
                     slash = i;
                  }
               }

               sb.append(desc.substring(slash + 1, i)).append(';');
            }
         }
      } else {
         i = desc.lastIndexOf(47);
         slash = desc.lastIndexOf(91);
         if (slash != -1) {
            sb.append(desc, 0, slash + 1);
         }

         sb.append(i == -1 ? desc : desc.substring(i + 1));
      }

   }

   private static void appendStr(StringBuilder sb, String s) {
      sb.append('"');

      for(int i = 0; i < s.length(); ++i) {
         char c = s.charAt(i);
         if (c == '\n') {
            sb.append("\\n");
         } else if (c == '\r') {
            sb.append("\\r");
         } else if (c == '\\') {
            sb.append("\\\\");
         } else if (c == '"') {
            sb.append("\\\"");
         } else if (c >= ' ' && c <= 127) {
            sb.append(c);
         } else {
            sb.append("\\u");
            if (c < 16) {
               sb.append("000");
            } else if (c < 256) {
               sb.append("00");
            } else if (c < 4096) {
               sb.append('0');
            }

            sb.append(Integer.toString(c, 16));
         }
      }

      sb.append('"');
   }

   public Printer visitAnnotationDefault() {
      throw new AssertionError();
   }

   public Printer visitClassAnnotation(String arg0, boolean arg1) {
      return this;
   }

   public void visitClassAttribute(Attribute arg0) {
      throw new AssertionError();
   }

   public Printer visitFieldAnnotation(String arg0, boolean arg1) {
      throw new AssertionError();
   }

   public void visitFieldAttribute(Attribute arg0) {
      throw new AssertionError();
   }

   public Printer visitMethodAnnotation(String arg0, boolean arg1) {
      return this;
   }

   public void visitMethodAttribute(Attribute arg0) {
      throw new AssertionError();
   }

   public Printer visitParameterAnnotation(int arg0, String arg1, boolean arg2) {
      throw new AssertionError();
   }

   public void visit(String arg0, Object arg1) {
      throw new AssertionError();
   }

   public Printer visitAnnotation(String arg0, String arg1) {
      throw new AssertionError();
   }

   public void visitAnnotationEnd() {
   }

   public Printer visitArray(String arg0) {
      throw new AssertionError();
   }

   public void visitEnum(String arg0, String arg1, String arg2) {
      throw new AssertionError();
   }

   public void visitInnerClass(String arg0, String arg1, String arg2, int arg3) {
      throw new AssertionError();
   }

   static class NashornLabel extends Label {
      final Label label;
      final int bci;
      final int opcode;

      NashornLabel(Label label, int bci) {
         this.label = label;
         this.bci = bci;
         this.opcode = -1;
      }

      NashornLabel(int opcode, int bci) {
         this.opcode = opcode;
         this.bci = bci;
         this.label = null;
      }

      Label getLabel() {
         return this.label;
      }

      public int getOffset() {
         return this.bci;
      }

      public String toString() {
         return "label " + this.bci;
      }
   }

   private static class Graph {
      private final LinkedHashSet<String> nodes;
      private final Map<String, StringBuilder> contents;
      private final Map<String, Set<String>> edges;
      private final Set<String> hasPreds;
      private final Set<String> noFallThru;
      private final Map<String, String> catches;
      private final Map<String, Set<String>> exceptionMap;
      private final String name;
      private static final String LEFT_ALIGN = "\\l";
      private static final String COLOR_CATCH = "\"#ee9999\"";
      private static final String COLOR_ORPHAN = "\"#9999bb\"";
      private static final String COLOR_DEFAULT = "\"#99bb99\"";
      private static final String COLOR_LOCALVARS = "\"#999999\"";

      Graph(String name) {
         this.name = name;
         this.nodes = new LinkedHashSet();
         this.contents = new HashMap();
         this.edges = new HashMap();
         this.hasPreds = new HashSet();
         this.catches = new HashMap();
         this.noFallThru = new HashSet();
         this.exceptionMap = new HashMap();
      }

      void addEdge(String from, String to) {
         Set<String> edgeSet = (Set)this.edges.get(from);
         if (edgeSet == null) {
            edgeSet = new LinkedHashSet();
            this.edges.put(from, edgeSet);
         }

         ((Set)edgeSet).add(to);
         this.hasPreds.add(to);
      }

      void addTryCatch(String tryNode, String catchNode) {
         Set<String> tryNodes = (Set)this.exceptionMap.get(catchNode);
         if (tryNodes == null) {
            tryNodes = new HashSet();
            this.exceptionMap.put(catchNode, tryNodes);
         }

         if (!((Set)tryNodes).contains(tryNode)) {
            this.addEdge(tryNode, catchNode);
         }

         ((Set)tryNodes).add(tryNode);
      }

      void addNode(String node) {
         assert !this.nodes.contains(node);

         this.nodes.add(node);
      }

      void setNoFallThru(String node) {
         this.noFallThru.add(node);
      }

      boolean isNoFallThru(String node) {
         return this.noFallThru.contains(node);
      }

      void setIsCatch(String node, String exception) {
         this.catches.put(node, exception);
      }

      String getName() {
         return this.name;
      }

      void addText(String node, String text) {
         StringBuilder sb = (StringBuilder)this.contents.get(node);
         if (sb == null) {
            sb = new StringBuilder();
         }

         for(int i = 0; i < text.length(); ++i) {
            switch(text.charAt(i)) {
            case '\n':
               sb.append("\\l");
               break;
            case '"':
               sb.append("'");
               break;
            default:
               sb.append(text.charAt(i));
            }
         }

         this.contents.put(node, sb);
      }

      private static String dottyFriendly(String name) {
         return name.replace(':', '_');
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("digraph " + dottyFriendly(this.name) + " {");
         sb.append("\n");
         sb.append("\tgraph [fontname=courier]\n");
         sb.append("\tnode [style=filled,color=\"#99bb99\",fontname=courier]\n");
         sb.append("\tedge [fontname=courier]\n\n");
         Iterator var2 = this.nodes.iterator();

         String node;
         String to;
         while(var2.hasNext()) {
            node = (String)var2.next();
            sb.append("\t");
            sb.append(node);
            sb.append(" [");
            sb.append("id=");
            sb.append(node);
            sb.append(", label=\"");
            String c = ((StringBuilder)this.contents.get(node)).toString();
            if (c.startsWith("\\l")) {
               c = c.substring(2);
            }

            to = (String)this.catches.get(node);
            if (to != null) {
               sb.append("*** CATCH: ").append(to).append(" ***\\l");
            }

            sb.append(c);
            sb.append("\"]\n");
         }

         var2 = this.edges.keySet().iterator();

         while(var2.hasNext()) {
            node = (String)var2.next();
            Iterator var6 = ((Set)this.edges.get(node)).iterator();

            while(var6.hasNext()) {
               to = (String)var6.next();
               sb.append("\t");
               sb.append(node);
               sb.append(" -> ");
               sb.append(to);
               sb.append("[label=\"");
               sb.append(to);
               sb.append("\"");
               if (this.catches.get(to) != null) {
                  sb.append(", color=red, style=dashed");
               }

               sb.append(']');
               sb.append(";\n");
            }
         }

         sb.append("\n");

         for(var2 = this.nodes.iterator(); var2.hasNext(); sb.append("]\n")) {
            node = (String)var2.next();
            sb.append("\t");
            sb.append(node);
            sb.append(" [shape=box");
            if (this.catches.get(node) != null) {
               sb.append(", color=\"#ee9999\"");
            } else if ("vars".equals(node)) {
               sb.append(", shape=hexagon, color=\"#999999\"");
            } else if (!this.hasPreds.contains(node)) {
               sb.append(", color=\"#9999bb\"");
            }
         }

         sb.append("}\n");
         return sb.toString();
      }
   }
}
