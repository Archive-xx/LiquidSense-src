package jdk.nashorn.internal.runtime;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public final class PropertyHashMap implements Map<String, Property> {
   private static final int INITIAL_BINS = 32;
   private static final int LIST_THRESHOLD = 8;
   public static final PropertyHashMap EMPTY_HASHMAP = new PropertyHashMap();
   private final int size;
   private final int threshold;
   private final PropertyHashMap.Element list;
   private final PropertyHashMap.Element[] bins;
   private Property[] properties;

   private PropertyHashMap() {
      this.size = 0;
      this.threshold = 0;
      this.bins = null;
      this.list = null;
   }

   private PropertyHashMap(PropertyHashMap map) {
      this.size = map.size;
      this.threshold = map.threshold;
      this.bins = map.bins;
      this.list = map.list;
   }

   private PropertyHashMap(int size, PropertyHashMap.Element[] bins, PropertyHashMap.Element list) {
      this.size = size;
      this.threshold = bins != null ? threeQuarters(bins.length) : 0;
      this.bins = bins;
      this.list = list;
   }

   public PropertyHashMap immutableReplace(Property property, Property newProperty) {
      assert property.getKey().equals(newProperty.getKey()) : "replacing properties with different keys: '" + property.getKey() + "' != '" + newProperty.getKey() + "'";

      assert this.findElement(property.getKey()) != null : "replacing property that doesn't exist in map: '" + property.getKey() + "'";

      return this.cloneMap().replaceNoClone(property.getKey(), newProperty);
   }

   public PropertyHashMap immutableAdd(Property property) {
      int newSize = this.size + 1;
      PropertyHashMap newMap = this.cloneMap(newSize);
      newMap = newMap.addNoClone(property);
      return newMap;
   }

   public PropertyHashMap immutableAdd(Property... newProperties) {
      int newSize = this.size + newProperties.length;
      PropertyHashMap newMap = this.cloneMap(newSize);
      Property[] var4 = newProperties;
      int var5 = newProperties.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Property property = var4[var6];
         newMap = newMap.addNoClone(property);
      }

      return newMap;
   }

   public PropertyHashMap immutableAdd(Collection<Property> newProperties) {
      if (newProperties == null) {
         return this;
      } else {
         int newSize = this.size + newProperties.size();
         PropertyHashMap newMap = this.cloneMap(newSize);

         Property property;
         for(Iterator var4 = newProperties.iterator(); var4.hasNext(); newMap = newMap.addNoClone(property)) {
            property = (Property)var4.next();
         }

         return newMap;
      }
   }

   public PropertyHashMap immutableRemove(Property property) {
      return this.immutableRemove(property.getKey());
   }

   public PropertyHashMap immutableRemove(String key) {
      int binIndex;
      if (this.bins != null) {
         binIndex = binIndex(this.bins, key);
         PropertyHashMap.Element bin = this.bins[binIndex];
         if (findElement(bin, key) != null) {
            int newSize = this.size - 1;
            PropertyHashMap.Element[] newBins = null;
            if (newSize >= 8) {
               newBins = (PropertyHashMap.Element[])this.bins.clone();
               newBins[binIndex] = removeFromList(bin, key);
            }

            PropertyHashMap.Element newList = removeFromList(this.list, key);
            return new PropertyHashMap(newSize, newBins, newList);
         }
      } else if (findElement(this.list, key) != null) {
         binIndex = this.size - 1;
         return binIndex != 0 ? new PropertyHashMap(binIndex, (PropertyHashMap.Element[])null, removeFromList(this.list, key)) : EMPTY_HASHMAP;
      }

      return this;
   }

   public Property find(String key) {
      PropertyHashMap.Element element = this.findElement(key);
      return element != null ? element.getProperty() : null;
   }

   Property[] getProperties() {
      if (this.properties == null) {
         Property[] array = new Property[this.size];
         int i = this.size;

         for(PropertyHashMap.Element element = this.list; element != null; element = element.getLink()) {
            --i;
            array[i] = element.getProperty();
         }

         this.properties = array;
      }

      return this.properties;
   }

   private static int binIndex(PropertyHashMap.Element[] bins, String key) {
      return key.hashCode() & bins.length - 1;
   }

   private static int binsNeeded(int n) {
      return 1 << 32 - Integer.numberOfLeadingZeros(n + (n >>> 1) | 31);
   }

   private static int threeQuarters(int n) {
      return (n >>> 1) + (n >>> 2);
   }

   private static PropertyHashMap.Element[] rehash(PropertyHashMap.Element list, int binSize) {
      PropertyHashMap.Element[] newBins = new PropertyHashMap.Element[binSize];

      for(PropertyHashMap.Element element = list; element != null; element = element.getLink()) {
         Property property = element.getProperty();
         String key = property.getKey();
         int binIndex = binIndex(newBins, key);
         newBins[binIndex] = new PropertyHashMap.Element(newBins[binIndex], property);
      }

      return newBins;
   }

   private PropertyHashMap.Element findElement(String key) {
      if (this.bins != null) {
         int binIndex = binIndex(this.bins, key);
         return findElement(this.bins[binIndex], key);
      } else {
         return findElement(this.list, key);
      }
   }

   private static PropertyHashMap.Element findElement(PropertyHashMap.Element elementList, String key) {
      int hashCode = key.hashCode();

      for(PropertyHashMap.Element element = elementList; element != null; element = element.getLink()) {
         if (element.match(key, hashCode)) {
            return element;
         }
      }

      return null;
   }

   private PropertyHashMap cloneMap() {
      return new PropertyHashMap(this.size, this.bins == null ? null : (PropertyHashMap.Element[])this.bins.clone(), this.list);
   }

   private PropertyHashMap cloneMap(int newSize) {
      PropertyHashMap.Element[] newBins;
      if (this.bins == null && newSize <= 8) {
         newBins = null;
      } else if (newSize > this.threshold) {
         newBins = rehash(this.list, binsNeeded(newSize));
      } else {
         newBins = (PropertyHashMap.Element[])this.bins.clone();
      }

      return new PropertyHashMap(newSize, newBins, this.list);
   }

   private PropertyHashMap addNoClone(Property property) {
      int newSize = this.size;
      String key = property.getKey();
      PropertyHashMap.Element newList = this.list;
      if (this.bins != null) {
         int binIndex = binIndex(this.bins, key);
         PropertyHashMap.Element bin = this.bins[binIndex];
         if (findElement(bin, key) != null) {
            --newSize;
            bin = removeFromList(bin, key);
            newList = removeFromList(this.list, key);
         }

         this.bins[binIndex] = new PropertyHashMap.Element(bin, property);
      } else if (findElement(this.list, key) != null) {
         --newSize;
         newList = removeFromList(this.list, key);
      }

      newList = new PropertyHashMap.Element(newList, property);
      return new PropertyHashMap(newSize, this.bins, newList);
   }

   private PropertyHashMap replaceNoClone(String key, Property property) {
      if (this.bins != null) {
         int binIndex = binIndex(this.bins, key);
         PropertyHashMap.Element bin = this.bins[binIndex];
         bin = replaceInList(bin, key, property);
         this.bins[binIndex] = bin;
      }

      PropertyHashMap.Element newList = this.list;
      newList = replaceInList(newList, key, property);
      return new PropertyHashMap(this.size, this.bins, newList);
   }

   private static PropertyHashMap.Element removeFromList(PropertyHashMap.Element list, String key) {
      if (list == null) {
         return null;
      } else {
         int hashCode = key.hashCode();
         if (list.match(key, hashCode)) {
            return list.getLink();
         } else {
            PropertyHashMap.Element head = new PropertyHashMap.Element((PropertyHashMap.Element)null, list.getProperty());
            PropertyHashMap.Element previous = head;

            for(PropertyHashMap.Element element = list.getLink(); element != null; element = element.getLink()) {
               if (element.match(key, hashCode)) {
                  previous.setLink(element.getLink());
                  return head;
               }

               PropertyHashMap.Element next = new PropertyHashMap.Element((PropertyHashMap.Element)null, element.getProperty());
               previous.setLink(next);
               previous = next;
            }

            return list;
         }
      }
   }

   private static PropertyHashMap.Element replaceInList(PropertyHashMap.Element list, String key, Property property) {
      assert list != null;

      int hashCode = key.hashCode();
      if (list.match(key, hashCode)) {
         return new PropertyHashMap.Element(list.getLink(), property);
      } else {
         PropertyHashMap.Element head = new PropertyHashMap.Element((PropertyHashMap.Element)null, list.getProperty());
         PropertyHashMap.Element previous = head;

         for(PropertyHashMap.Element element = list.getLink(); element != null; element = element.getLink()) {
            if (element.match(key, hashCode)) {
               previous.setLink(new PropertyHashMap.Element(element.getLink(), property));
               return head;
            }

            PropertyHashMap.Element next = new PropertyHashMap.Element((PropertyHashMap.Element)null, element.getProperty());
            previous.setLink(next);
            previous = next;
         }

         return list;
      }
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public boolean containsKey(Object key) {
      if (key instanceof String) {
         return this.findElement((String)key) != null;
      } else {
         assert key instanceof String;

         return false;
      }
   }

   public boolean containsKey(String key) {
      return this.findElement(key) != null;
   }

   public boolean containsValue(Object value) {
      if (!(value instanceof Property)) {
         return false;
      } else {
         Property property = (Property)value;
         PropertyHashMap.Element element = this.findElement(property.getKey());
         return element != null && element.getProperty().equals(value);
      }
   }

   public Property get(Object key) {
      if (key instanceof String) {
         PropertyHashMap.Element element = this.findElement((String)key);
         return element != null ? element.getProperty() : null;
      } else {
         assert key instanceof String;

         return null;
      }
   }

   public Property get(String key) {
      PropertyHashMap.Element element = this.findElement(key);
      return element != null ? element.getProperty() : null;
   }

   public Property put(String key, Property value) {
      throw new UnsupportedOperationException("Immutable map.");
   }

   public Property remove(Object key) {
      throw new UnsupportedOperationException("Immutable map.");
   }

   public void putAll(Map<? extends String, ? extends Property> m) {
      throw new UnsupportedOperationException("Immutable map.");
   }

   public void clear() {
      throw new UnsupportedOperationException("Immutable map.");
   }

   public Set<String> keySet() {
      HashSet<String> set = new HashSet();

      for(PropertyHashMap.Element element = this.list; element != null; element = element.getLink()) {
         set.add(element.getKey());
      }

      return Collections.unmodifiableSet(set);
   }

   public Collection<Property> values() {
      return Collections.unmodifiableList(Arrays.asList(this.getProperties()));
   }

   public Set<Entry<String, Property>> entrySet() {
      HashSet<Entry<String, Property>> set = new HashSet();

      for(PropertyHashMap.Element element = this.list; element != null; element = element.getLink()) {
         set.add(element);
      }

      return Collections.unmodifiableSet(set);
   }

   static final class Element implements Entry<String, Property> {
      private PropertyHashMap.Element link;
      private final Property property;
      private final String key;
      private final int hashCode;

      Element(PropertyHashMap.Element link, Property property) {
         this.link = link;
         this.property = property;
         this.key = property.getKey();
         this.hashCode = this.key.hashCode();
      }

      boolean match(String otherKey, int otherHashCode) {
         return this.hashCode == otherHashCode && this.key.equals(otherKey);
      }

      public boolean equals(Object other) {
         assert this.property != null && other != null;

         return other instanceof PropertyHashMap.Element && this.property.equals(((PropertyHashMap.Element)other).property);
      }

      public String getKey() {
         return this.key;
      }

      public Property getValue() {
         return this.property;
      }

      public int hashCode() {
         return this.hashCode;
      }

      public Property setValue(Property value) {
         throw new UnsupportedOperationException("Immutable map.");
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append('[');
         PropertyHashMap.Element elem = this;

         do {
            sb.append(elem.getValue());
            elem = elem.link;
            if (elem != null) {
               sb.append(" -> ");
            }
         } while(elem != null);

         sb.append(']');
         return sb.toString();
      }

      PropertyHashMap.Element getLink() {
         return this.link;
      }

      void setLink(PropertyHashMap.Element link) {
         this.link = link;
      }

      Property getProperty() {
         return this.property;
      }
   }
}
