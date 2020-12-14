package jdk.nashorn.api.scripting;

import jdk.Exported;

@Exported
public interface ClassFilter {
   boolean exposeToScripts(String var1);
}
