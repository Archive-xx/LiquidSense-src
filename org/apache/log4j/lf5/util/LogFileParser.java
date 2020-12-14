package org.apache.log4j.lf5.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.SwingUtilities;
import org.apache.log4j.lf5.Log4JLogRecord;
import org.apache.log4j.lf5.LogLevel;
import org.apache.log4j.lf5.LogLevelFormatException;
import org.apache.log4j.lf5.LogRecord;
import org.apache.log4j.lf5.viewer.LogBrokerMonitor;
import org.apache.log4j.lf5.viewer.LogFactor5ErrorDialog;
import org.apache.log4j.lf5.viewer.LogFactor5LoadingDialog;

public class LogFileParser implements Runnable {
   public static final String RECORD_DELIMITER = "[slf5s.start]";
   public static final String ATTRIBUTE_DELIMITER = "[slf5s.";
   public static final String DATE_DELIMITER = "[slf5s.DATE]";
   public static final String THREAD_DELIMITER = "[slf5s.THREAD]";
   public static final String CATEGORY_DELIMITER = "[slf5s.CATEGORY]";
   public static final String LOCATION_DELIMITER = "[slf5s.LOCATION]";
   public static final String MESSAGE_DELIMITER = "[slf5s.MESSAGE]";
   public static final String PRIORITY_DELIMITER = "[slf5s.PRIORITY]";
   public static final String NDC_DELIMITER = "[slf5s.NDC]";
   private static SimpleDateFormat _sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss,S");
   private LogBrokerMonitor _monitor;
   LogFactor5LoadingDialog _loadDialog;
   private InputStream _in;

   public LogFileParser(File file) throws IOException, FileNotFoundException {
      this((InputStream)(new FileInputStream(file)));
   }

   public LogFileParser(InputStream stream) throws IOException {
      this._in = null;
      this._in = stream;
   }

   public void parse(LogBrokerMonitor monitor) throws RuntimeException {
      this._monitor = monitor;
      Thread t = new Thread(this);
      t.start();
   }

   public void run() {
      int index = 0;
      int counter = false;
      boolean isLogFile = false;
      this._loadDialog = new LogFactor5LoadingDialog(this._monitor.getBaseFrame(), "Loading file...");

      try {
         LogRecord temp;
         String logRecords;
         int counter;
         for(logRecords = this.loadLogFile(this._in); (counter = logRecords.indexOf("[slf5s.start]", index)) != -1; index = counter + 13) {
            temp = this.createLogRecord(logRecords.substring(index, counter));
            isLogFile = true;
            if (temp != null) {
               this._monitor.addMessage(temp);
            }
         }

         if (index < logRecords.length() && isLogFile) {
            temp = this.createLogRecord(logRecords.substring(index));
            if (temp != null) {
               this._monitor.addMessage(temp);
            }
         }

         if (!isLogFile) {
            throw new RuntimeException("Invalid log file format");
         }

         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               LogFileParser.this.destroyDialog();
            }
         });
      } catch (RuntimeException var6) {
         this.destroyDialog();
         this.displayError("Error - Invalid log file format.\nPlease see documentation on how to load log files.");
      } catch (IOException var7) {
         this.destroyDialog();
         this.displayError("Error - Unable to load log file!");
      }

      this._in = null;
   }

   protected void displayError(String message) {
      new LogFactor5ErrorDialog(this._monitor.getBaseFrame(), message);
   }

   private void destroyDialog() {
      this._loadDialog.hide();
      this._loadDialog.dispose();
   }

   private String loadLogFile(InputStream stream) throws IOException {
      BufferedInputStream br = new BufferedInputStream(stream);
      int count = false;
      int size = br.available();
      StringBuffer sb = null;
      if (size > 0) {
         sb = new StringBuffer(size);
      } else {
         sb = new StringBuffer(1024);
      }

      int count;
      while((count = br.read()) != -1) {
         sb.append((char)count);
      }

      br.close();
      br = null;
      return sb.toString();
   }

   private String parseAttribute(String name, String record) {
      int index = record.indexOf(name);
      return index == -1 ? null : this.getAttribute(index, record);
   }

   private long parseDate(String record) {
      try {
         String s = this.parseAttribute("[slf5s.DATE]", record);
         if (s == null) {
            return 0L;
         } else {
            Date d = _sdf.parse(s);
            return d.getTime();
         }
      } catch (ParseException var4) {
         return 0L;
      }
   }

   private LogLevel parsePriority(String record) {
      String temp = this.parseAttribute("[slf5s.PRIORITY]", record);
      if (temp != null) {
         try {
            return LogLevel.valueOf(temp);
         } catch (LogLevelFormatException var4) {
            return LogLevel.DEBUG;
         }
      } else {
         return LogLevel.DEBUG;
      }
   }

   private String parseThread(String record) {
      return this.parseAttribute("[slf5s.THREAD]", record);
   }

   private String parseCategory(String record) {
      return this.parseAttribute("[slf5s.CATEGORY]", record);
   }

   private String parseLocation(String record) {
      return this.parseAttribute("[slf5s.LOCATION]", record);
   }

   private String parseMessage(String record) {
      return this.parseAttribute("[slf5s.MESSAGE]", record);
   }

   private String parseNDC(String record) {
      return this.parseAttribute("[slf5s.NDC]", record);
   }

   private String parseThrowable(String record) {
      return this.getAttribute(record.length(), record);
   }

   private LogRecord createLogRecord(String record) {
      if (record != null && record.trim().length() != 0) {
         LogRecord lr = new Log4JLogRecord();
         lr.setMillis(this.parseDate(record));
         lr.setLevel(this.parsePriority(record));
         lr.setCategory(this.parseCategory(record));
         lr.setLocation(this.parseLocation(record));
         lr.setThreadDescription(this.parseThread(record));
         lr.setNDC(this.parseNDC(record));
         lr.setMessage(this.parseMessage(record));
         lr.setThrownStackTrace(this.parseThrowable(record));
         return lr;
      } else {
         return null;
      }
   }

   private String getAttribute(int index, String record) {
      int start = record.lastIndexOf("[slf5s.", index - 1);
      if (start == -1) {
         return record.substring(0, index);
      } else {
         start = record.indexOf("]", start);
         return record.substring(start + 1, index).trim();
      }
   }
}
