package jdk.nashorn.internal.runtime;

public abstract class DefaultPropertyAccess implements PropertyAccess {
   public int getInt(Object key, int programPoint) {
      return JSType.toInt32(this.get(key));
   }

   public int getInt(double key, int programPoint) {
      return this.getInt(JSType.toObject(key), programPoint);
   }

   public int getInt(int key, int programPoint) {
      return this.getInt(JSType.toObject(key), programPoint);
   }

   public double getDouble(Object key, int programPoint) {
      return JSType.toNumber(this.get(key));
   }

   public double getDouble(double key, int programPoint) {
      return this.getDouble(JSType.toObject(key), programPoint);
   }

   public double getDouble(int key, int programPoint) {
      return this.getDouble(JSType.toObject(key), programPoint);
   }

   public abstract Object get(Object var1);

   public Object get(double key) {
      return this.get(JSType.toObject(key));
   }

   public Object get(int key) {
      return this.get(JSType.toObject(key));
   }

   public void set(double key, int value, int flags) {
      this.set(JSType.toObject(key), JSType.toObject(value), flags);
   }

   public void set(double key, double value, int flags) {
      this.set(JSType.toObject(key), JSType.toObject(value), flags);
   }

   public void set(double key, Object value, int flags) {
      this.set(JSType.toObject(key), JSType.toObject(value), flags);
   }

   public void set(int key, int value, int flags) {
      this.set(JSType.toObject(key), JSType.toObject(value), flags);
   }

   public void set(int key, double value, int flags) {
      this.set(JSType.toObject(key), JSType.toObject(value), flags);
   }

   public void set(int key, Object value, int flags) {
      this.set(JSType.toObject(key), value, flags);
   }

   public void set(Object key, int value, int flags) {
      this.set(key, JSType.toObject(value), flags);
   }

   public void set(Object key, double value, int flags) {
      this.set(key, JSType.toObject(value), flags);
   }

   public abstract void set(Object var1, Object var2, int var3);

   public abstract boolean has(Object var1);

   public boolean has(int key) {
      return this.has(JSType.toObject(key));
   }

   public boolean has(double key) {
      return this.has(JSType.toObject(key));
   }

   public boolean hasOwnProperty(int key) {
      return this.hasOwnProperty(JSType.toObject(key));
   }

   public boolean hasOwnProperty(double key) {
      return this.hasOwnProperty(JSType.toObject(key));
   }

   public abstract boolean hasOwnProperty(Object var1);

   public boolean delete(int key, boolean strict) {
      return this.delete(JSType.toObject(key), strict);
   }

   public boolean delete(double key, boolean strict) {
      return this.delete(JSType.toObject(key), strict);
   }
}
