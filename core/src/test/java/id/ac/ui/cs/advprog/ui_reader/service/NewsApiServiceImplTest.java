package id.ac.ui.cs.advprog.ui_reader.service;

import id.ac.ui.cs.advprog.ui_reader.model.News;
import id.ac.ui.cs.advprog.ui_reader.model.NewsProvider;
import id.ac.ui.cs.advprog.ui_reader.model.User;
import id.ac.ui.cs.advprog.ui_reader.model.dto.PostNewsListDto;
import id.ac.ui.cs.advprog.ui_reader.repository.NewsProviderUtility;
import id.ac.ui.cs.advprog.ui_reader.repository.NewsRepository;
import id.ac.ui.cs.advprog.ui_reader.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class NewsApiServiceImplTest {
    @MockBean
    NewsRepository newsRepository;
    
    @MockBean
    NewsProviderUtility newsProviderUtility;
    
    @MockBean
    UserRepository userRepository;
    
    @InjectMocks
    NewsApiServiceImpl newsApiService;
    
    
    NewsProvider newsProvider = new NewsProvider();
    User user = new User();
    String userEmail = "pradipta.davi@cs.ui.ac.id";
    News news1 = new News("12", "yow", "desc", "content",
                          "imageUrl", "videoUrl",
                          "category", "source", new Date(), null, 10);
    News news2 = new News("13", "ow", "desc2", "content2",
                          "imageUrl2", "videoUrl2",
                          "category2", "source2", new Date(), null, 10);
    List<News> multipleNews = List.of(news1, news2);
    
    @BeforeEach
    void setUp() {
        user.setEmail(userEmail);
        news1.setProvider(newsProvider);
        news2.setProvider(newsProvider);
        MockitoAnnotations.openMocks(this);
    }
    
    
    @Test
    void getAllNews() {
        when(newsRepository.findAll()).thenReturn(multipleNews);
        var result = newsApiService.getAllNews();
        assertEquals(multipleNews, result);
    }
    
    @Test
    void createNews() throws Exception{
        var postNewsListDto = Mockito.mock(PostNewsListDto.class);
        doNothing().when(postNewsListDto).validate();
        when(newsProviderUtility.convertPostNewsDtoToNews(postNewsListDto)).thenReturn(multipleNews);
    
        var result = newsApiService.createNews(postNewsListDto);
        assertEquals(multipleNews, result);
        
        verify(postNewsListDto, times(1)).validate();
        verify(newsProviderUtility, times(1)).checkForNewNewsAndNotifySubcribers(multipleNews);
        verify(newsRepository, times(1)).saveAll(multipleNews);
    }
    
    @Test
    void getNewsById() {
        var id = news2.getId();
        when(newsRepository.getById(id)).thenReturn(news2);
        var result = newsApiService.getNewsById(id);
        assertEquals(news2, result);
    }
    
    @Test
    void subscribeUser() throws Exception{
        var providerId = "123";
        when(userRepository.findByEmail(userEmail)).thenReturn(user);
        newsApiService.subscribeUser(userEmail, providerId);
        verify(newsProviderUtility, times(1)).subscribeUser(user, providerId);
    }
    
    @Test
    void isUserSubcribed() throws Exception{
        var providerId = "123";
        when(userRepository.findByEmail(userEmail)).thenReturn(user);
        
        when(newsProviderUtility.isUserSubcribed(user, providerId))
                .thenReturn(true)
                .thenReturn(false);
        
        boolean result;
        
        result = newsApiService.isUserSubcribed(userEmail, providerId);
        assertTrue(result);
        
        result = newsApiService.isUserSubcribed(userEmail, providerId);
        assertFalse(result);
    }
    
}