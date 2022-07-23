package id.ac.ui.cs.advprog.ui_reader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import id.ac.ui.cs.advprog.ui_reader.model.base.BaseModel;

import java.io.Serializable;
import java.util.Date;
import lombok.*;
import javax.persistence.*;

@Generated
@Builder(builderMethodName = "newsBuilder")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name="news")
public class News extends BaseModel implements Serializable {

  @Id
  @Column(name="id", updatable = false)
  private String id;

  @Column(name="title", length = 512)
  private String title;

  @Column(name="description", columnDefinition = "TEXT")
  private String description;

  @Column(name="content", columnDefinition = "TEXT")
  private String content;

  @Column(name="image_url", length = 512)
  private String imageUrl;

  @Column(name="video_url", length = 512)
  private String videoUrl;

  @Column(name="category")
  private String category;

  @Column(name="source", nullable = false, length = 512)
  private String source;

  @Column(name="published_date")
  private Date publishedDate;

  @ManyToOne
  @JoinColumn(name="news_provider_id", nullable=false)
  @JsonIgnoreProperties({"news"})
  private NewsProvider provider;

  @Column(name="view_count")
  private int viewCount;

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public Date getPublishedDate() {
    return publishedDate;
  }

  public void setPublishedDate(Date publishedDate) {
    this.publishedDate = publishedDate;
  }

  public NewsProvider getProvider() {
    return provider;
  }

  public void setProvider(NewsProvider provider) {
    this.provider = provider;
  }

  public int getViewCount() {
    return viewCount;
  }

  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }
}
