package iscteiul.ista.emailfeature;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
    @Autowired
    private EmailRepository emailRepository;

    public Email saveEmail(Email email) {
        return emailRepository.save(email);
    }

    public void sendEmail(Email email) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.tuservidor.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("usuario", "contraseña");
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(email.getFromAddress()));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getToAddress()));
        msg.setSubject(email.getSubject());
        msg.setText(email.getMessage());

        Transport.send(msg);
    }
}
