package org.apache.log4j.pattern;

import java.util.ArrayList;
import java.util.List;

public abstract class NameAbbreviator {
   private static final NameAbbreviator DEFAULT = new NameAbbreviator.NOPAbbreviator();

   public static NameAbbreviator getAbbreviator(String pattern) {
      if (pattern.length() > 0) {
         String trimmed = pattern.trim();
         if (trimmed.length() == 0) {
            return DEFAULT;
         } else {
            int i = 0;
            if (trimmed.length() > 0) {
               if (trimmed.charAt(0) == '-') {
                  ++i;
               }

               while(i < trimmed.length() && trimmed.charAt(i) >= '0' && trimmed.charAt(i) <= '9') {
                  ++i;
               }
            }

            if (i == trimmed.length()) {
               int elements = Integer.parseInt(trimmed);
               return (NameAbbreviator)(elements >= 0 ? new NameAbbreviator.MaxElementAbbreviator(elements) : new NameAbbreviator.DropElementAbbreviator(-elements));
            } else {
               ArrayList fragments = new ArrayList(5);

               for(int pos = 0; pos < trimmed.length() && pos >= 0; ++pos) {
                  int ellipsisPos = pos;
                  int charCount;
                  if (trimmed.charAt(pos) == '*') {
                     charCount = Integer.MAX_VALUE;
                     ellipsisPos = pos + 1;
                  } else if (trimmed.charAt(pos) >= '0' && trimmed.charAt(pos) <= '9') {
                     charCount = trimmed.charAt(pos) - 48;
                     ellipsisPos = pos + 1;
                  } else {
                     charCount = 0;
                  }

                  char ellipsis = 0;
                  if (ellipsisPos < trimmed.length()) {
                     ellipsis = trimmed.charAt(ellipsisPos);
                     if (ellipsis == '.') {
                        ellipsis = 0;
                     }
                  }

                  fragments.add(new NameAbbreviator.PatternAbbreviatorFragment(charCount, ellipsis));
                  pos = trimmed.indexOf(".", pos);
                  if (pos == -1) {
                     break;
                  }
               }

               return new NameAbbreviator.PatternAbbreviator(fragments);
            }
         }
      } else {
         return DEFAULT;
      }
   }

   public static NameAbbreviator getDefaultAbbreviator() {
      return DEFAULT;
   }

   public abstract void abbreviate(int var1, StringBuffer var2);

   private static class PatternAbbreviator extends NameAbbreviator {
      private final NameAbbreviator.PatternAbbreviatorFragment[] fragments;

      public PatternAbbreviator(List fragments) {
         if (fragments.size() == 0) {
            throw new IllegalArgumentException("fragments must have at least one element");
         } else {
            this.fragments = new NameAbbreviator.PatternAbbreviatorFragment[fragments.size()];
            fragments.toArray(this.fragments);
         }
      }

      public void abbreviate(int nameStart, StringBuffer buf) {
         int pos = nameStart;

         for(int i = 0; i < this.fragments.length - 1 && pos < buf.length(); ++i) {
            pos = this.fragments[i].abbreviate(buf, pos);
         }

         for(NameAbbreviator.PatternAbbreviatorFragment terminalFragment = this.fragments[this.fragments.length - 1]; pos < buf.length() && pos >= 0; pos = terminalFragment.abbreviate(buf, pos)) {
         }

      }
   }

   private static class PatternAbbreviatorFragment {
      private final int charCount;
      private final char ellipsis;

      public PatternAbbreviatorFragment(int charCount, char ellipsis) {
         this.charCount = charCount;
         this.ellipsis = ellipsis;
      }

      public int abbreviate(StringBuffer buf, int startPos) {
         int nextDot = buf.toString().indexOf(".", startPos);
         if (nextDot != -1) {
            if (nextDot - startPos > this.charCount) {
               buf.delete(startPos + this.charCount, nextDot);
               nextDot = startPos + this.charCount;
               if (this.ellipsis != 0) {
                  buf.insert(nextDot, this.ellipsis);
                  ++nextDot;
               }
            }

            ++nextDot;
         }

         return nextDot;
      }
   }

   private static class DropElementAbbreviator extends NameAbbreviator {
      private final int count;

      public DropElementAbbreviator(int count) {
         this.count = count;
      }

      public void abbreviate(int nameStart, StringBuffer buf) {
         int i = this.count;

         for(int pos = buf.indexOf(".", nameStart); pos != -1; pos = buf.indexOf(".", pos + 1)) {
            --i;
            if (i == 0) {
               buf.delete(nameStart, pos + 1);
               break;
            }
         }

      }
   }

   private static class MaxElementAbbreviator extends NameAbbreviator {
      private final int count;

      public MaxElementAbbreviator(int count) {
         this.count = count;
      }

      public void abbreviate(int nameStart, StringBuffer buf) {
         int end = buf.length() - 1;
         String bufString = buf.toString();

         for(int i = this.count; i > 0; --i) {
            end = bufString.lastIndexOf(".", end - 1);
            if (end == -1 || end < nameStart) {
               return;
            }
         }

         buf.delete(nameStart, end + 1);
      }
   }

   private static class NOPAbbreviator extends NameAbbreviator {
      public NOPAbbreviator() {
      }

      public void abbreviate(int nameStart, StringBuffer buf) {
      }
   }
}
