/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.redeye.MailSearch;

import java.io.File;

/**
 *
 * @author martin
 */
public class SearchThreadMain extends MultiThreadMain
{
    MainWin mainwin;
    
    public SearchThreadMain(MainWin mainwin, String cwd, String search_string, boolean ignore_case, boolean use_unicode, boolean interix_path_convert) {
        super(4, cwd, search_string, ignore_case, use_unicode, interix_path_convert);
        this.mainwin = mainwin;
    }   
    
    @Override
    public FormatOutput getFormater() 
    {
        return new MailFormatMatcher( mainwin, cwd, search_string, ignore_case, use_unicode, interix_path_convert );
    }    
}
