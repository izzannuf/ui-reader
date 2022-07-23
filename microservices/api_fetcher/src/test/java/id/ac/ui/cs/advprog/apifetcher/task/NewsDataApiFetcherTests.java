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
@PrepareForTest({NewsDataApiFetcher.class, ApiKey.class})
class NewsDataApiFetcherTests {

  @InjectMocks
  NewsDataApiFetcher newsDataApiFetcher;

  @Test
  void testRequestParameters() {
    Map<String, String> params = newsDataApiFetcher.getParams();
    assertEquals(params.get("apikey"), ApiKey.NEWSDATA.apiKey());
    assertNotNull(params.get("language"));
    assertNotNull(params.get("domain"));
  }

  @Test
  void testRequestType() throws Exception {
    NewsDataApiFetcher newsDataApiFetcher = PowerMockito.spy(new NewsDataApiFetcher());

    newsDataApiFetcher.execute();
    PowerMockito.verifyPrivate(newsDataApiFetcher, times(1)).invoke("sendRequest");
    PowerMockito.verifyPrivate(newsDataApiFetcher, times(1)).invoke("buildGetRequest");
  }
}
