package net.ccbluex.liquidbounce.features.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.modules.combat.Aura;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoArmor;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoBow;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoPot;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoSoup;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoWeapon;
import net.ccbluex.liquidbounce.features.module.modules.combat.BowAimbot;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.combat.FastBow;
import net.ccbluex.liquidbounce.features.module.modules.combat.HitBox;
import net.ccbluex.liquidbounce.features.module.modules.combat.NoFriends;
import net.ccbluex.liquidbounce.features.module.modules.combat.SuperKnockback;
import net.ccbluex.liquidbounce.features.module.modules.combat.Velocity;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AbortBreaking;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Clip;
import net.ccbluex.liquidbounce.features.module.modules.exploit.ConsoleSpammer;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Damage;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Ghost;
import net.ccbluex.liquidbounce.features.module.modules.exploit.GhostHand;
import net.ccbluex.liquidbounce.features.module.modules.exploit.KeepContainer;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Kick;
import net.ccbluex.liquidbounce.features.module.modules.exploit.PingSpoof;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Plugins;
import net.ccbluex.liquidbounce.features.module.modules.exploit.ServerCrasher;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Teleport;
import net.ccbluex.liquidbounce.features.module.modules.fun.Derp;
import net.ccbluex.liquidbounce.features.module.modules.fun.SkinDerp;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.ComponentOnHover;
import net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat;
import net.ccbluex.liquidbounce.features.module.modules.misc.MidClick;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.ccbluex.liquidbounce.features.module.modules.misc.NoRotateSet;
import net.ccbluex.liquidbounce.features.module.modules.misc.Spammer;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.features.module.modules.movement.AutoWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.BlockWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.BufferSpeed;
import net.ccbluex.liquidbounce.features.module.modules.movement.BugUp;
import net.ccbluex.liquidbounce.features.module.modules.movement.FastClimb;
import net.ccbluex.liquidbounce.features.module.modules.movement.FastStairs;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.features.module.modules.movement.Freeze;
import net.ccbluex.liquidbounce.features.module.modules.movement.HighJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.IceSpeed;
import net.ccbluex.liquidbounce.features.module.modules.movement.InventoryMove;
import net.ccbluex.liquidbounce.features.module.modules.movement.LiquidWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.LongJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoClip;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoJumpDelay;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoWeb;
import net.ccbluex.liquidbounce.features.module.modules.movement.PerfectHorseJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.SafeWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sprint;
import net.ccbluex.liquidbounce.features.module.modules.movement.Step;
import net.ccbluex.liquidbounce.features.module.modules.movement.Strafe;
import net.ccbluex.liquidbounce.features.module.modules.player.AntiAFK;
import net.ccbluex.liquidbounce.features.module.modules.player.AntiCactus;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoFish;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoRespawn;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoTool;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.features.module.modules.player.Eagle;
import net.ccbluex.liquidbounce.features.module.modules.player.FastUse;
import net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner;
import net.ccbluex.liquidbounce.features.module.modules.player.KeepAlive;
import net.ccbluex.liquidbounce.features.module.modules.player.NoFall;
import net.ccbluex.liquidbounce.features.module.modules.player.PotionSaver;
import net.ccbluex.liquidbounce.features.module.modules.player.Reach;
import net.ccbluex.liquidbounce.features.module.modules.player.Regen;
import net.ccbluex.liquidbounce.features.module.modules.player.Zoot;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.ccbluex.liquidbounce.features.module.modules.render.BlockESP;
import net.ccbluex.liquidbounce.features.module.modules.render.BlockOverlay;
import net.ccbluex.liquidbounce.features.module.modules.render.CameraClip;
import net.ccbluex.liquidbounce.features.module.modules.render.Chams;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.features.module.modules.render.ESP;
import net.ccbluex.liquidbounce.features.module.modules.render.FreeCam;
import net.ccbluex.liquidbounce.features.module.modules.render.Fullbright;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.features.module.modules.render.ItemESP;
import net.ccbluex.liquidbounce.features.module.modules.render.NameTags;
import net.ccbluex.liquidbounce.features.module.modules.render.NoBob;
import net.ccbluex.liquidbounce.features.module.modules.render.NoFOV;
import net.ccbluex.liquidbounce.features.module.modules.render.NoHurtCam;
import net.ccbluex.liquidbounce.features.module.modules.render.NoScoreboard;
import net.ccbluex.liquidbounce.features.module.modules.render.NoSwing;
import net.ccbluex.liquidbounce.features.module.modules.render.Projectiles;
import net.ccbluex.liquidbounce.features.module.modules.render.ProphuntESP;
import net.ccbluex.liquidbounce.features.module.modules.render.Rotations;
import net.ccbluex.liquidbounce.features.module.modules.render.StorageESP;
import net.ccbluex.liquidbounce.features.module.modules.render.SwingAnimation;
import net.ccbluex.liquidbounce.features.module.modules.render.TNTESP;
import net.ccbluex.liquidbounce.features.module.modules.render.Tracers;
import net.ccbluex.liquidbounce.features.module.modules.render.TrueSight;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestAura;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer;
import net.ccbluex.liquidbounce.features.module.modules.world.FastBreak;
import net.ccbluex.liquidbounce.features.module.modules.world.FastPlace;
import net.ccbluex.liquidbounce.features.module.modules.world.Fucker;
import net.ccbluex.liquidbounce.features.module.modules.world.Liquids;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.features.module.modules.world.Timer;
import net.ccbluex.liquidbounce.features.module.modules.world.Tower;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\u0002J\u0017\u0010\u000f\u001a\u0004\u0018\u00010\u00062\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0086\u0002J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00062\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0005J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00062\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0019H\u0003J\u0018\u0010\u001a\u001a\u00020\r2\u000e\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005H\u0002J\u0012\u0010\u001a\u001a\u00020\r2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0002J\u000e\u0010\u001a\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006J\u0006\u0010\u001d\u001a\u00020\rJ1\u0010\u001d\u001a\u00020\r2\"\u0010\b\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00050\u001e\"\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005H\u0007¢\u0006\u0002\u0010\u001fJ\u000e\u0010 \u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006R2\u0010\u0003\u001a&\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006!"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "moduleClassMap", "Ljava/util/HashMap;", "Ljava/lang/Class;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "Lkotlin/collections/HashMap;", "modules", "Ljava/util/TreeSet;", "getModules", "()Ljava/util/TreeSet;", "generateCommand", "", "module", "get", "clazz", "getModule", "moduleClass", "moduleName", "", "handleEvents", "", "onKey", "event", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "registerModule", "cbModule", "", "registerModules", "", "([Ljava/lang/Class;)V", "unregisterModule", "LiquidSense"}
)
public final class ModuleManager implements Listenable {
   // $FF: synthetic field
   private final HashMap<Class<?>, Module> moduleClassMap;
   // $FF: synthetic field
   @NotNull
   private final TreeSet<Module> modules;

   public final void unregisterModule(@NotNull Module llllllllllllllllllllIlIIlllIIlll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIlIIlllIIlll, "module");
      llllllllllllllllllllIlIIlllIlIII.modules.remove(llllllllllllllllllllIlIIlllIIlll);
      boolean var10001 = false;
      llllllllllllllllllllIlIIlllIlIII.moduleClassMap.remove(llllllllllllllllllllIlIIlllIIlll.getClass());
      var10001 = false;
      LiquidBounce.INSTANCE.getEventManager().unregisterListener((Listenable)llllllllllllllllllllIlIIlllIIlll);
   }

   @Nullable
   public final Module getModule(@NotNull Class<?> llllllllllllllllllllIlIIllIllIlI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIlIIllIllIlI, "moduleClass");
      return (Module)llllllllllllllllllllIlIIllIllIll.moduleClassMap.get(llllllllllllllllllllIlIIllIllIlI);
   }

   @NotNull
   public final TreeSet<Module> getModules() {
      return llllllllllllllllllllIlIlIIIlllIl.modules;
   }

   @SafeVarargs
   public final void registerModules(@NotNull Class<? extends Module>... llllllllllllllllllllIlIIlllllIlI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIlIIlllllIlI, "modules");
      List llllllllllllllllllllIlIIllllllII = LiquidBounce.INSTANCE.getLiquidSense().getLiquidSenseModules();
      char llllllllllllllllllllIlIIllllIlII = llllllllllllllllllllIlIIlllllIlI;
      Exception llllllllllllllllllllIlIIllllIIll = llllllllllllllllllllIlIIlllllIlI.length;

      for(int llllllllllllllllllllIlIIllllIlIl = 0; llllllllllllllllllllIlIIllllIlIl < llllllllllllllllllllIlIIllllIIll; ++llllllllllllllllllllIlIIllllIlIl) {
         Class llllllllllllllllllllIlIIlllllllI = llllllllllllllllllllIlIIllllIlII[llllllllllllllllllllIlIIllllIlIl];
         llllllllllllllllllllIlIIlllllIll.registerModule(llllllllllllllllllllIlIIlllllllI);
      }

      Iterator llllllllllllllllllllIlIIllllIlIl = llllllllllllllllllllIlIIllllllII.iterator();

      while(llllllllllllllllllllIlIIllllIlIl.hasNext()) {
         Object llllllllllllllllllllIlIIllllllIl = llllllllllllllllllllIlIIllllIlIl.next();
         llllllllllllllllllllIlIIlllllIll.registerModule(llllllllllllllllllllIlIIllllllIl);
      }

   }

   public boolean handleEvents() {
      return true;
   }

   public ModuleManager() {
      llllllllllllllllllllIlIIlIIlIIIl.modules = new TreeSet((Comparator)null.INSTANCE);
      Exception llllllllllllllllllllIlIIlIIIllll = false;
      Exception llllllllllllllllllllIlIIlIIIllIl = new HashMap();
      llllllllllllllllllllIlIIlIIlIIIl.moduleClassMap = llllllllllllllllllllIlIIlIIIllIl;
      LiquidBounce.INSTANCE.getEventManager().registerListener((Listenable)llllllllllllllllllllIlIIlIIlIIIl);
   }

   private final void registerModule(Object llllllllllllllllllllIlIIlllIllll) {
      if (llllllllllllllllllllIlIIlllIllll == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<out net.ccbluex.liquidbounce.features.module.Module>");
      } else {
         Object var10001 = ((Class)llllllllllllllllllllIlIIlllIllll).newInstance();
         Intrinsics.checkExpressionValueIsNotNull(var10001, "(cbModule as Class<out Module>).newInstance()");
         llllllllllllllllllllIlIIllllIIII.registerModule((Module)var10001);
      }
   }

   @Nullable
   public final Module get(@NotNull Class<?> llllllllllllllllllllIlIIllIlIllI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIlIIllIlIllI, "clazz");
      return llllllllllllllllllllIlIIllIlIlIl.getModule(llllllllllllllllllllIlIIllIlIllI);
   }

   private final void generateCommand(Module llllllllllllllllllllIlIIlllIIIIl) {
      List llllllllllllllllllllIlIIlllIIlII = llllllllllllllllllllIlIIlllIIIIl.getValues();
      if (!llllllllllllllllllllIlIIlllIIlII.isEmpty()) {
         LiquidBounce.INSTANCE.getCommandManager().registerCommand((Command)(new ModuleCommand(llllllllllllllllllllIlIIlllIIIIl, llllllllllllllllllllIlIIlllIIlII)));
         boolean var10001 = false;
      }
   }

   @EventTarget
   private final void onKey(KeyEvent llllllllllllllllllllIlIIlIlIIIlI) {
      long llllllllllllllllllllIlIIlIIlllll = (Iterable)llllllllllllllllllllIlIIlIlIIIll.modules;
      int llllllllllllllllllllIlIIlIlIIlII = false;
      Collection llllllllllllllllllllIlIIlIlIllII = (Collection)(new ArrayList());
      Exception llllllllllllllllllllIlIIlIIllIll = false;
      Iterator llllllllllllllllllllIlIIlIIllIlI = llllllllllllllllllllIlIIlIIlllll.iterator();

      while(llllllllllllllllllllIlIIlIIllIlI.hasNext()) {
         Object llllllllllllllllllllIlIIlIlIlllI = llllllllllllllllllllIlIIlIIllIlI.next();
         Module llllllllllllllllllllIlIIlIllIIII = (Module)llllllllllllllllllllIlIIlIlIlllI;
         boolean llllllllllllllllllllIlIIlIIlIlll = false;
         if (llllllllllllllllllllIlIIlIllIIII.getKeyBind() == llllllllllllllllllllIlIIlIlIIIlI.getKey()) {
            llllllllllllllllllllIlIIlIlIllII.add(llllllllllllllllllllIlIIlIlIlllI);
            boolean var10001 = false;
         }
      }

      llllllllllllllllllllIlIIlIIlllll = (Iterable)((List)llllllllllllllllllllIlIIlIlIllII);
      llllllllllllllllllllIlIIlIlIIlII = false;
      Iterator llllllllllllllllllllIlIIlIIlllIl = llllllllllllllllllllIlIIlIIlllll.iterator();

      while(llllllllllllllllllllIlIIlIIlllIl.hasNext()) {
         Object llllllllllllllllllllIlIIlIlIIllI = llllllllllllllllllllIlIIlIIlllIl.next();
         Module llllllllllllllllllllIlIIlIlIlIII = (Module)llllllllllllllllllllIlIIlIlIIllI;
         int llllllllllllllllllllIlIIlIlIIlll = false;
         llllllllllllllllllllIlIIlIlIlIII.toggle();
      }

   }

   public final void registerModule(@NotNull Module llllllllllllllllllllIlIlIIIlIIll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIlIlIIIlIIll, "module");
      long llllllllllllllllllllIlIlIIIlIIII = (Collection)llllllllllllllllllllIlIlIIIlIIlI.modules;
      float llllllllllllllllllllIlIlIIIIllll = false;
      llllllllllllllllllllIlIlIIIlIIII.add(llllllllllllllllllllIlIlIIIlIIll);
      boolean var10001 = false;
      ((Map)llllllllllllllllllllIlIlIIIlIIlI.moduleClassMap).put(llllllllllllllllllllIlIlIIIlIIll.getClass(), llllllllllllllllllllIlIlIIIlIIll);
      var10001 = false;
      llllllllllllllllllllIlIlIIIlIIlI.generateCommand(llllllllllllllllllllIlIlIIIlIIll);
      LiquidBounce.INSTANCE.getEventManager().registerListener((Listenable)llllllllllllllllllllIlIlIIIlIIll);
   }

   @Nullable
   public final Module getModule(@Nullable String llllllllllllllllllllIlIIllIIIlII) {
      boolean llllllllllllllllllllIlIIllIIIIll = (Iterable)llllllllllllllllllllIlIIllIIIlIl.modules;
      Exception llllllllllllllllllllIlIIllIIIIlI = false;
      int llllllllllllllllllllIlIIllIIIIII = false;
      Iterator llllllllllllllllllllIlIIlIllllll = llllllllllllllllllllIlIIllIIIIll.iterator();

      Object var10000;
      while(true) {
         if (llllllllllllllllllllIlIIlIllllll.hasNext()) {
            int llllllllllllllllllllIlIIlIlllllI = llllllllllllllllllllIlIIlIllllll.next();
            int llllllllllllllllllllIlIIlIllllIl = (Module)llllllllllllllllllllIlIIlIlllllI;
            int llllllllllllllllllllIlIIllIIlIII = false;
            if (!StringsKt.equals(llllllllllllllllllllIlIIlIllllIl.getName(), llllllllllllllllllllIlIIllIIIlII, true)) {
               continue;
            }

            var10000 = llllllllllllllllllllIlIIlIlllllI;
            break;
         }

         var10000 = null;
         break;
      }

      return (Module)var10000;
   }

   private final void registerModule(Class<? extends Module> llllllllllllllllllllIlIlIIIIIlll) {
      try {
         Object var10001 = llllllllllllllllllllIlIlIIIIIlll.newInstance();
         Intrinsics.checkExpressionValueIsNotNull(var10001, "moduleClass.newInstance()");
         llllllllllllllllllllIlIlIIIIlIII.registerModule((Module)var10001);
      } catch (Throwable var3) {
         ClientUtils.getLogger().error(String.valueOf((new StringBuilder()).append("Failed to load module: ").append(llllllllllllllllllllIlIlIIIIIlll.getName()).append(" (").append(var3.getClass().getName()).append(": ").append(var3.getMessage()).append(')')));
      }

   }

   public final void registerModules() {
      ClientUtils.getLogger().info("[ModuleManager] Loading modules...");
      llllllllllllllllllllIlIlIIIllIlI.registerModules(AutoArmor.class, AutoBow.class, AutoPot.class, AutoSoup.class, AutoWeapon.class, BowAimbot.class, Criticals.class, Aura.class, Ghost.class, Velocity.class, Fly.class, ClickGUI.class, HighJump.class, InventoryMove.class, GhostHand.class, NoSlow.class, LiquidWalk.class, SafeWalk.class, Strafe.class, Sprint.class, Teams.class, NoRotateSet.class, AntiBot.class, ChestStealer.class, Scaffold.class, Tower.class, FastBreak.class, FastPlace.class, ESP.class, Speed.class, Tracers.class, NameTags.class, FastUse.class, Teleport.class, Fullbright.class, ItemESP.class, StorageESP.class, Projectiles.class, NoClip.class, PingSpoof.class, FastClimb.class, Step.class, AutoRespawn.class, AutoTool.class, NoWeb.class, Spammer.class, IceSpeed.class, Zoot.class, Regen.class, NoFall.class, Blink.class, NameProtect.class, NoHurtCam.class, MidClick.class, XRay.class, Timer.class, SkinDerp.class, AutoWalk.class, FreeCam.class, Eagle.class, HitBox.class, AntiCactus.class, Plugins.class, ConsoleSpammer.class, LongJump.class, FastBow.class, AutoClicker.class, NoBob.class, NoFriends.class, BlockESP.class, Chams.class, Clip.class, ServerCrasher.class, NoFOV.class, FastStairs.class, SwingAnimation.class, Derp.class, InventoryCleaner.class, TrueSight.class, LiquidChat.class, AntiBlind.class, NoSwing.class, PotionSaver.class, CameraClip.class, Kick.class, Liquids.class, BufferSpeed.class, SuperKnockback.class, ProphuntESP.class, AutoFish.class, Freeze.class, KeepContainer.class, Reach.class, NoJumpDelay.class, BlockWalk.class, AntiAFK.class, PerfectHorseJump.class, HUD.class, TNTESP.class, ComponentOnHover.class, KeepAlive.class, AbortBreaking.class, BlockOverlay.class, Rotations.class, Damage.class, BugUp.class);
      llllllllllllllllllllIlIlIIIllIlI.registerModule((Module)NoScoreboard.INSTANCE);
      llllllllllllllllllllIlIlIIIllIlI.registerModule((Module)Fucker.INSTANCE);
      llllllllllllllllllllIlIlIIIllIlI.registerModule((Module)ChestAura.INSTANCE);
      ClientUtils.getLogger().info(String.valueOf((new StringBuilder()).append("[ModuleManager] Loaded ").append(llllllllllllllllllllIlIlIIIllIlI.modules.size()).append(" modules.")));
   }
}
