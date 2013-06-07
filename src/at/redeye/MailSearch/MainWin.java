package at.redeye.MailSearch;

import at.redeye.FrameWork.base.*;
import at.redeye.FrameWork.base.prm.impl.gui.LocalConfig;
import com.auxilii.msgparser.Message;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

/**
 *
 * @author martin
 */
public class MainWin extends BaseDialog {

    public static final String CONFIG_LAST_SEARCH_STRING = "LastSearchString";
    MailSearch mailsearch;
    DoSearch search = null;
    Vector<DisplayMail> messages = new Vector<DisplayMail>();
    int add_counter = 0;
    
    /** Creates new form MainWin */
    public MainWin(MailSearch mailsearch, Root root) {
        super(root, root.getAppTitle());
        this.mailsearch = mailsearch;

        initComponents();
        initStyle();

        jTSearch.setText(root.getSetup().getLocalConfig(CONFIG_LAST_SEARCH_STRING, ""));
        
        
        getAutoRefreshTimer().schedule(new TimerTask() {

            @Override
            public void run() {
                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        onTimeOut();
                    }
                });                
            }
        }, 300, 300);
        
        jLErg.setCellRenderer(new ImageCellRenderer());
    }
    
 void onTimeOut()
 {
     String statusText = "";
     
     if( search != null )
     {
         if( search.isRunning() ) {
             statusText = "suche...";
         } else {
             statusText = "Ich habe fertig!";
         }
     }          
     
     setStatusText( statusText );
 }

 void setStatusText( String statusText )
 {
     if( !jLStatus.getText().equals(statusText) )
         jLStatus.setText(statusText);
 }
 
 private void initStyle()
    {
        buttonGroupDesign.add(jRMetal1);
        buttonGroupDesign.add(jRSystem1);
        buttonGroupDesign.add(jRNimbus1);
        buttonGroupDesign.add(jRMotif1);
        
        String style = root.getSetup().getLocalConfig(FrameWorkConfigDefinitions.LookAndFeel);
        
        if( style.equals("motif") ) {
            jRMotif1.setSelected(true);
        } else if( style.equals("metal")) {
            jRMetal1.setSelected(true);            
        } else if( style.equals("nimbus")) {
            jRNimbus1.setSelected(true);
        } else {
            jRSystem1.setSelected(true);
        }
        
        ActionListener styleChanged = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                changeStyle();
            }
        };
        

        jRMetal1.addActionListener(styleChanged);
        jRSystem1.addActionListener(styleChanged);
        jRNimbus1.addActionListener(styleChanged);
        jRMotif1.addActionListener(styleChanged);
    }           
    
    void changeStyle()
    {
        if (jRMetal1.isSelected()) {
            changeStyle("metal");
        }
        else if (jRSystem1.isSelected()) {
            changeStyle("system");
        }
        else if (jRNimbus1.isSelected()) {
            changeStyle("nimbus");
        }
        else if (jRMotif1.isSelected()) {
            changeStyle("motif");
        }
    } 

    void changeStyle(final String name) {
        new AutoMBox(MainWin.class.getName()) {

            @Override
            public void do_stuff() throws Exception {
                root.getSetup().setLocalConfig(FrameWorkConfigDefinitions.LookAndFeel.getConfigName(), name);

                UIManager.setLookAndFeel(BaseModuleLauncher.getLookAndFeelStrByName(name));
                root.closeAllWindowsNoAppExit();
                new MainWin(mailsearch, root).setVisible(true);
            }
        };
    }    
    
    @Override
    public void close()
    {                
        root.getSetup().setLocalConfig(CONFIG_LAST_SEARCH_STRING, jTSearch.getText());        
        super.close();
    }    
 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupDesign = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLErg = new javax.swing.JList();
        jBSearch = new javax.swing.JButton();
        jBCancel = new javax.swing.JButton();
        jLStatus = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jBClean = new javax.swing.JButton();
        jTSearch = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMQuit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jRMetal1 = new javax.swing.JRadioButtonMenuItem();
        jRSystem1 = new javax.swing.JRadioButtonMenuItem();
        jRMotif1 = new javax.swing.JRadioButtonMenuItem();
        jRNimbus1 = new javax.swing.JRadioButtonMenuItem();
        jMSettings = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jLErg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLErgMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jLErg);

        jBSearch.setText("Suche");
        jBSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSearchActionPerformed(evt);
            }
        });

        jBCancel.setText("Abbrechen");
        jBCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelActionPerformed(evt);
            }
        });

        jLStatus.setText(" ");
        jLStatus.setToolTipText("");
        jLStatus.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jBClean.setIcon(new javax.swing.ImageIcon(getClass().getResource("/at/redeye/MailSearch/icons/konquefox_erase.png"))); // NOI18N
        jBClean.setBorderPainted(false);
        jBClean.setContentAreaFilled(false);
        jBClean.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jBClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCleanActionPerformed(evt);
            }
        });
        jPanel1.add(jBClean);

        jTSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTSearchActionPerformed(evt);
            }
        });
        jPanel1.add(jTSearch);

        jMenu1.setText("Programm");

        jMQuit.setText("Beenden");
        jMQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMQuitActionPerformed(evt);
            }
        });
        jMenu1.add(jMQuit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Einstellungen");

        jMenu5.setText("Design");

        jRMetal1.setSelected(true);
        jRMetal1.setText("Metal");
        jMenu5.add(jRMetal1);

        jRSystem1.setSelected(true);
        jRSystem1.setText("System");
        jMenu5.add(jRSystem1);

        jRMotif1.setSelected(true);
        jRMotif1.setText("Motif");
        jRMotif1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRMotif1ActionPerformed(evt);
            }
        });
        jMenu5.add(jRMotif1);

        jRNimbus1.setSelected(true);
        jRNimbus1.setText("Nimbus");
        jMenu5.add(jRNimbus1);

        jMenu2.add(jMenu5);

        jMSettings.setText("Einstellungen");
        jMSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMSettingsActionPerformed(evt);
            }
        });
        jMenu2.add(jMSettings);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBSearch)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBSearch)
                    .addComponent(jBCancel)
                    .addComponent(jLStatus))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRMotif1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRMotif1ActionPerformed

        // TODO add your handling code here:
	}//GEN-LAST:event_jRMotif1ActionPerformed

    private void jMSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMSettingsActionPerformed

        invokeDialogUnique(new LocalConfig(root));     
	}//GEN-LAST:event_jMSettingsActionPerformed

    private void jMQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMQuitActionPerformed

         close();     
	}//GEN-LAST:event_jMQuitActionPerformed

    private void jBSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSearchActionPerformed
        
        new AutoMBox("MailSearch") {

            @Override
            public void do_stuff() throws Exception {
                do_new_search();
            }
        };           
        
    }//GEN-LAST:event_jBSearchActionPerformed

    private void jBCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelActionPerformed
        
        
        if( search != null )
            search.stop();
        
    }//GEN-LAST:event_jBCancelActionPerformed

    private void jTSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTSearchActionPerformed
        
        jBSearchActionPerformed(evt);
        
    }//GEN-LAST:event_jTSearchActionPerformed

    private void jBCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCleanActionPerformed
        
        jTSearch.setText("");
        jTSearch.requestFocus();
        
    }//GEN-LAST:event_jBCleanActionPerformed

    private void jLErgMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLErgMousePressed
              
       System.out.println("pressed");

        Object obj = jLErg.getSelectedValue();
                
        DisplayMail cont = null;
        
        if( obj instanceof DisplayMail ) {
            cont = (DisplayMail) obj;
        } 
            

        boolean do_popup = false;

        if (evt != null) {
            do_popup = evt.isPopupTrigger();
        }

        if (!do_popup && Setup.is_win_system()) {
            if (evt.getButton() == MouseEvent.BUTTON3) {
                do_popup = true;
            }
        }


        if (do_popup) {
            System.out.println("popup trigger");

            int idx = jLErg.locationToIndex(evt.getPoint());

            if (idx >= 0) {
                jLErg.setSelectedIndex(idx);
                obj = jLErg.getSelectedValue();
            }

            if (obj instanceof DisplayMail) {
                cont = (DisplayMail) obj;
            } 
        }

        if (cont == null && do_popup) {
            /*
            JPopupMenu popup = new ActionPopupClipboard(this, null);

            popup.show(evt.getComponent(), evt.getX(), evt.getY());            
            */
            return;
        }

        if (cont == null) {
            return;
        }

        if (do_popup) {
            /*
            JPopupMenu popup = new ActionPopupClipboard(this, cont);

            popup.show(evt.getComponent(), evt.getX(), evt.getY());            
            */            
        } else {  
            openMail( cont );                    
        } // else
    }//GEN-LAST:event_jLErgMousePressed


    void do_new_search() throws InterruptedException
    {            
        if( search != null )
        {
            while( search.isRunning() )
            {
                search.stop();
                Thread.sleep(300);
                
                if( search.isRunning() ) {
                    setStatusText( "Stoppe alte Suche");
                    return;
                }
            }
        }
        
        messages.clear();
        jLErg.removeAll();
        
        search = new DoSearch(this, jTSearch.getText());
        search.start();        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupDesign;
    private javax.swing.JButton jBCancel;
    private javax.swing.JButton jBClean;
    private javax.swing.JButton jBSearch;
    private javax.swing.JList jLErg;
    private javax.swing.JLabel jLStatus;
    private javax.swing.JMenuItem jMQuit;
    private javax.swing.JMenuItem jMSettings;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButtonMenuItem jRMetal1;
    private javax.swing.JRadioButtonMenuItem jRMotif1;
    private javax.swing.JRadioButtonMenuItem jRNimbus1;
    private javax.swing.JRadioButtonMenuItem jRSystem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTSearch;
    // End of variables declaration//GEN-END:variables

    void appendResult(final Message msg, final String search_string, final File file ) 
    {
        messages.add(new DisplayMail(msg, search_string, file));
        add_counter++;
        
        java.awt.EventQueue.invokeLater(new Runnable(){
            
            @Override
            public void run() {
                if( add_counter > 0 )
                    jLErg.setListData(messages);                
                
                add_counter = 0;
            }
        });        
    }

    private void openMail(final DisplayMail cont)
    {
        new AutoMBox("MailSearch") {

            @Override
            public void do_stuff() throws Exception {
                String open_command = root.getSetup().getLocalConfig(AppConfigDefinitions.MailProgram);

                String command = open_command + " \"" + cont.getFile() + "\"";
                logger.info(command);

                String args[] = open_command.split("\\s");
                
                String command_array[] = new String[args.length+1];

                int i;
                
                for( i = 0; i < args.length; i++ )
                    command_array[i] = args[i];
                                ;
                command_array[i] = cont.getFile().getAbsolutePath();

                Process p = Runtime.getRuntime().exec(command_array);
            }

        };
                
    }
}
