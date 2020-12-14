package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/EventState;", "", "stateName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getStateName", "()Ljava/lang/String;", "PRE", "POST", "LiquidSense"}
)
public enum EventState {
   // $FF: synthetic field
   POST,
   // $FF: synthetic field
   PRE;

   // $FF: synthetic field
   @NotNull
   private final String stateName;

   @NotNull
   public final String getStateName() {
      return llllllllllllllllllIllIlIIIlIIIIl.stateName;
   }

   private EventState(String llllllllllllllllllIllIlIIIIllIII) {
      llllllllllllllllllIllIlIIIIlIlll.stateName = llllllllllllllllllIllIlIIIIllIII;
   }
}
