package id.ac.ui.cs.advprog.emailblaster.util;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailBlaster {
  private static final EmailBlaster emailBlaster = new EmailBlaster();
  private JavaMailSenderImpl mailSender;

  public EmailBlaster() {
    mailSender = new JavaMailSenderImpl();
    mailSender.setHost("mail.stevenn.my.id");
    mailSender.setPort(465);

    mailSender.setUsername("ui-reader-noreply@stevenn.my.id");
    mailSender.setPassword("ui-reader-noreply");

    var props = mailSender.getJavaMailProperties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.ssl.enable", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.ssl.checkserveridentity", "true");
  }

  public void sendMessage(String to, String subject, String text, boolean isHtml)
      throws MessagingException {
    var mimeMessage = mailSender.createMimeMessage();
    var helper = new MimeMessageHelper(mimeMessage, "utf-8");
    helper.setFrom("UI Reader Notification Service <ui-reader-noreply@stevenn.my.id>");
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(text, isHtml);
    mailSender.send(mimeMessage);
  }

  public void sendSimpleMessage(String to, String subject, String text) throws
      MessagingException {
    this.sendMessage(to, subject, text, false);
  }

  public boolean isConnectedToServer() {
    var connected = false;
    try {
      mailSender.testConnection();
      connected = true;
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    return connected;
  }

  public static EmailBlaster getInstance() {
    return emailBlaster;
  }
}
