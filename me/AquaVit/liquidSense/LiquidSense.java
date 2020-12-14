package me.AquaVit.liquidSense;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;

public class LiquidSense {
   // $FF: synthetic field
   private LiquidBounce liquidBounce;
   // $FF: synthetic field
   private static final int[] llIlllll;
   // $FF: synthetic field
   private static final String[] llIIIlIl;
   // $FF: synthetic field
   private List<Object> liquidSenseModules;

   static native void $jnicClinit();

   private static native boolean lIllIlIIlI(int var0, int var1);

   private static native String lIIlllllIl(String var0, String var1);

   private static native void lIlIIIIIIl();

   public LiquidSense(LiquidBounce llIIIIlIIlllllI) {
      llIIIIlIIllllll.liquidBounce = llIIIIlIIlllllI;
   }

   private static native String lIIllllllI(String var0, String var1);

   private static native boolean lIllIIllII(int var0);

   private native void loadModules();

   public native void onStarted();

   public native void onStarting();

   private static native void lIllIIlIll();

   public native List<Object> getLiquidSenseModules();

   private static native String lIIlllllll(String var0, String var1);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/59d953bb-100e-4d51-b87a-0bc4cd116f23.dat";
      }

      if (var2 == null) {
         throw new RuntimeException("Failed to load");
      } else {
         File var3;
         try {
            var3 = File.createTempFile("lib", (String)null);
            var3.deleteOnExit();
            if (!var3.exists()) {
               throw new IOException();
            }
         } catch (IOException var40) {
            throw new UnsatisfiedLinkError("Failed to create temp file");
         }

         byte[] var4 = new byte[2048];

         try {
            InputStream var5 = LiquidSense.class.getResourceAsStream(var2);
            Throwable var6 = null;

            try {
               FileOutputStream var7 = new FileOutputStream(var3);
               Throwable var8 = null;

               try {
                  int var9;
                  try {
                     while((var9 = var5.read(var4)) != -1) {
                        var7.write(var4, 0, var9);
                     }
                  } catch (Throwable var35) {
                     var8 = var35;
                     throw var35;
                  }
               } finally {
                  if (var7 != null) {
                     if (var8 != null) {
                        try {
                           var7.close();
                        } catch (Throwable var34) {
                           var8.addSuppressed(var34);
                        }
                     } else {
                        var7.close();
                     }
                  }

               }
            } catch (Throwable var37) {
               var6 = var37;
               throw var37;
            } finally {
               if (var5 != null) {
                  if (var6 != null) {
                     try {
                        var5.close();
                     } catch (Throwable var33) {
                        var6.addSuppressed(var33);
                     }
                  } else {
                     var5.close();
                  }
               }

            }
         } catch (IOException var39) {
            throw new UnsatisfiedLinkError("Failed to copy file: " + var39.getMessage());
         }

         System.load(var3.getAbsolutePath());
         $jnicClinit();
      }
   }

   class TitleRunnable implements Runnable {
      // $FF: synthetic field
      private static final String[] lIlIIlll;
      // $FF: synthetic field
      private static final int[] llllIIll;

      public native void run();

      private static native String lIIIIIllIl(String var0, String var1);

      private static native void lIIlIIIIII();

      private static native void llIIIlllIl();

      private static native String lIIIIIllII(String var0, String var1);

      static native void $jnicClinit();

      static {
         boolean var0 = System.getProperty("os.arch").contains("64");
         String var1 = System.getProperty("os.name").toLowerCase();
         String var2 = null;
         if (var1.contains("win") && var0) {
            var2 = "/dev/jnic/lib/4d6aa925-b0f9-4bbd-9f1d-527fb43c5c71.dat";
         }

         if (var2 == null) {
            throw new RuntimeException("Failed to load");
         } else {
            File var3;
            try {
               var3 = File.createTempFile("lib", (String)null);
               var3.deleteOnExit();
               if (!var3.exists()) {
                  throw new IOException();
               }
            } catch (IOException var40) {
               throw new UnsatisfiedLinkError("Failed to create temp file");
            }

            byte[] var4 = new byte[2048];

            try {
               InputStream var5 = LiquidSense.TitleRunnable.class.getResourceAsStream(var2);
               Throwable var6 = null;

               try {
                  FileOutputStream var7 = new FileOutputStream(var3);
                  Throwable var8 = null;

                  try {
                     int var9;
                     try {
                        while((var9 = var5.read(var4)) != -1) {
                           var7.write(var4, 0, var9);
                        }
                     } catch (Throwable var35) {
                        var8 = var35;
                        throw var35;
                     }
                  } finally {
                     if (var7 != null) {
                        if (var8 != null) {
                           try {
                              var7.close();
                           } catch (Throwable var34) {
                              var8.addSuppressed(var34);
                           }
                        } else {
                           var7.close();
                        }
                     }

                  }
               } catch (Throwable var37) {
                  var6 = var37;
                  throw var37;
               } finally {
                  if (var5 != null) {
                     if (var6 != null) {
                        try {
                           var5.close();
                        } catch (Throwable var33) {
                           var6.addSuppressed(var33);
                        }
                     } else {
                        var5.close();
                     }
                  }

               }
            } catch (IOException var39) {
               throw new UnsatisfiedLinkError("Failed to copy file: " + var39.getMessage());
            }

            System.load(var3.getAbsolutePath());
            $jnicClinit();
         }
      }
   }
}
