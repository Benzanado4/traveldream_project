/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.ejbmanagers;

import it.polimi.traveldream.helper.Constants;
import it.polimi.traveldream.helper.MailConstants;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/********************************************************
 * Classes
 *********************************************************/
public class MailManager {
	
	public void sendMail(String mailAddress, String mailType, String partPackageLink) {

		final String username = "traveldreampolimi2014@gmail.com";
		final String password = "bellaperepe";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
 
		try {
			System.out.println("Sending Email to: " + mailAddress );
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("traveldreampolimi2013@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(mailAddress));
			
			
			if(mailType.equals(MailConstants.TYPE_PURCHASE)){
				message.setSubject(MailConstants.SUBJECT_PURCHASE);
				message.setText(MailConstants.MESSAGE_PURCHASE);
			}
			if(mailType.equals(MailConstants.TYPE_REGISTER_CONFIRM)){
				message.setSubject(MailConstants.SUBJECT_REGISTER_CONFIRM);
				message.setText(MailConstants.MESSAGE_REGISTER_CONFIRM);
			}
			if(mailType.equals(MailConstants.TYPE_RECIEVE_INVITATION) && partPackageLink != null){
				message.setSubject(MailConstants.SUBJECT_RECIEVE_INVITATION);
				message.setText(MailConstants.MESSAGE_RECIEVE_INVITATION + "\n\n" + partPackageLink + "&faces-redirect=true");
			}
			
			if(mailType.equals(MailConstants.TYPE_PURCHASE_GIFT)){
				message.setSubject(MailConstants.TYPE_PURCHASE_GIFT);
				message.setText(MailConstants.TYPE_PURCHASE_GIFT );
				//inserire il nome del pacchetto e l'elemento acquistato della gift associata
			}
			Transport.send(message);
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
