package jdk.nashorn.internal.runtime.options;

import java.io.PrintWriter;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.PropertyPermission;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import jdk.nashorn.internal.runtime.QuotedStringTokenizer;

public final class Options {
   private static final AccessControlContext READ_PROPERTY_ACC_CTXT = createPropertyReadAccCtxt();
   private final String resource;
   private final PrintWriter err;
   private final List<String> files;
   private final List<String> arguments;
   private final TreeMap<String, Option<?>> options;
   private static final String NASHORN_ARGS_PREPEND_PROPERTY = "nashorn.args.prepend";
   private static final String NASHORN_ARGS_PROPERTY = "nashorn.args";
   private static final String MESSAGES_RESOURCE = "jdk.nashorn.internal.runtime.resources.Options";
   private static ResourceBundle bundle = ResourceBundle.getBundle("jdk.nashorn.internal.runtime.resources.Options", Locale.getDefault());
   private static HashMap<Object, Object> usage = new HashMap();
   private static Collection<OptionTemplate> validOptions = new TreeSet();
   private static OptionTemplate helpOptionTemplate;
   private static OptionTemplate definePropTemplate;
   private static String definePropPrefix;

   private static AccessControlContext createPropertyReadAccCtxt() {
      Permissions perms = new Permissions();
      perms.add(new PropertyPermission("nashorn.*", "read"));
      return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain((CodeSource)null, perms)});
   }

   public Options(String resource) {
      this(resource, new PrintWriter(System.err, true));
   }

   public Options(String resource, PrintWriter err) {
      this.resource = resource;
      this.err = err;
      this.files = new ArrayList();
      this.arguments = new ArrayList();
      this.options = new TreeMap();
      Iterator var3 = validOptions.iterator();

      while(var3.hasNext()) {
         OptionTemplate t = (OptionTemplate)var3.next();
         if (t.getDefaultValue() != null) {
            String v = getStringProperty(t.getKey(), (String)null);
            if (v != null) {
               this.set(t.getKey(), createOption(t, v));
            } else if (t.getDefaultValue() != null) {
               this.set(t.getKey(), createOption(t, t.getDefaultValue()));
            }
         }
      }

   }

   public String getResource() {
      return this.resource;
   }

   public String toString() {
      return this.options.toString();
   }

   private static void checkPropertyName(String name) {
      if (!((String)Objects.requireNonNull(name)).startsWith("nashorn.")) {
         throw new IllegalArgumentException(name);
      }
   }

   public static boolean getBooleanProperty(final String name, final Boolean defValue) {
      checkPropertyName(name);
      return (Boolean)AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
         public Boolean run() {
            try {
               String property = System.getProperty(name);
               return property == null && defValue != null ? defValue : property != null && !"false".equalsIgnoreCase(property);
            } catch (SecurityException var2) {
               return false;
            }
         }
      }, READ_PROPERTY_ACC_CTXT);
   }

   public static boolean getBooleanProperty(String name) {
      return getBooleanProperty(name, (Boolean)null);
   }

   public static String getStringProperty(final String name, final String defValue) {
      checkPropertyName(name);
      return (String)AccessController.doPrivileged(new PrivilegedAction<String>() {
         public String run() {
            try {
               return System.getProperty(name, defValue);
            } catch (SecurityException var2) {
               return defValue;
            }
         }
      }, READ_PROPERTY_ACC_CTXT);
   }

   public static int getIntProperty(final String name, final int defValue) {
      checkPropertyName(name);
      return (Integer)AccessController.doPrivileged(new PrivilegedAction<Integer>() {
         public Integer run() {
            try {
               return Integer.getInteger(name, defValue);
            } catch (SecurityException var2) {
               return defValue;
            }
         }
      }, READ_PROPERTY_ACC_CTXT);
   }

   public Option<?> get(String key) {
      return (Option)this.options.get(this.key(key));
   }

   public boolean getBoolean(String key) {
      Option<?> option = this.get(key);
      return option != null ? (Boolean)option.getValue() : false;
   }

   public int getInteger(String key) {
      Option<?> option = this.get(key);
      return option != null ? (Integer)option.getValue() : 0;
   }

   public String getString(String key) {
      Option<?> option = this.get(key);
      if (option != null) {
         String value = (String)option.getValue();
         if (value != null) {
            return value.intern();
         }
      }

      return null;
   }

   public void set(String key, Option<?> option) {
      this.options.put(this.key(key), option);
   }

   public void set(String key, boolean option) {
      this.set(key, new Option(option));
   }

   public void set(String key, String option) {
      this.set(key, new Option(option));
   }

   public List<String> getArguments() {
      return Collections.unmodifiableList(this.arguments);
   }

   public List<String> getFiles() {
      return Collections.unmodifiableList(this.files);
   }

   public static Collection<OptionTemplate> getValidOptions() {
      return Collections.unmodifiableCollection(validOptions);
   }

   private String key(String shortKey) {
      String key;
      for(key = shortKey; key.startsWith("-"); key = key.substring(1, key.length())) {
      }

      key = key.replace("-", ".");
      String keyPrefix = this.resource + ".option.";
      return key.startsWith(keyPrefix) ? key : keyPrefix + key;
   }

   static String getMsg(String msgId, String... args) {
      try {
         String msg = bundle.getString(msgId);
         return args.length == 0 ? msg : (new MessageFormat(msg)).format(args);
      } catch (MissingResourceException var3) {
         throw new IllegalArgumentException(var3);
      }
   }

   public void displayHelp(IllegalArgumentException e) {
      if (e instanceof Options.IllegalOptionException) {
         OptionTemplate template = ((Options.IllegalOptionException)e).getTemplate();
         if (template.isXHelp()) {
            this.displayHelp(true);
         } else {
            this.err.println(((Options.IllegalOptionException)e).getTemplate());
         }

      } else if (e != null && e.getMessage() != null) {
         this.err.println(getMsg("option.error.invalid.option", e.getMessage(), helpOptionTemplate.getShortName(), helpOptionTemplate.getName()));
         this.err.println();
      } else {
         this.displayHelp(false);
      }
   }

   public void displayHelp(boolean extended) {
      Iterator var2 = validOptions.iterator();

      while(true) {
         OptionTemplate t;
         do {
            if (!var2.hasNext()) {
               return;
            }

            t = (OptionTemplate)var2.next();
         } while(!extended && t.isUndocumented());

         if (t.getResource().equals(this.resource)) {
            this.err.println(t);
            this.err.println();
         }
      }
   }

   public void process(String[] args) {
      LinkedList<String> argList = new LinkedList();
      addSystemProperties("nashorn.args.prepend", argList);
      this.processArgList(argList);

      assert argList.isEmpty();

      Collections.addAll(argList, args);
      this.processArgList(argList);

      assert argList.isEmpty();

      addSystemProperties("nashorn.args", argList);
      this.processArgList(argList);

      assert argList.isEmpty();

   }

   private void processArgList(LinkedList<String> argList) {
      while(!argList.isEmpty()) {
         String arg = (String)argList.remove(0);
         if (!arg.isEmpty()) {
            if ("--".equals(arg)) {
               this.arguments.addAll(argList);
               argList.clear();
            } else if (arg.startsWith("-") && arg.length() != 1) {
               if (arg.startsWith(definePropPrefix)) {
                  String value = arg.substring(definePropPrefix.length());
                  int eq = value.indexOf(61);
                  if (eq != -1) {
                     System.setProperty(value.substring(0, eq), value.substring(eq + 1));
                  } else {
                     if (value.isEmpty()) {
                        throw new Options.IllegalOptionException(definePropTemplate);
                     }

                     System.setProperty(value, "");
                  }
               } else {
                  Options.ParsedArg parg = new Options.ParsedArg(arg);
                  if (parg.template.isValueNextArg()) {
                     if (argList.isEmpty()) {
                        throw new Options.IllegalOptionException(parg.template);
                     }

                     parg.value = (String)argList.remove(0);
                  }

                  if (parg.template.isHelp()) {
                     if (!argList.isEmpty()) {
                        try {
                           OptionTemplate t = (new Options.ParsedArg((String)argList.get(0))).template;
                           throw new Options.IllegalOptionException(t);
                        } catch (IllegalArgumentException var5) {
                           throw var5;
                        }
                     }

                     throw new IllegalArgumentException();
                  }

                  if (parg.template.isXHelp()) {
                     throw new Options.IllegalOptionException(parg.template);
                  }

                  this.set(parg.template.getKey(), createOption(parg.template, parg.value));
                  if (parg.template.getDependency() != null) {
                     argList.addFirst(parg.template.getDependency());
                  }
               }
            } else {
               this.files.add(arg);
            }
         }
      }

   }

   private static void addSystemProperties(String sysPropName, List<String> argList) {
      String sysArgs = getStringProperty(sysPropName, (String)null);
      if (sysArgs != null) {
         StringTokenizer st = new StringTokenizer(sysArgs);

         while(st.hasMoreTokens()) {
            argList.add(st.nextToken());
         }
      }

   }

   public OptionTemplate getOptionTemplateByKey(String shortKey) {
      String fullKey = this.key(shortKey);
      Iterator var3 = validOptions.iterator();

      OptionTemplate t;
      do {
         if (!var3.hasNext()) {
            throw new IllegalArgumentException(shortKey);
         }

         t = (OptionTemplate)var3.next();
      } while(!t.getKey().equals(fullKey));

      return t;
   }

   private static OptionTemplate getOptionTemplateByName(String name) {
      Iterator var1 = validOptions.iterator();

      OptionTemplate t;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         t = (OptionTemplate)var1.next();
      } while(!t.nameMatches(name));

      return t;
   }

   private static Option<?> createOption(OptionTemplate t, String value) {
      String var2 = t.getType();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -2076227591:
         if (var2.equals("timezone")) {
            var3 = 1;
         }
         break;
      case -1097462182:
         if (var2.equals("locale")) {
            var3 = 2;
         }
         break;
      case -1003964351:
         if (var2.equals("keyvalues")) {
            var3 = 3;
         }
         break;
      case -926053069:
         if (var2.equals("properties")) {
            var3 = 7;
         }
         break;
      case -891985903:
         if (var2.equals("string")) {
            var3 = 0;
         }
         break;
      case 107332:
         if (var2.equals("log")) {
            var3 = 4;
         }
         break;
      case 64711720:
         if (var2.equals("boolean")) {
            var3 = 5;
         }
         break;
      case 1958052158:
         if (var2.equals("integer")) {
            var3 = 6;
         }
      }

      switch(var3) {
      case 0:
         return new Option(value);
      case 1:
         return new Option(TimeZone.getTimeZone(value));
      case 2:
         return new Option(Locale.forLanguageTag(value));
      case 3:
         return new KeyValueOption(value);
      case 4:
         return new LoggingOption(value);
      case 5:
         return new Option(value != null && Boolean.parseBoolean(value));
      case 6:
         try {
            return new Option(value == null ? 0 : Integer.parseInt(value));
         } catch (NumberFormatException var5) {
            throw new Options.IllegalOptionException(t);
         }
      case 7:
         initProps(new KeyValueOption(value));
         return null;
      default:
         throw new IllegalArgumentException(value);
      }
   }

   private static void initProps(KeyValueOption kv) {
      Iterator var1 = kv.getValues().entrySet().iterator();

      while(var1.hasNext()) {
         Entry<String, String> entry = (Entry)var1.next();
         System.setProperty((String)entry.getKey(), (String)entry.getValue());
      }

   }

   static {
      Enumeration keys = bundle.getKeys();

      while(keys.hasMoreElements()) {
         String key = (String)keys.nextElement();
         StringTokenizer st = new StringTokenizer(key, ".");
         String resource = null;
         String type = null;
         if (st.countTokens() > 0) {
            resource = st.nextToken();
         }

         if (st.countTokens() > 0) {
            type = st.nextToken();
         }

         if ("option".equals(type)) {
            String helpKey = null;
            String xhelpKey = null;
            String definePropKey = null;

            try {
               helpKey = bundle.getString(resource + ".options.help.key");
               xhelpKey = bundle.getString(resource + ".options.xhelp.key");
               definePropKey = bundle.getString(resource + ".options.D.key");
            } catch (MissingResourceException var11) {
            }

            boolean isHelp = key.equals(helpKey);
            boolean isXHelp = key.equals(xhelpKey);
            OptionTemplate t = new OptionTemplate(resource, key, bundle.getString(key), isHelp, isXHelp);
            validOptions.add(t);
            if (isHelp) {
               helpOptionTemplate = t;
            }

            if (key.equals(definePropKey)) {
               definePropPrefix = t.getName();
               definePropTemplate = t;
            }
         } else if (resource != null && "options".equals(type)) {
            usage.put(resource, bundle.getObject(key));
         }
      }

   }

   private static class ParsedArg {
      OptionTemplate template;
      String value;

      ParsedArg(String argument) {
         QuotedStringTokenizer st = new QuotedStringTokenizer(argument, "=");
         if (!st.hasMoreTokens()) {
            throw new IllegalArgumentException();
         } else {
            String token = st.nextToken();
            this.template = Options.getOptionTemplateByName(token);
            if (this.template == null) {
               throw new IllegalArgumentException(argument);
            } else {
               this.value = "";
               if (st.hasMoreTokens()) {
                  while(st.hasMoreTokens()) {
                     this.value = this.value + st.nextToken();
                     if (st.hasMoreTokens()) {
                        this.value = this.value + ':';
                     }
                  }
               } else if ("boolean".equals(this.template.getType())) {
                  this.value = "true";
               }

            }
         }
      }
   }

   private static class IllegalOptionException extends IllegalArgumentException {
      private final OptionTemplate template;

      IllegalOptionException(OptionTemplate t) {
         this.template = t;
      }

      OptionTemplate getTemplate() {
         return this.template;
      }
   }
}
