package id.ac.ui.cs.advprog.ui_reader.controller;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import id.ac.ui.cs.advprog.ui_reader.model.News;
import id.ac.ui.cs.advprog.ui_reader.model.NewsProvider;
import id.ac.ui.cs.advprog.ui_reader.model.dto.PostNewsDto;
import id.ac.ui.cs.advprog.ui_reader.model.dto.PostNewsListDto;
import id.ac.ui.cs.advprog.ui_reader.service.NewsApiService;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = NewsApiController.class)
public class NewsApiControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  NewsApiService newsApiService;

  @MockBean
  UserDetailsService userDetailsService;

  NewsProvider dummyProvider;
  News dummyNews1;
  News dummyNews2;
  List<News> dummyNewsList;

  @BeforeEach
  void prepare() {
    dummyProvider = new NewsProvider();
    dummyProvider.setId("p1");
    dummyNews1 = new News("id1", "news1", "description1", "content1", "url1", "video1", "top", "google", new Date(),
        dummyProvider, 0);
    dummyNews2 = new News("id2", "news2", "description2", "content2", "url2", "video2", "bottom", "face", new Date(),
        dummyProvider, 0);
    dummyNewsList = List.of(dummyNews1, dummyNews2);
  }

  @Test
  public void testGetAllNews() throws Exception {
    NewsProvider provider = new NewsProvider();
    provider.setId("p1");

    lenient().when(newsApiService.getAllNews()).thenReturn(dummyNewsList);
    mockMvc.perform(get("/api/news")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(2))
        .andExpect(jsonPath("$[0].id").value(dummyNews1.getId()))
        .andExpect(jsonPath("$[0].provider.id").value(dummyNews1.getProvider().getId()))
        .andExpect(jsonPath("$[1].id").value(dummyNews2.getId()))
        .andExpect(jsonPath("$[1].provider.id").value(dummyNews2.getProvider().getId()));
  }

  @Test
  public void testPostNews() throws Exception {
    PostNewsListDto dto = new PostNewsListDto();
    dto.setPostNewsDtoList(dummyNewsList.stream()
        .map(x ->
            new PostNewsDto(x.getId(), x.getTitle(), x.getDescription(), x.getContent(), x.getImageUrl(), x.getVideoUrl(), x.getCategory(), x.getSource(), x.getPublishedDate(), "p1")
        ).collect(Collectors.toList()));

    mockMvc.perform(get("/api/news")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}