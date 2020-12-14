package jdk.nashorn.internal.runtime;

public interface PropertyAccess {
   int getInt(Object var1, int var2);

   int getInt(double var1, int var3);

   int getInt(int var1, int var2);

   double getDouble(Object var1, int var2);

   double getDouble(double var1, int var3);

   double getDouble(int var1, int var2);

   Object get(Object var1);

   Object get(double var1);

   Object get(int var1);

   void set(Object var1, int var2, int var3);

   void set(Object var1, double var2, int var4);

   void set(Object var1, Object var2, int var3);

   void set(double var1, int var3, int var4);

   void set(double var1, double var3, int var5);

   void set(double var1, Object var3, int var4);

   void set(int var1, int var2, int var3);

   void set(int var1, double var2, int var4);

   void set(int var1, Object var2, int var3);

   boolean has(Object var1);

   boolean has(int var1);

   boolean has(double var1);

   boolean hasOwnProperty(Object var1);

   boolean hasOwnProperty(int var1);

   boolean hasOwnProperty(double var1);

   boolean delete(int var1, boolean var2);

   boolean delete(double var1, boolean var3);

   boolean delete(Object var1, boolean var2);
}
