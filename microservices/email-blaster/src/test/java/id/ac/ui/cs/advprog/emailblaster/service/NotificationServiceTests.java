package id.ac.ui.cs.advprog.emailblaster.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import id.ac.ui.cs.advprog.emailblaster.model.dto.NotificationDto;
import id.ac.ui.cs.advprog.emailblaster.model.dto.NotificationListDto;
import id.ac.ui.cs.advprog.emailblaster.util.EmailBlaster;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class NotificationServiceTests {

  @Mock
  NotificationServiceImpl notificationService;

  private static MockedStatic<EmailBlaster> mockedSettings;

  @BeforeEach
  public void prepare() {
    notificationService = new NotificationServiceImpl();
  }

  @Test
  void testParseEmailContent() throws Exception {
    List<String> urls = new ArrayList<>();
    urls.add("www.google.com");
    urls.add("www.cnbc.com");

    Class[] types = new Class[]{List.class};
    String result = Whitebox.invokeMethod(notificationService, "parseEmailContent", types, urls);

    assertEquals(
        "Some news provider have updated their news\n" +
            "www.google.com\n" +
            "www.cnbc.com",
        result
    );
  }

  @Test
  void testSentNotification() throws MessagingException, InterruptedException {
    EmailBlaster emailBlaster = spy(new EmailBlaster());
    mockedSettings = mockStatic(EmailBlaster.class);
    when(EmailBlaster.getInstance()).thenReturn(emailBlaster);
    doNothing().when(emailBlaster).sendMessage(anyString(), anyString(), anyString(), anyBoolean());

    NotificationListDto notificationListDto = new NotificationListDto();
    List<NotificationDto> dtos = new ArrayList<>();

    NotificationDto dto1 = new NotificationDto("alice@gmail.com", List.of("box.com", "fox.com"));
    NotificationDto dto2 = new NotificationDto("bob@my.id", List.of("google.com"));
    NotificationDto dto3 = new NotificationDto("chad@tuvalu.tv", List.of("atcoder.com", "codeforces.com"));

    dtos.add(dto1);
    dtos.add(dto2);
    dtos.add(dto3);

    notificationListDto.setNotificationDtos(dtos);
    notificationService.sentNotification(notificationListDto);
    Thread.sleep(100);

    for (NotificationDto dto : dtos) {
      verify(emailBlaster, atLeastOnce()).sendMessage(
          argThat(x -> x.equals(dto.getEmail())),
          anyString(),
          argThat(x -> {
            for (String url : dto.getNewsUrl()) {
              assertTrue(x.contains(url));
            }
            return true;
          }),
          anyBoolean()
      );
    }
    mockedSettings.close();
  }
}
