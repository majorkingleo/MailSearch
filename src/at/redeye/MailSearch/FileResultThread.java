/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.redeye.MailSearch;

import at.redeye.MailSearch.lib.ReadFile;
import java.io.File;
import java.util.Queue;

/**
 *
 * @author martin
 */
public class FileResultThread extends Thread
{
    final FormatOutput formater;
    final Queue<File> queue;
    boolean stop = false;
    
    public FileResultThread( Queue<File> queue, String cwd, String search_string, boolean ignore_case , boolean use_unicode, boolean interix_path_convert)
    {
        super("FileResultThread");
        
        formater = new FastFormatMatcher( cwd, search_string, ignore_case, use_unicode, interix_path_convert );
        this.queue = queue;
    }
    
    public FileResultThread( Queue<File> queue, String cwd, String search_string, boolean ignore_case , boolean use_unicode, boolean interix_path_convert, FormatOutput formater)
    {
        super("FileResultThread");
        
        if( formater == null )
            formater = new FastFormatMatcher( cwd, search_string, ignore_case, use_unicode, interix_path_convert );
        
        this.formater = formater;
        this.queue = queue;
    }
        
    
    @Override
    public void run()
    {
        File file = null;
        
        do
        {
            while( ( file = queue.poll() ) != null )
            {
                final String content = ReadFile.read_file(file.toString());

                if( formater.containsSearchPattern(content) )
                {
                    formater.formatOutput(content, file);
                }
            }
            
            if( queue.isEmpty() ) {
                try {
                    sleep(50);
                } catch( InterruptedException ex ) {
                    
                }
            }
            
        } while( !stop );
    }
    
    public void doStop()
    {
        stop = true;
    }
    
}
