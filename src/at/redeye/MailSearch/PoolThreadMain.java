/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.redeye.MailSearch;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author martin
 */
public class PoolThreadMain extends NoThreadMain
{
    final String cwd;
    final String search_string;
    final boolean ignore_case;
    final boolean use_unicode;
    final boolean interix_path_convert;
    final ExecutorService pool;
    
    public PoolThreadMain( String cwd, String search_string, boolean ignore_case , boolean use_unicode, boolean interix_path_convert )
    {        
        this.cwd = cwd;
        this.search_string = search_string;
        this.ignore_case = ignore_case;
        this.use_unicode = use_unicode;
        this.interix_path_convert = interix_path_convert;
        pool = Executors.newCachedThreadPool();
    }
    

    @Override
    public boolean fileFound(final File file)
    {        
        pool.execute(new FormatOutputRunnable(cwd, search_string, ignore_case, use_unicode, interix_path_convert, file));
        return true;
    }    
    
    @Override
    void start()
    {
        
    }    
    
    
    @Override
    void waitUntilFinnished() {
      pool.shutdown();
    }    
    
    void stop()
    {
        pool.shutdownNow();
    }
    
    boolean isRunning() 
    {
        return !pool.isShutdown();
    }        
}
