package msku.ceng.madlab.roxid;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    public MailSender() {
    }

    public void sendMail(String UserEmail, String EmailSubject, String EmailText){
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", Appdata.Gmail_Host);
        properties.put("mail.smtp,port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Appdata.Sender_Email, Appdata.Sender_Email_Password);
            }

        });
        MimeMessage message = new MimeMessage(session);
        try {
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(UserEmail));
            message.setSubject(EmailSubject);
            message.setText(EmailText);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(message);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
