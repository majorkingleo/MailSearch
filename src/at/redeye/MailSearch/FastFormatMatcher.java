/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.redeye.MailSearch;

/**
 *
 * @author martin
 */
public class FastFormatMatcher extends FormatOutputMatcher
{
    final String search_string_lower;
    
    public FastFormatMatcher( String cwd, String search_string, boolean ignore_case , boolean use_unicode, boolean interix_path_convert)
    {
        super( cwd, search_string, ignore_case, use_unicode, interix_path_convert);
        search_string_lower = search_string.toLowerCase();
        
        if( search_string_lower == null ) {
                throw new NullPointerException("search_string_lower is null");
        }
    }
    
    @Override
    public boolean containsSearchPattern(final String content)
    {   
        if( content == null )
            return false;
        
        if( ignore_case) {
            
            if( content.toLowerCase() == null ) {            
                throw new NullPointerException("Content.toLowerCase() is null");
            }
            
            if( search_string_lower == null ) {
                throw new NullPointerException("search_string_lower is null");
            }
            
          if( content.toLowerCase().indexOf(search_string_lower) >= 0 )
            return true;
        } else {        
          if( content.indexOf(search_string) >= 0 )
            return true;        
        }
        return false;
    }
    
}
