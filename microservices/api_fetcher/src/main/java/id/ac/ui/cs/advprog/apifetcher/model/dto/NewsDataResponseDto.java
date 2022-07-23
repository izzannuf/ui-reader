package id.ac.ui.cs.advprog.apifetcher.model.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewsDataResponseDto {
  public String status;
  public int totalResults;
  public List<NewsDataDto> results;
  public int nextPage;
}
