package id.ac.ui.cs.advprog.apifetcher.model.dto;

import com.google.gson.annotations.SerializedName;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewsDataDto {
  public String title;

  public String link;

  public List<String> keywords;

  public List<String> creator;

  @SerializedName("video_url")
  public String videoUrl;

  public String description;

  public String content;

  public String pubDate;

  @SerializedName("image_url")
  public String imageUrl;

  @SerializedName("source_id")
  public String sourceId;

  public List<String> country;

  public List<String> category;

  public String language;

  private String getId(String sourceId, String pubDate) {
    pubDate = pubDate.replaceAll("[ \\-:]", "");
    return sourceId + "-" + pubDate;
  }

  private String parseDate(String pubDate) {
    Date date;
    try {
      date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pubDate);
    } catch (ParseException e) {
      date = new Date();
    }
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    return dateFormat.format(date);
  }

  public PostNewsDto toPostNewsDto() {
    PostNewsDto res = new PostNewsDto();
    res.setTitle(this.title);
    res.setDescription(this.description);
    res.setContent(this.content);
    res.setImageUrl(this.imageUrl);
    res.setVideoUrl(this.videoUrl);

    if (!this.category.isEmpty()) {
      res.setCategory(this.category.get(0));
    }

    res.setSource(this.link);
    res.setId(getId(this.sourceId, this.pubDate));

    res.setProviderId(this.sourceId);

    res.setPubDate(parseDate(this.pubDate));
    return res;
  }
}
