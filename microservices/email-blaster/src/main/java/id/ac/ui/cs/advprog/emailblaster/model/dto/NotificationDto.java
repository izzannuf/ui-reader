package id.ac.ui.cs.advprog.emailblaster.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
  String email;
  List<String> newsUrl;
}
