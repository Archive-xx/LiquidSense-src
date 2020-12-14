package net.ccbluex.liquidbounce.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.ccbluex.liquidbounce.features.module.modules.misc.Spammer;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/SettingsUtils;", "", "()V", "executeScript", "", "script", "", "generateScript", "values", "", "binds", "states", "LiquidSense"}
)
public final class SettingsUtils {
   // $FF: synthetic field
   public static final SettingsUtils INSTANCE;

   @NotNull
   public final String generateScript(boolean llIlllllIIIll, boolean llIlllllIIlIl, boolean llIlllllIIlII) {
      double llIlllllIIIII = new StringBuilder();
      float llIllllIlllll = (Iterable)LiquidBounce.INSTANCE.getModuleManager().getModules();
      int llIlllllIlIIl = false;
      Collection llIllllllIllI = (Collection)(new ArrayList());
      String llIllllIllIll = false;
      Iterator llIllllIllIlI = llIllllIlllll.iterator();

      boolean var10001;
      while(llIllllIllIlI.hasNext()) {
         byte llIllllIllIIl = llIllllIllIlI.next();
         Exception llIllllIllIII = (Module)llIllllIllIIl;
         Exception llIllllIlIlll = false;
         if (llIllllIllIII.getCategory() != ModuleCategory.RENDER && !(llIllllIllIII instanceof NameProtect) && !(llIllllIllIII instanceof Spammer)) {
            llIllllllIllI.add(llIllllIllIIl);
            var10001 = false;
         }
      }

      llIllllIlllll = (Iterable)((List)llIllllllIllI);
      llIlllllIlIIl = false;
      Iterator llIllllIlllIl = llIllllIlllll.iterator();

      while(llIllllIlllIl.hasNext()) {
         Object llIlllllIlIll = llIllllIlllIl.next();
         String llIllllIllIll = (Module)llIlllllIlIll;
         char llIllllIllIlI = false;
         if (llIlllllIIIll) {
            Iterable llIlllllIllll = (Iterable)llIllllIllIll.getValues();
            int llIlllllIlllI = false;

            for(Iterator llIllllIlIlll = llIlllllIllll.iterator(); llIllllIlIlll.hasNext(); var10001 = false) {
               Object llIllllllIIII = llIllllIlIlll.next();
               short llIllllIlIlIl = (Value)llIllllllIIII;
               int llIllllllIIIl = false;
               llIlllllIIIII.append(llIllllIllIll.getName()).append(" ").append(llIllllIlIlIl.getName()).append(" ").append(llIllllIlIlIl.get()).append("\n");
            }
         }

         if (llIlllllIIlII) {
            llIlllllIIIII.append(llIllllIllIll.getName()).append(" toggle ").append(llIllllIllIll.getState()).append("\n");
            var10001 = false;
         }

         if (llIlllllIIlIl) {
            llIlllllIIIII.append(llIllllIllIll.getName()).append(" bind ").append(Keyboard.getKeyName(llIllllIllIll.getKeyBind())).append("\n");
            var10001 = false;
         }
      }

      String var10000 = String.valueOf(llIlllllIIIII);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "stringBuilder.toString()");
      return var10000;
   }

   private SettingsUtils() {
   }

   public final void executeScript(@NotNull String lllIIIIlIIIlI) {
      Intrinsics.checkParameterIsNotNull(lllIIIIlIIIlI, "script");
      Iterable lllIIIIlIIIIl = (Iterable)StringsKt.lines((CharSequence)lllIIIIlIIIlI);
      int lllIIIIlIIlIl = false;
      int lllIIIIIllllI = (Collection)(new ArrayList());
      int lllIIIIlllIll = false;
      Iterator lllIIIIIlllII = lllIIIIlIIIIl.iterator();

      boolean lllIIIIlIlIIl;
      while(lllIIIIIlllII.hasNext()) {
         double lllIIIIIllIll = lllIIIIIlllII.next();
         String lllIIIlIIIIII = (String)lllIIIIIllIll;
         int lllIIIIllllll = false;
         boolean lllIIIIIllIII = (CharSequence)lllIIIlIIIIII;
         lllIIIIlIlIIl = false;
         if (lllIIIIIllIII.length() > 0 && !StringsKt.startsWith$default((CharSequence)lllIIIlIIIIII, '#', false, 2, (Object)null)) {
            lllIIIIIllllI.add(lllIIIIIllIll);
            boolean var10001 = false;
         }
      }

      lllIIIIlIIIIl = (Iterable)((List)lllIIIIIllllI);
      lllIIIIlIIlIl = false;
      int lllIIIIlIIlll = 0;
      Iterator lllIIIIIllllI = lllIIIIlIIIIl.iterator();

      while(lllIIIIIllllI.hasNext()) {
         Object lllIIIIlIlIII = lllIIIIIllllI.next();
         Exception lllIIIIIlllII = lllIIIIlIIlll++;
         double lllIIIIIllIll = false;
         if (lllIIIIIlllII < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         String lllIIIIlIlIlI = (String)lllIIIIlIlIII;
         lllIIIIlIlIIl = false;
         boolean lllIIIIIlIllI = (Collection)StringsKt.split$default((CharSequence)lllIIIIlIlIlI, new String[]{" "}, false, 0, 6, (Object)null);
         int lllIIIIllIllI = false;
         Object[] var35 = lllIIIIIlIllI.toArray(new String[0]);
         if (var35 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         }

         Exception lllIIIIIlIIll = (String[])var35;
         if (lllIIIIIlIIll.length <= 1) {
            ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §cSyntax error at line '").append(lllIIIIIlllII).append("' in setting script.\n§8§lLine: §7").append(lllIIIIlIlIlI)));
         } else {
            boolean lllIIIIIlIllI = lllIIIIIlIIll[0];
            String lllIIIIIlIlII;
            String lllIIIIlIllIl;
            switch(lllIIIIIlIllI.hashCode()) {
            case -634337326:
               if (lllIIIIIlIllI.equals("targetPlayer")) {
                  EntityUtils.targetPlayer = StringsKt.equals(lllIIIIIlIIll[1], "true", true);
                  ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §a§l").append(lllIIIIIlIIll[0]).append("§7 set to §c§l").append(EntityUtils.targetPlayer).append("§7.")));
                  continue;
               }
               break;
            case 3052376:
               if (lllIIIIIlIllI.equals("chat")) {
                  StringBuilder var39 = (new StringBuilder()).append("§7[§3§lAutoSettings§7] §e");
                  String var37 = StringUtils.toCompleteString(lllIIIIIlIIll, 1);
                  Intrinsics.checkExpressionValueIsNotNull(var37, "StringUtils.toCompleteString(args, 1)");
                  ClientUtils.displayChatMessage(String.valueOf(var39.append(ColorUtils.translateAlternateColorCodes(var37))));
                  continue;
               }
               break;
            case 3327206:
               if (lllIIIIIlIllI.equals("load")) {
                  lllIIIIlIllIl = StringUtils.toCompleteString(lllIIIIIlIIll, 1);
                  Intrinsics.checkExpressionValueIsNotNull(lllIIIIlIllIl, "urlRaw");
                  String var36;
                  if (StringsKt.startsWith$default(lllIIIIlIllIl, "http", false, 2, (Object)null)) {
                     var36 = lllIIIIlIllIl;
                  } else {
                     Exception lllIIIIIlIIIl = (new StringBuilder()).append("https://cloud.liquidbounce.net/LiquidBounce/settings/");
                     double lllIIIIIlIIII = false;
                     var36 = lllIIIIlIllIl.toLowerCase();
                     Intrinsics.checkExpressionValueIsNotNull(var36, "(this as java.lang.String).toLowerCase()");
                     long lllIIIIIIllll = var36;
                     var36 = String.valueOf(lllIIIIIlIIIl.append(lllIIIIIIllll));
                  }

                  lllIIIIIlIlII = var36;

                  try {
                     ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §7Loading settings from §a§l").append(lllIIIIIlIlII).append("§7...")));
                     SettingsUtils var38 = INSTANCE;
                     Intrinsics.checkExpressionValueIsNotNull(lllIIIIIlIlII, "url");
                     var38.executeScript(HttpUtils.get(lllIIIIIlIlII));
                     ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §7Loaded settings from §a§l").append(lllIIIIIlIlII).append("§7.")));
                  } catch (Exception var27) {
                     ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §7Failed to load settings from §a§l").append(lllIIIIIlIlII).append("§7.")));
                  }
                  continue;
               }
               break;
            case 283156764:
               if (lllIIIIIlIllI.equals("targetInvisible")) {
                  EntityUtils.targetInvisible = StringsKt.equals(lllIIIIIlIIll[1], "true", true);
                  ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §a§l").append(lllIIIIIlIIll[0]).append("§7 set to §c§l").append(EntityUtils.targetInvisible).append("§7.")));
                  continue;
               }
               break;
            case 486125973:
               if (lllIIIIIlIllI.equals("targetDead")) {
                  EntityUtils.targetDead = StringsKt.equals(lllIIIIIlIIll[1], "true", true);
                  ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §a§l").append(lllIIIIIlIIll[0]).append("§7 set to §c§l").append(EntityUtils.targetDead).append("§7.")));
                  continue;
               }
               break;
            case 486403748:
               if (lllIIIIIlIllI.equals("targetMobs")) {
                  EntityUtils.targetMobs = StringsKt.equals(lllIIIIIlIIll[1], "true", true);
                  ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §a§l").append(lllIIIIIlIIll[0]).append("§7 set to §c§l").append(EntityUtils.targetMobs).append("§7.")));
                  continue;
               }
               break;
            case 1447011110:
               if (lllIIIIIlIllI.equals("targetAnimals")) {
                  EntityUtils.targetAnimals = StringsKt.equals(lllIIIIIlIIll[1], "true", true);
                  ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §a§l").append(lllIIIIIlIIll[0]).append("§7 set to §c§l").append(EntityUtils.targetAnimals).append("§7.")));
                  continue;
               }
            }

            if (lllIIIIIlIIll.length != 3) {
               ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §cSyntax error at line '").append(lllIIIIIlllII).append("' in setting script.\n§8§lLine: §7").append(lllIIIIlIlIlI)));
            } else {
               lllIIIIlIllIl = lllIIIIIlIIll[0];
               lllIIIIIlIlII = lllIIIIIlIIll[1];
               int lllIIIIIlIIlI = lllIIIIIlIIll[2];
               Module lllIIIIllIIII = LiquidBounce.INSTANCE.getModuleManager().getModule(lllIIIIlIllIl);
               if (lllIIIIllIIII == null) {
                  ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §cModule §a§l").append(lllIIIIlIllIl).append("§c was not found!")));
               } else if (StringsKt.equals(lllIIIIIlIlII, "toggle", true)) {
                  lllIIIIllIIII.setState(StringsKt.equals(lllIIIIIlIIlI, "true", true));
                  ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §a§l").append(lllIIIIllIIII.getName()).append(" §7was toggled §c§l").append(lllIIIIllIIII.getState() ? "on" : "off").append("§7.")));
               } else if (StringsKt.equals(lllIIIIIlIlII, "bind", true)) {
                  lllIIIIllIIII.setKeyBind(Keyboard.getKeyIndex(lllIIIIIlIIlI));
                  ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §a§l").append(lllIIIIllIIII.getName()).append(" §7was bound to §c§l").append(Keyboard.getKeyName(lllIIIIllIIII.getKeyBind())).append("§7.")));
               } else {
                  Value lllIIIIllIIIl = lllIIIIllIIII.getValue(lllIIIIIlIlII);
                  if (lllIIIIllIIIl == null) {
                     ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §cValue §a§l").append(lllIIIIIlIlII).append("§c don't found in module §a§l").append(lllIIIIlIllIl).append("§c.")));
                  } else {
                     try {
                        boolean lllIIIIIIlIll;
                        if (lllIIIIllIIIl instanceof BoolValue) {
                           Exception lllIIIIIlIIIl = (BoolValue)lllIIIIllIIIl;
                           lllIIIIIIlIll = false;
                           long lllIIIIIIllll = Boolean.parseBoolean(lllIIIIIlIIlI);
                           lllIIIIIlIIIl.changeValue(lllIIIIIIllll);
                        } else if (lllIIIIllIIIl instanceof FloatValue) {
                           Exception lllIIIIIlIIIl = (FloatValue)lllIIIIllIIIl;
                           lllIIIIIIlIll = false;
                           long lllIIIIIIllll = Float.parseFloat(lllIIIIIlIIlI);
                           lllIIIIIlIIIl.changeValue(lllIIIIIIllll);
                        } else if (lllIIIIllIIIl instanceof IntegerValue) {
                           Exception lllIIIIIlIIIl = (IntegerValue)lllIIIIllIIIl;
                           lllIIIIIIlIll = false;
                           long lllIIIIIIllll = Integer.parseInt(lllIIIIIlIIlI);
                           lllIIIIIlIIIl.changeValue(lllIIIIIIllll);
                        } else if (lllIIIIllIIIl instanceof TextValue) {
                           ((TextValue)lllIIIIllIIIl).changeValue(lllIIIIIlIIlI);
                        } else if (lllIIIIllIIIl instanceof ListValue) {
                           ((ListValue)lllIIIIllIIIl).changeValue(lllIIIIIlIIlI);
                        }

                        ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §a§l").append(lllIIIIllIIII.getName()).append("§7 value §8§l").append(lllIIIIllIIIl.getName()).append("§7 set to §c§l").append(lllIIIIIlIIlI).append("§7.")));
                     } catch (Exception var26) {
                        ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§3§lAutoSettings§7] §a§l").append(var26.getClass().getName()).append("§7(").append(var26.getMessage()).append(") §cAn Exception occurred while setting §a§l").append(lllIIIIIlIIlI).append("§c to §a§l").append(lllIIIIllIIIl.getName()).append("§c in §a§l").append(lllIIIIllIIII.getName()).append("§c.")));
                     }
                  }
               }
            }
         }
      }

      LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
   }

   static {
      SettingsUtils llIllllIIllll = new SettingsUtils();
      INSTANCE = llIllllIIllll;
   }
}
