package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b'\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006J\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\fJ\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H&J\u000b\u0010\u0013\u001a\u00028\u0000¢\u0006\u0002\u0010\nJ\u001d\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u0017J\u001d\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u0017J\u0013\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00028\u0000¢\u0006\u0002\u0010\fJ\n\u0010\u001a\u001a\u0004\u0018\u00010\u0012H&R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001c\u0010\u0005\u001a\u00028\u0000X\u0084\u000e¢\u0006\u0010\n\u0002\u0010\r\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u001b"},
   d2 = {"Lnet/ccbluex/liquidbounce/value/Value;", "T", "", "name", "", "value", "(Ljava/lang/String;Ljava/lang/Object;)V", "getName", "()Ljava/lang/String;", "getValue", "()Ljava/lang/Object;", "setValue", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "changeValue", "", "fromJson", "element", "Lcom/google/gson/JsonElement;", "get", "onChange", "oldValue", "newValue", "(Ljava/lang/Object;Ljava/lang/Object;)V", "onChanged", "set", "toJson", "LiquidSense"}
)
@SideOnly(Side.CLIENT)
public abstract class Value<T> {
   // $FF: synthetic field
   @NotNull
   private final String name;
   // $FF: synthetic field
   private T value;

   protected void onChanged(T lllllllllllllllllIlllIlIIIlIlIlI, T lllllllllllllllllIlllIlIIIlIlIIl) {
   }

   public final T get() {
      return lllllllllllllllllIlllIlIIIllIlIl.value;
   }

   @NotNull
   public final String getName() {
      return lllllllllllllllllIlllIlIIIlIIlll.name;
   }

   public Value(@NotNull String lllllllllllllllllIlllIlIIIIIllll, T lllllllllllllllllIlllIlIIIIIlIlI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIlllIlIIIIIllll, "name");
      super();
      lllllllllllllllllIlllIlIIIIIllIl.name = lllllllllllllllllIlllIlIIIIIllll;
      lllllllllllllllllIlllIlIIIIIllIl.value = lllllllllllllllllIlllIlIIIIIlIlI;
   }

   @Nullable
   public abstract JsonElement toJson();

   public final void set(T lllllllllllllllllIlllIlIIIllllII) {
      if (!Intrinsics.areEqual(lllllllllllllllllIlllIlIIIllllII, lllllllllllllllllIlllIlIIIlllIll.value)) {
         Object lllllllllllllllllIlllIlIIIlllIIl = lllllllllllllllllIlllIlIIIlllIll.get();

         try {
            lllllllllllllllllIlllIlIIIlllIll.onChange(lllllllllllllllllIlllIlIIIlllIIl, lllllllllllllllllIlllIlIIIllllII);
            lllllllllllllllllIlllIlIIIlllIll.changeValue(lllllllllllllllllIlllIlIIIllllII);
            lllllllllllllllllIlllIlIIIlllIll.onChanged(lllllllllllllllllIlllIlIIIlllIIl, lllllllllllllllllIlllIlIIIllllII);
            LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
         } catch (Exception var4) {
            ClientUtils.getLogger().error(String.valueOf((new StringBuilder()).append("[ValueSystem (").append(lllllllllllllllllIlllIlIIIlllIll.name).append(")]: ").append(var4.getClass().getName()).append(" (").append(var4.getMessage()).append(") [").append(lllllllllllllllllIlllIlIIIlllIIl).append(" >> ").append(lllllllllllllllllIlllIlIIIllllII).append(']')));
         }

      }
   }

   protected final void setValue(T lllllllllllllllllIlllIlIIIIllIIl) {
      lllllllllllllllllIlllIlIIIIllIlI.value = lllllllllllllllllIlllIlIIIIllIIl;
   }

   public void changeValue(T lllllllllllllllllIlllIlIIIllIIIl) {
      lllllllllllllllllIlllIlIIIllIIII.value = lllllllllllllllllIlllIlIIIllIIIl;
   }

   protected final T getValue() {
      return lllllllllllllllllIlllIlIIIlIIIll.value;
   }

   public abstract void fromJson(@NotNull JsonElement var1);

   protected void onChange(T lllllllllllllllllIlllIlIIIlIllIl, T lllllllllllllllllIlllIlIIIlIllII) {
   }
}
