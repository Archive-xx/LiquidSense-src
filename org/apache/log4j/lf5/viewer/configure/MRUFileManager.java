package org.apache.log4j.lf5.viewer.configure;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;

public class MRUFileManager {
   private static final String CONFIG_FILE_NAME = "mru_file_manager";
   private static final int DEFAULT_MAX_SIZE = 3;
   private int _maxSize = 0;
   private LinkedList _mruFileList;

   public MRUFileManager() {
      this.load();
      this.setMaxSize(3);
   }

   public MRUFileManager(int maxSize) {
      this.load();
      this.setMaxSize(maxSize);
   }

   public void save() {
      File file = new File(this.getFilename());

      try {
         ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
         oos.writeObject(this._mruFileList);
         oos.flush();
         oos.close();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public int size() {
      return this._mruFileList.size();
   }

   public Object getFile(int index) {
      return index < this.size() ? this._mruFileList.get(index) : null;
   }

   public InputStream getInputStream(int index) throws IOException, FileNotFoundException {
      if (index < this.size()) {
         Object o = this.getFile(index);
         return o instanceof File ? this.getInputStream((File)o) : this.getInputStream((URL)o);
      } else {
         return null;
      }
   }

   public void set(File file) {
      this.setMRU(file);
   }

   public void set(URL url) {
      this.setMRU(url);
   }

   public String[] getMRUFileList() {
      if (this.size() == 0) {
         return null;
      } else {
         String[] ss = new String[this.size()];

         for(int i = 0; i < this.size(); ++i) {
            Object o = this.getFile(i);
            if (o instanceof File) {
               ss[i] = ((File)o).getAbsolutePath();
            } else {
               ss[i] = o.toString();
            }
         }

         return ss;
      }
   }

   public void moveToTop(int index) {
      this._mruFileList.add(0, this._mruFileList.remove(index));
   }

   public static void createConfigurationDirectory() {
      String home = System.getProperty("user.home");
      String sep = System.getProperty("file.separator");
      File f = new File(home + sep + "lf5");
      if (!f.exists()) {
         try {
            f.mkdir();
         } catch (SecurityException var4) {
            var4.printStackTrace();
         }
      }

   }

   protected InputStream getInputStream(File file) throws IOException, FileNotFoundException {
      BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
      return reader;
   }

   protected InputStream getInputStream(URL url) throws IOException {
      return url.openStream();
   }

   protected void setMRU(Object o) {
      int index = this._mruFileList.indexOf(o);
      if (index == -1) {
         this._mruFileList.add(0, o);
         this.setMaxSize(this._maxSize);
      } else {
         this.moveToTop(index);
      }

   }

   protected void load() {
      createConfigurationDirectory();
      File file = new File(this.getFilename());
      if (file.exists()) {
         try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            this._mruFileList = (LinkedList)ois.readObject();
            ois.close();
            Iterator it = this._mruFileList.iterator();

            while(it.hasNext()) {
               Object o = it.next();
               if (!(o instanceof File) && !(o instanceof URL)) {
                  it.remove();
               }
            }
         } catch (Exception var5) {
            this._mruFileList = new LinkedList();
         }
      } else {
         this._mruFileList = new LinkedList();
      }

   }

   protected String getFilename() {
      String home = System.getProperty("user.home");
      String sep = System.getProperty("file.separator");
      return home + sep + "lf5" + sep + "mru_file_manager";
   }

   protected void setMaxSize(int maxSize) {
      if (maxSize < this._mruFileList.size()) {
         for(int i = 0; i < this._mruFileList.size() - maxSize; ++i) {
            this._mruFileList.removeLast();
         }
      }

      this._maxSize = maxSize;
   }
}
