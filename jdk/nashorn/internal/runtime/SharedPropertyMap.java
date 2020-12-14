package jdk.nashorn.internal.runtime;

import java.lang.invoke.SwitchPoint;

public final class SharedPropertyMap extends PropertyMap {
   private SwitchPoint switchPoint = new SwitchPoint();
   private static final long serialVersionUID = 2166297719721778876L;

   public SharedPropertyMap(PropertyMap map) {
      super(map);
   }

   public void propertyAdded(Property property, boolean isSelf) {
      if (isSelf) {
         this.invalidateSwitchPoint();
      }

      super.propertyAdded(property, isSelf);
   }

   public void propertyDeleted(Property property, boolean isSelf) {
      if (isSelf) {
         this.invalidateSwitchPoint();
      }

      super.propertyDeleted(property, isSelf);
   }

   public void propertyModified(Property oldProperty, Property newProperty, boolean isSelf) {
      if (isSelf) {
         this.invalidateSwitchPoint();
      }

      super.propertyModified(oldProperty, newProperty, isSelf);
   }

   synchronized boolean isValidSharedProtoMap() {
      return this.switchPoint != null;
   }

   synchronized SwitchPoint getSharedProtoSwitchPoint() {
      return this.switchPoint;
   }

   synchronized void invalidateSwitchPoint() {
      if (this.switchPoint != null) {
         assert !this.switchPoint.hasBeenInvalidated();

         SwitchPoint.invalidateAll(new SwitchPoint[]{this.switchPoint});
         this.switchPoint = null;
      }

   }
}
