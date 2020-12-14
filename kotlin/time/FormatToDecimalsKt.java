package kotlin.time;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\u001a\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0000\u001a\u0018\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0000\u001a\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0000\"\u001c\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0004\"\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
   d2 = {"precisionFormats", "", "Ljava/lang/ThreadLocal;", "Ljava/text/DecimalFormat;", "[Ljava/lang/ThreadLocal;", "rootNegativeExpFormatSymbols", "Ljava/text/DecimalFormatSymbols;", "rootPositiveExpFormatSymbols", "scientificFormat", "createFormatForDecimals", "decimals", "", "formatScientific", "", "value", "", "formatToExactDecimals", "formatUpToDecimals", "kotlin-stdlib"}
)
public final class FormatToDecimalsKt {
   private static final DecimalFormatSymbols rootNegativeExpFormatSymbols;
   private static final DecimalFormatSymbols rootPositiveExpFormatSymbols;
   private static final ThreadLocal<DecimalFormat>[] precisionFormats;
   private static final ThreadLocal<DecimalFormat> scientificFormat;

   private static final DecimalFormat createFormatForDecimals(int decimals) {
      DecimalFormat var1 = new DecimalFormat("0", rootNegativeExpFormatSymbols);
      boolean var2 = false;
      boolean var3 = false;
      int var5 = false;
      if (decimals > 0) {
         var1.setMinimumFractionDigits(decimals);
      }

      var1.setRoundingMode(RoundingMode.HALF_UP);
      return var1;
   }

   @NotNull
   public static final String formatToExactDecimals(double value, int decimals) {
      DecimalFormat var11;
      if (decimals < precisionFormats.length) {
         ThreadLocal var4 = precisionFormats[decimals];
         boolean var5 = false;
         Object var10000 = var4.get();
         if (var10000 == null) {
            int var6 = false;
            DecimalFormat var12 = createFormatForDecimals(decimals);
            boolean var7 = false;
            boolean var8 = false;
            boolean var10 = false;
            var4.set(var12);
            var10000 = var12;
         }

         var11 = (DecimalFormat)var10000;
      } else {
         var11 = createFormatForDecimals(decimals);
      }

      DecimalFormat format = var11;
      String var13 = format.format(value);
      Intrinsics.checkExpressionValueIsNotNull(var13, "format.format(value)");
      return var13;
   }

   @NotNull
   public static final String formatUpToDecimals(double value, int decimals) {
      DecimalFormat var3 = createFormatForDecimals(0);
      boolean var4 = false;
      boolean var5 = false;
      int var7 = false;
      var3.setMaximumFractionDigits(decimals);
      String var10000 = var3.format(value);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "createFormatForDecimals(… }\n        .format(value)");
      return var10000;
   }

   @NotNull
   public static final String formatScientific(double value) {
      ThreadLocal var2 = scientificFormat;
      boolean var3 = false;
      Object var10000 = var2.get();
      boolean var4;
      DecimalFormat $this$apply;
      boolean var6;
      if (var10000 == null) {
         var4 = false;
         $this$apply = new DecimalFormat("0E0", rootNegativeExpFormatSymbols);
         var6 = false;
         boolean var7 = false;
         int var9 = false;
         $this$apply.setMinimumFractionDigits(2);
         DecimalFormat var11 = $this$apply;
         boolean var12 = false;
         var6 = false;
         boolean var8 = false;
         var2.set(var11);
         var10000 = var11;
      }

      Object var10 = var10000;
      var3 = false;
      var4 = false;
      $this$apply = (DecimalFormat)var10;
      var6 = false;
      $this$apply.setDecimalFormatSymbols(!(value >= (double)1) && !(value <= (double)-1) ? rootNegativeExpFormatSymbols : rootPositiveExpFormatSymbols);
      String var13 = ((DecimalFormat)var10).format(value);
      Intrinsics.checkExpressionValueIsNotNull(var13, "scientificFormat.getOrSe… }\n        .format(value)");
      return var13;
   }

   static {
      DecimalFormatSymbols var0 = new DecimalFormatSymbols(Locale.ROOT);
      boolean var1 = false;
      boolean var2 = false;
      int var4 = false;
      var0.setExponentSeparator("e");
      rootNegativeExpFormatSymbols = var0;
      var0 = new DecimalFormatSymbols(Locale.ROOT);
      var1 = false;
      var2 = false;
      var4 = false;
      var0.setExponentSeparator("e+");
      rootPositiveExpFormatSymbols = var0;
      byte var8 = 4;
      ThreadLocal[] var9 = new ThreadLocal[var8];

      for(int var10 = 0; var10 < var8; ++var10) {
         var4 = false;
         ThreadLocal var7 = new ThreadLocal();
         var9[var10] = var7;
      }

      precisionFormats = var9;
      scientificFormat = new ThreadLocal();
   }
}
