package id.ac.ui.cs.advprog.apifetcher.util.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

import com.google.gson.Gson;
import id.ac.ui.cs.advprog.apifetcher.dummy.Dummy;
import id.ac.ui.cs.advprog.apifetcher.model.constraint.ApiKey;
import id.ac.ui.cs.advprog.apifetcher.model.dto.NewsDataDto;
import id.ac.ui.cs.advprog.apifetcher.model.dto.PostNewsDto;
import id.ac.ui.cs.advprog.apifetcher.model.dto.PostNewsListDto;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NewsDataResponseConverterTests {
  @Mock
  private HttpResponse<String> httpResponse;

  String responseBody;

  @InjectMocks
  NewsDataResponseConverter newsDataResponseConverter;

  private boolean isEqualByJson(List<PostNewsDto> postNewsDtos1, List<PostNewsDto> postNewsDtos2) {
    Gson gson = new Gson();
    String json1 = gson.toJson(postNewsDtos1);
    String json2 = gson.toJson(postNewsDtos2);
    return json1.equals(json2);
  }

  @BeforeEach
  void setUp() {
    responseBody = Dummy.NEWSDATARESPONSE.content();
  }

  @Test
  void testGetListOfRawNews() {
    List<NewsDataDto> newsDataDtos = newsDataResponseConverter.getListOfRawNews(responseBody);
    assertNotNull(newsDataDtos);
    assertFalse(newsDataDtos.isEmpty());

    NewsDataDto newsDataDto = newsDataDtos.get(0);
    assertEquals(newsDataDto.toPostNewsDto().getSource(), "https://www.foxnews.com/us/tennessee-execution-2-people-knew-drugs-hadnt-tested-new-records");
  }

  @Test
  void testConvertList() {
    lenient().when(httpResponse.body()).thenReturn(responseBody);
    PostNewsListDto postNewsListDto = newsDataResponseConverter.convertList(httpResponse);
    assertEquals(postNewsListDto.getApiKey(), ApiKey.CORE.apiKey());

    List<NewsDataDto> newsDataDtos = newsDataResponseConverter.getListOfRawNews(responseBody);
    List<PostNewsDto> postNewsDtos = newsDataDtos.stream()
        .map(NewsDataDto::toPostNewsDto)
        .collect(Collectors.toList());

    assertNotNull(postNewsListDto.getPostNewsDtoList());
    assertTrue(isEqualByJson(postNewsListDto.getPostNewsDtoList(), postNewsDtos));
  }
}
