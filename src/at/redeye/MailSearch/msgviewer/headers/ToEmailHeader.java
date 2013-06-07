/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.redeye.MailSearch.msgviewer.headers;

import at.redeye.MailSearch.msgviewer.MailAddress;
import com.auxilii.msgparser.Message;
import java.util.List;

/**
 *
 * @author martin
 */
public class ToEmailHeader extends EmailHeader 
{
    public ToEmailHeader()
    {
        super("From");
    }

    @Override
    public void assign(Message msg, List<MailAddress> emails) 
    {       
        msg.setToEmail(emails.get(0).getEmail());
        msg.setToName(emails.get(0).getDisplayName());
    }
}
