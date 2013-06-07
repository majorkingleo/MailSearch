/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.redeye.MailSearch;

import at.redeye.MailSearch.lib.FileExtFilter;
import at.redeye.MailSearch.lib.SearchForFiles;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author martin
 */
public class Main 
{
    static final String SUA_PREFIX = "C:\\Windows\\SUA";

    String search_string;    
    final String args[];
    boolean from_interix = false;    
    final long start_time;

    public String getParam(String name) {
        return getParam(args, name);
    }

    public static String getParam(String args[], String name) {
        return getParam(args, name, null);
    }

    public static String getParam(String args[], String name, String default_value) {
        boolean next = false;

        for (String arg : args) {
            if (next) {
                return arg;
            } else if (arg.equalsIgnoreCase("-" + name)) {
                next = true;
            }
        }

        return default_value;
    }

    String getExprParam(String name) {
        boolean next = false;

        StringBuilder res = new StringBuilder();

        for (String arg : args) {
            if (next) {
                if( arg.equalsIgnoreCase("-i") )
                    continue;

                if( res.length() > 0 )
                    res.append(' ');

                res.append(arg);

            } else if (arg.equalsIgnoreCase("-" + name)) {
                next = true;
            }
        }

        if( res.length() > 0 )
            return res.toString();
        
        return null;
    }

    public boolean getFlag( String name )
    {
        return getFlag(args,name);
    }

    public static boolean getFlag(String args[], String name) {
        boolean next = false;

        for (String arg : args) {
            if (arg.equalsIgnoreCase("-" + name)) {
                return true;
            }
        }

        return false;
    }

    public Main( String args[] )
    {
       this.args = args;       
       start_time = System.currentTimeMillis();
    }

    public String get_help()
    {
        return 
            "Usage:\n" +
                "   -cwd        Working directory (required)\n" +
                "   -ext        '*.c,*.h,..'      (required)\n" +
                "   -expr       Expression        (required)\n" +
                "   -i          Incasesesitive (optional)\n" +
                "   -debug      write debugging messages (optional)\n" +
                "   -interix    convert paths to interix format /dev/fs/C to c:\\ and reverse\n" +
                "   -nothreads  Single Threaded (optional)\n" +
                "   -maxthreads NUMBER (default 4)(optional)\n" +
                "   -stats      print statistics (optional)\n" +
                "   -pool       use thread pool (optional)\n";
    }

    void exit_help( String message )
    {        
        System.err.println(get_help());
        System.err.print("\n" + "Error:" + "\n\t");
        System.err.println(message);
        System.exit(1);
    }

    public void run()
    {
        String cwd = getParam("cwd");
        String ext = getParam("ext");
        search_string = getExprParam("expr");
        boolean ignoreCase = getFlag("i");
        from_interix = getFlag("interix");
        boolean nothreads = getFlag("nothreads");
        String s_maxthreads = getParam("maxthreads");
        boolean print_stats = getFlag("stats");
        boolean use_thread_pool = getFlag("pool");
        
        if( cwd == null || ext == null || search_string == null )
          exit_help("Param cwd or ext missing");

        FileExtFilter filter = new FileExtFilter(ext);        

        boolean is_ascii = true;

        for( int i = 0; i < search_string.length(); i++ ) {
            if( search_string.charAt(i) > 127 ) {
                is_ascii = false;
                break;
            }
        }

        String cwd_orig = cwd;

        if( from_interix )
            cwd = interix2winPath(cwd);     
        
        File fcwd = new File(cwd);
        
        try {
            if( !fcwd.getAbsolutePath().equals(fcwd.getCanonicalPath()) )
            {
                // wir sind in einem Verzeichnis das irgendwo einen Symbolischen 
                // Link drinnen hat. Alse folend wir dem Link, weil die DateiSuche
                // haßt Symbolisch Links wie die Pest und folgt denen nicht.
                
                cwd = fcwd.getCanonicalPath();
                
                if( from_interix )
                    cwd_orig = winPath2Interix(cwd);
                else
                    cwd_orig = cwd;
            }
        } catch( IOException ex ) {
            
        }
               
        
        NoThreadMain main = null;
        
        if( nothreads ) {            
            main = new NoThreadMain( new FastFormatMatcher(cwd_orig, search_string, ignoreCase, !is_ascii, from_interix) );
        } else if( use_thread_pool || (s_maxthreads == null || s_maxthreads.trim().isEmpty() ) ) {
            main = new PoolThreadMain(cwd_orig, search_string, ignoreCase, !is_ascii, from_interix);
        } else {
            
            int num_threads = 3;
        
            try {
                num_threads = Integer.parseInt(s_maxthreads) - 1;

                if (num_threads <= 1) {
                    num_threads = 1;
                }

            } catch (NumberFormatException ex) {
                num_threads = 3;
            }

            main = new MultiThreadMain(num_threads, cwd_orig, search_string, ignoreCase, !is_ascii, from_interix);
        }
        
        SearchForFiles searcher = new SearchForFiles(filter, main);               
        
        main.start();

        try {
            searcher.findFiles(new File(cwd));
        } catch( FileNotFoundException ex ) {
            exit_help(String.format("Directory %s does not exists",cwd));
        }
        
        main.waitUntilFinnished();  
        
        if( print_stats ) {                                    
            printStats();
        }
    }
    
    void printStats()
    {
        long duration = System.currentTimeMillis() - start_time;
        
        if( duration > 2000 )
            System.out.println(String.format("runtime: %3fs", duration / 1000.0 ));
        else
            System.out.println(String.format("runtime: %dms", duration ));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {        
        Main main = new Main(args);
        main.run();                
    }    

    static String interix2winPath( final String path )
    {
        if( path.startsWith("/dev/fs") )
        {
            final String parts[] = path.split("/");
            final StringBuilder res = new StringBuilder();

            res.append(parts[3]);
            res.append(":/");

            for( int i = 4; i < parts.length; i++ )
            {
                if( i > 4 )
                    res.append('/');
                
                res.append(parts[i]);
            }

            return res.toString();
        } else {
            final String parts[] = path.split("/");
            final StringBuilder res = new StringBuilder();

            res.append("C:/Windows/SUA");

            for( int i = 0; i < parts.length; i++ )
            {
                res.append('/');
                res.append(parts[i]);
            }

            return res.toString();
        }
    }

    static String winPath2Interix( final String path )
    {
        // System.out.println("path: " + path );


        if( path.length() > SUA_PREFIX.length() && path.substring(0,SUA_PREFIX.length()).equalsIgnoreCase(SUA_PREFIX) )
        {
             String res = path.substring(SUA_PREFIX.length());
             res = res.replaceAll("\\\\", "/");
             return res;
        }
        else
        {
            String res = path.replaceAll("\\\\", "/");
            res = res.replaceAll("^(.+):/(.*)", "/dev/fs/$1/$2" );
            return res;
        }
    }

}
