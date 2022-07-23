package id.ac.ui.cs.advprog.apifetcher.model.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class NewsApiOrgDto {
  Map<String, String> source;
  String author;
  String title;
  String description;
  String url;
  String urlToImage;
  String publishedAt;
  String content;

  private String getId(String sourceId, String pubDate) {
    pubDate = pubDate.replaceAll("[ \\-:TZ]", "");
    return sourceId + "-" + pubDate;
  }

  private String parseDate(String pubDate) {
    Date date;
    try {
      pubDate = pubDate.replaceAll("[TZ]", " ");
      date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").parse(pubDate);
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
    res.setImageUrl(this.urlToImage);
    res.setVideoUrl(null);
    res.setCategory(null);

    res.setSource(this.url);
    res.setId(getId(this.source.get("id"), this.publishedAt));

    res.setProviderId(this.source.get("id"));
    res.setPubDate(parseDate(this.publishedAt));

    return res;
  }

}
