//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.hud.designer.GuiHudDesigner;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Armor;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Arraylist;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Effects;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Image;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Model;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notifications;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Radar;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.ScoreboardElement;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.TabGUI;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Target;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.TargetHud;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Text;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\f\n\u0000\n\u0002\u0010\b\n\u0002\b\r\b\u0017\u0018\u0000 #2\u00020\u0001:\u0001#B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u0005J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\nJ\u0006\u0010\u0011\u001a\u00020\u0012J\u0016\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u001e\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u0017J\u0016\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u0017J\u0006\u0010\u001d\u001a\u00020\u0012J\u000e\u0010\u001e\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u0005J\u000e\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\nJ\u000e\u0010 \u001a\u00020\u00122\u0006\u0010!\u001a\u00020\u000fJ\u0006\u0010\"\u001a\u00020\u0012R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\b\b\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007¨\u0006$"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "elements", "", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "getElements", "()Ljava/util/List;", "elements$1", "notifications", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification;", "getNotifications", "addElement", "element", "addNotification", "", "notification", "clearElements", "", "handleKey", "c", "", "keyCode", "", "handleMouseClick", "mouseX", "mouseY", "button", "handleMouseMove", "handleMouseReleased", "removeElement", "removeNotification", "render", "designer", "update", "Companion", "LiquidSense"}
)
public class HUD extends MinecraftInstance {
   // $FF: synthetic field
   public static final HUD.Companion Companion = new HUD.Companion((DefaultConstructorMarker)null);
   // $FF: synthetic field
   @NotNull
   private final List<Element> elements$1;
   // $FF: synthetic field
   @NotNull
   private static final Class<? extends Element>[] elements = new Class[]{Armor.class, Arraylist.class, Effects.class, Image.class, Model.class, Notifications.class, TabGUI.class, Text.class, ScoreboardElement.class, Target.class, TargetHud.class, Radar.class};
   // $FF: synthetic field
   @NotNull
   private final List<Notification> notifications;

   @JvmStatic
   @NotNull
   public static final HUD createDefault() {
      return Companion.createDefault();
   }

   public final void render(boolean llIIllIIlllIlll) {
      Iterable llIIllIIlllllII = (Iterable)llIIllIIllllIII.elements$1;
      float llIIllIIlllIlII = false;
      byte llIIllIIlllIIIl = false;
      float llIIllIIlllIIII = (Comparator)(new HUD$render$$inlined$sortedBy$1());
      llIIllIIlllllII = (Iterable)CollectionsKt.sortedWith(llIIllIIlllllII, llIIllIIlllIIII);
      llIIllIIlllIlII = false;

      for(Iterator llIIllIIlllIIll = llIIllIIlllllII.iterator(); llIIllIIlllIIll.hasNext(); GL11.glPopMatrix()) {
         Object llIIllIIlllllIl = llIIllIIlllIIll.next();
         Element llIIllIIlllllll = (Element)llIIllIIlllllIl;
         byte llIIllIIllIllll = false;
         GL11.glPushMatrix();
         if (!llIIllIIlllllll.getInfo().disableScale()) {
            GL11.glScalef(llIIllIIlllllll.getScale(), llIIllIIlllllll.getScale(), llIIllIIlllllll.getScale());
         }

         GL11.glTranslated(llIIllIIlllllll.getRenderX(), llIIllIIlllllll.getRenderY(), 0.0D);

         try {
            llIIllIIlllllll.setBorder(llIIllIIlllllll.drawElement());
            if (llIIllIIlllIlll) {
               Border var10000 = llIIllIIlllllll.getBorder();
               if (var10000 != null) {
                  var10000.draw();
               } else {
                  boolean var10001 = false;
               }
            }
         } catch (Exception var9) {
            ClientUtils.getLogger().error(String.valueOf((new StringBuilder()).append("Something went wrong while drawing ").append(llIIllIIlllllll.getName()).append(" element in HUD.")), (Throwable)var9);
         }
      }

   }

   public final void update() {
      Iterator llIIllIIllIIIII = llIIllIIllIIIll.elements$1.iterator();

      while(llIIllIIllIIIII.hasNext()) {
         Element llIIllIIllIIlII = (Element)llIIllIIllIIIII.next();
         llIIllIIllIIlII.updateElement();
      }

   }

   @NotNull
   public final HUD removeElement(@NotNull Element llIIlIllIIlIlII) {
      Intrinsics.checkParameterIsNotNull(llIIlIllIIlIlII, "element");
      llIIlIllIIlIlII.destroyElement();
      llIIlIllIIlIlIl.elements$1.remove(llIIlIllIIlIlII);
      boolean var10001 = false;
      return llIIlIllIIlIlIl;
   }

   @NotNull
   public final List<Element> getElements() {
      return llIIllIlIlIlIII.elements$1;
   }

   public final boolean removeNotification(@NotNull Notification llIIlIlIlIIlIIl) {
      Intrinsics.checkParameterIsNotNull(llIIlIlIlIIlIIl, "notification");
      return llIIlIlIlIIllII.notifications.remove(llIIlIlIlIIlIIl);
   }

   @NotNull
   public final List<Notification> getNotifications() {
      return llIIllIlIlIIIIl.notifications;
   }

   public final void handleKey(char llIIlIllIlIlIIl, int llIIlIllIlIIlIl) {
      Iterator llIIlIllIlIIIll = llIIlIllIlIIlll.elements$1.iterator();

      while(llIIlIllIlIIIll.hasNext()) {
         Element llIIlIllIlIlIll = (Element)llIIlIllIlIIIll.next();
         llIIlIllIlIlIll.handleKey(llIIlIllIlIlIIl, llIIlIllIlIIlIl);
      }

   }

   public HUD() {
      long llIIlIlIlIIIIlI = false;
      Exception llIIlIlIlIIIIII = (List)(new ArrayList());
      llIIlIlIlIIIlII.elements$1 = llIIlIlIlIIIIII;
      llIIlIlIlIIIIlI = false;
      llIIlIlIlIIIIII = (List)(new ArrayList());
      llIIlIlIlIIIlII.notifications = llIIlIlIlIIIIII;
   }

   public final boolean addNotification(@NotNull Notification llIIlIlIlIllIII) {
      Intrinsics.checkParameterIsNotNull(llIIlIlIlIllIII, "notification");
      Iterable llIIlIlIllIIIlI = (Iterable)llIIlIlIlIllllI.elements$1;
      float llIIlIlIlIlIlII = false;
      boolean var10000;
      if (llIIlIlIllIIIlI instanceof Collection && ((Collection)llIIlIlIllIIIlI).isEmpty()) {
         var10000 = false;
      } else {
         Iterator llIIlIlIlIlIIlI = llIIlIlIllIIIlI.iterator();

         while(true) {
            if (!llIIlIlIlIlIIlI.hasNext()) {
               var10000 = false;
               break;
            }

            Object llIIlIlIllIIlII = llIIlIlIlIlIIlI.next();
            boolean llIIlIlIlIlIIII = (Element)llIIlIlIllIIlII;
            int llIIlIlIlIIllll = false;
            if (llIIlIlIlIlIIII instanceof Notifications) {
               var10000 = true;
               break;
            }
         }
      }

      return var10000 && llIIlIlIlIllllI.notifications.add(llIIlIlIlIllIII);
   }

   public final void handleMouseReleased() {
      Iterator llIIllIIIlIlllI = llIIllIIIllIIII.elements$1.iterator();

      while(llIIllIIIlIlllI.hasNext()) {
         Exception llIIllIIIlIllll = (Element)llIIllIIIlIlllI.next();
         llIIllIIIlIllll.setDrag(false);
      }

   }

   public final void handleMouseMove(int llIIlIlllIIIllI, int llIIlIlllIIlIII) {
      if (access$getMc$p$s1046033730().currentScreen instanceof GuiHudDesigner) {
         ScaledResolution llIIlIlllIIlIll = new ScaledResolution(access$getMc$p$s1046033730());
         Iterator llIIlIlllIIIIlI = llIIlIlllIIIlll.elements$1.iterator();

         while(true) {
            Element llIIlIlllIIllII;
            float llIIlIlllIlIIlI;
            float llIIlIllIlllIIl;
            float llIIlIllIllIllI;
            float llIIlIllllIIIII;
            do {
               do {
                  while(true) {
                     float llIIlIlllIlIIIl;
                     do {
                        float llIIlIlllIIIIIl;
                        float llIIlIlllIIIIII;
                        float llIIlIllIllllll;
                        float llIIlIlllIlIIII;
                        do {
                           if (!llIIlIlllIIIIlI.hasNext()) {
                              return;
                           }

                           llIIlIlllIIllII = (Element)llIIlIlllIIIIlI.next();
                           llIIlIlllIIIIIl = (float)llIIlIlllIIIllI / llIIlIlllIIllII.getScale();
                           llIIlIlllIIIIII = (float)llIIlIlllIIlIII / llIIlIlllIIllII.getScale();
                           llIIlIllIllllll = llIIlIlllIIllII.getPrevMouseX();
                           llIIlIlllIlIIII = llIIlIlllIIllII.getPrevMouseY();
                           llIIlIlllIIllII.setPrevMouseX(llIIlIlllIIIIIl);
                           llIIlIlllIIllII.setPrevMouseY(llIIlIlllIIIIII);
                        } while(!llIIlIlllIIllII.getDrag());

                        llIIlIlllIlIIIl = llIIlIlllIIIIIl - llIIlIllIllllll;
                        llIIlIlllIlIIlI = llIIlIlllIIIIII - llIIlIlllIlIIII;
                     } while(llIIlIlllIlIIIl == 0.0F && llIIlIlllIlIIlI == 0.0F);

                     Border var10000 = llIIlIlllIIllII.getBorder();
                     if (var10000 != null) {
                        short llIIlIllIlllIll = var10000;
                        llIIlIllIlllIIl = llIIlIllIlllIll.getX();
                        long llIIlIlllIllIlI = llIIlIllIlllIll.getX2();
                        char llIIlIllIllIllI = false;
                        float llIIlIlllIlIllI = Math.min(llIIlIllIlllIIl, llIIlIlllIllIlI) + (float)1;
                        llIIlIlllIllIlI = llIIlIllIlllIll.getY();
                        llIIlIllIllIllI = llIIlIllIlllIll.getY2();
                        float llIIlIllIllIlIl = false;
                        llIIlIllIlllIIl = Math.min(llIIlIlllIllIlI, llIIlIllIllIllI) + (float)1;
                        llIIlIllIllIllI = llIIlIllIlllIll.getX();
                        float llIIlIllIllIlIl = llIIlIllIlllIll.getX2();
                        double llIIlIllIllIlII = false;
                        llIIlIlllIllIlI = Math.max(llIIlIllIllIllI, llIIlIllIllIlIl) - (float)1;
                        llIIlIllIllIlIl = llIIlIllIlllIll.getY();
                        llIIlIllllIIIII = llIIlIllIlllIll.getY2();
                        int llIIlIllIllIIll = false;
                        llIIlIllIllIllI = Math.max(llIIlIllIllIlIl, llIIlIllllIIIII) - (float)1;
                        llIIlIllIllIlIl = (float)llIIlIlllIIlIll.getScaledWidth() / llIIlIlllIIllII.getScale();
                        llIIlIllllIIIII = (float)llIIlIlllIIlIll.getScaledHeight() / llIIlIlllIIllII.getScale();
                        if ((llIIlIlllIIllII.getRenderX() + (double)llIIlIlllIlIllI + (double)llIIlIlllIlIIIl >= 0.0D || llIIlIlllIlIIIl > (float)0) && (llIIlIlllIIllII.getRenderX() + (double)llIIlIlllIllIlI + (double)llIIlIlllIlIIIl <= (double)llIIlIllIllIlIl || llIIlIlllIlIIIl < (float)0)) {
                           llIIlIlllIIllII.setRenderX((double)llIIlIlllIlIIIl);
                        }
                        break;
                     }

                     boolean var10001 = false;
                  }
               } while(!(llIIlIlllIIllII.getRenderY() + (double)llIIlIllIlllIIl + (double)llIIlIlllIlIIlI >= 0.0D) && !(llIIlIlllIlIIlI > (float)0));
            } while(!(llIIlIlllIIllII.getRenderY() + (double)llIIlIllIllIllI + (double)llIIlIlllIlIIlI <= (double)llIIlIllllIIIII) && !(llIIlIlllIlIIlI < (float)0));

            llIIlIlllIIllII.setRenderY((double)llIIlIlllIlIIlI);
         }
      }
   }

   public final void handleMouseClick(int llIIllIIlIIIIII, int llIIllIIlIIIllI, int llIIllIIIllllIl) {
      Iterator llIIllIIIlllIll = llIIllIIlIIIIlI.elements$1.iterator();

      Element llIIllIIlIIllIl;
      while(llIIllIIIlllIll.hasNext()) {
         llIIllIIlIIllIl = (Element)llIIllIIIlllIll.next();
         llIIllIIlIIllIl.handleMouseClick((double)((float)llIIllIIlIIIIII / llIIllIIlIIllIl.getScale()) - llIIllIIlIIllIl.getRenderX(), (double)((float)llIIllIIlIIIllI / llIIllIIlIIllIl.getScale()) - llIIllIIlIIllIl.getRenderY(), llIIllIIIllllIl);
      }

      if (llIIllIIIllllIl == 0) {
         llIIllIIIlllIll = CollectionsKt.reversed((Iterable)llIIllIIlIIIIlI.elements$1).iterator();

         while(llIIllIIIlllIll.hasNext()) {
            llIIllIIlIIllIl = (Element)llIIllIIIlllIll.next();
            if (llIIllIIlIIllIl.isInBorder((double)((float)llIIllIIlIIIIII / llIIllIIlIIllIl.getScale()) - llIIllIIlIIllIl.getRenderX(), (double)((float)llIIllIIlIIIllI / llIIllIIlIIllIl.getScale()) - llIIllIIlIIllIl.getRenderY())) {
               llIIllIIlIIllIl.setDrag(true);
               llIIllIIlIIIIlI.elements$1.remove(llIIllIIlIIllIl);
               boolean var10001 = false;
               llIIllIIlIIIIlI.elements$1.add(llIIllIIlIIllIl);
               var10001 = false;
               break;
            }
         }
      }

   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public final void clearElements() {
      Iterator llIIlIllIIIIIIl = llIIlIllIIIIlll.elements$1.iterator();

      while(llIIlIllIIIIIIl.hasNext()) {
         Element llIIlIllIIIlIIl = (Element)llIIlIllIIIIIIl.next();
         llIIlIllIIIlIIl.destroyElement();
      }

      llIIlIllIIIIlll.elements$1.clear();
   }

   @NotNull
   public final HUD addElement(@NotNull Element llIIlIllIIlllIl) {
      Intrinsics.checkParameterIsNotNull(llIIlIllIIlllIl, "element");
      llIIlIllIIllllI.elements$1.add(llIIlIllIIlllIl);
      boolean var10001 = false;
      llIIlIllIIlllIl.updateElement();
      return llIIlIllIIllllI;
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0007R!\u0010\u0003\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00050\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\b¨\u0006\f"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/HUD$Companion;", "", "()V", "elements", "", "Ljava/lang/Class;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "getElements", "()[Ljava/lang/Class;", "[Ljava/lang/Class;", "createDefault", "Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "LiquidSense"}
   )
   public static final class Companion {
      @JvmStatic
      @NotNull
      public final HUD createDefault() {
         return (new HUD()).addElement((Element)Text.Companion.defaultClient()).addElement((Element)(new TabGUI(0.0D, 0.0D, 3, (DefaultConstructorMarker)null))).addElement((Element)(new Arraylist(0.0D, 0.0D, 0.0F, (net.ccbluex.liquidbounce.ui.client.hud.element.Side)null, 15, (DefaultConstructorMarker)null))).addElement((Element)(new ScoreboardElement(0.0D, 0.0D, 0.0F, (net.ccbluex.liquidbounce.ui.client.hud.element.Side)null, 15, (DefaultConstructorMarker)null))).addElement((Element)(new Armor(0.0D, 0.0D, 0.0F, (net.ccbluex.liquidbounce.ui.client.hud.element.Side)null, 15, (DefaultConstructorMarker)null))).addElement((Element)(new Effects(0.0D, 0.0D, 0.0F, (net.ccbluex.liquidbounce.ui.client.hud.element.Side)null, 15, (DefaultConstructorMarker)null))).addElement((Element)(new Notifications(0.0D, 0.0D, 0.0F, (net.ccbluex.liquidbounce.ui.client.hud.element.Side)null, 15, (DefaultConstructorMarker)null)));
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker lllllllllllllllllIlllIIlIlIllIII) {
         this();
      }

      @NotNull
      public final Class<? extends Element>[] getElements() {
         return HUD.elements;
      }
   }
}
