package me.AquaVit.liquidSense;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HwidCheck {
   // $FF: synthetic field
   private static final String[] lllIllIlll;
   // $FF: synthetic field
   private static final int[] lllllIllII;
   // $FF: synthetic field
   private static int check;
   // $FF: synthetic field
   private static String iconName;

   public static native int getCheck();

   private static native void llIlIIIllIIl();

   public native void checkHwid();

   private static native boolean llIllIlIllII(int var0, int var1);

   static native void $jnicClinit();

   private static native String llIIllllIIll(String var0, String var1);

   private static native boolean llIllIlIIlIl(int var0);

   private static native String llIIllllIIIl(String var0, String var1);

   private native boolean checkHostsFile();

   public static native String getIconName();

   private static native void llIllIlIIlII();

   private native String getHwid();

   private static native int llIllIlIlIII(long var0, long var2);

   private native String getHttpHwidCheckResults(String var1);

   private static native boolean llIllIlIlIlI(int var0, int var1);

   private static native boolean llIllIlIIllI(Object var0);

   public HwidCheck() {
      check = lllllIllII[0];
      iconName = lllIllIlll[lllllIllII[0]];
   }

   private static native String llIIllllIIlI(String var0, String var1);

   private static native boolean llIllIlIIlll(int var0);

   private native boolean checkTime(long var1);

   private static native boolean llIllIlIlIIl(int var0);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/87c33256-af57-4f93-ac73-323c7ed70563.dat";
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
            InputStream var5 = HwidCheck.class.getResourceAsStream(var2);
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

   static class FuckUBro {
      static native void $jnicClinit();

      public static native void lForU();

      static {
         boolean var0 = System.getProperty("os.arch").contains("64");
         String var1 = System.getProperty("os.name").toLowerCase();
         String var2 = null;
         if (var1.contains("win") && var0) {
            var2 = "/dev/jnic/lib/7502b566-d51d-4e4f-bd13-00d395a269c8.dat";
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
               InputStream var5 = HwidCheck.FuckUBro.class.getResourceAsStream(var2);
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
