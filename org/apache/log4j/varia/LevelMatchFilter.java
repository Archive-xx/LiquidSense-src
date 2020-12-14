package org.apache.log4j.varia;

import org.apache.log4j.Level;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public class LevelMatchFilter extends Filter {
   boolean acceptOnMatch = true;
   Level levelToMatch;

   public void setLevelToMatch(String level) {
      this.levelToMatch = OptionConverter.toLevel(level, (Level)null);
   }

   public String getLevelToMatch() {
      return this.levelToMatch == null ? null : this.levelToMatch.toString();
   }

   public void setAcceptOnMatch(boolean acceptOnMatch) {
      this.acceptOnMatch = acceptOnMatch;
   }

   public boolean getAcceptOnMatch() {
      return this.acceptOnMatch;
   }

   public int decide(LoggingEvent event) {
      if (this.levelToMatch == null) {
         return 0;
      } else {
         boolean matchOccured = false;
         if (this.levelToMatch.equals(event.getLevel())) {
            matchOccured = true;
         }

         if (matchOccured) {
            return this.acceptOnMatch ? 1 : -1;
         } else {
            return 0;
         }
      }
   }
}
