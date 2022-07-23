package id.ac.ui.cs.advprog.emailblaster.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import id.ac.ui.cs.advprog.emailblaster.model.dto.NotificationDto;
import id.ac.ui.cs.advprog.emailblaster.model.dto.NotificationListDto;
import id.ac.ui.cs.advprog.emailblaster.service.NotificationService;
import id.ac.ui.cs.advprog.emailblaster.service.NotificationServiceImpl;
import id.ac.ui.cs.advprog.emailblaster.util.EmailBlaster;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = NotificationController.class)
class NotificationControllerTests {
  @Autowired
  private MockMvc mockMvc;

  private NotificationListDto notificationListDto;

  @TestConfiguration
  static class AdditionalConfig {
    @Bean
    public NotificationService notificationService() {
      return new NotificationServiceImpl();
    }
  }

  @BeforeEach
  void prepareDto() {
    notificationListDto = new NotificationListDto();
    List<NotificationDto> dtos = new ArrayList<>();

    NotificationDto dto1 = new NotificationDto("alice@gmail.com", List.of("box.com", "fox.com"));
    NotificationDto dto2 = new NotificationDto("bob@my.id", List.of("google.com"));
    NotificationDto dto3 = new NotificationDto("chad@tuvalu.tv", List.of("atcoder.com", "codeforces.com"));

    dtos.add(dto1);
    dtos.add(dto2);
    dtos.add(dto3);

    notificationListDto.setNotificationDtos(dtos);
  }

  @Test
  void testPostArticle() throws Exception {
    EmailBlaster emailBlaster = spy(new EmailBlaster());
    MockedStatic<EmailBlaster> mockedSettings = mockStatic(EmailBlaster.class);
    when(EmailBlaster.getInstance()).thenReturn(emailBlaster);
    doNothing().when(emailBlaster).sendMessage(anyString(), anyString(), anyString(), anyBoolean());

    Gson gson = new Gson();

    mockMvc.perform(post("/notification")
            .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(notificationListDto)))
        .andExpect(status().isOk());
    Thread.sleep(100);

    for (NotificationDto dto : notificationListDto.getNotificationDtos()) {
      verify(emailBlaster).sendMessage(
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
