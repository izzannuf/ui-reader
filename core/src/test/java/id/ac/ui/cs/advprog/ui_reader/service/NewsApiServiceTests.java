package id.ac.ui.cs.advprog.ui_reader.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;

import id.ac.ui.cs.advprog.ui_reader.model.News;
import id.ac.ui.cs.advprog.ui_reader.model.User;
import id.ac.ui.cs.advprog.ui_reader.model.dto.PostNewsListDto;
import id.ac.ui.cs.advprog.ui_reader.repository.NewsProviderUtility;
import id.ac.ui.cs.advprog.ui_reader.repository.NewsRepository;
import id.ac.ui.cs.advprog.ui_reader.repository.UserRepository;
import id.ac.ui.cs.advprog.ui_reader.util.exception.RequestErrorException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@PrepareForTest({NewsApiService.class, NewsApiServiceImpl.class})
@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
class NewsApiServiceTests {

  NewsApiService newsApiService;

  @Mock
  NewsRepository newsRepository;

  @Mock
  NewsProviderUtility newsProviderUtility;

  @Mock
  UserRepository userRepository;

  List<News> dummyAllNews = new LinkedList<>();
  List<News> dummyPopulerNews = new LinkedList<>();
  List<News> dummyLatestNews = new LinkedList<>();
  List<News> dummyAllNewsFullImage = new LinkedList<>();
  List<News> dummyNewsFound =  new LinkedList<>();

  @BeforeEach
  public void prepare() {
    newsApiService = spy(new NewsApiServiceImpl(newsRepository, newsProviderUtility, userRepository));
  }

  @BeforeEach
  public void setUp(){
    News news1 = Whitebox.newInstance(News.class);
    News news2 = Whitebox.newInstance(News.class);
    News news3 = Whitebox.newInstance(News.class);
    News news4 = Whitebox.newInstance(News.class);
    News news5 = Whitebox.newInstance(News.class);
    News news6 = Whitebox.newInstance(News.class);
    News news7 = Whitebox.newInstance(News.class);

    news1.setId("0");
    news1.setTitle("Proyek Akhir Adpro");
    news1.setViewCount(1);
    news1.setPublishedDate(new Date(2022, 01, 15));
    news2.setId("1");
    news2.setTitle("Proyek Akhir Sister");
    news2.setViewCount(2);
    news2.setPublishedDate(new Date(2022, 02, 15));
    news3.setId("2");
    news3.setTitle("Proyek Akhir TBA");
    news3.setImageUrl("tba.jpg");
    news3.setViewCount(3);
    news3.setPublishedDate(new Date(2022, 03, 15));
    news4.setId("3");
    news4.setTitle("Proyek Akhir Basdat");
    news4.setImageUrl("basdat.jpg");
    news4.setViewCount(4);
    news4.setPublishedDate(new Date(2022, 04, 15));
    news5.setId("4");
    news5.setTitle("Proyek Akhir SDA");
    news5.setImageUrl("sda.jpg");
    news5.setViewCount(5);
    news5.setPublishedDate(new Date(2022, 05, 15));
    news6.setId("5");
    news6.setTitle("Proyek Akhir Statprob");
    news6.setImageUrl("statprob.jpg");
    news6.setViewCount(6);
    news6.setPublishedDate(new Date(2022, 06, 15));
    news7.setId("6");
    news7.setTitle("Proyek Akhir DDP");
    news7.setImageUrl("ddp.jpg");
    news7.setViewCount(7);
    news7.setPublishedDate(new Date(2022, 07, 15));

    dummyAllNews.add(news1);
    dummyAllNews.add(news2);
    dummyAllNews.add(news3);
    dummyAllNews.add(news4);
    dummyAllNews.add(news5);
    dummyAllNews.add(news6);
    dummyAllNews.add(news7);

    dummyPopulerNews.add(news7);
    dummyPopulerNews.add(news6);
    dummyPopulerNews.add(news5);
    dummyPopulerNews.add(news4);
    dummyPopulerNews.add(news3);

    dummyLatestNews.add(news7);
    dummyLatestNews.add(news6);
    dummyLatestNews.add(news5);
    dummyLatestNews.add(news4);
    dummyLatestNews.add(news3);

    dummyAllNewsFullImage.add(news3);
    dummyAllNewsFullImage.add(news4);
    dummyAllNewsFullImage.add(news5);
    dummyAllNewsFullImage.add(news6);
    dummyAllNewsFullImage.add(news7);

    dummyNewsFound.add(news7);
  }

  @Test
  void testGetAllNews() {
    when(newsRepository.findAll()).thenReturn(null);
    newsApiService.getAllNews();
    verify(newsRepository).findAll();
  }


  @Test
  void testCreateNews() throws RequestErrorException {
    PostNewsListDto dto = spy(Whitebox.newInstance(PostNewsListDto.class));
    List<News> newsList = new ArrayList<>();

    doNothing().when(dto).validate();
    when(newsProviderUtility.convertPostNewsDtoToNews(dto)).thenReturn(newsList);
    doReturn(null).when(newsRepository).saveAll(newsList);
    assertEquals(newsApiService.createNews(dto), newsList);

    verify(dto).validate();
    verify(newsProviderUtility).checkForNewNewsAndNotifySubcribers(argThat(x -> {
      assertEquals(newsList, x);
      return true;
    }));
    verify(newsRepository).saveAll(argThat(x -> {
      assertEquals(newsList, x);
      return true;
    }));
  }

  @Test
  void testGetNewsById() {
    News news = Whitebox.newInstance(News.class);
    news.setId("news");

    when(newsRepository.getById("news")).thenReturn(news);
    assertEquals(news, newsApiService.getNewsById("news"));
  }

  @Test
  void testSubscribeUser() throws RequestErrorException {
    String email = "user@foo.id";
    String providerId = "provider";
    User user = Whitebox.newInstance(User.class);
    user.setEmail("user@foo.id");

    when(userRepository.findByEmail(email)).thenReturn(user);
    doNothing().when(newsProviderUtility).subscribeUser(user, providerId);

    newsApiService.subscribeUser(email, providerId);
    verify(newsProviderUtility).subscribeUser(user, providerId);
  }

  @Test
  void testIsUserSubcribed() throws RequestErrorException {
    String email = "user@foo.id";
    String providerId = "provider";
    User user = Whitebox.newInstance(User.class);
    user.setEmail("user@foo.id");

    when(userRepository.findByEmail(email)).thenReturn(user);
    when(newsProviderUtility.isUserSubcribed(user, providerId)).thenReturn(true);

    assertTrue(newsApiService.isUserSubcribed(email, providerId));
    verify(newsProviderUtility).isUserSubcribed(user, providerId);
  }

  @Test
  void testGetPopulerHomeNews() {

    when(newsApiService.getPopularHomeNews(dummyAllNews)).thenReturn(dummyPopulerNews);

    List<News> newsPopuler = newsApiService.getPopularHomeNews(dummyAllNews);

    assertIterableEquals(newsPopuler, dummyPopulerNews);
  }

  @Test
  void testGetLatestHomeNews(){
    when(newsApiService.getLatestHomeNews(dummyAllNews)).thenReturn(dummyLatestNews);

    List<News> newsLatest = newsApiService.getLatestHomeNews(dummyAllNews);

    assertIterableEquals(newsLatest, dummyLatestNews);

  }
  @Test
  void testGetNewsFullImage(){
    String keyword = "Proyek";
    when(newsApiService.getNewsFullImage(keyword,dummyAllNews,null,null,null)).thenReturn(dummyAllNewsFullImage);

    List<News> newsFullImage = newsApiService.getNewsFullImage(keyword,dummyAllNews,null,null,null);

    assertIterableEquals(newsFullImage, dummyAllNewsFullImage);
  }

  @Test
  void testSearchNews(){
    String keyword = "DDP";
    when(newsApiService.searchNews(keyword)).thenReturn(dummyNewsFound);

    List<News> newsFound = newsApiService.searchNews(keyword);

    assertIterableEquals(newsFound, dummyNewsFound);
  }
}