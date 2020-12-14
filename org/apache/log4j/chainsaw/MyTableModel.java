package org.apache.log4j.chainsaw;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

class MyTableModel extends AbstractTableModel {
   private static final Logger LOG;
   private static final Comparator MY_COMP;
   private static final String[] COL_NAMES;
   private static final EventDetails[] EMPTY_LIST;
   private static final DateFormat DATE_FORMATTER;
   private final Object mLock = new Object();
   private final SortedSet mAllEvents;
   private EventDetails[] mFilteredEvents;
   private final List mPendingEvents;
   private boolean mPaused;
   private String mThreadFilter;
   private String mMessageFilter;
   private String mNDCFilter;
   private String mCategoryFilter;
   private Priority mPriorityFilter;

   MyTableModel() {
      this.mAllEvents = new TreeSet(MY_COMP);
      this.mFilteredEvents = EMPTY_LIST;
      this.mPendingEvents = new ArrayList();
      this.mPaused = false;
      this.mThreadFilter = "";
      this.mMessageFilter = "";
      this.mNDCFilter = "";
      this.mCategoryFilter = "";
      this.mPriorityFilter = Priority.DEBUG;
      Thread t = new Thread(new MyTableModel.Processor());
      t.setDaemon(true);
      t.start();
   }

   public int getRowCount() {
      synchronized(this.mLock) {
         return this.mFilteredEvents.length;
      }
   }

   public int getColumnCount() {
      return COL_NAMES.length;
   }

   public String getColumnName(int aCol) {
      return COL_NAMES[aCol];
   }

   public Class getColumnClass(int aCol) {
      return aCol == 2 ? Boolean.class : Object.class;
   }

   public Object getValueAt(int aRow, int aCol) {
      synchronized(this.mLock) {
         EventDetails event = this.mFilteredEvents[aRow];
         if (aCol == 0) {
            return DATE_FORMATTER.format(new Date(event.getTimeStamp()));
         } else if (aCol == 1) {
            return event.getPriority();
         } else if (aCol == 2) {
            return event.getThrowableStrRep() == null ? Boolean.FALSE : Boolean.TRUE;
         } else if (aCol == 3) {
            return event.getCategoryName();
         } else {
            return aCol == 4 ? event.getNDC() : event.getMessage();
         }
      }
   }

   public void setPriorityFilter(Priority aPriority) {
      synchronized(this.mLock) {
         this.mPriorityFilter = aPriority;
         this.updateFilteredEvents(false);
      }
   }

   public void setThreadFilter(String aStr) {
      synchronized(this.mLock) {
         this.mThreadFilter = aStr.trim();
         this.updateFilteredEvents(false);
      }
   }

   public void setMessageFilter(String aStr) {
      synchronized(this.mLock) {
         this.mMessageFilter = aStr.trim();
         this.updateFilteredEvents(false);
      }
   }

   public void setNDCFilter(String aStr) {
      synchronized(this.mLock) {
         this.mNDCFilter = aStr.trim();
         this.updateFilteredEvents(false);
      }
   }

   public void setCategoryFilter(String aStr) {
      synchronized(this.mLock) {
         this.mCategoryFilter = aStr.trim();
         this.updateFilteredEvents(false);
      }
   }

   public void addEvent(EventDetails aEvent) {
      synchronized(this.mLock) {
         this.mPendingEvents.add(aEvent);
      }
   }

   public void clear() {
      synchronized(this.mLock) {
         this.mAllEvents.clear();
         this.mFilteredEvents = new EventDetails[0];
         this.mPendingEvents.clear();
         this.fireTableDataChanged();
      }
   }

   public void toggle() {
      synchronized(this.mLock) {
         this.mPaused = !this.mPaused;
      }
   }

   public boolean isPaused() {
      synchronized(this.mLock) {
         return this.mPaused;
      }
   }

   public EventDetails getEventDetails(int aRow) {
      synchronized(this.mLock) {
         return this.mFilteredEvents[aRow];
      }
   }

   private void updateFilteredEvents(boolean aInsertedToFront) {
      long start = System.currentTimeMillis();
      List filtered = new ArrayList();
      int size = this.mAllEvents.size();
      Iterator it = this.mAllEvents.iterator();

      EventDetails lastFirst;
      while(it.hasNext()) {
         lastFirst = (EventDetails)it.next();
         if (this.matchFilter(lastFirst)) {
            filtered.add(lastFirst);
         }
      }

      lastFirst = this.mFilteredEvents.length == 0 ? null : this.mFilteredEvents[0];
      this.mFilteredEvents = (EventDetails[])((EventDetails[])filtered.toArray(EMPTY_LIST));
      if (aInsertedToFront && lastFirst != null) {
         int index = filtered.indexOf(lastFirst);
         if (index < 1) {
            LOG.warn("In strange state");
            this.fireTableDataChanged();
         } else {
            this.fireTableRowsInserted(0, index - 1);
         }
      } else {
         this.fireTableDataChanged();
      }

      long end = System.currentTimeMillis();
      LOG.debug("Total time [ms]: " + (end - start) + " in update, size: " + size);
   }

   private boolean matchFilter(EventDetails aEvent) {
      if (aEvent.getPriority().isGreaterOrEqual(this.mPriorityFilter) && aEvent.getThreadName().indexOf(this.mThreadFilter) >= 0 && aEvent.getCategoryName().indexOf(this.mCategoryFilter) >= 0 && (this.mNDCFilter.length() == 0 || aEvent.getNDC() != null && aEvent.getNDC().indexOf(this.mNDCFilter) >= 0)) {
         String rm = aEvent.getMessage();
         if (rm == null) {
            return this.mMessageFilter.length() == 0;
         } else {
            return rm.indexOf(this.mMessageFilter) >= 0;
         }
      } else {
         return false;
      }
   }

   static {
      LOG = Logger.getLogger(MyTableModel.class);
      MY_COMP = new Comparator() {
         public int compare(Object aObj1, Object aObj2) {
            if (aObj1 == null && aObj2 == null) {
               return 0;
            } else if (aObj1 == null) {
               return -1;
            } else if (aObj2 == null) {
               return 1;
            } else {
               EventDetails le1 = (EventDetails)aObj1;
               EventDetails le2 = (EventDetails)aObj2;
               return le1.getTimeStamp() < le2.getTimeStamp() ? 1 : -1;
            }
         }
      };
      COL_NAMES = new String[]{"Time", "Priority", "Trace", "Category", "NDC", "Message"};
      EMPTY_LIST = new EventDetails[0];
      DATE_FORMATTER = DateFormat.getDateTimeInstance(3, 2);
   }

   private class Processor implements Runnable {
      private Processor() {
      }

      public void run() {
         while(true) {
            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var7) {
            }

            synchronized(MyTableModel.this.mLock) {
               if (!MyTableModel.this.mPaused) {
                  boolean toHead = true;
                  boolean needUpdate = false;

                  EventDetails event;
                  for(Iterator it = MyTableModel.this.mPendingEvents.iterator(); it.hasNext(); needUpdate = needUpdate || MyTableModel.this.matchFilter(event)) {
                     event = (EventDetails)it.next();
                     MyTableModel.this.mAllEvents.add(event);
                     toHead = toHead && event == MyTableModel.this.mAllEvents.first();
                  }

                  MyTableModel.this.mPendingEvents.clear();
                  if (needUpdate) {
                     MyTableModel.this.updateFilteredEvents(toHead);
                  }
               }
            }
         }
      }

      // $FF: synthetic method
      Processor(Object x1) {
         this();
      }
   }
}
