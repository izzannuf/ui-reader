package id.ac.ui.cs.advprog.emailblaster.service;

import id.ac.ui.cs.advprog.emailblaster.model.dto.NotificationDto;
import id.ac.ui.cs.advprog.emailblaster.model.dto.NotificationListDto;
import id.ac.ui.cs.advprog.emailblaster.util.EmailBlaster;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

  private String parseEmailContent(List<String> urls) {
    var res = new StringBuilder("Some news provider have updated their news");
    for (String url : urls) {
      res.append("\n").append(url);
    }
    return res.toString();
  }

  private void sendMessage(String email, String subject, String text) {
    var emailBlaster = EmailBlaster.getInstance();
    CompletableFuture.supplyAsync(() -> {
      try {
        emailBlaster.sendSimpleMessage(email, subject, text);
      } catch (MessagingException e) {
        e.printStackTrace();
      }
      return 0;
    });
  }

  @Override
  public void sentNotification(NotificationListDto notificationListDto) {
    for (NotificationDto notificationDto : notificationListDto.getNotificationDtos()) {
      sendMessage(
          notificationDto.getEmail(),
          "News Update Notification",
          parseEmailContent(notificationDto.getNewsUrl())
      );
    }
  }
}
