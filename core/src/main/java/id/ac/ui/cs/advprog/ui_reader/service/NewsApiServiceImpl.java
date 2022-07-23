package id.ac.ui.cs.advprog.ui_reader.service;

import id.ac.ui.cs.advprog.ui_reader.model.News;
import id.ac.ui.cs.advprog.ui_reader.model.dto.PostNewsListDto;
import id.ac.ui.cs.advprog.ui_reader.repository.NewsProviderUtility;
import id.ac.ui.cs.advprog.ui_reader.repository.NewsRepository;
import id.ac.ui.cs.advprog.ui_reader.repository.UserRepository;
import id.ac.ui.cs.advprog.ui_reader.util.exception.RequestErrorException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.SneakyThrows;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NewsApiServiceImpl implements NewsApiService {

  @Autowired
  NewsRepository newsRepository;

  @Autowired
  NewsProviderUtility newsProviderUtility;

  @Autowired
  UserRepository userRepository;

  @Override
  public Iterable<News> getAllNews() {
    return newsRepository.findAll();
  }

  @Override
  public Iterable<News> createNews(PostNewsListDto postNewsListDto) throws RequestErrorException {
    postNewsListDto.validate();
    List<News> newsList = newsProviderUtility.convertPostNewsDtoToNews(postNewsListDto);
    newsProviderUtility.checkForNewNewsAndNotifySubcribers(newsList);
    newsRepository.saveAll(newsList);
    return newsList;
  }

  @Override
  public News getNewsById(String id) {
    return newsRepository.getById(id);
  }

  @Override
  public void subscribeUser(String userEmail, String providerId) throws RequestErrorException {
    var user = userRepository.findByEmail(userEmail);
    newsProviderUtility.subscribeUser(user, providerId);
  }

  @Override
  public boolean isUserSubcribed(String userEmail, String providerId) throws RequestErrorException {
    var user = userRepository.findByEmail(userEmail);
    return newsProviderUtility.isUserSubcribed(user, providerId);
  }

  @Override
  public List<News> getPopularHomeNews(List<News> allNews) {
    List<News> popularNews = new LinkedList<>();
    popularNews.add(allNews.get(0));
    for (int k = 0; k < 5; k++){
      for (News listAllNew : allNews) {
        if (popularNews.get(k) != null) {
          if (listAllNew.getImageUrl() != null && listAllNew.getTitle() != null) {
            if (!popularNews.contains(listAllNew)) {
              if (listAllNew.getViewCount() >= popularNews.get(k).getViewCount()) {
                popularNews.set(k, listAllNew);
              }
            }
          }
        } else {
          popularNews.set(k, listAllNew);
        }
      }
      if (k != 5) {
        popularNews.add(null);
      }
    }

    return popularNews;
  }

  @Override
  public List<News> getLatestHomeNews(List<News> allNews) {
    List<News> latestNews = new LinkedList<>();
    latestNews.add(allNews.get(0));
    for(var k = 0; k < 7; k++) {
      for (News allNew : allNews) {
        if (latestNews.get(k) != null) {
          var news1Date = latestNews.get(k).getPublishedDate();
          var news2Date = allNew.getPublishedDate();
          if (!latestNews.contains(allNew) && news2Date.compareTo(news1Date) < 0) {
            latestNews.set(k, allNew);
          }
        } else {
          latestNews.set(k, allNew);
        }
      }
      if (k != 7)
        latestNews.add(null);
    }
    return latestNews;
  }

  @Override
  public List<News> getNewsHomePage(String keyword, String category, String startDate, String endDate) {
    List<News> news = new LinkedList<>();
    List<News> listAllNews = newsRepository.findAll();
    if (keyword == null || keyword.isBlank()) {
      for (var k = 0; k < 10; k++) {
        if (listAllNews.get(k).getImageUrl() != null && listAllNews.get(k).getTitle() != null)
          news.add(listAllNews.get(k));
      }
    } else {
      news = searchNews(keyword);
    }
    if (category != null && !category.isBlank()) {
      news = filterNewsByCategory(news, category);
    }
    if ((startDate != null && endDate != null) && (!startDate.isBlank() || !endDate.isBlank())) {
      news = filterNewsByDateRange(news, startDate, endDate);
    }
    return news;
  }

  @Override
  public List<News> getNewsFullImage(String keyword, List<News> allNews, String category, String startDate, String endDate) {
    List<News> news = new LinkedList<>();
    List<News> listAllNews = allNews;
    if (keyword == null || keyword.isBlank()) {
      for (News allNew : allNews) {
        if (allNew.getImageUrl() != null && allNew.getTitle() != null)
          news.add(allNew);
      }
    }
    else{
      news = searchNews(keyword);
    }
    if (category != null && !category.isBlank()) {
      news = filterNewsByCategory(news, category);
    }
    if ((startDate != null && endDate != null) && (!startDate.isBlank() || !endDate.isBlank())) {
      news = filterNewsByDateRange(news, startDate, endDate);
    }
    return news;
  }

  @Override
  public List<News> searchNews(String keyword) {
    String[] keywords = keyword.split(" ");
    List<News> newsFiltered = new LinkedList<>();
    for (String key : keywords) {
      List<News> newsFounded = newsRepository.findByTitleContainingIgnoreCase(key);
      if (!newsFiltered.isEmpty()) {
        for (News berita : newsFounded) {
          if (!newsFiltered.contains(berita)) {
            newsFiltered.add(berita);
          }
        }
      } else {
        newsFiltered = newsFounded;
      }
    }

    return newsFiltered;
  }
  @Override
  public List<News> filterNewsByCategory(List<News> unfilteredNews, String category) {
    List<News> filteredNews = new LinkedList<>();
    for (News news: unfilteredNews) {
      var newsCategory = news.getCategory();
      if (category.equalsIgnoreCase(newsCategory)) {
        filteredNews.add(news);
      }
    }
    return filteredNews;
  }
  @Override @SneakyThrows
  public List<News> filterNewsByDateRange(List<News> unfilteredNews, String startDateString, String endDateString) {
    Date startDate = null;
    Date endDate = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    if (startDateString != null && !startDateString.isBlank()) {
      startDate = dateFormat.parse(startDateString);
    }
    if (endDateString != null && !endDateString.isBlank()) {
      endDate = dateFormat.parse(endDateString);
    }

    List<News> filteredNews = new LinkedList<>();
    for (News news: unfilteredNews) {
      var newsDate = news.getPublishedDate();
      if (startDate != null && endDate != null) {
        if (newsDate.after(startDate) && newsDate.before(endDate)) {
          filteredNews.add(news);
        }
      }
      else if (startDate != null) {
        if (newsDate.after(startDate)) {
          filteredNews.add(news);
        }
      }
      else {
        if (newsDate.before(endDate)) {
          filteredNews.add(news);
        }
      }
    }
    return filteredNews;
  }
}
