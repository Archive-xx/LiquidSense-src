package org.slf4j.helpers;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class NOPLoggerFactory implements ILoggerFactory {
   public Logger getLogger(String name) {
      return NOPLogger.NOP_LOGGER;
   }
}
