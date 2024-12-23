package wp46927.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("patryk141476@outlook.com"); 
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }
    public void send(MimeMessagePreparator preparator) {
        try {
            javaMailSender.send(preparator);
            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
