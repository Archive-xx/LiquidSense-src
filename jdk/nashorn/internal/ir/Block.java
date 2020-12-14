package jdk.nashorn.internal.ir;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public class Block extends Node implements BreakableNode, Terminal, Flags<Block> {
   private static final long serialVersionUID = 1L;
   protected final List<Statement> statements;
   protected final Map<String, Symbol> symbols;
   private final Label entryLabel;
   private final Label breakLabel;
   protected final int flags;
   private final LocalVariableConversion conversion;
   public static final int NEEDS_SCOPE = 1;
   public static final int IS_TERMINAL = 4;
   public static final int IS_GLOBAL_SCOPE = 8;

   public Block(long token, int finish, Statement... statements) {
      super(token, finish);
      this.statements = Arrays.asList(statements);
      this.symbols = new LinkedHashMap();
      this.entryLabel = new Label("block_entry");
      this.breakLabel = new Label("block_break");
      int len = statements.length;
      this.flags = len > 0 && statements[len - 1].hasTerminalFlags() ? 4 : 0;
      this.conversion = null;
   }

   public Block(long token, int finish, List<Statement> statements) {
      this(token, finish, (Statement[])statements.toArray(new Statement[statements.size()]));
   }

   private Block(Block block, int finish, List<Statement> statements, int flags, Map<String, Symbol> symbols, LocalVariableConversion conversion) {
      super(block);
      this.statements = statements;
      this.flags = flags;
      this.symbols = new LinkedHashMap(symbols);
      this.entryLabel = new Label(block.entryLabel);
      this.breakLabel = new Label(block.breakLabel);
      this.finish = finish;
      this.conversion = conversion;
   }

   public boolean isGlobalScope() {
      return this.getFlag(8);
   }

   public boolean hasSymbols() {
      return !this.symbols.isEmpty();
   }

   public Block replaceSymbols(LexicalContext lc, Map<Symbol, Symbol> replacements) {
      if (this.symbols.isEmpty()) {
         return this;
      } else {
         LinkedHashMap<String, Symbol> newSymbols = new LinkedHashMap(this.symbols);
         Iterator var4 = newSymbols.entrySet().iterator();

         while(var4.hasNext()) {
            Entry<String, Symbol> entry = (Entry)var4.next();
            Symbol newSymbol = (Symbol)replacements.get(entry.getValue());

            assert newSymbol != null : "Missing replacement for " + (String)entry.getKey();

            entry.setValue(newSymbol);
         }

         return (Block)Node.replaceInLexicalContext(lc, this, new Block(this, this.finish, this.statements, this.flags, newSymbols, this.conversion));
      }
   }

   public Block copyWithNewSymbols() {
      return new Block(this, this.finish, this.statements, this.flags, new LinkedHashMap(this.symbols), this.conversion);
   }

   public Node ensureUniqueLabels(LexicalContext lc) {
      return (Node)Node.replaceInLexicalContext(lc, this, new Block(this, this.finish, this.statements, this.flags, this.symbols, this.conversion));
   }

   public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterBlock(this) ? visitor.leaveBlock(this.setStatements(lc, Node.accept(visitor, this.statements))) : this);
   }

   public List<Symbol> getSymbols() {
      return this.symbols.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList(this.symbols.values()));
   }

   public Symbol getExistingSymbol(String name) {
      return (Symbol)this.symbols.get(name);
   }

   public boolean isCatchBlock() {
      return this.statements.size() == 1 && this.statements.get(0) instanceof CatchNode;
   }

   public void toString(StringBuilder sb, boolean printType) {
      Iterator var3 = this.statements.iterator();

      while(var3.hasNext()) {
         Node statement = (Node)var3.next();
         statement.toString(sb, printType);
         sb.append(';');
      }

   }

   public boolean printSymbols(PrintWriter stream) {
      List<Symbol> values = new ArrayList(this.symbols.values());
      Collections.sort(values, new Comparator<Symbol>() {
         public int compare(Symbol s0, Symbol s1) {
            return s0.getName().compareTo(s1.getName());
         }
      });
      Iterator var3 = values.iterator();

      while(var3.hasNext()) {
         Symbol symbol = (Symbol)var3.next();
         symbol.print(stream);
      }

      return !values.isEmpty();
   }

   public Block setIsTerminal(LexicalContext lc, boolean isTerminal) {
      return isTerminal ? this.setFlag(lc, 4) : this.clearFlag(lc, 4);
   }

   public int getFlags() {
      return this.flags;
   }

   public boolean isTerminal() {
      return this.getFlag(4);
   }

   public Label getEntryLabel() {
      return this.entryLabel;
   }

   public Label getBreakLabel() {
      return this.breakLabel;
   }

   public Block setLocalVariableConversion(LexicalContext lc, LocalVariableConversion conversion) {
      return this.conversion == conversion ? this : (Block)Node.replaceInLexicalContext(lc, this, new Block(this, this.finish, this.statements, this.flags, this.symbols, conversion));
   }

   public LocalVariableConversion getLocalVariableConversion() {
      return this.conversion;
   }

   public List<Statement> getStatements() {
      return Collections.unmodifiableList(this.statements);
   }

   public int getStatementCount() {
      return this.statements.size();
   }

   public int getFirstStatementLineNumber() {
      return this.statements != null && !this.statements.isEmpty() ? ((Statement)this.statements.get(0)).getLineNumber() : -1;
   }

   public Statement getLastStatement() {
      return this.statements.isEmpty() ? null : (Statement)this.statements.get(this.statements.size() - 1);
   }

   public Block setStatements(LexicalContext lc, List<Statement> statements) {
      if (this.statements == statements) {
         return this;
      } else {
         int lastFinish = 0;
         if (!statements.isEmpty()) {
            lastFinish = ((Statement)statements.get(statements.size() - 1)).getFinish();
         }

         return (Block)Node.replaceInLexicalContext(lc, this, new Block(this, Math.max(this.finish, lastFinish), statements, this.flags, this.symbols, this.conversion));
      }
   }

   public void putSymbol(Symbol symbol) {
      this.symbols.put(symbol.getName(), symbol);
   }

   public boolean needsScope() {
      return (this.flags & 1) == 1;
   }

   public Block setFlags(LexicalContext lc, int flags) {
      return this.flags == flags ? this : (Block)Node.replaceInLexicalContext(lc, this, new Block(this, this.finish, this.statements, flags, this.symbols, this.conversion));
   }

   public Block clearFlag(LexicalContext lc, int flag) {
      return this.setFlags(lc, this.flags & ~flag);
   }

   public Block setFlag(LexicalContext lc, int flag) {
      return this.setFlags(lc, this.flags | flag);
   }

   public boolean getFlag(int flag) {
      return (this.flags & flag) == flag;
   }

   public Block setNeedsScope(LexicalContext lc) {
      return this.needsScope() ? this : (Block)Node.replaceInLexicalContext(lc, this, new Block(this, this.finish, this.statements, this.flags | 1, this.symbols, this.conversion));
   }

   public int nextSlot() {
      int next = 0;
      Iterator var2 = this.getSymbols().iterator();

      while(var2.hasNext()) {
         Symbol symbol = (Symbol)var2.next();
         if (symbol.hasSlot()) {
            next += symbol.slotCount();
         }
      }

      return next;
   }

   public boolean isBreakableWithoutLabel() {
      return false;
   }

   public List<Label> getLabels() {
      return Collections.unmodifiableList(Arrays.asList(this.entryLabel, this.breakLabel));
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return LexicalContextNode.Acceptor.accept(this, visitor);
   }
}
