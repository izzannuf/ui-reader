package id.ac.ui.cs.advprog.apifetcher.model.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewsApiOrgResponseDto {
  String status;
  int totalResult;
  List<NewsApiOrgDto> articles;
}
