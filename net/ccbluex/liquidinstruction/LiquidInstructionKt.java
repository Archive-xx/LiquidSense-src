package net.ccbluex.liquidinstruction;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.swing.JFrame;
import javax.swing.JLabel;
import kotlin.Metadata;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import net.ccbluex.liquidbounce.LiquidBounce;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u001a\u0006\u0010\u0000\u001a\u00020\u0001¨\u0006\u0002"},
   d2 = {"main", "", "LiquidSense"}
)
public final class LiquidInstructionKt {
   public static final void main() {
      String llIllIIIlIIIIIl = new JFrame("LiquidSense | Install Instruction.");
      llIllIIIlIIIIIl.setDefaultCloseOperation(3);
      llIllIIIlIIIIIl.setLayout((LayoutManager)(new BorderLayout()));
      llIllIIIlIIIIIl.setResizable(false);
      llIllIIIlIIIIIl.setAlwaysOnTop(true);
      InputStream var10000 = LiquidBounce.class.getResourceAsStream("/instructions.html");
      Intrinsics.checkExpressionValueIsNotNull(var10000, "LiquidBounce::class.java…eam(\"/instructions.html\")");
      int llIllIIIIllllll = var10000;
      short llIllIIIIllllIl = Charsets.UTF_8;
      char llIllIIIIllllII = false;
      double llIllIIIIlllIll = new InputStreamReader(llIllIIIIllllll, llIllIIIIllllIl);
      byte llIllIIIIlllIIl = TextStreamsKt.readText((Reader)llIllIIIIlllIll);
      int llIllIIIlIIIIII = new JLabel(llIllIIIIlllIIl);
      llIllIIIlIIIIIl.add((Component)llIllIIIlIIIIII, "Center");
      llIllIIIlIIIIIl.pack();
      llIllIIIlIIIIIl.setLocationRelativeTo((Component)null);
      llIllIIIlIIIIIl.setVisible(true);
   }

   // $FF: synthetic method
   public static void main(String[] var0) {
      main();
   }
}
