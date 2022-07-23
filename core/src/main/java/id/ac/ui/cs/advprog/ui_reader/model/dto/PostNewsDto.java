package id.ac.ui.cs.advprog.ui_reader.model.dto;

import com.sun.istack.NotNull;
import id.ac.ui.cs.advprog.ui_reader.model.News;
import id.ac.ui.cs.advprog.ui_reader.model.NewsProvider;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostNewsDto {
  @NotNull
  private String id;

  @NotNull
  private String title;

  private String description;

  private String content;

  private String imageUrl;

  private String videoUrl;

  @NotNull
  private String category;

  @NotNull
  private String source;

  @NotNull
  private Date pubDate;

  @NotNull
  private String providerId;

  public News toNewsWithProvider(NewsProvider newsProvider) {
    return new News(id, title, description, content, imageUrl, videoUrl, category, source, pubDate, newsProvider, 0);
  }
}
