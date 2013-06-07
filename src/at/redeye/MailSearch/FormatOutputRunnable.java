/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.redeye.MailSearch;

import at.redeye.MailSearch.lib.ReadFile;
import java.io.File;

/**
 *
 * @author martin
 */
public class FormatOutputRunnable extends FastFormatMatcher implements Runnable 
{
    final File file;
    
    public FormatOutputRunnable( String cwd, 
                                 String search_string, 
                                 boolean ignore_case , 
                                 boolean use_unicode, 
                                 boolean interix_path_convert,
                                 File file)
    {
        super(cwd, search_string, ignore_case, use_unicode, interix_path_convert);
        this.file = file;
    }
    
    
    public void run() {
        final String content = ReadFile.read_file(file.toString());

        if (containsSearchPattern(content)) {
            formatOutput(content, file);
        } 
    }
    
}
