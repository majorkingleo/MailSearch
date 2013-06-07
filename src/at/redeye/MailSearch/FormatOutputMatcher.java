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
public class FormatOutputMatcher extends FormatOutput
{    
    public FormatOutputMatcher( String cwd, String search_string, boolean ignore_case , boolean use_unicode, boolean interix_path_convert)
    {
        super( cwd, search_string, ignore_case, use_unicode, interix_path_convert);
    }

    @Override
    public void formatOutput( String content,  File file)
    {
        final String file_name = formatFile(file);
        final Pattern patt_whole_line;
        
        if( ignore_case && use_unicode )
        {
            patt_whole_line = Pattern.compile(search_string,Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        }
        else if( ignore_case )
        {
            patt_whole_line = Pattern.compile(search_string, Pattern.CASE_INSENSITIVE);
        }
        else
        {
            patt_whole_line = Pattern.compile(search_string);
        }
               
        final Matcher matcher = patt_whole_line.matcher(content);               

        int index = 0;

        while( matcher.find() )
        {
            index = matcher.end();

            // System.out.println(file_name + ":" + countLines(content, index) + ' ' + getWholeLine(content, index));
            synchronized (System.out) {
                System.out.print(file_name);
                System.out.print(':');
                System.out.print(countLines(content, index));
                System.out.print(' ');
                System.out.print(getWholeLine(content, index));
                System.out.print('\n');
            }
        }
    }

    static String getWholeLine( String buf, int index )
    {
        int start = buf.lastIndexOf('\n', index-1);
        int end =   buf.indexOf('\n', index);

        return buf.substring(start+1, end);
    }

    static int countLines( String buf, int index )
    {
        int count = 0;

        while( index > 0 )
        {
            index = buf.lastIndexOf('\n', index-1);
            count++;
        }

        return count;
    }

}
