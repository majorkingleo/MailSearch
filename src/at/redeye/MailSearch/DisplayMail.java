/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.redeye.MailSearch;

import com.auxilii.msgparser.Message;
import java.io.File;
import javax.swing.JLabel;

/**
 *
 * @author moberza
 */
public class DisplayMail extends JLabel
{
    private Message message;
    private File file;

    public DisplayMail(Message message, String search_string, File file) {
        this.message = message;
        this.file = file;

        StringBuilder line = new StringBuilder();

        line.append("<html><body>&nbsp;");
        
        line.append("Von: <b>");
        line.append( message.getFromName() );
        line.append("</b> An: <b>");        
        line.append( message.getToName() );
        line.append("</b> am: <i>");        
        line.append( message.getDate() );
        line.append("</i>");
        
        line.append("<blockquote>");        
        line.append(message.getSubject());               
        
        final String message_body =  message.getBodyText();
        
        if(message_body.contains(search_string) )
        {
            line.append("<blockquote>");   
            
            line.append(message_body.replaceAll("\n", "<br/>"));
            
            line.append("</blockquote>");
        }
        
        line.append("</blockquote>");
        line.append("<br/>");
        line.append("</body></html>");

        setText( line.toString() );

        setOpaque(true);
    }
    
    public Message getMessage()
    {
        return message;
    }
    
    public File getFile()
    {
        return file;
    }
}
