package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;

public abstract class Matcher extends IntHolder {
   protected final Regex regex;
   protected final char[] chars;
   protected final int str;
   protected final int end;
   protected int msaStart;
   protected int msaOptions;
   protected final Region msaRegion;
   protected int msaBestLen;
   protected int msaBestS;
   protected int msaBegin;
   protected int msaEnd;
   int low;
   int high;

   public Matcher(Regex regex, char[] chars) {
      this(regex, chars, 0, chars.length);
   }

   public Matcher(Regex regex, char[] chars, int p, int end) {
      this.regex = regex;
      this.chars = chars;
      this.str = p;
      this.end = end;
      this.msaRegion = regex.numMem == 0 ? null : new Region(regex.numMem + 1);
   }

   protected abstract int matchAt(int var1, int var2, int var3);

   public final Region getRegion() {
      return this.msaRegion;
   }

   public final int getBegin() {
      return this.msaBegin;
   }

   public final int getEnd() {
      return this.msaEnd;
   }

   protected final void msaInit(int option, int start) {
      this.msaOptions = option;
      this.msaStart = start;
      this.msaBestLen = -1;
   }

   public final int match(int at, int range, int option) {
      this.msaInit(option, at);
      int prev = EncodingHelper.prevCharHead(this.str, at);
      return this.matchAt(range, at, prev);
   }

   private boolean forwardSearchRange(char[] ch, int string, int e, int s, int range, IntHolder lowPrev) {
      int pprev = -1;
      int p = s;
      if (this.regex.dMin > 0) {
         p = s + this.regex.dMin;
      }

      while(true) {
         p = this.regex.searchAlgorithm.search(this.regex, ch, p, e, range);
         if (p != -1 && p < range) {
            if (p - this.regex.dMin < s) {
               pprev = p++;
               continue;
            }

            if (this.regex.subAnchor != 0) {
               switch(this.regex.subAnchor) {
               case 2:
                  if (p != string) {
                     int prev = EncodingHelper.prevCharHead(pprev != -1 ? pprev : string, p);
                     if (!EncodingHelper.isNewLine(ch, prev, e)) {
                        pprev = p++;
                        continue;
                     }
                  }
                  break;
               case 32:
                  if (p != e && !EncodingHelper.isNewLine(ch, p, e)) {
                     pprev = p++;
                     continue;
                  }
               }
            }

            if (this.regex.dMax == 0) {
               this.low = p;
               if (lowPrev != null) {
                  if (this.low > s) {
                     lowPrev.value = EncodingHelper.prevCharHead(s, p);
                  } else {
                     lowPrev.value = EncodingHelper.prevCharHead(pprev != -1 ? pprev : string, p);
                  }
               }
            } else if (this.regex.dMax != Integer.MAX_VALUE) {
               this.low = p - this.regex.dMax;
               if (this.low > s) {
                  this.low = EncodingHelper.rightAdjustCharHeadWithPrev(this.low, lowPrev);
                  if (lowPrev != null && lowPrev.value == -1) {
                     lowPrev.value = EncodingHelper.prevCharHead(pprev != -1 ? pprev : s, this.low);
                  }
               } else if (lowPrev != null) {
                  lowPrev.value = EncodingHelper.prevCharHead(pprev != -1 ? pprev : string, this.low);
               }
            }

            this.high = p - this.regex.dMin;
            return true;
         }

         return false;
      }
   }

   private boolean backwardSearchRange(char[] ch, int string, int e, int s, int range, int adjrange) {
      int r = range + this.regex.dMin;
      int p = s;

      label40:
      while(true) {
         p = this.regex.searchAlgorithm.searchBackward(this.regex, ch, r, adjrange, e, p, s, r);
         if (p == -1) {
            return false;
         }

         if (this.regex.subAnchor == 0) {
            break;
         }

         switch(this.regex.subAnchor) {
         case 2:
            if (p == string) {
               break label40;
            }

            int prev = EncodingHelper.prevCharHead(string, p);
            if (EncodingHelper.isNewLine(ch, prev, e)) {
               break label40;
            }

            p = prev;
            break;
         case 32:
            if (p != e && !EncodingHelper.isNewLine(ch, p, e)) {
               p = EncodingHelper.prevCharHead(adjrange, p);
               if (p == -1) {
                  return false;
               }
               break;
            }
         default:
            break label40;
         }
      }

      if (this.regex.dMax != Integer.MAX_VALUE) {
         this.low = p - this.regex.dMax;
         this.high = p - this.regex.dMin;
      }

      return true;
   }

   private boolean matchCheck(int upperRange, int s, int prev) {
      return this.matchAt(this.end, s, prev) != -1 && !Option.isFindLongest(this.regex.options);
   }

   public final int search(int startp, int rangep, int option) {
      int start = startp;
      int range = rangep;
      int origStart = startp;
      int origRange = rangep;
      if (startp <= this.end && startp >= this.str) {
         int s;
         int schRange;
         int schStart;
         if (this.regex.anchor != 0 && this.str < this.end) {
            if ((this.regex.anchor & 4) != 0) {
               if (rangep > startp) {
                  range = startp + 1;
               } else {
                  range = startp;
               }
            } else if ((this.regex.anchor & 1) != 0) {
               if (rangep > startp) {
                  if (startp != this.str) {
                     return -1;
                  }

                  range = this.str + 1;
               } else {
                  if (rangep > this.str) {
                     return -1;
                  }

                  start = this.str;
                  range = this.str;
               }
            } else if ((this.regex.anchor & 8) != 0) {
               schRange = schStart = this.end;
               if (this.endBuf(startp, rangep, schRange, schStart)) {
                  return -1;
               }
            } else if ((this.regex.anchor & 16) != 0) {
               int preEnd = EncodingHelper.stepBack(this.str, this.end, 1);
               schStart = this.end;
               if (EncodingHelper.isNewLine(this.chars, preEnd, this.end)) {
                  if (preEnd > this.str && startp <= preEnd && this.endBuf(startp, rangep, preEnd, schStart)) {
                     return -1;
                  }
               } else {
                  schRange = this.end;
                  if (this.endBuf(startp, rangep, schRange, schStart)) {
                     return -1;
                  }
               }
            } else if ((this.regex.anchor & '耀') != 0) {
               if (rangep > startp) {
                  range = startp + 1;
               } else {
                  range = startp;
               }
            }
         } else if (this.str == this.end) {
            if (this.regex.thresholdLength == 0) {
               s = start = this.str;
               int prev = -1;
               this.msaInit(option, start);
               if (this.matchCheck(this.end, s, prev)) {
                  return this.match(s);
               }

               return this.mismatch();
            }

            return -1;
         }

         this.msaInit(option, startp);
         s = start;
         int prev;
         if (range > start) {
            if (start > this.str) {
               prev = EncodingHelper.prevCharHead(this.str, start);
            } else {
               prev = 0;
            }

            if (this.regex.searchAlgorithm != SearchAlgorithm.NONE) {
               schRange = range;
               if (this.regex.dMax != 0) {
                  if (this.regex.dMax == Integer.MAX_VALUE) {
                     schRange = this.end;
                  } else {
                     schRange = range + this.regex.dMax;
                     if (schRange > this.end) {
                        schRange = this.end;
                     }
                  }
               }

               if (this.end - start < this.regex.thresholdLength) {
                  return this.mismatch();
               }

               if (this.regex.dMax != Integer.MAX_VALUE) {
                  do {
                     if (!this.forwardSearchRange(this.chars, this.str, this.end, s, schRange, this)) {
                        return this.mismatch();
                     }

                     if (s < this.low) {
                        s = this.low;
                        prev = this.value;
                     }

                     while(s <= this.high) {
                        if (this.matchCheck(origRange, s, prev)) {
                           return this.match(s);
                        }

                        prev = s++;
                     }
                  } while(s < range);
               }

               if (!this.forwardSearchRange(this.chars, this.str, this.end, s, schRange, (IntHolder)null)) {
                  return this.mismatch();
               }

               if ((this.regex.anchor & 16384) != 0) {
                  while(!this.matchCheck(origRange, s, prev)) {
                     prev = s++;
                     if (s >= range) {
                        return this.mismatch();
                     }
                  }

                  return this.match(s);
               }
            }

            do {
               if (this.matchCheck(origRange, s, prev)) {
                  return this.match(s);
               }

               prev = s++;
            } while(s < range);

            if (s == range && this.matchCheck(origRange, s, prev)) {
               return this.match(s);
            }
         } else {
            if (this.regex.searchAlgorithm != SearchAlgorithm.NONE) {
               if (range < this.end) {
                  schRange = range;
               } else {
                  schRange = this.end;
               }

               if (this.regex.dMax != Integer.MAX_VALUE && this.end - range >= this.regex.thresholdLength) {
                  do {
                     schStart = s + this.regex.dMax;
                     if (schStart > this.end) {
                        schStart = this.end;
                     }

                     if (!this.backwardSearchRange(this.chars, this.str, this.end, schStart, range, schRange)) {
                        return this.mismatch();
                     }

                     if (s > this.high) {
                        s = this.high;
                     }

                     while(s != -1 && s >= this.low) {
                        prev = EncodingHelper.prevCharHead(this.str, s);
                        if (this.matchCheck(origStart, s, prev)) {
                           return this.match(s);
                        }

                        s = prev;
                     }
                  } while(s >= range);

                  return this.mismatch();
               }

               if (this.end - range < this.regex.thresholdLength) {
                  return this.mismatch();
               }

               schStart = start;
               if (this.regex.dMax != 0) {
                  if (this.regex.dMax == Integer.MAX_VALUE) {
                     schStart = this.end;
                  } else {
                     schStart = start + this.regex.dMax;
                     if (schStart > this.end) {
                        schStart = this.end;
                     }
                  }
               }

               if (!this.backwardSearchRange(this.chars, this.str, this.end, schStart, range, schRange)) {
                  return this.mismatch();
               }
            }

            do {
               prev = EncodingHelper.prevCharHead(this.str, s);
               if (this.matchCheck(origStart, s, prev)) {
                  return this.match(s);
               }

               s = prev;
            } while(prev >= range);
         }

         return this.mismatch();
      } else {
         return -1;
      }
   }

   private boolean endBuf(int startp, int rangep, int minSemiEnd, int maxSemiEnd) {
      int start = startp;
      int range = rangep;
      if (maxSemiEnd - this.str < this.regex.anchorDmin) {
         return true;
      } else {
         if (rangep > startp) {
            if (minSemiEnd - startp > this.regex.anchorDmax) {
               start = minSemiEnd - this.regex.anchorDmax;
               if (start >= this.end) {
                  start = EncodingHelper.prevCharHead(this.str, this.end);
               }
            }

            if (maxSemiEnd - (rangep - 1) < this.regex.anchorDmin) {
               range = maxSemiEnd - this.regex.anchorDmin + 1;
            }

            if (start >= range) {
               return true;
            }
         } else {
            if (minSemiEnd - rangep > this.regex.anchorDmax) {
               range = minSemiEnd - this.regex.anchorDmax;
            }

            if (maxSemiEnd - startp < this.regex.anchorDmin) {
               start = maxSemiEnd - this.regex.anchorDmin;
            }

            if (range > start) {
               return true;
            }
         }

         return false;
      }
   }

   private int match(int s) {
      return s - this.str;
   }

   private int mismatch() {
      if (this.msaBestLen >= 0) {
         int s = this.msaBestS;
         return this.match(s);
      } else {
         return -1;
      }
   }
}
