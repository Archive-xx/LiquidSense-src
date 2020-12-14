package net.ccbluex.liquidbounce.ui.client.hud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\n\u001a\u00020\u0006J\u0006\u0010\u000b\u001a\u00020\u0003R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/Config;", "", "config", "", "(Ljava/lang/String;)V", "hud", "Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "(Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;)V", "jsonArray", "Lcom/google/gson/JsonArray;", "toHUD", "toJson", "LiquidSense"}
)
public final class Config {
   // $FF: synthetic field
   private JsonArray jsonArray;

   public Config(@NotNull HUD llllllllllllllllllIIIIllIllIlIll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIllIllIlIll, "hud");
      super();
      llllllllllllllllllIIIIllIllIlllI.jsonArray = new JsonArray();
      Iterator llllllllllllllllllIIIIllIllIlIIl = llllllllllllllllllIIIIllIllIlIll.getElements().iterator();

      while(llllllllllllllllllIIIIllIllIlIIl.hasNext()) {
         int llllllllllllllllllIIIIllIllIlIlI = (Element)llllllllllllllllllIIIIllIllIlIIl.next();
         byte llllllllllllllllllIIIIllIllIlIII = new JsonObject();
         llllllllllllllllllIIIIllIllIlIII.addProperty("Type", llllllllllllllllllIIIIllIllIlIlI.getName());
         llllllllllllllllllIIIIllIllIlIII.addProperty("X", (Number)llllllllllllllllllIIIIllIllIlIlI.getX());
         llllllllllllllllllIIIIllIllIlIII.addProperty("Y", (Number)llllllllllllllllllIIIIllIllIlIlI.getY());
         llllllllllllllllllIIIIllIllIlIII.addProperty("Scale", (Number)llllllllllllllllllIIIIllIllIlIlI.getScale());
         llllllllllllllllllIIIIllIllIlIII.addProperty("HorizontalFacing", llllllllllllllllllIIIIllIllIlIlI.getSide().getHorizontal().getSideName());
         llllllllllllllllllIIIIllIllIlIII.addProperty("VerticalFacing", llllllllllllllllllIIIIllIllIlIlI.getSide().getVertical().getSideName());
         Iterator llllllllllllllllllIIIIllIllIIllI = llllllllllllllllllIIIIllIllIlIlI.getValues().iterator();

         while(llllllllllllllllllIIIIllIllIIllI.hasNext()) {
            Value llllllllllllllllllIIIIllIlllIIIl = (Value)llllllllllllllllllIIIIllIllIIllI.next();
            llllllllllllllllllIIIIllIllIlIII.add(llllllllllllllllllIIIIllIlllIIIl.getName(), llllllllllllllllllIIIIllIlllIIIl.toJson());
         }

         llllllllllllllllllIIIIllIllIlllI.jsonArray.add((JsonElement)llllllllllllllllllIIIIllIllIlIII);
      }

   }

   public Config(@NotNull String llllllllllllllllllIIIIllIllllIll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIllIllllIll, "config");
      super();
      llllllllllllllllllIIIIllIlllllII.jsonArray = new JsonArray();
      Object var10001 = (new Gson()).fromJson(llllllllllllllllllIIIIllIllllIll, JsonArray.class);
      Intrinsics.checkExpressionValueIsNotNull(var10001, "Gson().fromJson(config, JsonArray::class.java)");
      llllllllllllllllllIIIIllIlllllII.jsonArray = (JsonArray)var10001;
   }

   @NotNull
   public final HUD toHUD() {
      HUD llllllllllllllllllIIIIlllIIlIIII = new HUD();

      try {
         Iterator llllllllllllllllllIIIIlllIIIlllI = llllllllllllllllllIIIIlllIIlIIlI.jsonArray.iterator();

         Element llllllllllllllllllIIIIlllIlIIIII;
         boolean var36;
         while(llllllllllllllllllIIIIlllIIIlllI.hasNext()) {
            JsonElement llllllllllllllllllIIIIlllIIIllll = (JsonElement)llllllllllllllllllIIIIlllIIIlllI.next();

            try {
               if (llllllllllllllllllIIIIlllIIIllll instanceof JsonObject && ((JsonObject)llllllllllllllllllIIIIlllIIIllll).has("Type")) {
                  JsonElement var10000 = ((JsonObject)llllllllllllllllllIIIIlllIIIllll).get("Type");
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "jsonObject[\"Type\"]");
                  String llllllllllllllllllIIIIlllIIIllIl = var10000.getAsString();
                  double llllllllllllllllllIIIIlllIIIlIlI = HUD.Companion.getElements();
                  int llllllllllllllllllIIIIlllIIIlIIl = llllllllllllllllllIIIIlllIIIlIlI.length;

                  for(int llllllllllllllllllIIIIlllIIIlIll = 0; llllllllllllllllllIIIIlllIIIlIll < llllllllllllllllllIIIIlllIIIlIIl; ++llllllllllllllllllIIIIlllIIIlIll) {
                     Class llllllllllllllllllIIIIlllIIllllI = llllllllllllllllllIIIIlllIIIlIlI[llllllllllllllllllIIIIlllIIIlIll];
                     Exception llllllllllllllllllIIIIlllIIIlIII = ((ElementInfo)llllllllllllllllllIIIIlllIIllllI.getAnnotation(ElementInfo.class)).name();
                     if (Intrinsics.areEqual((Object)llllllllllllllllllIIIIlllIIIlIII, (Object)llllllllllllllllllIIIIlllIIIllIl)) {
                        llllllllllllllllllIIIIlllIlIIIII = (Element)llllllllllllllllllIIIIlllIIllllI.newInstance();
                        JsonElement var10001 = ((JsonObject)llllllllllllllllllIIIIlllIIIllll).get("X");
                        Intrinsics.checkExpressionValueIsNotNull(var10001, "jsonObject[\"X\"]");
                        llllllllllllllllllIIIIlllIlIIIII.setX((double)var10001.getAsInt());
                        var10001 = ((JsonObject)llllllllllllllllllIIIIlllIIIllll).get("Y");
                        Intrinsics.checkExpressionValueIsNotNull(var10001, "jsonObject[\"Y\"]");
                        llllllllllllllllllIIIIlllIlIIIII.setY((double)var10001.getAsInt());
                        var10001 = ((JsonObject)llllllllllllllllllIIIIlllIIIllll).get("Scale");
                        Intrinsics.checkExpressionValueIsNotNull(var10001, "jsonObject[\"Scale\"]");
                        llllllllllllllllllIIIIlllIlIIIII.setScale(var10001.getAsFloat());
                        Side var35 = new Side;
                        Side.Horizontal.Companion var10003 = Side.Horizontal.Companion;
                        JsonElement var10004 = ((JsonObject)llllllllllllllllllIIIIlllIIIllll).get("HorizontalFacing");
                        Intrinsics.checkExpressionValueIsNotNull(var10004, "jsonObject[\"HorizontalFacing\"]");
                        String var39 = var10004.getAsString();
                        Intrinsics.checkExpressionValueIsNotNull(var39, "jsonObject[\"HorizontalFacing\"].asString");
                        Side.Horizontal var37 = var10003.getByName(var39);
                        if (var37 == null) {
                           Intrinsics.throwNpe();
                        }

                        Side.Vertical.Companion var40 = Side.Vertical.Companion;
                        JsonElement var10005 = ((JsonObject)llllllllllllllllllIIIIlllIIIllll).get("VerticalFacing");
                        Intrinsics.checkExpressionValueIsNotNull(var10005, "jsonObject[\"VerticalFacing\"]");
                        String var42 = var10005.getAsString();
                        Intrinsics.checkExpressionValueIsNotNull(var42, "jsonObject[\"VerticalFacing\"].asString");
                        Side.Vertical var41 = var40.getByName(var42);
                        if (var41 == null) {
                           Intrinsics.throwNpe();
                        }

                        var35.<init>(var37, var41);
                        llllllllllllllllllIIIIlllIlIIIII.setSide(var35);
                        Iterator llllllllllllllllllIIIIlllIIIIlIl = llllllllllllllllllIIIIlllIlIIIII.getValues().iterator();

                        while(llllllllllllllllllIIIIlllIIIIlIl.hasNext()) {
                           char llllllllllllllllllIIIIlllIIIIllI = (Value)llllllllllllllllllIIIIlllIIIIlIl.next();
                           if (((JsonObject)llllllllllllllllllIIIIlllIIIllll).has(llllllllllllllllllIIIIlllIIIIllI.getName())) {
                              var10001 = ((JsonObject)llllllllllllllllllIIIIlllIIIllll).get(llllllllllllllllllIIIIlllIIIIllI.getName());
                              Intrinsics.checkExpressionValueIsNotNull(var10001, "jsonObject[value.name]");
                              llllllllllllllllllIIIIlllIIIIllI.fromJson(var10001);
                           }
                        }

                        if (((JsonObject)llllllllllllllllllIIIIlllIIIllll).has("font")) {
                           char llllllllllllllllllIIIIlllIIIIllI = (Iterable)llllllllllllllllllIIIIlllIlIIIII.getValues();
                           char llllllllllllllllllIIIIlllIIIIlIl = false;
                           float llllllllllllllllllIIIIlllIIIIIll = false;
                           Iterator llllllllllllllllllIIIIlllIIIIIlI = llllllllllllllllllIIIIlllIIIIllI.iterator();

                           Object var32;
                           while(true) {
                              if (!llllllllllllllllllIIIIlllIIIIIlI.hasNext()) {
                                 var32 = null;
                                 break;
                              }

                              int llllllllllllllllllIIIIlllIIIIIIl = llllllllllllllllllIIIIlllIIIIIlI.next();
                              Value llllllllllllllllllIIIIlllIlIIIlI = (Value)llllllllllllllllllIIIIlllIIIIIIl;
                              float llllllllllllllllllIIIIllIlllllll = false;
                              if (llllllllllllllllllIIIIlllIlIIIlI instanceof FontValue) {
                                 var32 = llllllllllllllllllIIIIlllIIIIIIl;
                                 break;
                              }
                           }

                           Value var33 = (Value)var32;
                           if ((Value)var32 != null) {
                              var10001 = ((JsonObject)llllllllllllllllllIIIIlllIIIllll).get("font");
                              Intrinsics.checkExpressionValueIsNotNull(var10001, "jsonObject[\"font\"]");
                              var33.fromJson(var10001);
                           } else {
                              var36 = false;
                           }
                        }

                        Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllIIIIlllIlIIIII, "element");
                        llllllllllllllllllIIIIlllIIlIIII.addElement(llllllllllllllllllIIIIlllIlIIIII);
                        var36 = false;
                        break;
                     }
                  }
               }
            } catch (Exception var19) {
               ClientUtils.getLogger().error("Error while loading custom hud element from config.", (Throwable)var19);
            }
         }

         String llllllllllllllllllIIIIlllIIIllIl = HUD.Companion.getElements();
         int llllllllllllllllllIIIIlllIIIllII = llllllllllllllllllIIIIlllIIIllIl.length;

         for(int llllllllllllllllllIIIIlllIIIlllI = 0; llllllllllllllllllIIIIlllIIIlllI < llllllllllllllllllIIIIlllIIIllII; ++llllllllllllllllllIIIIlllIIIlllI) {
            boolean llllllllllllllllllIIIIlllIIIllll = llllllllllllllllllIIIIlllIIIllIl[llllllllllllllllllIIIIlllIIIlllI];
            if (((ElementInfo)llllllllllllllllllIIIIlllIIIllll.getAnnotation(ElementInfo.class)).force()) {
               String llllllllllllllllllIIIIlllIIIlIll = (Iterable)llllllllllllllllllIIIIlllIIlIIII.getElements();
               int llllllllllllllllllIIIIlllIIlIllI = false;
               boolean var34;
               if (llllllllllllllllllIIIIlllIIIlIll instanceof Collection && ((Collection)llllllllllllllllllIIIIlllIIIlIll).isEmpty()) {
                  var34 = true;
               } else {
                  Iterator llllllllllllllllllIIIIlllIIIlIIl = llllllllllllllllllIIIIlllIIIlIll.iterator();

                  while(true) {
                     if (!llllllllllllllllllIIIIlllIIIlIIl.hasNext()) {
                        var34 = true;
                        break;
                     }

                     Exception llllllllllllllllllIIIIlllIIIlIII = llllllllllllllllllIIIIlllIIIlIIl.next();
                     llllllllllllllllllIIIIlllIlIIIII = (Element)llllllllllllllllllIIIIlllIIIlIII;
                     char llllllllllllllllllIIIIlllIIIIllI = false;
                     if (Intrinsics.areEqual((Object)llllllllllllllllllIIIIlllIlIIIII.getClass(), (Object)llllllllllllllllllIIIIlllIIIllll)) {
                        var34 = false;
                        break;
                     }
                  }
               }

               if (var34) {
                  Object var38 = llllllllllllllllllIIIIlllIIIllll.newInstance();
                  Intrinsics.checkExpressionValueIsNotNull(var38, "elementClass.newInstance()");
                  llllllllllllllllllIIIIlllIIlIIII.addElement((Element)var38);
                  var36 = false;
               }
            }
         }

         return llllllllllllllllllIIIIlllIIlIIII;
      } catch (Exception var20) {
         ClientUtils.getLogger().error("Error while loading custom hud config.", (Throwable)var20);
         return HUD.Companion.createDefault();
      }
   }

   @NotNull
   public final String toJson() {
      String var10000 = (new GsonBuilder()).setPrettyPrinting().create().toJson((JsonElement)llllllllllllllllllIIIIlllIlllIII.jsonArray);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "GsonBuilder().setPrettyP…reate().toJson(jsonArray)");
      return var10000;
   }
}
