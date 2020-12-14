package jdk.nashorn.internal.runtime;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.Base64.Encoder;
import jdk.nashorn.api.scripting.URLReader;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(
   name = "source"
)
public final class Source implements Loggable {
   private static final int BUF_SIZE = 8192;
   private static final Source.Cache CACHE = new Source.Cache();
   private static final Encoder BASE64 = Base64.getUrlEncoder().withoutPadding();
   private final String name;
   private final String base;
   private final Source.Data data;
   private int hash;
   private volatile byte[] digest;
   private String explicitURL;

   private Source(String name, String base, Source.Data data) {
      this.name = name;
      this.base = base;
      this.data = data;
   }

   private static synchronized Source sourceFor(String name, String base, Source.URLData data) throws IOException {
      try {
         Source newSource = new Source(name, base, data);
         Source existingSource = CACHE.get(newSource);
         if (existingSource != null) {
            data.checkPermissionAndClose();
            return existingSource;
         } else {
            data.load();
            CACHE.put(newSource, newSource);
            return newSource;
         }
      } catch (RuntimeException var5) {
         Throwable cause = var5.getCause();
         if (cause instanceof IOException) {
            throw (IOException)cause;
         } else {
            throw var5;
         }
      }
   }

   DebuggerSupport.SourceInfo getSourceInfo() {
      return new DebuggerSupport.SourceInfo(this.getName(), this.data.hashCode(), this.data.url(), this.data.array());
   }

   private static void debug(Object... msg) {
      DebugLogger logger = getLoggerStatic();
      if (logger != null) {
         logger.info(msg);
      }

   }

   private char[] data() {
      return this.data.array();
   }

   public static Source sourceFor(String name, char[] content, boolean isEval) {
      return new Source(name, baseName(name), new Source.RawData(content, isEval));
   }

   public static Source sourceFor(String name, char[] content) {
      return sourceFor(name, content, false);
   }

   public static Source sourceFor(String name, String content, boolean isEval) {
      return new Source(name, baseName(name), new Source.RawData(content, isEval));
   }

   public static Source sourceFor(String name, String content) {
      return sourceFor(name, content, false);
   }

   public static Source sourceFor(String name, URL url) throws IOException {
      return sourceFor(name, (URL)url, (Charset)null);
   }

   public static Source sourceFor(String name, URL url, Charset cs) throws IOException {
      return sourceFor(name, baseURL(url), new Source.URLData(url, cs));
   }

   public static Source sourceFor(String name, File file) throws IOException {
      return sourceFor(name, (File)file, (Charset)null);
   }

   public static Source sourceFor(String name, File file, Charset cs) throws IOException {
      File absFile = file.getAbsoluteFile();
      return sourceFor(name, (String)dirName(absFile, (String)null), (Source.URLData)(new Source.FileData(file, cs)));
   }

   public static Source sourceFor(String name, Reader reader) throws IOException {
      if (reader instanceof URLReader) {
         URLReader urlReader = (URLReader)reader;
         return sourceFor(name, urlReader.getURL(), urlReader.getCharset());
      } else {
         return new Source(name, baseName(name), new Source.RawData(reader));
      }
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof Source)) {
         return false;
      } else {
         Source other = (Source)obj;
         return Objects.equals(this.name, other.name) && this.data.equals(other.data);
      }
   }

   public int hashCode() {
      int h = this.hash;
      if (h == 0) {
         h = this.hash = this.data.hashCode() ^ Objects.hashCode(this.name);
      }

      return h;
   }

   public String getString() {
      return this.data.toString();
   }

   public String getName() {
      return this.name;
   }

   public long getLastModified() {
      return this.data.lastModified();
   }

   public String getBase() {
      return this.base;
   }

   public String getString(int start, int len) {
      return new String(this.data(), start, len);
   }

   public String getString(long token) {
      int start = Token.descPosition(token);
      int len = Token.descLength(token);
      return new String(this.data(), start, len);
   }

   public URL getURL() {
      return this.data.url();
   }

   public String getExplicitURL() {
      return this.explicitURL;
   }

   public void setExplicitURL(String explicitURL) {
      this.explicitURL = explicitURL;
   }

   public boolean isEvalCode() {
      return this.data.isEvalCode();
   }

   private int findBOLN(int position) {
      char[] d = this.data();

      for(int i = position - 1; i > 0; --i) {
         char ch = d[i];
         if (ch == '\n' || ch == '\r') {
            return i + 1;
         }
      }

      return 0;
   }

   private int findEOLN(int position) {
      char[] d = this.data();
      int length = d.length;

      for(int i = position; i < length; ++i) {
         char ch = d[i];
         if (ch == '\n' || ch == '\r') {
            return i - 1;
         }
      }

      return length - 1;
   }

   public int getLine(int position) {
      char[] d = this.data();
      int line = 1;

      for(int i = 0; i < position; ++i) {
         char ch = d[i];
         if (ch == '\n') {
            ++line;
         }
      }

      return line;
   }

   public int getColumn(int position) {
      return position - this.findBOLN(position);
   }

   public String getSourceLine(int position) {
      int first = this.findBOLN(position);
      int last = this.findEOLN(position);
      return new String(this.data(), first, last - first + 1);
   }

   public char[] getContent() {
      return this.data();
   }

   public int getLength() {
      return this.data.length();
   }

   public static char[] readFully(Reader reader) throws IOException {
      char[] arr = new char[8192];
      StringBuilder sb = new StringBuilder();

      int numChars;
      try {
         while((numChars = reader.read(arr, 0, arr.length)) > 0) {
            sb.append(arr, 0, numChars);
         }
      } finally {
         reader.close();
      }

      return sb.toString().toCharArray();
   }

   public static char[] readFully(File file) throws IOException {
      if (!file.isFile()) {
         throw new IOException(file + " is not a file");
      } else {
         return byteToCharArray(Files.readAllBytes(file.toPath()));
      }
   }

   public static char[] readFully(File file, Charset cs) throws IOException {
      if (!file.isFile()) {
         throw new IOException(file + " is not a file");
      } else {
         byte[] buf = Files.readAllBytes(file.toPath());
         return cs != null ? (new String(buf, cs)).toCharArray() : byteToCharArray(buf);
      }
   }

   public static char[] readFully(URL url) throws IOException {
      return readFully(url.openStream());
   }

   public static char[] readFully(URL url, Charset cs) throws IOException {
      return readFully(url.openStream(), cs);
   }

   public String getDigest() {
      return new String(this.getDigestBytes(), StandardCharsets.US_ASCII);
   }

   private byte[] getDigestBytes() {
      byte[] ldigest = this.digest;
      if (ldigest == null) {
         char[] content = this.data();
         byte[] bytes = new byte[content.length * 2];

         for(int i = 0; i < content.length; ++i) {
            bytes[i * 2] = (byte)(content[i] & 255);
            bytes[i * 2 + 1] = (byte)((content[i] & '\uff00') >> 8);
         }

         try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            if (this.name != null) {
               md.update(this.name.getBytes(StandardCharsets.UTF_8));
            }

            if (this.base != null) {
               md.update(this.base.getBytes(StandardCharsets.UTF_8));
            }

            if (this.getURL() != null) {
               md.update(this.getURL().toString().getBytes(StandardCharsets.UTF_8));
            }

            this.digest = ldigest = BASE64.encode(md.digest(bytes));
         } catch (NoSuchAlgorithmException var5) {
            throw new RuntimeException(var5);
         }
      }

      return ldigest;
   }

   public static String baseURL(URL url) {
      if (url.getProtocol().equals("file")) {
         try {
            Path path = Paths.get(url.toURI());
            Path parent = path.getParent();
            return parent != null ? parent + File.separator : null;
         } catch (URISyntaxException | IOError | SecurityException var4) {
            return null;
         }
      } else {
         String path = url.getPath();
         if (path.isEmpty()) {
            return null;
         } else {
            path = path.substring(0, path.lastIndexOf(47) + 1);
            int port = url.getPort();

            try {
               return (new URL(url.getProtocol(), url.getHost(), port, path)).toString();
            } catch (MalformedURLException var5) {
               return null;
            }
         }
      }
   }

   private static String dirName(File file, String DEFAULT_BASE_NAME) {
      String res = file.getParent();
      return res != null ? res + File.separator : DEFAULT_BASE_NAME;
   }

   private static String baseName(String name) {
      int idx = name.lastIndexOf(47);
      if (idx == -1) {
         idx = name.lastIndexOf(92);
      }

      return idx != -1 ? name.substring(0, idx + 1) : null;
   }

   private static char[] readFully(InputStream is, Charset cs) throws IOException {
      return cs != null ? (new String(readBytes(is), cs)).toCharArray() : readFully(is);
   }

   private static char[] readFully(InputStream is) throws IOException {
      return byteToCharArray(readBytes(is));
   }

   private static char[] byteToCharArray(byte[] bytes) {
      Charset cs = StandardCharsets.UTF_8;
      int start = 0;
      if (bytes.length > 1 && bytes[0] == -2 && bytes[1] == -1) {
         start = 2;
         cs = StandardCharsets.UTF_16BE;
      } else if (bytes.length > 1 && bytes[0] == -1 && bytes[1] == -2) {
         if (bytes.length > 3 && bytes[2] == 0 && bytes[3] == 0) {
            start = 4;
            cs = Charset.forName("UTF-32LE");
         } else {
            start = 2;
            cs = StandardCharsets.UTF_16LE;
         }
      } else if (bytes.length > 2 && bytes[0] == -17 && bytes[1] == -69 && bytes[2] == -65) {
         start = 3;
         cs = StandardCharsets.UTF_8;
      } else if (bytes.length > 3 && bytes[0] == 0 && bytes[1] == 0 && bytes[2] == -2 && bytes[3] == -1) {
         start = 4;
         cs = Charset.forName("UTF-32BE");
      }

      return (new String(bytes, start, bytes.length - start, cs)).toCharArray();
   }

   static byte[] readBytes(InputStream is) throws IOException {
      byte[] arr = new byte[8192];

      byte[] var5;
      try {
         ByteArrayOutputStream buf = new ByteArrayOutputStream();
         Throwable var3 = null;

         try {
            int numBytes;
            while((numBytes = is.read(arr, 0, arr.length)) > 0) {
               buf.write(arr, 0, numBytes);
            }

            var5 = buf.toByteArray();
         } catch (Throwable var21) {
            var3 = var21;
            throw var21;
         } finally {
            if (buf != null) {
               if (var3 != null) {
                  try {
                     buf.close();
                  } catch (Throwable var20) {
                     var3.addSuppressed(var20);
                  }
               } else {
                  buf.close();
               }
            }

         }
      } finally {
         is.close();
      }

      return var5;
   }

   public String toString() {
      return this.getName();
   }

   private static URL getURLFromFile(File file) {
      try {
         return file.toURI().toURL();
      } catch (MalformedURLException | SecurityException var2) {
         return null;
      }
   }

   private static DebugLogger getLoggerStatic() {
      Context context = Context.getContextTrustedOrNull();
      return context == null ? null : context.getLogger(Source.class);
   }

   public DebugLogger initLogger(Context context) {
      return context.getLogger(this.getClass());
   }

   public DebugLogger getLogger() {
      return this.initLogger(Context.getContextTrusted());
   }

   private File dumpFile(File dirFile) {
      URL u = this.getURL();
      StringBuilder buf = new StringBuilder();
      buf.append(LocalDateTime.now().toString());
      buf.append('_');
      if (u != null) {
         buf.append(u.toString().replace('/', '_').replace('\\', '_'));
      } else {
         buf.append(this.getName());
      }

      return new File(dirFile, buf.toString());
   }

   void dump(String dir) {
      File dirFile = new File(dir);
      File file = this.dumpFile(dirFile);
      if (!dirFile.exists() && !dirFile.mkdirs()) {
         debug("Skipping source dump for " + this.name);
      } else {
         try {
            FileOutputStream fos = new FileOutputStream(file);
            Throwable var5 = null;

            try {
               PrintWriter pw = new PrintWriter(fos);
               pw.print(this.data.toString());
               pw.flush();
            } catch (Throwable var15) {
               var5 = var15;
               throw var15;
            } finally {
               if (fos != null) {
                  if (var5 != null) {
                     try {
                        fos.close();
                     } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                     }
                  } else {
                     fos.close();
                  }
               }

            }
         } catch (IOException var17) {
            debug("Skipping source dump for " + this.name + ": " + ECMAErrors.getMessage("io.error.cant.write", dir.toString() + " : " + var17.toString()));
         }

      }
   }

   private static class FileData extends Source.URLData {
      private final File file;

      private FileData(File file, Charset cs) {
         super(Source.getURLFromFile(file), cs, null);
         this.file = file;
      }

      protected void checkPermissionAndClose() throws IOException {
         if (!this.file.canRead()) {
            throw new FileNotFoundException(this.file + " (Permission Denied)");
         } else {
            Source.debug("permission checked for ", this.file);
         }
      }

      protected void loadMeta() {
         if (this.length == 0 && this.lastModified == 0L) {
            this.length = (int)this.file.length();
            this.lastModified = this.file.lastModified();
            Source.debug("loaded metadata for ", this.file);
         }

      }

      protected void load() throws IOException {
         if (this.array == null) {
            this.array = this.cs == null ? Source.readFully(this.file) : Source.readFully(this.file, this.cs);
            this.length = this.array.length;
            this.lastModified = this.file.lastModified();
            Source.debug("loaded content for ", this.file);
         }

      }

      // $FF: synthetic method
      FileData(File x0, Charset x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class URLData implements Source.Data {
      private final URL url;
      protected final Charset cs;
      private int hash;
      protected char[] array;
      protected int length;
      protected long lastModified;

      private URLData(URL url, Charset cs) {
         this.url = (URL)Objects.requireNonNull(url);
         this.cs = cs;
      }

      public int hashCode() {
         int h = this.hash;
         if (h == 0) {
            h = this.hash = this.url.hashCode();
         }

         return h;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof Source.URLData)) {
            return false;
         } else {
            Source.URLData otherData = (Source.URLData)other;
            if (this.url.equals(otherData.url)) {
               try {
                  if (this.isDeferred()) {
                     assert !otherData.isDeferred();

                     this.loadMeta();
                  } else if (otherData.isDeferred()) {
                     otherData.loadMeta();
                  }
               } catch (IOException var4) {
                  throw new RuntimeException(var4);
               }

               return this.length == otherData.length && this.lastModified == otherData.lastModified;
            } else {
               return false;
            }
         }
      }

      public String toString() {
         return new String(this.array());
      }

      public URL url() {
         return this.url;
      }

      public int length() {
         return this.length;
      }

      public long lastModified() {
         return this.lastModified;
      }

      public char[] array() {
         assert !this.isDeferred();

         return this.array;
      }

      public boolean isEvalCode() {
         return false;
      }

      boolean isDeferred() {
         return this.array == null;
      }

      protected void checkPermissionAndClose() throws IOException {
         InputStream in = this.url.openStream();
         Object var2 = null;
         if (in != null) {
            if (var2 != null) {
               try {
                  in.close();
               } catch (Throwable var4) {
                  ((Throwable)var2).addSuppressed(var4);
               }
            } else {
               in.close();
            }
         }

         Source.debug("permission checked for ", this.url);
      }

      protected void load() throws IOException {
         if (this.array == null) {
            URLConnection c = this.url.openConnection();
            InputStream in = c.getInputStream();
            Throwable var3 = null;

            try {
               this.array = this.cs == null ? Source.readFully(in) : Source.readFully(in, this.cs);
               this.length = this.array.length;
               this.lastModified = c.getLastModified();
               Source.debug("loaded content for ", this.url);
            } catch (Throwable var12) {
               var3 = var12;
               throw var12;
            } finally {
               if (in != null) {
                  if (var3 != null) {
                     try {
                        in.close();
                     } catch (Throwable var11) {
                        var3.addSuppressed(var11);
                     }
                  } else {
                     in.close();
                  }
               }

            }
         }

      }

      protected void loadMeta() throws IOException {
         if (this.length == 0 && this.lastModified == 0L) {
            URLConnection c = this.url.openConnection();
            this.length = c.getContentLength();
            this.lastModified = c.getLastModified();
            Source.debug("loaded metadata for ", this.url);
         }

      }

      // $FF: synthetic method
      URLData(URL x0, Charset x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class RawData implements Source.Data {
      private final char[] array;
      private final boolean evalCode;
      private int hash;

      private RawData(char[] array, boolean evalCode) {
         this.array = (char[])Objects.requireNonNull(array);
         this.evalCode = evalCode;
      }

      private RawData(String source, boolean evalCode) {
         this.array = ((String)Objects.requireNonNull(source)).toCharArray();
         this.evalCode = evalCode;
      }

      private RawData(Reader reader) throws IOException {
         this(Source.readFully(reader), false);
      }

      public int hashCode() {
         int h = this.hash;
         if (h == 0) {
            h = this.hash = Arrays.hashCode(this.array) ^ (this.evalCode ? 1 : 0);
         }

         return h;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof Source.RawData)) {
            return false;
         } else {
            Source.RawData other = (Source.RawData)obj;
            return Arrays.equals(this.array, other.array) && this.evalCode == other.evalCode;
         }
      }

      public String toString() {
         return new String(this.array());
      }

      public URL url() {
         return null;
      }

      public int length() {
         return this.array.length;
      }

      public long lastModified() {
         return 0L;
      }

      public char[] array() {
         return this.array;
      }

      public boolean isEvalCode() {
         return this.evalCode;
      }

      // $FF: synthetic method
      RawData(char[] x0, boolean x1, Object x2) {
         this(x0, x1);
      }

      // $FF: synthetic method
      RawData(String x0, boolean x1, Object x2) {
         this(x0, x1);
      }

      // $FF: synthetic method
      RawData(Reader x0, Object x1) throws IOException {
         this(x0);
      }
   }

   private interface Data {
      URL url();

      int length();

      long lastModified();

      char[] array();

      boolean isEvalCode();
   }

   private static class Cache extends WeakHashMap<Source, WeakReference<Source>> {
      private Cache() {
      }

      public Source get(Source key) {
         WeakReference<Source> ref = (WeakReference)super.get(key);
         return ref == null ? null : (Source)ref.get();
      }

      public void put(Source key, Source value) {
         assert !(value.data instanceof Source.RawData);

         this.put(key, new WeakReference(value));
      }

      // $FF: synthetic method
      Cache(Object x0) {
         this();
      }
   }
}
