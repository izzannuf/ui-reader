package id.ac.ui.cs.advprog.ui_reader.service;

import id.ac.ui.cs.advprog.ui_reader.model.News;
import id.ac.ui.cs.advprog.ui_reader.model.dto.PostNewsListDto;
import id.ac.ui.cs.advprog.ui_reader.util.exception.RequestErrorException;

import java.util.List;

public interface NewsApiService {
  Iterable<News> getAllNews();

  Iterable<News> createNews(PostNewsListDto postNewsListDto) throws RequestErrorException;

  News getNewsById(String id);

  boolean isUserSubcribed(String userEmail, String providerId) throws RequestErrorException;

  void subscribeUser(String userEmail, String providerId) throws RequestErrorException;

  List<News> getPopularHomeNews(List<News> allNews);

  List<News> getLatestHomeNews(List<News> allNews);

  List<News> getNewsHomePage(String keyword, String category, String startDate, String endDate);

  List<News> getNewsFullImage(String keyword, List<News> allNews, String category, String startDate, String endDate);

  List<News> searchNews(String keyword);

  List<News> filterNewsByCategory(List<News> unfilteredNews, String category);

  List<News> filterNewsByDateRange(List<News> unfilteredNews, String startDateString, String endDateString);
}
