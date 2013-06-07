/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.redeye.MailSearch;

import at.redeye.FrameWork.base.BaseAppConfigDefinitions;
import at.redeye.FrameWork.base.prm.bindtypes.DBConfig;
import at.redeye.FrameWork.base.prm.impl.GlobalConfigDefinitions;
import at.redeye.FrameWork.base.prm.impl.LocalConfigDefinitions;

/**
 *
 * @author martin
 */
public class AppConfigDefinitions extends BaseAppConfigDefinitions 
{    
    public static DBConfig MailDirectory = new DBConfig( "MailDirector", "~/.local/share/.local-mail.directory");
    
    public static void registerDefinitions() {

        BaseRegisterDefinitions();

        addLocal(MailDirectory);          
 
        GlobalConfigDefinitions.add_help_path("/at/redeye/MailSearch/resources/Help/Params/");
        LocalConfigDefinitions.add_help_path("/at/redeye/MailSearch/resources/Help/Params/");
    }    
}
