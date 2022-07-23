package id.ac.ui.cs.advprog.apifetcher.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostNewsDto {
  private String id;

  private String title;

  private String description;

  private String content;

  private String imageUrl;

  private String videoUrl;

  private String category;

  private String source;

  private String pubDate;

  private String providerId;
}
