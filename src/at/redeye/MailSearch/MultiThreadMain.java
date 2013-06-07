/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.redeye.MailSearch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author martin
 */
public class MultiThreadMain extends NoThreadMain
{
    int num_threads;
    final Queue<File> queue = new ConcurrentLinkedQueue<File>();
    final List<FileResultThread> formater_threads = new ArrayList();
    
    final String cwd;
    final String search_string;
    final boolean ignore_case;
    final boolean use_unicode;
    final boolean interix_path_convert;
    
    public MultiThreadMain( int num_threads, String cwd, String search_string, boolean ignore_case , boolean use_unicode, boolean interix_path_convert )
    {
        this.num_threads = num_threads;
        this.cwd = cwd;
        this.search_string = search_string;
        this.ignore_case = ignore_case;
        this.use_unicode = use_unicode;
        this.interix_path_convert = interix_path_convert;
    }
    
    @Override
    public boolean fileFound(final File file)
    {        
        queue.add(file);
        return true;
    }    
    
    
    
    @Override
    void start()
    {
        for( int i = 0; i < num_threads; i++ )
        {
            FileResultThread thread = new FileResultThread(queue, cwd, search_string, ignore_case, use_unicode, interix_path_convert, getFormater());
            formater_threads.add(thread);
            thread.start();
        }   
    }    
    
    
    @Override
    void waitUntilFinnished() {

        while (!queue.isEmpty()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
            }
        }

        for (FileResultThread thread : formater_threads) {
            thread.doStop();
        }

        for (FileResultThread thread : formater_threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
            }
        }
    }
    
    
    void stop()
    {
        queue.clear();
        waitUntilFinnished();
    }

    public FormatOutput getFormater() {
        return null;
    }
 
}
