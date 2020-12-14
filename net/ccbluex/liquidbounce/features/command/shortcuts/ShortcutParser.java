package net.ccbluex.liquidbounce.features.command.shortcuts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.PrimitiveIterator.OfInt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\n\u0010\n\u001a\u00060\u000bj\u0002`\fH\u0002J\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u000e2\u0006\u0010\u0010\u001a\u00020\u000fJ\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u000e2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/shortcuts/ShortcutParser;", "", "()V", "SEPARATOR", "", "finishLiteral", "", "tokens", "", "Lnet/ccbluex/liquidbounce/features/command/shortcuts/Token;", "tokenBuf", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "parse", "", "", "script", "tokenize", "LiquidSense"}
)
public final class ShortcutParser {
   // $FF: synthetic field
   public static final ShortcutParser INSTANCE;
   // $FF: synthetic field
   private static final int SEPARATOR;

   private final List<Token> tokenize(String lIIlllIIlIIIll) {
      int lIIlllIIlIIIIl = false;
      boolean lIIlllIIlIIIlI = (List)(new ArrayList());
      int lIIlllIIlIIIIl = new StringBuilder();
      OfInt lIIlllIIIlllll = lIIlllIIlIIIll.codePoints().iterator();

      while(lIIlllIIIlllll.hasNext()) {
         Integer lIIlllIIlIlIIl = lIIlllIIIlllll.next();
         Intrinsics.checkExpressionValueIsNotNull(lIIlllIIlIlIIl, "code");
         if (Character.isWhitespace(lIIlllIIlIlIIl)) {
            lIIlllIIlIIlII.finishLiteral(lIIlllIIlIIIlI, lIIlllIIlIIIIl);
         } else {
            boolean lIIlllIIIllllI = SEPARATOR;
            boolean var10001;
            if (lIIlllIIlIlIIl == lIIlllIIIllllI) {
               lIIlllIIlIIlII.finishLiteral(lIIlllIIlIIIlI, lIIlllIIlIIIIl);
               boolean lIIlllIIIllllI = (Collection)lIIlllIIlIIIlI;
               int lIIlllIIIlllIl = new StatementEnd();
               float lIIlllIIIlllII = false;
               lIIlllIIIllllI.add(lIIlllIIIlllIl);
               var10001 = false;
            } else {
               lIIlllIIlIIIIl.appendCodePoint(lIIlllIIlIlIIl);
               var10001 = false;
            }
         }
      }

      int lIIlllIIlIIIII = (CharSequence)lIIlllIIlIIIIl;
      int lIIlllIIIlllll = false;
      if (lIIlllIIlIIIII.length() > 0) {
         throw (Throwable)(new IllegalArgumentException("Unexpected end of literal!"));
      } else {
         return lIIlllIIlIIIlI;
      }
   }

   @NotNull
   public final List<List<String>> parse(@NotNull String lIIlllIIlllllI) {
      Intrinsics.checkParameterIsNotNull(lIIlllIIlllllI, "script");
      List lIIlllIlIIIIII = lIIlllIIllllll.tokenize(lIIlllIIlllllI);
      char lIIlllIIlllIIl = false;
      List lIIlllIlIIIIIl = (List)(new ArrayList());
      char lIIlllIIlllIII = false;
      char lIIlllIIlllIIl = (List)(new ArrayList());
      Iterator lIIlllIIllIlll = lIIlllIlIIIIII.iterator();

      while(lIIlllIIllIlll.hasNext()) {
         char lIIlllIIlllIII = (Token)lIIlllIIllIlll.next();
         Collection lIIlllIIllIlIl;
         boolean lIIlllIIllIIll;
         boolean var10001;
         if (lIIlllIIlllIII instanceof Literal) {
            lIIlllIIllIlIl = (Collection)lIIlllIIlllIIl;
            double lIIlllIIllIlII = ((Literal)lIIlllIIlllIII).getLiteral();
            lIIlllIIllIIll = false;
            lIIlllIIllIlIl.add(lIIlllIIllIlII);
            var10001 = false;
         } else if (lIIlllIIlllIII instanceof StatementEnd) {
            lIIlllIIllIlIl = (Collection)lIIlllIlIIIIIl;
            double lIIlllIIllIlII = CollectionsKt.toList((Iterable)lIIlllIIlllIIl);
            lIIlllIIllIIll = false;
            lIIlllIIllIlIl.add(lIIlllIIllIlII);
            var10001 = false;
            lIIlllIIlllIIl.clear();
         }
      }

      char lIIlllIIlllIII = (Collection)lIIlllIIlllIIl;
      boolean lIIlllIIllIlll = false;
      if (!lIIlllIIlllIII.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Unexpected end of statement!"));
      } else {
         return lIIlllIlIIIIIl;
      }
   }

   static {
      short lIIlllIIIIIlll = new ShortcutParser();
      INSTANCE = lIIlllIIIIIlll;
      String lIIlllIIIIIllI = ";";
      byte lIIlllIIIIIlIl = 0;
      boolean lIIlllIIIIIlII = false;
      SEPARATOR = lIIlllIIIIIllI.codePointAt(lIIlllIIIIIlIl);
   }

   private final void finishLiteral(List<Token> lIIlllIIIlIlIl, StringBuilder lIIlllIIIlIIlI) {
      char lIIlllIIIlIIIl = (CharSequence)lIIlllIIIlIIlI;
      boolean lIIlllIIIlIIII = false;
      if (lIIlllIIIlIIIl.length() > 0) {
         char lIIlllIIIlIIIl = (Collection)lIIlllIIIlIlIl;
         String var10002 = String.valueOf(lIIlllIIIlIIlI);
         Intrinsics.checkExpressionValueIsNotNull(var10002, "tokenBuf.toString()");
         boolean lIIlllIIIlIIII = new Literal(var10002);
         double lIIlllIIIIllll = false;
         lIIlllIIIlIIIl.add(lIIlllIIIlIIII);
         boolean var10001 = false;
         StringsKt.clear(lIIlllIIIlIIlI);
         var10001 = false;
      }

   }

   private ShortcutParser() {
   }
}
