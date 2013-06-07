/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.redeye.MailSearch.msgviewer.headers;

import com.auxilii.msgparser.Message;

/**
 *
 * @author martin
 */
public abstract class HeaderParser 
{    
        final String header;
        
        public HeaderParser( String header )
        {
            this.header = header;
        }
        
        public String getHeader()
        {
            return header;
        }
        
        public abstract void parse(Message message, String line) throws Exception;
}
