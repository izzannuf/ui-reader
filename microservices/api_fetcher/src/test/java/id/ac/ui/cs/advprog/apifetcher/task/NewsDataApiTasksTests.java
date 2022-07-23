package id.ac.ui.cs.advprog.apifetcher.task;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.spy;

import id.ac.ui.cs.advprog.apifetcher.dummy.Dummy;
import id.ac.ui.cs.advprog.apifetcher.model.constraint.ApiKey;
import id.ac.ui.cs.advprog.apifetcher.model.dto.PostNewsListDto;
import id.ac.ui.cs.advprog.apifetcher.util.converter.NewsDataResponseConverter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;

@RunWith(PowerMockRunner.class)
@ExtendWith(MockitoExtension.class)
@PrepareForTest({NewsDataApiTasks.class, UIReaderApiFetcher.class, ApiKey.class})
class NewsDataApiTasksTests {

  @InjectMocks
  NewsDataApiTasks newsDataApiTasks;

  @Mock
  NewsDataApiFetcher newsDataApiFetcher;

  @Mock
  UIReaderApiFetcher uiReaderApiFetcher;

  @Mock
  NewsDataResponseConverter converter;

  @Mock
  private Logger logger;

  @Mock
  private HttpResponse<String> httpResponse;

  PostNewsListDto dummyDto;

  @BeforeEach
  void prepareDto() {
    when(httpResponse.body()).thenReturn(Dummy.NEWSDATARESPONSE.content());

    NewsDataResponseConverter converter = new NewsDataResponseConverter();
    dummyDto = converter.convertList(httpResponse);
  }

  @BeforeEach
  void prepareTasks() {
    newsDataApiTasks = spy(new NewsDataApiTasks(newsDataApiFetcher, uiReaderApiFetcher, converter));
  }


  @Test
  void testConvertAndPostToCore() throws Exception {
    doNothing().when(logger).info(anyString());

    doReturn(httpResponse).when(uiReaderApiFetcher).execute();

    Class[] types = new Class[]{Logger.class, HttpResponse.class};
    Whitebox.invokeMethod(newsDataApiTasks, "convertAndPostToCore", types, logger, httpResponse);
    verify(logger, atLeastOnce()).info(anyString());
    verify(uiReaderApiFetcher, atLeastOnce()).execute();
  }

  @Test
  void testRequestToEndPoint() throws Exception {
    doNothing().when(logger).info(anyString());
    doReturn(httpResponse).when(newsDataApiFetcher).execute();

    Class[] types = new Class[]{Logger.class};
    Whitebox.invokeMethod(newsDataApiTasks, "requestToEndPoint", types, logger);

    verify(logger, atLeastOnce()).info(anyString());
    verify(newsDataApiFetcher).execute();
  }

  @Test
  void testBadExecutedRequest() throws Exception {
    doReturn(404).when(httpResponse).statusCode();
    doReturn(httpResponse).when(newsDataApiFetcher).execute();

    doNothing().when(logger).info(anyString());

    newsDataApiTasks.execute(logger);
    verify(logger, atLeastOnce()).info(anyString());
    verify(newsDataApiFetcher).execute();
    verify(uiReaderApiFetcher, times(0)).execute();
  }

  @Test
  void testGoodExecutedRequest() throws IOException, InterruptedException, URISyntaxException {
    doReturn(200).when(httpResponse).statusCode();
    doReturn(httpResponse).when(newsDataApiFetcher).execute();
    doReturn(httpResponse).when(uiReaderApiFetcher).execute();

    doNothing().when(logger).info(anyString());

    newsDataApiTasks.execute(logger);
    verify(logger, atLeastOnce()).info(anyString());
    verify(newsDataApiFetcher).execute();
    verify(uiReaderApiFetcher).execute();
  }
}
