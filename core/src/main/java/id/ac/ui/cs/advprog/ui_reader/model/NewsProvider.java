package id.ac.ui.cs.advprog.ui_reader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.*;

@Generated
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="news_provider")
@EqualsAndHashCode(exclude = "news")
public class NewsProvider implements Serializable {

  @Id
  @Column(name="id", updatable = false)
  private String id;

  @OneToMany(mappedBy="provider")
  @JsonIgnoreProperties({"provider"})
  private Set<News> news;

  @ManyToMany(mappedBy="subscription")
  @JsonIgnoreProperties({"subscription"})
  private Set<User> subscribedUser = new HashSet<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Set<News> getNews() {
    return news;
  }

  public void setNews(Set<News> news) {
    this.news = news;
  }

  public Set<User> getSubscribedUser() {
    return subscribedUser;
  }

  public void setSubscribedUser(Set<User> subscribedUser) {
    this.subscribedUser = subscribedUser;
  }
}
