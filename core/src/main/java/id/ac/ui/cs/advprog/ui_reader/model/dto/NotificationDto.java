package id.ac.ui.cs.advprog.ui_reader.model.dto;

import java.util.List;
import lombok.Data;

@Data
public class NotificationDto {
  String email;
  List<String> newsUrl;

  public NotificationDto(String key, List<String> value) {
    email = key;
    newsUrl = value;
  }
}
