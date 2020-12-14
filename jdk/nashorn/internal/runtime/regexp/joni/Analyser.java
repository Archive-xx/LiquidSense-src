package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.AnchorNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.BackRefNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.EncloseNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.Node;
import jdk.nashorn.internal.runtime.regexp.joni.ast.QuantifierNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.ObjPtr;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

final class Analyser extends Parser {
   private static final int GET_CHAR_LEN_VARLEN = -1;
   private static final int GET_CHAR_LEN_TOP_ALT_VARLEN = -2;
   private static final int THRESHOLD_CASE_FOLD_ALT_FOR_EXPANSION = 8;
   private static final int IN_ALT = 1;
   private static final int IN_NOT = 2;
   private static final int IN_REPEAT = 4;
   private static final int IN_VAR_REPEAT = 8;
   private static final int EXPAND_STRING_MAX_LENGTH = 100;
   private static final int MAX_NODE_OPT_INFO_REF_COUNT = 5;

   protected Analyser(ScanEnvironment env, char[] chars, int p, int end) {
      super(env, chars, p, end);
   }

   protected final void compile() {
      this.reset();
      this.regex.numMem = 0;
      this.regex.numRepeat = 0;
      this.regex.numNullCheck = 0;
      this.regex.repeatRangeLo = null;
      this.regex.repeatRangeHi = null;
      this.parse();
      this.root = this.setupTree(this.root, 0);
      this.regex.captureHistory = this.env.captureHistory;
      this.regex.btMemStart = this.env.btMemStart;
      this.regex.btMemEnd = this.env.btMemEnd;
      if (Option.isFindCondition(this.regex.options)) {
         this.regex.btMemEnd = -1;
      } else {
         this.regex.btMemEnd = this.env.btMemEnd;
         Regex var10000 = this.regex;
         var10000.btMemEnd |= this.regex.captureHistory;
      }

      this.regex.clearOptimizeInfo();
      this.setOptimizedInfoFromTree(this.root);
      this.env.memNodes = null;
      if (this.regex.numRepeat == 0 && this.regex.btMemEnd == 0) {
         if (this.regex.btMemStart != 0) {
            this.regex.stackPopLevel = 1;
         } else {
            this.regex.stackPopLevel = 0;
         }
      } else {
         this.regex.stackPopLevel = 2;
      }

   }

   private void swap(Node a, Node b) {
      a.swap(b);
      if (this.root == b) {
         this.root = a;
      } else if (this.root == a) {
         this.root = b;
      }

   }

   private int quantifiersMemoryInfo(Node node) {
      int info = 0;
      switch(node.getType()) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 7:
      default:
         break;
      case 5:
         QuantifierNode qn = (QuantifierNode)node;
         if (qn.upper != 0) {
            info = this.quantifiersMemoryInfo(qn.target);
         }
         break;
      case 6:
         EncloseNode en = (EncloseNode)node;
         switch(en.type) {
         case 1:
            return 2;
         case 2:
         case 4:
            info = this.quantifiersMemoryInfo(en.target);
            return info;
         case 3:
         default:
            return info;
         }
      case 8:
      case 9:
         ConsAltNode can = (ConsAltNode)node;

         do {
            int v = this.quantifiersMemoryInfo(can.car);
            if (v > info) {
               info = v;
            }
         } while((can = can.cdr) != null);
      }

      return info;
   }

   private int getMinMatchLength(Node node) {
      int min = 0;
      switch(node.getType()) {
      case 0:
         min = ((StringNode)node).length();
         break;
      case 1:
      case 3:
         min = 1;
         break;
      case 2:
         min = 1;
         break;
      case 4:
         BackRefNode br = (BackRefNode)node;
         if (!br.isRecursion()) {
            if (br.backRef > this.env.numMem) {
               throw new ValueException("invalid backref number");
            }

            min = this.getMinMatchLength(this.env.memNodes[br.backRef]);
         }
         break;
      case 5:
         QuantifierNode qn = (QuantifierNode)node;
         if (qn.lower > 0) {
            min = this.getMinMatchLength(qn.target);
            min = MinMaxLen.distanceMultiply(min, qn.lower);
         }
         break;
      case 6:
         EncloseNode en = (EncloseNode)node;
         switch(en.type) {
         case 1:
            if (en.isMinFixed()) {
               min = en.minLength;
            } else {
               min = this.getMinMatchLength(en.target);
               en.minLength = min;
               en.setMinFixed();
            }
            break;
         case 2:
         case 4:
            min = this.getMinMatchLength(en.target);
         case 3:
         }
      case 7:
      default:
         break;
      case 8:
         ConsAltNode can = (ConsAltNode)node;

         do {
            min += this.getMinMatchLength(can.car);
         } while((can = can.cdr) != null);

         return min;
      case 9:
         ConsAltNode y = (ConsAltNode)node;

         do {
            Node x = y.car;
            int tmin = this.getMinMatchLength(x);
            if (y == node) {
               min = tmin;
            } else if (min > tmin) {
               min = tmin;
            }
         } while((y = y.cdr) != null);
      }

      return min;
   }

   private int getMaxMatchLength(Node node) {
      int max = 0;
      switch(node.getType()) {
      case 0:
         max = ((StringNode)node).length();
         break;
      case 1:
      case 3:
         max = 1;
         break;
      case 2:
         max = 1;
         break;
      case 4:
         BackRefNode br = (BackRefNode)node;
         if (br.isRecursion()) {
            max = Integer.MAX_VALUE;
         } else {
            if (br.backRef > this.env.numMem) {
               throw new ValueException("invalid backref number");
            }

            int tmax = this.getMaxMatchLength(this.env.memNodes[br.backRef]);
            if (max < tmax) {
               max = tmax;
            }
         }
         break;
      case 5:
         QuantifierNode qn = (QuantifierNode)node;
         if (qn.upper != 0) {
            max = this.getMaxMatchLength(qn.target);
            if (max != 0) {
               if (!QuantifierNode.isRepeatInfinite(qn.upper)) {
                  max = MinMaxLen.distanceMultiply(max, qn.upper);
               } else {
                  max = Integer.MAX_VALUE;
               }
            }
         }
         break;
      case 6:
         EncloseNode en = (EncloseNode)node;
         switch(en.type) {
         case 1:
            if (en.isMaxFixed()) {
               max = en.maxLength;
            } else {
               max = this.getMaxMatchLength(en.target);
               en.maxLength = max;
               en.setMaxFixed();
            }
            break;
         case 2:
         case 4:
            max = this.getMaxMatchLength(en.target);
         case 3:
         }
      case 7:
      default:
         break;
      case 8:
         ConsAltNode ln = (ConsAltNode)node;

         do {
            int tmax = this.getMaxMatchLength(ln.car);
            max = MinMaxLen.distanceAdd(max, tmax);
         } while((ln = ln.cdr) != null);

         return max;
      case 9:
         ConsAltNode an = (ConsAltNode)node;

         do {
            int tmax = this.getMaxMatchLength(an.car);
            if (max < tmax) {
               max = tmax;
            }
         } while((an = an.cdr) != null);
      }

      return max;
   }

   protected final int getCharLengthTree(Node node) {
      return this.getCharLengthTree(node, 0);
   }

   private int getCharLengthTree(Node node, int levelp) {
      int level = levelp + 1;
      int len = 0;
      this.returnCode = 0;
      int tlen;
      switch(node.getType()) {
      case 0:
         StringNode sn = (StringNode)node;
         len = sn.length();
         break;
      case 1:
      case 2:
      case 3:
         len = 1;
         break;
      case 4:
      default:
         this.returnCode = -1;
         break;
      case 5:
         QuantifierNode qn = (QuantifierNode)node;
         if (qn.lower == qn.upper) {
            tlen = this.getCharLengthTree(qn.target, level);
            if (this.returnCode == 0) {
               len = MinMaxLen.distanceMultiply(tlen, qn.lower);
            }
         } else {
            this.returnCode = -1;
         }
         break;
      case 6:
         EncloseNode en = (EncloseNode)node;
         switch(en.type) {
         case 1:
            if (en.isCLenFixed()) {
               len = en.charLength;
            } else {
               len = this.getCharLengthTree(en.target, level);
               if (this.returnCode == 0) {
                  en.charLength = len;
                  en.setCLenFixed();
               }
            }
            break;
         case 2:
         case 4:
            len = this.getCharLengthTree(en.target, level);
         case 3:
         }
      case 7:
         break;
      case 8:
         ConsAltNode ln = (ConsAltNode)node;

         do {
            int tlen = this.getCharLengthTree(ln.car, level);
            if (this.returnCode == 0) {
               len = MinMaxLen.distanceAdd(len, tlen);
            }
         } while(this.returnCode == 0 && (ln = ln.cdr) != null);

         return len;
      case 9:
         ConsAltNode an = (ConsAltNode)node;
         boolean varLen = false;
         tlen = this.getCharLengthTree(an.car, level);

         while(this.returnCode == 0 && (an = an.cdr) != null) {
            int tlen2 = this.getCharLengthTree(an.car, level);
            if (this.returnCode == 0 && tlen != tlen2) {
               varLen = true;
            }
         }

         if (this.returnCode == 0) {
            if (varLen) {
               if (level == 1) {
                  this.returnCode = -2;
               } else {
                  this.returnCode = -1;
               }
            } else {
               len = tlen;
            }
         }
      }

      return len;
   }

   private static boolean isNotIncluded(Node xn, Node yn) {
      Node x = xn;
      Node y = yn;

      while(true) {
         int yType = y.getType();
         Node tmp;
         switch(x.getType()) {
         case 0:
            StringNode xs = (StringNode)x;
            if (xs.length() != 0) {
               switch(yType) {
               case 0:
                  StringNode ys = (StringNode)y;
                  int len = xs.length();
                  if (len > ys.length()) {
                     len = ys.length();
                  }

                  if (xs.isAmbig() || ys.isAmbig()) {
                     return false;
                  }

                  int i = 0;
                  int pt = ys.p;

                  for(int q = xs.p; i < len; ++q) {
                     if (ys.chars[pt] != xs.chars[q]) {
                        return true;
                     }

                     ++i;
                     ++pt;
                  }

                  return false;
               case 1:
                  CClassNode cc = (CClassNode)y;
                  int code = xs.chars[xs.p];
                  return !cc.isCodeInCC(code);
               }
            }
            break;
         case 1:
            CClassNode xc = (CClassNode)x;
            switch(yType) {
            case 0:
               tmp = x;
               x = y;
               y = tmp;
               continue;
            case 1:
               CClassNode yc = (CClassNode)y;

               for(int i = 0; i < 256; ++i) {
                  boolean v = xc.bs.at(i);
                  if (v && !xc.isNot() || !v && xc.isNot()) {
                     v = yc.bs.at(i);
                     if (v && !yc.isNot() || !v && yc.isNot()) {
                        return false;
                     }
                  }
               }

               if ((xc.mbuf != null || xc.isNot()) && (yc.mbuf != null || yc.isNot())) {
                  return false;
               }

               return true;
            default:
               return false;
            }
         case 2:
            switch(yType) {
            case 0:
               tmp = x;
               x = y;
               y = tmp;
               continue;
            case 1:
               tmp = x;
               x = y;
               y = tmp;
               continue;
            }
         }

         return false;
      }
   }

   private Node getHeadValueNode(Node node, boolean exact) {
      Node n = null;
      switch(node.getType()) {
      case 0:
         StringNode sn = (StringNode)node;
         if (sn.end > sn.p && (!exact || sn.isRaw() || !Option.isIgnoreCase(this.regex.options))) {
            n = node;
         }
         break;
      case 1:
      case 2:
         if (!exact) {
            n = node;
         }
      case 3:
      case 4:
      case 9:
      default:
         break;
      case 5:
         QuantifierNode qn = (QuantifierNode)node;
         if (qn.lower > 0) {
            if (qn.headExact != null) {
               n = qn.headExact;
            } else {
               n = this.getHeadValueNode(qn.target, exact);
            }
         }
         break;
      case 6:
         EncloseNode en = (EncloseNode)node;
         switch(en.type) {
         case 1:
         case 4:
            n = this.getHeadValueNode(en.target, exact);
            return n;
         case 2:
            int options = this.regex.options;
            this.regex.options = en.option;
            n = this.getHeadValueNode(en.target, exact);
            this.regex.options = options;
            return n;
         case 3:
         default:
            return n;
         }
      case 7:
         AnchorNode an = (AnchorNode)node;
         if (an.type == 1024) {
            n = this.getHeadValueNode(an.target, exact);
         }
         break;
      case 8:
         n = this.getHeadValueNode(((ConsAltNode)node).car, exact);
      }

      return n;
   }

   private boolean checkTypeTree(Node node, int typeMask, int encloseMask, int anchorMask) {
      if ((node.getType2Bit() & typeMask) == 0) {
         return true;
      } else {
         boolean invalid = false;
         switch(node.getType()) {
         case 5:
            invalid = this.checkTypeTree(((QuantifierNode)node).target, typeMask, encloseMask, anchorMask);
            break;
         case 6:
            EncloseNode en = (EncloseNode)node;
            if ((en.type & encloseMask) == 0) {
               return true;
            }

            invalid = this.checkTypeTree(en.target, typeMask, encloseMask, anchorMask);
            break;
         case 7:
            AnchorNode an = (AnchorNode)node;
            if ((an.type & anchorMask) == 0) {
               return true;
            }

            if (an.target != null) {
               invalid = this.checkTypeTree(an.target, typeMask, encloseMask, anchorMask);
            }
            break;
         case 8:
         case 9:
            ConsAltNode can = (ConsAltNode)node;

            do {
               invalid = this.checkTypeTree(can.car, typeMask, encloseMask, anchorMask);
            } while(!invalid && (can = can.cdr) != null);
         }

         return invalid;
      }
   }

   private Node divideLookBehindAlternatives(Node nodep) {
      AnchorNode an = (AnchorNode)nodep;
      int anchorType = an.type;
      Node head = an.target;
      Node np = ((ConsAltNode)head).car;
      this.swap(nodep, head);
      ((ConsAltNode)head).setCar(nodep);
      ((AnchorNode)nodep).setTarget(np);
      Object np = head;

      while((np = ((ConsAltNode)np).cdr) != null) {
         AnchorNode insert = new AnchorNode(anchorType);
         insert.setTarget(((ConsAltNode)np).car);
         ((ConsAltNode)np).setCar(insert);
      }

      if (anchorType == 8192) {
         np = head;

         do {
            ((ConsAltNode)np).toListNode();
         } while((np = ((ConsAltNode)np).cdr) != null);
      }

      return head;
   }

   private Node setupLookBehind(Node node) {
      AnchorNode an = (AnchorNode)node;
      int len = this.getCharLengthTree(an.target);
      switch(this.returnCode) {
      case -2:
         if (this.syntax.differentLengthAltLookBehind()) {
            return this.divideLookBehindAlternatives(node);
         }

         throw new SyntaxException("invalid pattern in look-behind");
      case -1:
         throw new SyntaxException("invalid pattern in look-behind");
      case 0:
         an.charLength = len;
      default:
         return node;
      }
   }

   private void nextSetup(Node nodep, Node nextNode) {
      Node node = nodep;

      while(true) {
         int type = node.getType();
         if (type == 5) {
            QuantifierNode qn = (QuantifierNode)node;
            if (qn.greedy && QuantifierNode.isRepeatInfinite(qn.upper)) {
               StringNode n = (StringNode)this.getHeadValueNode(nextNode, true);
               if (n != null && n.chars[n.p] != 0) {
                  qn.nextHeadExact = n;
               }

               if (qn.lower <= 1 && qn.target.isSimple()) {
                  Node x = this.getHeadValueNode(qn.target, false);
                  if (x != null) {
                     Node y = this.getHeadValueNode(nextNode, false);
                     if (y != null && isNotIncluded(x, y)) {
                        EncloseNode en = new EncloseNode(4);
                        en.setStopBtSimpleRepeat();
                        this.swap(node, en);
                        en.setTarget(node);
                     }
                  }
               }
            }
            break;
         }

         if (type != 6) {
            break;
         }

         EncloseNode en = (EncloseNode)node;
         if (!en.isMemory()) {
            break;
         }

         node = en.target;
      }

   }

   private void updateStringNodeCaseFoldMultiByte(StringNode sn) {
      char[] ch = sn.chars;
      int end = sn.end;
      this.value = sn.p;

      for(int sp = 0; this.value < end; ++sp) {
         int ovalue = this.value;
         char buf = EncodingHelper.toLowerCase(ch[this.value++]);
         if (ch[ovalue] != buf) {
            char[] sbuf = new char[sn.length() << 1];
            System.arraycopy(ch, sn.p, sbuf, 0, ovalue - sn.p);

            for(this.value = ovalue; this.value < end; sbuf[sp++] = buf) {
               buf = EncodingHelper.toLowerCase(ch[this.value++]);
               if (sp >= sbuf.length) {
                  char[] tmp = new char[sbuf.length << 1];
                  System.arraycopy(sbuf, 0, tmp, 0, sbuf.length);
                  sbuf = tmp;
               }
            }

            sn.set(sbuf, 0, sp);
            return;
         }
      }

   }

   private void updateStringNodeCaseFold(Node node) {
      StringNode sn = (StringNode)node;
      this.updateStringNodeCaseFoldMultiByte(sn);
   }

   private Node expandCaseFoldMakeRemString(char[] ch, int pp, int end) {
      StringNode node = new StringNode(ch, pp, end);
      this.updateStringNodeCaseFold(node);
      node.setAmbig();
      node.setDontGetOptInfo();
      return node;
   }

   private static boolean expandCaseFoldStringAlt(int itemNum, char[] items, char[] chars, int p, int slen, int end, ObjPtr<Node> node) {
      ConsAltNode altNode;
      node.p = altNode = ConsAltNode.newAltNode((Node)null, (ConsAltNode)null);
      StringNode snode = new StringNode(chars, p, p + slen);
      altNode.setCar(snode);

      for(int i = 0; i < itemNum; ++i) {
         snode = new StringNode();
         snode.catCode(items[i]);
         ConsAltNode an = ConsAltNode.newAltNode((Node)null, (ConsAltNode)null);
         an.setCar(snode);
         altNode.setCdr(an);
         altNode = an;
      }

      return false;
   }

   private Node expandCaseFoldString(Node node) {
      StringNode sn = (StringNode)node;
      if (!sn.isAmbig() && sn.length() > 0) {
         char[] chars1 = sn.chars;
         int pt = sn.p;
         int end = sn.end;
         int altNum = 1;
         ConsAltNode topRoot = null;
         ConsAltNode r = null;
         ObjPtr<Node> prevNode = new ObjPtr();

         for(StringNode stringNode = null; pt < end; ++pt) {
            char[] items = EncodingHelper.caseFoldCodesByString(this.regex.caseFoldFlag, chars1[pt]);
            if (items.length == 0) {
               if (stringNode == null) {
                  if (r == null && prevNode.p != null) {
                     topRoot = r = ConsAltNode.listAdd((ConsAltNode)null, (Node)prevNode.p);
                  }

                  prevNode.p = stringNode = new StringNode();
                  if (r != null) {
                     ConsAltNode.listAdd(r, stringNode);
                  }
               }

               stringNode.cat(chars1, pt, pt + 1);
            } else {
               altNum *= items.length + 1;
               if (altNum > 8) {
                  break;
               }

               if (r == null && prevNode.p != null) {
                  topRoot = r = ConsAltNode.listAdd((ConsAltNode)null, (Node)prevNode.p);
               }

               expandCaseFoldStringAlt(items.length, items, chars1, pt, 1, end, prevNode);
               if (r != null) {
                  ConsAltNode.listAdd(r, (Node)prevNode.p);
               }

               stringNode = null;
            }
         }

         if (pt < end) {
            Node srem = this.expandCaseFoldMakeRemString(chars1, pt, end);
            if (prevNode.p != null && r == null) {
               topRoot = r = ConsAltNode.listAdd((ConsAltNode)null, (Node)prevNode.p);
            }

            if (r == null) {
               prevNode.p = srem;
            } else {
               ConsAltNode.listAdd(r, srem);
            }
         }

         Node xnode = topRoot != null ? topRoot : (Node)prevNode.p;
         this.swap(node, (Node)xnode);
         return (Node)xnode;
      } else {
         return node;
      }
   }

   protected final Node setupTree(Node nodep, int statep) {
      Node node = nodep;
      int state = statep;

      while(true) {
         int len;
         switch(node.getType()) {
         case 0:
            if (Option.isIgnoreCase(this.regex.options) && !((StringNode)node).isRaw()) {
               node = this.expandCaseFoldString(node);
            }

            return node;
         case 1:
         case 2:
         case 3:
         default:
            return node;
         case 4:
            BackRefNode br = (BackRefNode)node;
            if (br.backRef > this.env.numMem) {
               throw new ValueException("invalid backref number");
            }

            this.env.backrefedMem = BitStatus.bsOnAt(this.env.backrefedMem, br.backRef);
            this.env.btMemStart = BitStatus.bsOnAt(this.env.btMemStart, br.backRef);
            ((EncloseNode)this.env.memNodes[br.backRef]).setMemBackrefed();
            return node;
         case 5:
            QuantifierNode qn = (QuantifierNode)node;
            Node target = qn.target;
            if ((statep & 4) != 0) {
               qn.setInRepeat();
            }

            if (QuantifierNode.isRepeatInfinite(qn.upper) || qn.lower >= 1) {
               int d = this.getMinMatchLength(target);
               if (d == 0) {
                  qn.targetEmptyInfo = 1;
                  len = this.quantifiersMemoryInfo(target);
                  if (len > 0) {
                     qn.targetEmptyInfo = len;
                  }
               }
            }

            state = statep | 4;
            if (qn.lower != qn.upper) {
               state |= 8;
            }

            target = this.setupTree(target, state);
            if (target.getType() == 0 && !QuantifierNode.isRepeatInfinite(qn.lower) && qn.lower == qn.upper && qn.lower > 1 && qn.lower <= 100) {
               StringNode sn = (StringNode)target;
               len = sn.length();
               if (len * qn.lower <= 100) {
                  StringNode str = qn.convertToString(sn.flag);
                  int n = qn.lower;

                  for(int i = 0; i < n; ++i) {
                     str.cat(sn.chars, sn.p, sn.end);
                  }

                  return node;
               }
            }

            if (qn.greedy && qn.targetEmptyInfo != 0) {
               if (target.getType() == 5) {
                  QuantifierNode tqn = (QuantifierNode)target;
                  if (tqn.headExact != null) {
                     qn.headExact = tqn.headExact;
                     tqn.headExact = null;
                     return node;
                  }
               } else {
                  qn.headExact = this.getHeadValueNode(qn.target, true);
               }

               return node;
            }

            return node;
         case 6:
            EncloseNode en = (EncloseNode)node;
            switch(en.type) {
            case 1:
               if ((statep & 11) != 0) {
                  this.env.btMemStart = BitStatus.bsOnAt(this.env.btMemStart, en.regNum);
               }

               this.setupTree(en.target, statep);
               return node;
            case 2:
               len = this.regex.options;
               this.regex.options = en.option;
               this.setupTree(en.target, statep);
               this.regex.options = len;
               return node;
            case 3:
            default:
               return node;
            case 4:
               this.setupTree(en.target, statep);
               if (en.target.getType() == 5) {
                  QuantifierNode tqn = (QuantifierNode)en.target;
                  if (QuantifierNode.isRepeatInfinite(tqn.upper) && tqn.lower <= 1 && tqn.greedy && tqn.target.isSimple()) {
                     en.setStopBtSimpleRepeat();
                     return node;
                  }
               }

               return node;
            }
         case 7:
            AnchorNode an = (AnchorNode)node;
            switch(an.type) {
            case 1024:
               this.setupTree(an.target, statep);
               return node;
            case 2048:
               this.setupTree(an.target, statep | 2);
               return node;
            case 4096:
               if (this.checkTypeTree(an.target, 2031, 1, 4135)) {
                  throw new SyntaxException("invalid pattern in look-behind");
               }

               node = this.setupLookBehind(node);
               if (node.getType() != 7) {
                  continue;
               }

               this.setupTree(((AnchorNode)node).target, statep);
               return node;
            case 8192:
               if (this.checkTypeTree(an.target, 2031, 1, 4135)) {
                  throw new SyntaxException("invalid pattern in look-behind");
               }

               node = this.setupLookBehind(node);
               if (node.getType() != 7) {
                  continue;
               }

               this.setupTree(((AnchorNode)node).target, statep | 2);
               return node;
            default:
               return node;
            }
         case 8:
            ConsAltNode lin = (ConsAltNode)node;
            Node prev = null;

            do {
               this.setupTree(lin.car, state);
               if (prev != null) {
                  this.nextSetup(prev, lin.car);
               }

               prev = lin.car;
            } while((lin = lin.cdr) != null);

            return node;
         case 9:
            ConsAltNode aln = (ConsAltNode)node;

            do {
               this.setupTree(aln.car, state | 1);
            } while((aln = aln.cdr) != null);

            return node;
         }
      }
   }

   private void optimizeNodeLeft(Node node, NodeOptInfo opt, OptEnvironment oenv) {
      opt.clear();
      opt.setBoundNode(oenv.mmd);
      NodeOptInfo nopt;
      int max;
      NodeOptInfo nopt;
      int i;
      int i;
      switch(node.getType()) {
      case 0:
         StringNode sn = (StringNode)node;
         i = sn.length();
         if (!sn.isAmbig()) {
            opt.exb.concatStr(sn.chars, sn.p, sn.end, sn.isRaw());
            if (i > 0) {
               opt.map.addChar(sn.chars[sn.p]);
            }

            opt.length.set(i, i);
         } else {
            if (sn.isDontGetOptInfo()) {
               i = sn.length();
            } else {
               opt.exb.concatStr(sn.chars, sn.p, sn.end, sn.isRaw());
               opt.exb.ignoreCase = true;
               if (i > 0) {
                  opt.map.addCharAmb(sn.chars, sn.p, sn.end, oenv.caseFoldFlag);
               }

               i = i;
            }

            opt.length.set(i, i);
         }

         if (opt.exb.length == i) {
            opt.exb.reachEnd = true;
         }
         break;
      case 1:
         CClassNode cc = (CClassNode)node;
         if (cc.mbuf == null && !cc.isNot()) {
            for(i = 0; i < 256; ++i) {
               boolean z = cc.bs.at(i);
               if (z && !cc.isNot() || !z && cc.isNot()) {
                  opt.map.addChar(i);
               }
            }

            opt.length.set(1, 1);
         } else {
            opt.length.set(1, 1);
         }
         break;
      case 2:
      default:
         throw new InternalException("internal parser error (bug)");
      case 3:
         opt.length.set(1, 1);
         break;
      case 4:
         BackRefNode br = (BackRefNode)node;
         if (br.isRecursion()) {
            opt.length.set(0, Integer.MAX_VALUE);
         } else {
            Node[] nodes = oenv.scanEnv.memNodes;
            i = this.getMinMatchLength(nodes[br.backRef]);
            max = this.getMaxMatchLength(nodes[br.backRef]);
            opt.length.set(i, max);
         }
         break;
      case 5:
         nopt = new NodeOptInfo();
         QuantifierNode qn = (QuantifierNode)node;
         this.optimizeNodeLeft(qn.target, nopt, oenv);
         if (qn.lower == 0 && QuantifierNode.isRepeatInfinite(qn.upper)) {
            if (oenv.mmd.max == 0 && qn.target.getType() == 3 && qn.greedy) {
               if (Option.isMultiline(oenv.options)) {
                  opt.anchor.add(32768);
               } else {
                  opt.anchor.add(16384);
               }
            }
         } else if (qn.lower > 0) {
            opt.copy(nopt);
            if (nopt.exb.length > 0 && nopt.exb.reachEnd) {
               for(i = 2; i <= qn.lower && !opt.exb.isFull(); ++i) {
                  opt.exb.concat(nopt.exb);
               }

               if (i < qn.lower) {
                  opt.exb.reachEnd = false;
               }
            }

            if (qn.lower != qn.upper) {
               opt.exb.reachEnd = false;
               opt.exm.reachEnd = false;
            }

            if (qn.lower > 1) {
               opt.exm.reachEnd = false;
            }
         }

         i = MinMaxLen.distanceMultiply(nopt.length.min, qn.lower);
         if (QuantifierNode.isRepeatInfinite(qn.upper)) {
            max = nopt.length.max > 0 ? Integer.MAX_VALUE : 0;
         } else {
            max = MinMaxLen.distanceMultiply(nopt.length.max, qn.upper);
         }

         opt.length.set(i, max);
         break;
      case 6:
         EncloseNode en = (EncloseNode)node;
         switch(en.type) {
         case 1:
            if (++en.optCount > 5) {
               i = 0;
               max = Integer.MAX_VALUE;
               if (en.isMinFixed()) {
                  i = en.minLength;
               }

               if (en.isMaxFixed()) {
                  max = en.maxLength;
               }

               opt.length.set(i, max);
            } else {
               this.optimizeNodeLeft(en.target, opt, oenv);
               if (opt.anchor.isSet(49152) && BitStatus.bsAt(oenv.scanEnv.backrefedMem, en.regNum)) {
                  opt.anchor.remove(49152);
                  return;
               }
            }

            return;
         case 2:
            i = oenv.options;
            oenv.options = en.option;
            this.optimizeNodeLeft(en.target, opt, oenv);
            oenv.options = i;
            return;
         case 3:
         default:
            return;
         case 4:
            this.optimizeNodeLeft(en.target, opt, oenv);
            return;
         }
      case 7:
         AnchorNode an = (AnchorNode)node;
         switch(an.type) {
         case 1:
         case 2:
         case 4:
         case 8:
         case 16:
         case 32:
            opt.anchor.add(an.type);
            return;
         case 1024:
            nopt = new NodeOptInfo();
            this.optimizeNodeLeft(an.target, nopt, oenv);
            if (nopt.exb.length > 0) {
               opt.expr.copy(nopt.exb);
            } else if (nopt.exm.length > 0) {
               opt.expr.copy(nopt.exm);
            }

            opt.expr.reachEnd = false;
            if (nopt.map.value > 0) {
               opt.map.copy(nopt.map);
            }

            return;
         case 2048:
         case 4096:
         case 8192:
         default:
            return;
         }
      case 8:
         OptEnvironment nenv = new OptEnvironment();
         nopt = new NodeOptInfo();
         nenv.copy(oenv);
         ConsAltNode lin = (ConsAltNode)node;

         do {
            this.optimizeNodeLeft(lin.car, nopt, nenv);
            nenv.mmd.add(nopt.length);
            opt.concatLeftNode(nopt);
         } while((lin = lin.cdr) != null);

         return;
      case 9:
         nopt = new NodeOptInfo();
         ConsAltNode aln = (ConsAltNode)node;

         do {
            this.optimizeNodeLeft(aln.car, nopt, oenv);
            if (aln == node) {
               opt.copy(nopt);
            } else {
               opt.altMerge(nopt, oenv);
            }
         } while((aln = aln.cdr) != null);
      }

   }

   protected final void setOptimizedInfoFromTree(Node node) {
      NodeOptInfo opt = new NodeOptInfo();
      OptEnvironment oenv = new OptEnvironment();
      oenv.options = this.regex.options;
      oenv.caseFoldFlag = this.regex.caseFoldFlag;
      oenv.scanEnv = this.env;
      oenv.mmd.clear();
      this.optimizeNodeLeft(node, opt, oenv);
      this.regex.anchor = opt.anchor.leftAnchor & '쀅';
      Regex var10000 = this.regex;
      var10000.anchor |= opt.anchor.rightAnchor & 24;
      if ((this.regex.anchor & 24) != 0) {
         this.regex.anchorDmin = opt.length.min;
         this.regex.anchorDmax = opt.length.max;
      }

      if (opt.exb.length <= 0 && opt.exm.length <= 0) {
         if (opt.map.value > 0) {
            this.regex.setOptimizeMapInfo(opt.map);
            this.regex.setSubAnchor(opt.map.anchor);
         } else {
            var10000 = this.regex;
            var10000.subAnchor |= opt.anchor.leftAnchor & 2;
            if (opt.length.max == 0) {
               var10000 = this.regex;
               var10000.subAnchor |= opt.anchor.rightAnchor & 32;
            }
         }
      } else {
         opt.exb.select(opt.exm);
         if (opt.map.value > 0 && opt.exb.compare(opt.map) > 0) {
            this.regex.setOptimizeMapInfo(opt.map);
            this.regex.setSubAnchor(opt.map.anchor);
         } else {
            this.regex.setExactInfo(opt.exb);
            this.regex.setSubAnchor(opt.exb.anchor);
         }
      }

   }
}
