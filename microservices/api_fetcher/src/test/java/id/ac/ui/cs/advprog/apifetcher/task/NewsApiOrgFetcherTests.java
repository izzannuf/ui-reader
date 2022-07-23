package id.ac.ui.cs.advprog.apifetcher.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

import id.ac.ui.cs.advprog.apifetcher.model.constraint.ApiKey;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@ExtendWith(MockitoExtension.class)
@PrepareForTest({ApiKey.class, NewsApiOrgApiFetcher.class})
class NewsApiOrgFetcherTests {
  @InjectMocks
  NewsApiOrgApiFetcher newsApiOrgApiFetcher;
  
  @Test
  void testRequestParameters() {
    Map<String, String> params = newsApiOrgApiFetcher.getParams();
    assertEquals(params.get("apiKey"), ApiKey.NEWSAPIORG.apiKey());
    assertNotNull(params.get("language"));
    assertNotNull(params.get("domains"));
  }

  @Test
  void testRequestType() throws Exception {
    NewsApiOrgApiFetcher newsApiOrgApiFetcher = PowerMockito.spy(new NewsApiOrgApiFetcher());

    newsApiOrgApiFetcher.execute();
    PowerMockito.verifyPrivate(newsApiOrgApiFetcher, times(1)).invoke("sendRequest");
    PowerMockito.verifyPrivate(newsApiOrgApiFetcher, times(1)).invoke("buildGetRequest");
  }
}
