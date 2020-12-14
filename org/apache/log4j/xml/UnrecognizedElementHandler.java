package org.apache.log4j.xml;

import java.util.Properties;
import org.w3c.dom.Element;

public interface UnrecognizedElementHandler {
   boolean parseUnrecognizedElement(Element var1, Properties var2) throws Exception;
}
