package id.ac.ui.cs.advprog.emailblaster.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class EmailBlasterTests {

  @Mock
  JavaMailSenderImpl mailSender;

  @InjectMocks
  EmailBlaster emailBlaster;

  @Test
  void serverConnectionTest() {
    EmailBlaster emailBlaster = EmailBlaster.getInstance();
    assertTrue(emailBlaster.isConnectedToServer());
  }

  @Test
  void serverConnectionFailedTest() throws MessagingException {
    doThrow(new MessagingException()).when(mailSender).testConnection();
    assertFalse(emailBlaster.isConnectedToServer());
  }

  @Test
  void sendMessageTest() throws MessagingException {
    EmailBlaster originalEmailBlaster = EmailBlaster.getInstance();
    JavaMailSenderImpl originalMailSender = Whitebox.getInternalState(originalEmailBlaster, "mailSender");
    when(mailSender.createMimeMessage()).thenReturn(originalMailSender.createMimeMessage());
    emailBlaster.sendMessage("kasen@gmail.com", "Subject", "Text", false);
    verify(mailSender).send(any(MimeMessage.class));
  }
}
