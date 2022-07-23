package id.ac.ui.cs.advprog.ui_reader.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.ac.ui.cs.advprog.ui_reader.model.News;
import id.ac.ui.cs.advprog.ui_reader.model.dto.PostNewsDto;
import id.ac.ui.cs.advprog.ui_reader.model.dto.PostNewsListDto;
import id.ac.ui.cs.advprog.ui_reader.repository.UserRepository;
import id.ac.ui.cs.advprog.ui_reader.service.NewsApiService;
import id.ac.ui.cs.advprog.ui_reader.util.exception.RequestErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockSettings;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = NewsApiController.class)
class NewsApiControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserRepository repo;

    @MockBean
    private NewsApiService newsApiService;
    private News news;
    private final String newsName = "Ukraine War";

    private PostNewsDto postNewsDto;


    private PostNewsListDto postNewsListDto;

    @InjectMocks
    NewsApiController newsApiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        news = new News();
        news.setTitle(newsName);

        postNewsDto = new PostNewsDto("1", "Title", "Desc", "Content", "image", "video", "news", "source",
                new Date(), "provider");
        postNewsDto.setTitle(newsName);

        postNewsListDto = new PostNewsListDto(List.of(postNewsDto));
    }

    @Test
    void getAllNews() throws Exception {
        Iterable<News> newsList = List.of(news);
        when(newsApiService.getAllNews()).thenReturn(newsList);

        mockMvc.perform(get("/api/news").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value(newsName));
    }

    @Test
    void postNewsList() throws Exception {
        when(newsApiService.createNews(postNewsListDto)).thenReturn(List.of(news));

        mockMvc.perform(post("/api/news")
                        .contentType(MediaType.APPLICATION_JSON).content(Mapper.mapToJson(postNewsListDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(newsName));
    }

//    @Test
//    void postNewsListError() throws Exception {
//        when(newsApiService.createNews(postNewsListDto)).thenThrow(new Exception("Something went wrong"));
//
//        mockMvc.perform(post("/api/news")
//                        .contentType(MediaType.APPLICATION_JSON).content(Mapper.mapToJson(postNewsListDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].title").value(newsName));
//    }
}