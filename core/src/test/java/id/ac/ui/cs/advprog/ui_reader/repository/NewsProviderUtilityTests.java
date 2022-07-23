package id.ac.ui.cs.advprog.ui_reader.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import com.sun.istack.NotNull;
import id.ac.ui.cs.advprog.ui_reader.model.News;
import id.ac.ui.cs.advprog.ui_reader.model.NewsProvider;
import id.ac.ui.cs.advprog.ui_reader.model.User;
import id.ac.ui.cs.advprog.ui_reader.model.dto.NotificationDto;
import id.ac.ui.cs.advprog.ui_reader.model.dto.PostNewsDto;
import id.ac.ui.cs.advprog.ui_reader.model.dto.PostNewsListDto;
import id.ac.ui.cs.advprog.ui_reader.util.email.EmailBlasterUtility;
import id.ac.ui.cs.advprog.ui_reader.util.exception.RequestError;
import id.ac.ui.cs.advprog.ui_reader.util.exception.RequestErrorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.http.HttpStatus;


@PrepareForTest(NewsProviderUtility.class)
@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
class NewsProviderUtilityTests {

  @Spy
  @InjectMocks
  NewsProviderUtility newsProviderUtility;

  @Mock
  NewsRepository newsRepository;

  @Mock
  NewsProviderRepository newsProviderRepository;

  @Mock
  UserRepository userRepository;

  @Mock
  EmailBlasterUtility emailBlasterUtility;

  final String dummyProviderId = "news-provider";
  NewsProvider dummyProvider;

  @BeforeEach
  public void setUp() {
    this.newsProviderUtility = spy(new NewsProviderUtility(newsProviderRepository, newsRepository, userRepository, emailBlasterUtility));
    this.dummyProvider = spy(new NewsProvider("news-provider", Collections.emptySet(), Collections.emptySet()));
  }

  @Test
  void getOrCreateProviderTestCreateProvvider() {
    when(newsProviderRepository.existsById(anyString())).thenReturn(false);
    newsProviderUtility.getOrCreateProvider(dummyProviderId);

    verify(newsProviderRepository, times(1)).save(argThat(x -> {
      assertEquals(dummyProviderId,x.getId() );
      assertEquals(Collections.emptySet(), x.getNews());
      assertEquals(Collections.emptySet(),x.getSubscribedUser() );
      return true;
    }));
  }

  @Test
  void getOrCreateProviderTestGetProvider() {
    when(newsProviderRepository.existsById(anyString())).thenReturn(true);
    when(newsProviderRepository.findById(dummyProviderId)).thenReturn(Optional.of(dummyProvider));
    NewsProvider takenProvider = newsProviderUtility.getOrCreateProvider(dummyProviderId);

    verify(newsProviderRepository, times(0)).save(any());

    assertEquals(dummyProvider, takenProvider);
  }

  @Test
  void convertPostNewsDtoToNewsTest() {
    Date date = new Date();
    PostNewsDto postNewsDto1 = new PostNewsDto("news1", "Title 1", "Description 1", "Content 1", "Image 1", "Video 1", "Top", "Source", date, dummyProviderId);
    PostNewsDto postNewsDto2 = new PostNewsDto("news2", "Title 2", "Description 2", "Content 2", "Image 2", "Video 2", "Tech", "Source", date, dummyProviderId);
    PostNewsDto postNewsDto3 = new PostNewsDto("news3", "Title 3", "Description 3", "Content 3", "Image 3", "Video 3", "null", "Source", date, "new-news-provider");

    when(newsProviderRepository.existsById(dummyProviderId)).thenReturn(true);
    when(newsProviderRepository.existsById("new-news-provider")).thenReturn(false);
    when(newsProviderRepository.findById(dummyProviderId)).thenReturn(Optional.of(dummyProvider));

    PostNewsListDto postNewsListDto = new PostNewsListDto(List.of(postNewsDto1, postNewsDto2, postNewsDto3));

    List<News> newsList = newsProviderUtility.convertPostNewsDtoToNews(postNewsListDto);

    assertEquals(3, newsList.size());

    InOrder inOrder = inOrder(newsProviderRepository);

    inOrder.verify(newsProviderRepository, times(2)).findById(dummyProviderId);
    inOrder.verify(newsProviderRepository, times(1)).save(argThat( x -> {
      assertEquals("new-news-provider", x.getId());
      assertEquals(Collections.emptySet(),x.getNews() );
      assertEquals(Collections.emptySet(),x.getSubscribedUser());
      return true;
    }));
  }

  @Test
  void getNewsProviderTestNotExist() {
    when(newsProviderRepository.findById(dummyProviderId)).thenReturn(Optional.empty());
    try {
      newsProviderUtility.getNewsProvider(dummyProviderId);
      fail("Expected Not Found RequestErrorException");
    } catch (RequestErrorException e) {
      assertEquals(HttpStatus.NOT_FOUND, e.getResponseEntity().getStatusCode());
    }
  }

  @Test
  void getNewsProviderTestExist() throws RequestErrorException {
    when(newsProviderRepository.findById(dummyProviderId)).thenReturn(Optional.of(dummyProvider));
    NewsProvider takenProvider = newsProviderUtility.getNewsProvider(dummyProviderId);
    assertEquals(dummyProvider, takenProvider);
  }

  @Test
  void isUserSubcribedTest() throws RequestErrorException {
    User user1 = new User();
    User user2 = new User();
    user1.setId(1L);
    user2.setId(2L);

    NewsProvider provider = new NewsProvider(dummyProviderId, Collections.emptySet(), Set.of(user1));

    when(newsProviderRepository.findById(dummyProviderId)).thenReturn(Optional.of(provider));

    assertTrue(newsProviderUtility.isUserSubcribed(user1, dummyProviderId));
    assertFalse(newsProviderUtility.isUserSubcribed(user2, dummyProviderId));
  }

  @Test
  void subcribeUserTestUnsubcribeUser() throws RequestErrorException {
    NewsProvider provider = new NewsProvider(dummyProviderId, Collections.emptySet(), Collections.emptySet());

    User user1 = new User();
    user1.setId(1L);

    List<NewsProvider> newsProviders = new ArrayList<>();
    newsProviders.add(provider);

    user1.setSubscription(newsProviders);
    provider.setSubscribedUser(Set.of(user1));

    when(newsProviderRepository.findById(dummyProviderId)).thenReturn(Optional.of(provider));

    newsProviderUtility.subscribeUser(user1, dummyProviderId);

    verify(userRepository, times(1)).save(argThat( x -> {
      List<NewsProvider> theProviders = x.getSubscription();
      assertEquals(0, theProviders.size());
      return true;
    }));
  }

  @Test
  void subcribeUserTestSubcribeUser() throws RequestErrorException {
    NewsProvider provider = new NewsProvider(dummyProviderId, Collections.emptySet(), Collections.emptySet());

    User user1 = new User();
    user1.setId(1L);
    user1.setSubscription(new ArrayList<>());

    when(newsProviderRepository.findById(dummyProviderId)).thenReturn(Optional.of(provider));

    newsProviderUtility.subscribeUser(user1, dummyProviderId);

    verify(userRepository, times(1)).save(argThat( x -> {
      List<NewsProvider> newsProviders = x.getSubscription();
      assertEquals(1, newsProviders.size());
      assertTrue(newsProviders.contains(provider));
      return true;
    }));
  }

  @Test
  void testCheckForNewNewsAndNotifySubcribers() throws Exception {
    News dummyNews1 = new News();
    News dummyNews2 = new News();
    News dummyNews3 = new News();

    dummyNews1.setId("1");
    dummyNews2.setId("2");
    dummyNews3.setId("3");

    dummyNews1.setProvider(dummyProvider);
    dummyNews2.setProvider(dummyProvider);
    dummyNews3.setProvider(dummyProvider);

    dummyNews1.setSource("alice.my.id");
    dummyNews2.setSource("bob.my.id");
    dummyNews3.setSource("chad.my.id");

    when(newsRepository.existsById("1")).thenReturn(true);
    when(newsRepository.existsById("2")).thenReturn(true);
    when(newsRepository.existsById("3")).thenReturn(true);

    User dummyUser1 = new User();
    dummyUser1.setEmail("user1@user1.my.id");

    List<News> newsList = new ArrayList<>();
    newsList.add(dummyNews1);
    newsList.add(dummyNews2);
    newsList.add(dummyNews3);

    Set<User> subcribedUsers = new HashSet<>();
    subcribedUsers.add(dummyUser1);
    when(dummyProvider.getSubscribedUser()).thenReturn(subcribedUsers);

    newsProviderUtility.checkForNewNewsAndNotifySubcribers(newsList);

    verify(emailBlasterUtility).sendNotification(argThat(x -> {
      assertNotNull(x.getNotificationDtos());
      for (NotificationDto dto : x.getNotificationDtos()) {
        assertTrue(dto.getNewsUrl().stream().anyMatch(obj -> obj.equals("alice.my.id")));
        assertTrue(dto.getNewsUrl().stream().anyMatch(obj -> obj.equals("bob.my.id")));
        assertTrue(dto.getNewsUrl().stream().anyMatch(obj -> obj.equals("chad.my.id")));
        assertEquals(3, dto.getNewsUrl().size());

        assertEquals("user1@user1.my.id",dto.getEmail());
      }
      return true;
    }));
  }
}
