/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.redeye.MailSearch;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author martin
 */
public class FormatOutput
{        
    final String cwd;
    final boolean ignore_case;
    final String search_string;    
    final Pattern pattern;
    final boolean use_unicode;
    boolean interix_path_convert;    

    public FormatOutput( String cwd, String search_string, boolean ignore_case, boolean use_unicode, boolean interix_path_convert )
    {
        if( interix_path_convert )
            this.cwd = Main.winPath2Interix(cwd);
        else
            this.cwd = cwd;
        
        this.search_string = search_string;
        this.ignore_case = ignore_case;
        this.use_unicode = use_unicode;
        this.interix_path_convert = interix_path_convert;

        search_string = ".*" + search_string + ".*";

        if( ignore_case && use_unicode )
        {
            pattern = Pattern.compile(search_string,Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        }
        else if( ignore_case )
        {
            pattern = Pattern.compile(search_string, Pattern.CASE_INSENSITIVE);
        }
        else
        {
            pattern = Pattern.compile(search_string);
        }                
    }

    public boolean containsSearchPattern(final String content)
    {   
        /*
        final Matcher matcher = pattern.matcher(content);
        final boolean found = matcher.lookingAt();        
        
        return found;        
         * 
         */
        return true;
    }

    public void formatOutput( String content,  File file)
    {
        final String lines[] = content.split("\n");
     
        final String file_name = formatFile(file);

        for( int i = 0; i < lines.length; i++ )
        {
            Matcher matcher = pattern.matcher( lines[i] );

            if( matcher.matches() )
            {                                
                System.out.print(file_name);
                System.out.print(':');
                System.out.print(i + 1);
                System.out.print(' ');
                System.out.print(lines[i]);
                System.out.print('\n');                
            }
        }        
    }

    String formatFile( File file )
    {
        String file_name = file.toString();

        if( interix_path_convert )
            file_name = Main.winPath2Interix(file_name);

        // System.out.println("1: " + file_name + " 2: " + cwd);       

        if( file_name.startsWith(cwd) )
        {
           String res = file_name.substring(cwd.length());

           if( res.startsWith("/") )
                return res.substring(1);
           
           return res;
        }

        return file_name.toString();
    }
}
