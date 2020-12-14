package jdk.nashorn.internal.runtime.logging;

import jdk.nashorn.internal.runtime.Context;

public interface Loggable {
   DebugLogger initLogger(Context var1);

   DebugLogger getLogger();
}
