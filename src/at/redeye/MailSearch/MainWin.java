package at.redeye.MailSearch;

import at.redeye.FrameWork.base.AutoMBox;
import at.redeye.FrameWork.base.BaseDialog;
import at.redeye.FrameWork.base.BaseModuleLauncher;
import at.redeye.FrameWork.base.FrameWorkConfigDefinitions;
import at.redeye.FrameWork.base.Root;
import at.redeye.FrameWork.base.prm.impl.gui.LocalConfig;
import com.auxilii.msgparser.Message;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.String;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.UIManager;

/**
 *
 * @author martin
 */
public class MainWin extends BaseDialog {

    public static final String CONFIG_LAST_SEARCH_STRING = "LastSearchString";
    MailSearch mailsearch;
    DoSearch search = null;
    Vector<String> messages = new Vector<String>();
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
        jTSearch = new javax.swing.JTextField();
        jBCancel = new javax.swing.JButton();
        jLStatus = new javax.swing.JLabel();
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBSearch)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
            .addComponent(jTSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
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
        
        search = new DoSearch(this, jTSearch.getText());
        search.start();        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupDesign;
    private javax.swing.JButton jBCancel;
    private javax.swing.JButton jBSearch;
    private javax.swing.JList jLErg;
    private javax.swing.JLabel jLStatus;
    private javax.swing.JMenuItem jMQuit;
    private javax.swing.JMenuItem jMSettings;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JRadioButtonMenuItem jRMetal1;
    private javax.swing.JRadioButtonMenuItem jRMotif1;
    private javax.swing.JRadioButtonMenuItem jRNimbus1;
    private javax.swing.JRadioButtonMenuItem jRSystem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTSearch;
    // End of variables declaration//GEN-END:variables

    void appendResult(final Message msg) 
    {
        messages.add(msg.toString());
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
}
