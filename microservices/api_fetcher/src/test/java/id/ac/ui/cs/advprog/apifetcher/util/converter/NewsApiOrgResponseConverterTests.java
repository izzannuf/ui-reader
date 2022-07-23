package id.ac.ui.cs.advprog.apifetcher.util.converter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import com.google.gson.Gson;
import id.ac.ui.cs.advprog.apifetcher.dummy.Dummy;
import id.ac.ui.cs.advprog.apifetcher.model.constraint.ApiKey;
import id.ac.ui.cs.advprog.apifetcher.model.dto.NewsApiOrgDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class NewsApiOrgResponseConverterTests {
  @Mock
  private HttpResponse<String> httpResponse;

  String responseBody;

  @InjectMocks
  NewsApiOrgResponseConverter newsApiOrgResponseConverter;

  private boolean isEqualByJson(List<PostNewsDto> postNewsDtos1, List<PostNewsDto> postNewsDtos2) {
    Gson gson = new Gson();
    String json1 = gson.toJson(postNewsDtos1);
    String json2 = gson.toJson(postNewsDtos2);
    return json1.equals(json2);
  }

  @BeforeEach
  public void setUp() {
    responseBody = Dummy.NEWSAPIORGRESPONSE.content();
  }

  @Test
  void testGetListOfRawNews() {
    List<NewsApiOrgDto> newsApiOrgDtos = newsApiOrgResponseConverter.getListOfRawNews(responseBody);
    assertNotNull(newsApiOrgDtos);
    assertFalse(newsApiOrgDtos.isEmpty());

    NewsApiOrgDto newsApiOrgDto = newsApiOrgDtos.get(0);
    assertEquals("https://www.businessinsider.com/russian-oligarchs-payment-system-akin-terrorist-financing-hawala-2022-5", newsApiOrgDto.toPostNewsDto().getSource());
  }


  @Test
  void testConvertList() {
    lenient().when(httpResponse.body()).thenReturn(responseBody);
    PostNewsListDto postNewsListDto = newsApiOrgResponseConverter.convertList(httpResponse);
    assertEquals(postNewsListDto.getApiKey(), ApiKey.CORE.apiKey());

    List<NewsApiOrgDto> newsApiOrgDtos = newsApiOrgResponseConverter.getListOfRawNews(responseBody);
    List<PostNewsDto> postNewsDtos = newsApiOrgDtos.stream()
        .map(NewsApiOrgDto::toPostNewsDto)
        .collect(Collectors.toList());

    assertNotNull(postNewsListDto.getPostNewsDtoList());
    assertTrue(isEqualByJson(postNewsListDto.getPostNewsDtoList(), postNewsDtos));
  }
}
