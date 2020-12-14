package org.apache.log4j.chainsaw;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main extends JFrame {
   private static final int DEFAULT_PORT = 4445;
   public static final String PORT_PROP_NAME = "chainsaw.port";
   private static final Logger LOG;

   private Main() {
      super("CHAINSAW - Log4J Log Viewer");
      MyTableModel model = new MyTableModel();
      JMenuBar menuBar = new JMenuBar();
      this.setJMenuBar(menuBar);
      JMenu menu = new JMenu("File");
      menuBar.add(menu);

      try {
         LoadXMLAction lxa = new LoadXMLAction(this, model);
         JMenuItem loadMenuItem = new JMenuItem("Load file...");
         menu.add(loadMenuItem);
         loadMenuItem.addActionListener(lxa);
      } catch (NoClassDefFoundError var10) {
         LOG.info("Missing classes for XML parser", var10);
         JOptionPane.showMessageDialog(this, "XML parser not in classpath - unable to load XML events.", "CHAINSAW", 0);
      } catch (Exception var11) {
         LOG.info("Unable to create the action to load XML files", var11);
         JOptionPane.showMessageDialog(this, "Unable to create a XML parser - unable to load XML events.", "CHAINSAW", 0);
      }

      JMenuItem exitMenuItem = new JMenuItem("Exit");
      menu.add(exitMenuItem);
      exitMenuItem.addActionListener(ExitAction.INSTANCE);
      ControlPanel cp = new ControlPanel(model);
      this.getContentPane().add(cp, "North");
      JTable table = new JTable(model);
      table.setSelectionMode(0);
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setBorder(BorderFactory.createTitledBorder("Events: "));
      scrollPane.setPreferredSize(new Dimension(900, 300));
      JPanel details = new DetailPanel(table, model);
      details.setPreferredSize(new Dimension(900, 300));
      JSplitPane jsp = new JSplitPane(0, scrollPane, details);
      this.getContentPane().add(jsp, "Center");
      this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent aEvent) {
            ExitAction.INSTANCE.actionPerformed((ActionEvent)null);
         }
      });
      this.pack();
      this.setVisible(true);
      this.setupReceiver(model);
   }

   private void setupReceiver(MyTableModel aModel) {
      int port = 4445;
      String strRep = System.getProperty("chainsaw.port");
      if (strRep != null) {
         try {
            port = Integer.parseInt(strRep);
         } catch (NumberFormatException var6) {
            LOG.fatal("Unable to parse chainsaw.port property with value " + strRep + ".");
            JOptionPane.showMessageDialog(this, "Unable to parse port number from '" + strRep + "', quitting.", "CHAINSAW", 0);
            System.exit(1);
         }
      }

      try {
         LoggingReceiver lr = new LoggingReceiver(aModel, port);
         lr.start();
      } catch (IOException var5) {
         LOG.fatal("Unable to connect to socket server, quiting", var5);
         JOptionPane.showMessageDialog(this, "Unable to create socket on port " + port + ", quitting.", "CHAINSAW", 0);
         System.exit(1);
      }

   }

   private static void initLog4J() {
      Properties props = new Properties();
      props.setProperty("log4j.rootLogger", "DEBUG, A1");
      props.setProperty("log4j.appender.A1", "org.apache.log4j.ConsoleAppender");
      props.setProperty("log4j.appender.A1.layout", "org.apache.log4j.TTCCLayout");
      PropertyConfigurator.configure(props);
   }

   public static void main(String[] aArgs) {
      initLog4J();
      new Main();
   }

   static {
      LOG = Logger.getLogger(Main.class);
   }
}
