package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.event.EventState;

// $FF: synthetic class
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 3
)
public final class NoSlow$WhenMappings {
   // $FF: synthetic field
   public static final int[] $EnumSwitchMapping$0 = new int[EventState.values().length];
   // $FF: synthetic field
   public static final int[] $EnumSwitchMapping$1;
   // $FF: synthetic field
   public static final int[] $EnumSwitchMapping$2;

   static {
      $EnumSwitchMapping$0[EventState.PRE.ordinal()] = 1;
      $EnumSwitchMapping$0[EventState.POST.ordinal()] = 2;
      $EnumSwitchMapping$1 = new int[EventState.values().length];
      $EnumSwitchMapping$1[EventState.PRE.ordinal()] = 1;
      $EnumSwitchMapping$1[EventState.POST.ordinal()] = 2;
      $EnumSwitchMapping$2 = new int[EventState.values().length];
      $EnumSwitchMapping$2[EventState.PRE.ordinal()] = 1;
      $EnumSwitchMapping$2[EventState.POST.ordinal()] = 2;
   }
}
