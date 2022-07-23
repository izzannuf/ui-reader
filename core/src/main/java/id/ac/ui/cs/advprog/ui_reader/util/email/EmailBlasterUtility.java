package id.ac.ui.cs.advprog.ui_reader.util.email;

import com.google.gson.Gson;
import id.ac.ui.cs.advprog.ui_reader.model.constant.Env;
import id.ac.ui.cs.advprog.ui_reader.model.dto.NotificationListDto;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Component;

@Component
public class EmailBlasterUtility {
  public void sendNotification(NotificationListDto notificationListDto) {
    var gson = new Gson();
    var payload = gson.toJson(notificationListDto);

    try {
      var uri = new URI(Env.EMAILBLASTERHOST.val() + "/notification");

      HttpRequest request = HttpRequest.newBuilder()
          .uri(uri)
          .header("Content-Type", "application/json")
          .POST(HttpRequest.BodyPublishers.ofString(payload))
          .build();

      var httpClient = HttpClient
          .newBuilder()
          .build();

      httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (URISyntaxException | IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }
}