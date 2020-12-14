package jdk.nashorn.internal.runtime;

import java.math.BigInteger;

public final class NumberToString {
   private final boolean isNaN;
   private boolean isNegative;
   private int decimalExponent;
   private char[] digits;
   private int nDigits;
   private static final int expMask = 2047;
   private static final long fractMask = 4503599627370495L;
   private static final int expShift = 52;
   private static final int expBias = 1023;
   private static final long fractHOB = 4503599627370496L;
   private static final long expOne = 4607182418800017408L;
   private static final int maxSmallBinExp = 62;
   private static final int minSmallBinExp = -21;
   private static final long[] powersOf5 = new long[]{1L, 5L, 25L, 125L, 625L, 3125L, 15625L, 78125L, 390625L, 1953125L, 9765625L, 48828125L, 244140625L, 1220703125L, 6103515625L, 30517578125L, 152587890625L, 762939453125L, 3814697265625L, 19073486328125L, 95367431640625L, 476837158203125L, 2384185791015625L, 11920928955078125L, 59604644775390625L, 298023223876953125L, 1490116119384765625L};
   private static final int[] nBitsPowerOf5 = new int[]{0, 3, 5, 7, 10, 12, 14, 17, 19, 21, 24, 26, 28, 31, 33, 35, 38, 40, 42, 45, 47, 49, 52, 54, 56, 59, 61};
   private static final char[] infinityDigits = new char[]{'I', 'n', 'f', 'i', 'n', 'i', 't', 'y'};
   private static final char[] nanDigits = new char[]{'N', 'a', 'N'};
   private static final char[] zeroes = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'};
   private static BigInteger[] powerOf5Cache;

   public static String stringFor(double value) {
      return (new NumberToString(value)).toString();
   }

   private NumberToString(double value) {
      long bits = Double.doubleToLongBits(value);
      int upper = (int)(bits >> 32);
      this.isNegative = upper < 0;
      int exponent = upper >> 20 & 2047;
      bits &= 4503599627370495L;
      if (exponent == 2047) {
         this.isNaN = true;
         if (bits == 0L) {
            this.digits = infinityDigits;
         } else {
            this.digits = nanDigits;
            this.isNegative = false;
         }

         this.nDigits = this.digits.length;
      } else {
         this.isNaN = false;
         int nSignificantBits;
         if (exponent == 0) {
            if (bits == 0L) {
               this.decimalExponent = 0;
               this.digits = zeroes;
               this.nDigits = 1;
               return;
            }

            while((bits & 4503599627370496L) == 0L) {
               bits <<= 1;
               --exponent;
            }

            nSignificantBits = 52 + exponent + 1;
            ++exponent;
         } else {
            bits |= 4503599627370496L;
            nSignificantBits = 53;
         }

         exponent -= 1023;
         int nFractBits = countSignificantBits(bits);
         int nTinyBits = Math.max(0, nFractBits - exponent - 1);
         int i;
         int decExp;
         int ndigits;
         int c;
         if (exponent <= 62 && exponent >= -21 && nTinyBits < powersOf5.length && nFractBits + nBitsPowerOf5[nTinyBits] < 64 && nTinyBits == 0) {
            long halfULP;
            if (exponent > nSignificantBits) {
               halfULP = 1L << exponent - nSignificantBits - 1;
            } else {
               halfULP = 0L;
            }

            if (exponent >= 52) {
               bits <<= exponent - 52;
            } else {
               bits >>>= 52 - exponent;
            }

            for(i = 0; halfULP >= 10L; ++i) {
               halfULP /= 10L;
            }

            decExp = 0;
            if (i != 0) {
               long powerOf10 = powersOf5[i] << i;
               long residue = bits % powerOf10;
               bits /= powerOf10;
               decExp += i;
               if (residue >= powerOf10 >> 1) {
                  ++bits;
               }
            }

            int ndigits = 20;
            char[] digits0 = new char[26];
            int digitno = ndigits - 1;
            c = (int)(bits % 10L);

            for(bits /= 10L; c == 0; bits /= 10L) {
               ++decExp;
               c = (int)(bits % 10L);
            }

            while(bits != 0L) {
               digits0[digitno--] = (char)(c + 48);
               ++decExp;
               c = (int)(bits % 10L);
               bits /= 10L;
            }

            digits0[digitno] = (char)(c + 48);
            ndigits = ndigits - digitno;
            char[] result = new char[ndigits];
            System.arraycopy(digits0, digitno, result, 0, ndigits);
            this.digits = result;
            this.decimalExponent = decExp + 1;
            this.nDigits = ndigits;
         } else {
            double d2 = Double.longBitsToDouble(4607182418800017408L | bits & -4503599627370497L);
            int decExponent = (int)Math.floor((d2 - 1.5D) * 0.289529654D + 0.176091259D + (double)exponent * 0.301029995663981D);
            i = Math.max(0, -decExponent);
            decExp = i + nTinyBits + exponent;
            ndigits = Math.max(0, decExponent);
            int S2 = ndigits + nTinyBits;
            c = decExp - nSignificantBits;
            bits >>>= 53 - nFractBits;
            decExp -= nFractBits - 1;
            int common2factor = Math.min(decExp, S2);
            decExp -= common2factor;
            S2 -= common2factor;
            c -= common2factor;
            if (nFractBits == 1) {
               --c;
            }

            if (c < 0) {
               decExp -= c;
               S2 -= c;
               c = 0;
            }

            char[] digits0 = this.digits = new char[32];
            int Bbits = nFractBits + decExp + (i < nBitsPowerOf5.length ? nBitsPowerOf5[i] : i * 3);
            int tenSbits = S2 + 1 + (ndigits + 1 < nBitsPowerOf5.length ? nBitsPowerOf5[ndigits + 1] : (ndigits + 1) * 3);
            int ndigit;
            boolean low;
            boolean high;
            long lowDigitDifference;
            int q;
            if (Bbits < 64 && tenSbits < 64) {
               long b = bits * powersOf5[i] << decExp;
               long s = powersOf5[ndigits] << S2;
               long m = powersOf5[i] << c;
               long tens = s * 10L;
               ndigit = 0;
               q = (int)(b / s);
               b = 10L * (b % s);
               m *= 10L;
               low = b < m;
               high = b + m > tens;
               if (q == 0 && !high) {
                  --decExponent;
               } else {
                  digits0[ndigit++] = (char)(48 + q);
               }

               if (decExponent < -3 || decExponent >= 8) {
                  low = false;
                  high = false;
               }

               while(!low && !high) {
                  q = (int)(b / s);
                  b = 10L * (b % s);
                  m *= 10L;
                  if (m > 0L) {
                     low = b < m;
                     high = b + m > tens;
                  } else {
                     low = true;
                     high = true;
                  }

                  if (low && q == 0) {
                     break;
                  }

                  digits0[ndigit++] = (char)(48 + q);
               }

               lowDigitDifference = (b << 1) - tens;
            } else {
               BigInteger Bval = multiplyPowerOf5And2(BigInteger.valueOf(bits), i, decExp);
               BigInteger Sval = constructPowerOf5And2(ndigits, S2);
               BigInteger Mval = constructPowerOf5And2(i, c);
               int shiftBias = Long.numberOfLeadingZeros(bits) - 4;
               Bval = Bval.shiftLeft(shiftBias);
               Mval = Mval.shiftLeft(shiftBias);
               Sval = Sval.shiftLeft(shiftBias);
               BigInteger tenSval = Sval.multiply(BigInteger.TEN);
               ndigit = 0;
               BigInteger[] quoRem = Bval.divideAndRemainder(Sval);
               q = quoRem[0].intValue();
               Bval = quoRem[1].multiply(BigInteger.TEN);
               Mval = Mval.multiply(BigInteger.TEN);
               low = Bval.compareTo(Mval) < 0;
               high = Bval.add(Mval).compareTo(tenSval) > 0;
               if (q == 0 && !high) {
                  --decExponent;
               } else {
                  digits0[ndigit++] = (char)(48 + q);
               }

               if (decExponent < -3 || decExponent >= 8) {
                  low = false;
                  high = false;
               }

               while(!low && !high) {
                  quoRem = Bval.divideAndRemainder(Sval);
                  q = quoRem[0].intValue();
                  Bval = quoRem[1].multiply(BigInteger.TEN);
                  Mval = Mval.multiply(BigInteger.TEN);
                  low = Bval.compareTo(Mval) < 0;
                  high = Bval.add(Mval).compareTo(tenSval) > 0;
                  if (low && q == 0) {
                     break;
                  }

                  digits0[ndigit++] = (char)(48 + q);
               }

               if (high && low) {
                  Bval = Bval.shiftLeft(1);
                  lowDigitDifference = (long)Bval.compareTo(tenSval);
               } else {
                  lowDigitDifference = 0L;
               }
            }

            this.decimalExponent = decExponent + 1;
            this.digits = digits0;
            this.nDigits = ndigit;
            if (high) {
               if (low) {
                  if (lowDigitDifference == 0L) {
                     if ((digits0[this.nDigits - 1] & 1) != 0) {
                        this.roundup();
                     }
                  } else if (lowDigitDifference > 0L) {
                     this.roundup();
                  }
               } else {
                  this.roundup();
               }
            }

         }
      }
   }

   private static int countSignificantBits(long bits) {
      return bits != 0L ? 64 - Long.numberOfLeadingZeros(bits) - Long.numberOfTrailingZeros(bits) : 0;
   }

   private static BigInteger bigPowerOf5(int power) {
      if (powerOf5Cache == null) {
         powerOf5Cache = new BigInteger[power + 1];
      } else if (powerOf5Cache.length <= power) {
         BigInteger[] t = new BigInteger[power + 1];
         System.arraycopy(powerOf5Cache, 0, t, 0, powerOf5Cache.length);
         powerOf5Cache = t;
      }

      if (powerOf5Cache[power] != null) {
         return powerOf5Cache[power];
      } else if (power < powersOf5.length) {
         return powerOf5Cache[power] = BigInteger.valueOf(powersOf5[power]);
      } else {
         int q = power >> 1;
         int r = power - q;
         BigInteger bigQ = powerOf5Cache[q];
         if (bigQ == null) {
            bigQ = bigPowerOf5(q);
         }

         if (r < powersOf5.length) {
            return powerOf5Cache[power] = bigQ.multiply(BigInteger.valueOf(powersOf5[r]));
         } else {
            BigInteger bigR = powerOf5Cache[r];
            if (bigR == null) {
               bigR = bigPowerOf5(r);
            }

            return powerOf5Cache[power] = bigQ.multiply(bigR);
         }
      }
   }

   private static BigInteger multiplyPowerOf5And2(BigInteger value, int p5, int p2) {
      BigInteger returnValue = value;
      if (p5 != 0) {
         returnValue = value.multiply(bigPowerOf5(p5));
      }

      if (p2 != 0) {
         returnValue = returnValue.shiftLeft(p2);
      }

      return returnValue;
   }

   private static BigInteger constructPowerOf5And2(int p5, int p2) {
      BigInteger v = bigPowerOf5(p5);
      if (p2 != 0) {
         v = v.shiftLeft(p2);
      }

      return v;
   }

   private void roundup() {
      int i;
      char q;
      for(q = this.digits[i = this.nDigits - 1]; q == '9' && i > 0; q = this.digits[i]) {
         if (this.decimalExponent < 0) {
            --this.nDigits;
         } else {
            this.digits[i] = '0';
         }

         --i;
      }

      if (q == '9') {
         ++this.decimalExponent;
         this.digits[0] = '1';
      } else {
         this.digits[i] = (char)(q + 1);
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(32);
      if (this.isNegative) {
         sb.append('-');
      }

      if (this.isNaN) {
         sb.append(this.digits, 0, this.nDigits);
      } else {
         int exponent;
         if (this.decimalExponent > 0 && this.decimalExponent <= 21) {
            exponent = Math.min(this.nDigits, this.decimalExponent);
            sb.append(this.digits, 0, exponent);
            if (exponent < this.decimalExponent) {
               sb.append(zeroes, 0, this.decimalExponent - exponent);
            } else if (exponent < this.nDigits) {
               sb.append('.');
               sb.append(this.digits, exponent, this.nDigits - exponent);
            }
         } else if (this.decimalExponent <= 0 && this.decimalExponent > -6) {
            sb.append('0');
            sb.append('.');
            if (this.decimalExponent != 0) {
               sb.append(zeroes, 0, -this.decimalExponent);
            }

            sb.append(this.digits, 0, this.nDigits);
         } else {
            sb.append(this.digits[0]);
            if (this.nDigits > 1) {
               sb.append('.');
               sb.append(this.digits, 1, this.nDigits - 1);
            }

            sb.append('e');
            int e;
            if (this.decimalExponent <= 0) {
               sb.append('-');
               exponent = e = -this.decimalExponent + 1;
            } else {
               sb.append('+');
               exponent = e = this.decimalExponent - 1;
            }

            if (exponent > 99) {
               sb.append((char)(e / 100 + 48));
               e %= 100;
            }

            if (exponent > 9) {
               sb.append((char)(e / 10 + 48));
               e %= 10;
            }

            sb.append((char)(e + 48));
         }
      }

      return sb.toString();
   }
}
