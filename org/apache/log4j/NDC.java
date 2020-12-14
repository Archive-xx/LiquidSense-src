package org.apache.log4j;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;
import org.apache.log4j.helpers.LogLog;

public class NDC {
   static Hashtable ht = new Hashtable();
   static int pushCounter = 0;
   static final int REAP_THRESHOLD = 5;

   private NDC() {
   }

   private static Stack getCurrentStack() {
      return ht != null ? (Stack)ht.get(Thread.currentThread()) : null;
   }

   public static void clear() {
      Stack stack = getCurrentStack();
      if (stack != null) {
         stack.setSize(0);
      }

   }

   public static Stack cloneStack() {
      Stack stack = getCurrentStack();
      return stack == null ? null : (Stack)stack.clone();
   }

   public static void inherit(Stack stack) {
      if (stack != null) {
         ht.put(Thread.currentThread(), stack);
      }

   }

   public static String get() {
      Stack s = getCurrentStack();
      return s != null && !s.isEmpty() ? ((NDC.DiagnosticContext)s.peek()).fullMessage : null;
   }

   public static int getDepth() {
      Stack stack = getCurrentStack();
      return stack == null ? 0 : stack.size();
   }

   private static void lazyRemove() {
      if (ht != null) {
         Vector v;
         int i;
         synchronized(ht) {
            if (++pushCounter <= 5) {
               return;
            }

            pushCounter = 0;
            i = 0;
            v = new Vector();
            Enumeration enumeration = ht.keys();

            while(true) {
               if (!enumeration.hasMoreElements() || i > 4) {
                  break;
               }

               Thread t = (Thread)enumeration.nextElement();
               if (t.isAlive()) {
                  ++i;
               } else {
                  i = 0;
                  v.addElement(t);
               }
            }
         }

         int size = v.size();

         for(i = 0; i < size; ++i) {
            Thread t = (Thread)v.elementAt(i);
            LogLog.debug("Lazy NDC removal for thread [" + t.getName() + "] (" + ht.size() + ").");
            ht.remove(t);
         }

      }
   }

   public static String pop() {
      Stack stack = getCurrentStack();
      return stack != null && !stack.isEmpty() ? ((NDC.DiagnosticContext)stack.pop()).message : "";
   }

   public static String peek() {
      Stack stack = getCurrentStack();
      return stack != null && !stack.isEmpty() ? ((NDC.DiagnosticContext)stack.peek()).message : "";
   }

   public static void push(String message) {
      Stack stack = getCurrentStack();
      NDC.DiagnosticContext dc;
      if (stack == null) {
         dc = new NDC.DiagnosticContext(message, (NDC.DiagnosticContext)null);
         stack = new Stack();
         Thread key = Thread.currentThread();
         ht.put(key, stack);
         stack.push(dc);
      } else if (stack.isEmpty()) {
         dc = new NDC.DiagnosticContext(message, (NDC.DiagnosticContext)null);
         stack.push(dc);
      } else {
         dc = (NDC.DiagnosticContext)stack.peek();
         stack.push(new NDC.DiagnosticContext(message, dc));
      }

   }

   public static void remove() {
      if (ht != null) {
         ht.remove(Thread.currentThread());
         lazyRemove();
      }

   }

   public static void setMaxDepth(int maxDepth) {
      Stack stack = getCurrentStack();
      if (stack != null && maxDepth < stack.size()) {
         stack.setSize(maxDepth);
      }

   }

   private static class DiagnosticContext {
      String fullMessage;
      String message;

      DiagnosticContext(String message, NDC.DiagnosticContext parent) {
         this.message = message;
         if (parent != null) {
            this.fullMessage = parent.fullMessage + ' ' + message;
         } else {
            this.fullMessage = message;
         }

      }
   }
}
