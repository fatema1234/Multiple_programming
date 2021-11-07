import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

	public static void main(String[] args) throws Exception 
    {
		if(args.length != 3)
			System.out.println("Usage: provide gmail username, gmail password, recipient. \n"
					+ "Turn ON gmail 'Access for less secure apps' setting.");
		else
			buildAndSendMessage(args[0], args[1], args[2]);
    }
    
    private static void buildAndSendMessage(String username, String pass, String recipient) throws Exception {
    	String subject = "Test Email";
    	String body = "Hello, this is a test email";
    	String host = "smtp.gmail.com";
        
        // Get system properties 
        Properties properties = System.getProperties(); 
        // Setup mail server 
        properties.setProperty("mail.smtp.host", host);           
        // SSL Port 
        properties.put("mail.smtp.port", "465");            
        // enable authentication 
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        // SSL Factory 
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        // creating Session instance referenced to  
        // Authenticator object to pass in  
        // Session.getInstance argument 
        Session session = Session.getDefaultInstance(properties, 
            new javax.mail.Authenticator() { 
                  
                // override the getPasswordAuthentication  
                // method 
                protected PasswordAuthentication  
                        getPasswordAuthentication() { 
                    return new PasswordAuthentication(username, pass); 
                } 
            });
        
        try
        {
	        // javax.mail.internet.MimeMessage class is mostly  
	        // used for abstraction. 
	        MimeMessage message = new MimeMessage(session);  
	          
	        // header field of the header. 
	        message.setFrom(new InternetAddress(username)); 
	          
	        message.addRecipient(Message.RecipientType.TO,  
	                              new InternetAddress(recipient)); 
	        message.setSubject(subject); 
	        message.setText(body); 
	        
	        Transport transport = session.getTransport();
	        transport.connect(host, username, pass);
	        message.saveChanges();
	        
	        // Send message 
	        Transport.send(message); 
	        System.out.println("Message has been sent.."); 
	        transport.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: " + e.getMessage());
            throw e;

        }
    }
}
