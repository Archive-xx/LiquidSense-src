package kotlin.text;

import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0019\b\u0086\u0001\u0018\u0000 \u001b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001a¨\u0006\u001c"},
   d2 = {"Lkotlin/text/CharDirectionality;", "", "value", "", "(Ljava/lang/String;II)V", "getValue", "()I", "UNDEFINED", "LEFT_TO_RIGHT", "RIGHT_TO_LEFT", "RIGHT_TO_LEFT_ARABIC", "EUROPEAN_NUMBER", "EUROPEAN_NUMBER_SEPARATOR", "EUROPEAN_NUMBER_TERMINATOR", "ARABIC_NUMBER", "COMMON_NUMBER_SEPARATOR", "NONSPACING_MARK", "BOUNDARY_NEUTRAL", "PARAGRAPH_SEPARATOR", "SEGMENT_SEPARATOR", "WHITESPACE", "OTHER_NEUTRALS", "LEFT_TO_RIGHT_EMBEDDING", "LEFT_TO_RIGHT_OVERRIDE", "RIGHT_TO_LEFT_EMBEDDING", "RIGHT_TO_LEFT_OVERRIDE", "POP_DIRECTIONAL_FORMAT", "Companion", "kotlin-stdlib"}
)
public enum CharDirectionality {
   UNDEFINED,
   LEFT_TO_RIGHT,
   RIGHT_TO_LEFT,
   RIGHT_TO_LEFT_ARABIC,
   EUROPEAN_NUMBER,
   EUROPEAN_NUMBER_SEPARATOR,
   EUROPEAN_NUMBER_TERMINATOR,
   ARABIC_NUMBER,
   COMMON_NUMBER_SEPARATOR,
   NONSPACING_MARK,
   BOUNDARY_NEUTRAL,
   PARAGRAPH_SEPARATOR,
   SEGMENT_SEPARATOR,
   WHITESPACE,
   OTHER_NEUTRALS,
   LEFT_TO_RIGHT_EMBEDDING,
   LEFT_TO_RIGHT_OVERRIDE,
   RIGHT_TO_LEFT_EMBEDDING,
   RIGHT_TO_LEFT_OVERRIDE,
   POP_DIRECTIONAL_FORMAT;

   private final int value;
   private static final Lazy directionalityMap$delegate = LazyKt.lazy((Function0)null.INSTANCE);
   public static final CharDirectionality.Companion Companion = new CharDirectionality.Companion((DefaultConstructorMarker)null);

   public final int getValue() {
      return this.value;
   }

   private CharDirectionality(int value) {
      this.value = value;
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0005R'\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006\r"},
      d2 = {"Lkotlin/text/CharDirectionality$Companion;", "", "()V", "directionalityMap", "", "", "Lkotlin/text/CharDirectionality;", "getDirectionalityMap", "()Ljava/util/Map;", "directionalityMap$delegate", "Lkotlin/Lazy;", "valueOf", "directionality", "kotlin-stdlib"}
   )
   public static final class Companion {
      // $FF: synthetic field
      static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(CharDirectionality.Companion.class), "directionalityMap", "getDirectionalityMap()Ljava/util/Map;"))};

      private final Map<Integer, CharDirectionality> getDirectionalityMap() {
         Lazy var1 = CharDirectionality.directionalityMap$delegate;
         CharDirectionality.Companion var2 = CharDirectionality.Companion;
         KProperty var3 = $$delegatedProperties[0];
         boolean var4 = false;
         return (Map)var1.getValue();
      }

      @NotNull
      public final CharDirectionality valueOf(int directionality) {
         CharDirectionality var10000 = (CharDirectionality)((CharDirectionality.Companion)this).getDirectionalityMap().get(directionality);
         if (var10000 != null) {
            return var10000;
         } else {
            throw (Throwable)(new IllegalArgumentException("Directionality #" + directionality + " is not defined."));
         }
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
