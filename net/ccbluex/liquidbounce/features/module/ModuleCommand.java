//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.block.Block;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\b\u0002\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005¢\u0006\u0002\u0010\u0007J\u001b\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016¢\u0006\u0002\u0010\u0011J!\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u00052\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016¢\u0006\u0002\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001b\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/ModuleCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "module", "Lnet/ccbluex/liquidbounce/features/module/Module;", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "(Lnet/ccbluex/liquidbounce/features/module/Module;Ljava/util/List;)V", "getModule", "()Lnet/ccbluex/liquidbounce/features/module/Module;", "getValues", "()Ljava/util/List;", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "([Ljava/lang/String;)Ljava/util/List;", "LiquidSense"}
)
public final class ModuleCommand extends Command {
   // $FF: synthetic field
   @NotNull
   private final List<Value<?>> values;
   // $FF: synthetic field
   @NotNull
   private final Module module;

   @NotNull
   public final Module getModule() {
      return lIIIIlIIlllIll.module;
   }

   public ModuleCommand(@NotNull Module lIIIIlIIlIlllI, @NotNull List<? extends Value<?>> lIIIIlIIlIllIl) {
      Intrinsics.checkParameterIsNotNull(lIIIIlIIlIlllI, "module");
      Intrinsics.checkParameterIsNotNull(lIIIIlIIlIllIl, "values");
      Exception lIIIIlIIlIlIIl = lIIIIlIIlIlllI.getName();
      Exception lIIIIlIIlIlIII = false;
      if (lIIIIlIIlIlIIl == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         String var10000 = lIIIIlIIlIlIIl.toLowerCase();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
         float lIIIIlIIlIIllI = var10000;
         double lIIIIlIIlIIlIl = new String[0];
         super(lIIIIlIIlIIllI, lIIIIlIIlIIlIl);
         lIIIIlIIlIllll.module = lIIIIlIIlIlllI;
         lIIIIlIIlIllll.values = lIIIIlIIlIllIl;
         if (lIIIIlIIlIllll.values.isEmpty()) {
            throw (Throwable)(new IllegalArgumentException("Values are empty!"));
         }
      }
   }

   // $FF: synthetic method
   public ModuleCommand(Module lIIIIlIIIlllll, List var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = lIIIIlIIIlllll.getValues();
      }

      this(lIIIIlIIIlllll, var2);
   }

   @NotNull
   public List<String> tabComplete(@NotNull String[] lIIIIlIlIIlllI) {
      Intrinsics.checkParameterIsNotNull(lIIIIlIlIIlllI, "args");
      float lIIIIlIlIIlIlI = false;
      if (lIIIIlIlIIlllI.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         String lIIIIlIIlllllI;
         List var10000;
         boolean var10001;
         String var28;
         switch(lIIIIlIlIIlllI.length) {
         case 1:
            Iterable lIIIIlIllIIIIl = (Iterable)lIIIIlIlIIllIl.values;
            lIIIIlIlIIlIlI = false;
            Collection lIIIIlIlIIlIII = (Collection)(new ArrayList());
            boolean lIIIIlIlIIIlll = false;
            Iterator lIIIIlIlIIIllI = lIIIIlIllIIIIl.iterator();

            Object lIIIIlIlIIIlIl;
            Value lIIIIlIllIIlll;
            boolean lIIIIlIlIIIIll;
            while(lIIIIlIlIIIllI.hasNext()) {
               lIIIIlIlIIIlIl = lIIIIlIlIIIllI.next();
               lIIIIlIllIIlll = (Value)lIIIIlIlIIIlIl;
               lIIIIlIlIIIIll = false;
               if (!(lIIIIlIllIIlll instanceof FontValue) && StringsKt.startsWith(lIIIIlIllIIlll.getName(), lIIIIlIlIIlllI[0], true)) {
                  lIIIIlIlIIlIII.add(lIIIIlIlIIIlIl);
                  var10001 = false;
               }
            }

            lIIIIlIllIIIIl = (Iterable)((List)lIIIIlIlIIlIII);
            lIIIIlIlIIlIlI = false;
            lIIIIlIlIIlIII = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(lIIIIlIllIIIIl, 10)));
            lIIIIlIlIIIlll = false;

            for(lIIIIlIlIIIllI = lIIIIlIllIIIIl.iterator(); lIIIIlIlIIIllI.hasNext(); var10001 = false) {
               lIIIIlIlIIIlIl = lIIIIlIlIIIllI.next();
               lIIIIlIllIIlll = (Value)lIIIIlIlIIIlIl;
               lIIIIlIlIIIIll = false;
               float lIIIIlIlIIIIlI = lIIIIlIllIIlll.getName();
               boolean lIIIIlIlIIIIIl = false;
               if (lIIIIlIlIIIIlI == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var28 = lIIIIlIlIIIIlI.toLowerCase();
               Intrinsics.checkExpressionValueIsNotNull(var28, "(this as java.lang.String).toLowerCase()");
               lIIIIlIIlllllI = var28;
               lIIIIlIlIIlIII.add(lIIIIlIIlllllI);
            }

            var10000 = (List)lIIIIlIlIIlIII;
            break;
         case 2:
            Exception lIIIIlIlIIlIll = lIIIIlIlIIllIl.module.getValue(lIIIIlIlIIlllI[0]);
            if (lIIIIlIlIIlIll instanceof BlockValue) {
               RegistryNamespacedDefaultedByKey var26 = Block.blockRegistry;
               Intrinsics.checkExpressionValueIsNotNull(var26, "Block.blockRegistry");
               Set var27 = var26.getKeys();
               Intrinsics.checkExpressionValueIsNotNull(var27, "Block.blockRegistry.keys");
               Iterable lIIIIlIlIIlIlI = (Iterable)var27;
               int lIIIIlIlIlIIII = false;
               boolean lIIIIlIlIIIlll = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(lIIIIlIlIIlIlI, 10)));
               char lIIIIlIlIlIIlI = false;

               Iterator lIIIIlIlIIIlIl;
               Object lIIIIlIlIlIlIl;
               boolean lIIIIlIlIIIIlI;
               for(lIIIIlIlIIIlIl = lIIIIlIlIIlIlI.iterator(); lIIIIlIlIIIlIl.hasNext(); var10001 = false) {
                  lIIIIlIlIlIlIl = lIIIIlIlIIIlIl.next();
                  int lIIIIlIlIIIIll = (ResourceLocation)lIIIIlIlIlIlIl;
                  lIIIIlIlIIIIlI = false;
                  Intrinsics.checkExpressionValueIsNotNull(lIIIIlIlIIIIll, "it");
                  var28 = lIIIIlIlIIIIll.getResourcePath();
                  Intrinsics.checkExpressionValueIsNotNull(var28, "it.resourcePath");
                  boolean lIIIIlIlIIIIIl = var28;
                  byte lIIIIlIlIIIIII = false;
                  if (lIIIIlIlIIIIIl == null) {
                     throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                  }

                  var28 = lIIIIlIlIIIIIl.toLowerCase();
                  Intrinsics.checkExpressionValueIsNotNull(var28, "(this as java.lang.String).toLowerCase()");
                  lIIIIlIIlllllI = var28;
                  lIIIIlIlIIIlll.add(lIIIIlIIlllllI);
               }

               lIIIIlIlIIlIlI = (Iterable)((List)lIIIIlIlIIIlll);
               lIIIIlIlIlIIII = false;
               lIIIIlIlIIIlll = (Collection)(new ArrayList());
               lIIIIlIlIlIIlI = false;
               lIIIIlIlIIIlIl = lIIIIlIlIIlIlI.iterator();

               while(lIIIIlIlIIIlIl.hasNext()) {
                  lIIIIlIlIlIlIl = lIIIIlIlIIIlIl.next();
                  int lIIIIlIlIIIIll = (String)lIIIIlIlIlIlIl;
                  lIIIIlIlIIIIlI = false;
                  if (StringsKt.startsWith(lIIIIlIlIIIIll, lIIIIlIlIIlllI[1], true)) {
                     lIIIIlIlIIIlll.add(lIIIIlIlIlIlIl);
                     var10001 = false;
                  }
               }

               return (List)lIIIIlIlIIIlll;
            }

            var10000 = CollectionsKt.emptyList();
            break;
         default:
            var10000 = CollectionsKt.emptyList();
         }

         return var10000;
      }
   }

   @NotNull
   public final List<Value<?>> getValues() {
      return lIIIIlIIlllIIl.values;
   }

   public void execute(@NotNull String[] lIIIIllIIlIllI) {
      Intrinsics.checkParameterIsNotNull(lIIIIllIIlIllI, "args");
      byte lIIIIllIIlIIll = (Iterable)lIIIIllIIlIlll.values;
      double lIIIIllIIlIIlI = false;
      byte lIIIIllIIIllll = (Collection)(new ArrayList());
      short lIIIIllIIIlllI = false;
      Iterator lIIIIllIIIllIl = lIIIIllIIlIIll.iterator();

      while(lIIIIllIIIllIl.hasNext()) {
         Object lIIIIllIlIlllI = lIIIIllIIIllIl.next();
         Value lIIIIllIllIIIl = (Value)lIIIIllIlIlllI;
         int lIIIIllIlIllll = false;
         if (!(lIIIIllIllIIIl instanceof FontValue)) {
            lIIIIllIIIllll.add(lIIIIllIlIlllI);
            boolean var10001 = false;
         }
      }

      Exception lIIIIllIIlIlIl = CollectionsKt.joinToString$default((Iterable)((List)lIIIIllIIIllll), (CharSequence)"/", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null.INSTANCE, 30, (Object)null);
      double lIIIIllIIlIIlI = lIIIIllIIlIlll.module.getName();
      byte lIIIIllIlIIlII = false;
      if (lIIIIllIIlIIlI == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         String var10000 = lIIIIllIIlIIlI.toLowerCase();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
         String lIIIIllIIlllIl = var10000;
         if (lIIIIllIIlIllI.length < 2) {
            lIIIIllIIlIlll.chatSyntax(lIIIIllIIlIlll.values.size() == 1 ? String.valueOf((new StringBuilder()).append(lIIIIllIIlllIl).append(' ').append(lIIIIllIIlIlIl).append(" <value>")) : String.valueOf((new StringBuilder()).append(lIIIIllIIlllIl).append(" <").append(lIIIIllIIlIlIl).append('>')));
         } else {
            double lIIIIllIIlIIlI = lIIIIllIIlIlll.module.getValue(lIIIIllIIlIllI[1]);
            if (lIIIIllIIlIIlI == null) {
               lIIIIllIIlIlll.chatSyntax(String.valueOf((new StringBuilder()).append(lIIIIllIIlllIl).append(" <").append(lIIIIllIIlIlIl).append('>')));
            } else {
               if (lIIIIllIIlIIlI instanceof BoolValue) {
                  lIIIIllIlIIlII = !(Boolean)((BoolValue)lIIIIllIIlIIlI).get();
                  ((BoolValue)lIIIIllIIlIIlI).set(lIIIIllIlIIlII);
                  lIIIIllIIlIlll.chat(String.valueOf((new StringBuilder()).append("§7").append(lIIIIllIIlIlll.module.getName()).append(" §8").append(lIIIIllIIlIllI[1]).append("§7 was toggled ").append(lIIIIllIlIIlII ? "§8on§7" : "§8off§7.")));
                  lIIIIllIIlIlll.playEdit();
               } else {
                  String lIIIIllIIIIllI;
                  boolean lIIIIllIIIllll;
                  StringBuilder lIIIIllIIIIlll;
                  StringBuilder var31;
                  if (lIIIIllIIlIllI.length < 3) {
                     String lIIIIllIIlIIII;
                     if (!(lIIIIllIIlIIlI instanceof IntegerValue) && !(lIIIIllIIlIIlI instanceof FloatValue) && !(lIIIIllIIlIIlI instanceof TextValue)) {
                        if (lIIIIllIIlIIlI instanceof ListValue) {
                           var31 = (new StringBuilder()).append(lIIIIllIIlllIl).append(' ');
                           lIIIIllIIlIIII = lIIIIllIIlIllI[1];
                           lIIIIllIIIIlll = var31;
                           lIIIIllIIIllll = false;
                           if (lIIIIllIIlIIII == null) {
                              throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                           }

                           var10000 = lIIIIllIIlIIII.toLowerCase();
                           Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
                           lIIIIllIIIIllI = var10000;
                           var31 = lIIIIllIIIIlll.append(lIIIIllIIIIllI).append(" <");
                           lIIIIllIIlIIII = ArraysKt.joinToString$default(((ListValue)lIIIIllIIlIIlI).getValues(), (CharSequence)"/", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null);
                           lIIIIllIIIIlll = var31;
                           lIIIIllIIIllll = false;
                           if (lIIIIllIIlIIII == null) {
                              throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                           }

                           var10000 = lIIIIllIIlIIII.toLowerCase();
                           Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
                           lIIIIllIIIIllI = var10000;
                           lIIIIllIIlIlll.chatSyntax(String.valueOf(lIIIIllIIIIlll.append(lIIIIllIIIIllI).append('>')));
                        }
                     } else {
                        var31 = (new StringBuilder()).append(lIIIIllIIlllIl).append(' ');
                        lIIIIllIIlIIII = lIIIIllIIlIllI[1];
                        lIIIIllIIIIlll = var31;
                        lIIIIllIIIllll = false;
                        if (lIIIIllIIlIIII == null) {
                           throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }

                        var10000 = lIIIIllIIlIIII.toLowerCase();
                        Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
                        lIIIIllIIIIllI = var10000;
                        lIIIIllIIlIlll.chatSyntax(String.valueOf(lIIIIllIIIIlll.append(lIIIIllIIIIllI).append(" <value>")));
                     }

                     return;
                  }

                  try {
                     if (lIIIIllIIlIIlI instanceof BlockValue) {
                        lIIIIllIIIllll = false;

                        int lIIIIllIIIllll;
                        boolean lIIIIllIIIllIl;
                        String lIIIIllIIIlllI;
                        try {
                           lIIIIllIIIlllI = lIIIIllIIlIllI[2];
                           lIIIIllIIIllIl = false;
                           lIIIIllIIIllll = Integer.parseInt(lIIIIllIIIlllI);
                        } catch (NumberFormatException var15) {
                           lIIIIllIIIllll = Block.getIdFromBlock(Block.getBlockFromName(lIIIIllIIlIllI[2]));
                           if (lIIIIllIIIllll <= 0) {
                              lIIIIllIIlIlll.chat(String.valueOf((new StringBuilder()).append("§7Block §8").append(lIIIIllIIlIllI[2]).append("§7 does not exist!")));
                              return;
                           }
                        }

                        ((BlockValue)lIIIIllIIlIIlI).set(lIIIIllIIIllll);
                        var31 = (new StringBuilder()).append("§7").append(lIIIIllIIlIlll.module.getName()).append(" §8");
                        lIIIIllIIIlllI = lIIIIllIIlIllI[1];
                        lIIIIllIIIIlll = var31;
                        lIIIIllIIIllIl = false;
                        if (lIIIIllIIIlllI == null) {
                           throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }

                        var10000 = lIIIIllIIIlllI.toLowerCase();
                        Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
                        lIIIIllIIIIllI = var10000;
                        lIIIIllIIlIlll.chat(String.valueOf(lIIIIllIIIIlll.append(lIIIIllIIIIllI).append("§7 was set to §8").append(BlockUtils.getBlockName(lIIIIllIIIllll)).append("§7.")));
                        lIIIIllIIlIlll.playEdit();
                        return;
                     }

                     String lIIIIllIIIllll;
                     if (lIIIIllIIlIIlI instanceof IntegerValue) {
                        IntegerValue var29 = (IntegerValue)lIIIIllIIlIIlI;
                        lIIIIllIIIllll = lIIIIllIIlIllI[2];
                        long lIIIIllIIIlIII = var29;
                        lIIIIllIIIlllI = false;
                        short lIIIIllIIIIlll = Integer.parseInt(lIIIIllIIIllll);
                        lIIIIllIIIlIII.set(lIIIIllIIIIlll);
                     } else if (lIIIIllIIlIIlI instanceof FloatValue) {
                        FloatValue var30 = (FloatValue)lIIIIllIIlIIlI;
                        lIIIIllIIIllll = lIIIIllIIlIllI[2];
                        long lIIIIllIIIlIII = var30;
                        lIIIIllIIIlllI = false;
                        short lIIIIllIIIIlll = Float.parseFloat(lIIIIllIIIllll);
                        lIIIIllIIIlIII.set(lIIIIllIIIIlll);
                     } else if (lIIIIllIIlIIlI instanceof ListValue) {
                        if (!((ListValue)lIIIIllIIlIIlI).contains(lIIIIllIIlIllI[2])) {
                           var31 = (new StringBuilder()).append(lIIIIllIIlllIl).append(' ');
                           lIIIIllIIIllll = lIIIIllIIlIllI[1];
                           lIIIIllIIIIlll = var31;
                           lIIIIllIIIlllI = false;
                           if (lIIIIllIIIllll == null) {
                              throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                           }

                           var10000 = lIIIIllIIIllll.toLowerCase();
                           Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
                           lIIIIllIIIIllI = var10000;
                           var31 = lIIIIllIIIIlll.append(lIIIIllIIIIllI).append(" <");
                           lIIIIllIIIllll = ArraysKt.joinToString$default(((ListValue)lIIIIllIIlIIlI).getValues(), (CharSequence)"/", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null);
                           lIIIIllIIIIlll = var31;
                           lIIIIllIIIlllI = false;
                           if (lIIIIllIIIllll == null) {
                              throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                           }

                           var10000 = lIIIIllIIIllll.toLowerCase();
                           Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
                           lIIIIllIIIIllI = var10000;
                           lIIIIllIIlIlll.chatSyntax(String.valueOf(lIIIIllIIIIlll.append(lIIIIllIIIIllI).append('>')));
                           return;
                        }

                        ((ListValue)lIIIIllIIlIIlI).set(lIIIIllIIlIllI[2]);
                     } else if (lIIIIllIIlIIlI instanceof TextValue) {
                        TextValue var32 = (TextValue)lIIIIllIIlIIlI;
                        String var33 = StringUtils.toCompleteString(lIIIIllIIlIllI, 2);
                        Intrinsics.checkExpressionValueIsNotNull(var33, "StringUtils.toCompleteString(args, 2)");
                        var32.set(var33);
                     }

                     lIIIIllIIlIlll.chat(String.valueOf((new StringBuilder()).append("§7").append(lIIIIllIIlIlll.module.getName()).append(" §8").append(lIIIIllIIlIllI[1]).append("§7 was set to §8").append(lIIIIllIIlIIlI.get()).append("§7.")));
                     lIIIIllIIlIlll.playEdit();
                  } catch (NumberFormatException var16) {
                     lIIIIllIIlIlll.chat(String.valueOf((new StringBuilder()).append("§8").append(lIIIIllIIlIllI[2]).append("§7 cannot be converted to number!")));
                  }
               }

            }
         }
      }
   }
}
