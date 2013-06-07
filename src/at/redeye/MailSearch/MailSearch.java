/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.redeye.MailSearch;

import at.redeye.FrameWork.base.BaseAppConfigDefinitions;
import at.redeye.FrameWork.base.BaseModuleLauncher;
import at.redeye.FrameWork.base.FrameWorkConfigDefinitions;
import at.redeye.FrameWork.base.LocalRoot;
import at.redeye.FrameWork.widgets.StartupWindow;

/**
 *
 * @author martin
 */
public class MailSearch extends BaseModuleLauncher
{
    public MailSearch( String args[] )
    {
        super( args );
        
        root = new LocalRoot("MailSearch", "MailSearch", false, false );
        
        root.setBaseLanguage("de");
        root.setDefaultLanguage("en");
          
        BaseAppConfigDefinitions.LoggingLevel.setConfigValue("DEBUG");
        BaseAppConfigDefinitions.DoLogging.setConfigValue("TRUE");
        
        root.setLanguageTranslationResourcePath("/at/redeye/griesdorn/resources/translations");        
    }
    
    public void run()
    {
        if (splashEnabled()) {
            splash = new StartupWindow(
                    "/at/redeye/FrameWork/base/resources/pictures/redeye.png");
        }

        AppConfigDefinitions.registerDefinitions();
        FrameWorkConfigDefinitions.registerDefinitions();
        
        
        // this sets the default value only
        FrameWorkConfigDefinitions.LookAndFeel.value.loadFromString("nimbus");
        
         setLookAndFeel(root);
         
         configureLogging();
         
        MainWin mainwin = new MainWin(this,root);     
        
        closeSplash();
        
        mainwin.setVisible(true);      
        mainwin.toFront();         
                
    }

    @Override
    public String getVersion() {
        return "0.1";
    }
        
    public static void main( String args[] )
    {
        MailSearch mailsearch = new MailSearch( args );
        mailsearch.run();
    }        
}
