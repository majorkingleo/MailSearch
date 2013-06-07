/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.redeye.MailSearch;

import at.redeye.MailSearch.lib.FileFoundInterface;
import at.redeye.MailSearch.lib.ReadFile;
import java.io.File;

/**
 *
 * @author martin
 */
public class NoThreadMain implements FileFoundInterface
{
    FormatOutput formater = null;

    public NoThreadMain( FormatOutput formater ) {
        this.formater = formater;
    }        

    public NoThreadMain() {        
    }        

    
    public boolean fileFound(final File file)
    {        
        final String content = ReadFile.read_file(file.toString());

        if( formater.containsSearchPattern(content) )
        {
            formater.formatOutput(content, file);
        }
                 
        return true;
    }
    
    public boolean diveIntoSubDir(File file)
    {
        if( file.getName().equals("CVS") ||
            file.getName().equals(".svn") )
            return false;

        return true;
    }    
    
    void waitUntilFinnished()
    {
        
    }
    
    void start()
    {
        
    }
}
