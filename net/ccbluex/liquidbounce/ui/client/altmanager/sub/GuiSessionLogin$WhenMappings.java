package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.utils.login.LoginUtils;

// $FF: synthetic class
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 3
)
public final class GuiSessionLogin$WhenMappings {
   // $FF: synthetic field
   public static final int[] $EnumSwitchMapping$0 = new int[LoginUtils.LoginResult.values().length];

   static {
      $EnumSwitchMapping$0[LoginUtils.LoginResult.LOGGED.ordinal()] = 1;
      $EnumSwitchMapping$0[LoginUtils.LoginResult.FAILED_PARSE_TOKEN.ordinal()] = 2;
      $EnumSwitchMapping$0[LoginUtils.LoginResult.INVALID_ACCOUNT_DATA.ordinal()] = 3;
   }
}
