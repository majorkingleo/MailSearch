/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.redeye.MailSearch;

import at.redeye.MailSearch.msgviewer.JavaMailParser;
import com.auxilii.msgparser.Message;
import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author martin
 */
public class MailFormatMatcher extends FastFormatMatcher
{
    private static final Logger logger = Logger.getLogger(MailFormatMatcher.class);
    MainWin mainwin;
    JavaMailParser parser;
    
    public MailFormatMatcher( MainWin mainwin, String cwd, String search_string, boolean ignore_case , boolean use_unicode, boolean interix_path_convert)
    {
        super( cwd, search_string, ignore_case, use_unicode , interix_path_convert );
        this.mainwin = mainwin;
        parser = new JavaMailParser();
    }

    @Override
    public void formatOutput(String content, File file) {
        try {
            
            Message msg = parser.parse(file);
            logger.debug(msg.getSubject());
            
            mainwin.appendResult( msg, search_string, file );
            
        } catch (IOException ex) {
            logger.error(ex,ex);
        } catch (Exception ex) {
            logger.error(ex,ex);            
        }        
    }
        
}
