package id.ac.ui.cs.advprog.apifetcher.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

import com.google.gson.Gson;
import id.ac.ui.cs.advprog.apifetcher.dummy.Dummy;
import id.ac.ui.cs.advprog.apifetcher.model.dto.PostNewsListDto;
import id.ac.ui.cs.advprog.apifetcher.util.converter.NewsDataResponseConverter;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@ExtendWith(MockitoExtension.class)
@PrepareForTest(UIReaderApiFetcher.class)
class UIReaderApiFetcherTests {

  @InjectMocks
  UIReaderApiFetcher uiReaderApiFetcher;

  @Mock
  private HttpResponse<String> httpResponse;

  PostNewsListDto dummyDto;

  @BeforeEach
  void prepareDto() {
    when(httpResponse.body()).thenReturn(Dummy.NEWSDATARESPONSE.content());

    NewsDataResponseConverter converter = new NewsDataResponseConverter();
    dummyDto = converter.convertList(httpResponse);
  }

  @Test
  void testNullPayload() throws URISyntaxException {
    uiReaderApiFetcher = new UIReaderApiFetcher();
    assertThrows(NullPointerException.class, () -> {
      uiReaderApiFetcher.execute();
    });
  }

  @Test
  void testObjectConstructionPayload() throws URISyntaxException {
    UIReaderApiFetcher uiReaderApiFetcher1 = PowerMockito.spy(new UIReaderApiFetcher(dummyDto));

    String dummyPayload = new Gson().toJson(dummyDto);
    String payload = Whitebox.getInternalState(uiReaderApiFetcher1, "payload");
    assertEquals(dummyPayload, payload);
  }

  @Test
  void testRequestType() throws Exception {
    UIReaderApiFetcher uiReaderApiFetcher = PowerMockito.spy(Whitebox.newInstance(UIReaderApiFetcher.class));
    doNothing().when(uiReaderApiFetcher).buildPostRequest();

    doNothing().when(uiReaderApiFetcher, "addHeaders", any());
    doReturn(httpResponse).when(uiReaderApiFetcher, "sendRequest");

    uiReaderApiFetcher.setPayload(dummyDto);
    uiReaderApiFetcher.execute();
    verifyPrivate(uiReaderApiFetcher, times(1)).invoke("sendRequest");
    verifyPrivate(uiReaderApiFetcher, times(1)).invoke("buildPostRequest");
  }
}
