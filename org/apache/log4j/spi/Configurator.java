package org.apache.log4j.spi;

import java.io.InputStream;
import java.net.URL;

public interface Configurator {
   String INHERITED = "inherited";
   String NULL = "null";

   void doConfigure(InputStream var1, LoggerRepository var2);

   void doConfigure(URL var1, LoggerRepository var2);
}
