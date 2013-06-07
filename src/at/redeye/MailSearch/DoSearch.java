/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.redeye.MailSearch;

import at.redeye.MailSearch.lib.FileExtFilter;
import at.redeye.MailSearch.lib.SearchForFiles;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author martin
 */
public class DoSearch 
{
    static class SearchThread extends Thread
    {
        SearchForFiles searcher;
        SearchThreadMain main;
        String cwd;
        String error;
        boolean failed = false;
        boolean working = false;
        
        public SearchThread( SearchForFiles searcher,  SearchThreadMain main , String cwd )
        {
            super( SearchThread.class.getName() );
            this.searcher = searcher;
            this.main = main;
            this.cwd = cwd;
        }                
        
        @Override
        public void run()
        {
            working = true;
            main.start();   
            
            try {
                searcher.findFiles(new File(cwd));
            } catch (FileNotFoundException ex) {
                error =  String.format("Directory %s does not exists", cwd);;
                failed = true;
            }         
            main.waitUntilFinnished();
            
            working = false;                        
        }
        
        public boolean isFailed()
        {
            return failed;
        }
        
        public String getError()
        {
            return error;
        }
        
        public boolean isWorking()
        {
            return working;
        }                            
    }
    
    MainWin mainwin;
    String search_string;
    SearchForFiles searcher;
    SearchThreadMain main;
    SearchThread search_thread;
    
    public DoSearch( MainWin mainwin, String search_string )
    {
        this.mainwin = mainwin;
        this.search_string = search_string;
                
    }
    
    public void start()
    {
        String cwd = mainwin.root.getSetup().getLocalConfig(AppConfigDefinitions.MailDirectory);
                
        main = new SearchThreadMain(mainwin, cwd, search_string, true, true, false);        
   
        FileExtFilter filter = new FileExtFilter("*,S"); 
        
        searcher = new SearchForFiles(filter, main);
        
        search_thread = new SearchThread( searcher, main, cwd );
        search_thread.start();
    }        
    
    public boolean isRunning()
    {
        return search_thread.isWorking();        
    }
    
    
    public void waitUntilFinnished()
    {
        main.waitUntilFinnished();
    }       
    
    void stop() {
        main.stop();
    }
    
}
