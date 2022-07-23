package id.ac.ui.cs.advprog.apifetcher.task;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;

import id.ac.ui.cs.advprog.apifetcher.dummy.Dummy;
import id.ac.ui.cs.advprog.apifetcher.model.dto.PostNewsListDto;
import id.ac.ui.cs.advprog.apifetcher.util.converter.NewsApiOrgResponseConverter;
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
@PrepareForTest({NewsApiOrgApiTasks.class, UIReaderApiFetcher.class})
class NewsApiOrgApiTasksTests {

  @InjectMocks
  NewsApiOrgApiTasks newsApiOrgApiTasks;

  @Mock
  NewsApiOrgApiFetcher newsApiOrgApiFetcher;

  @Mock
  UIReaderApiFetcher uiReaderApiFetcher;

  @Mock
  NewsApiOrgResponseConverter converter;

  @Mock
  private Logger logger;

  @Mock
  private HttpResponse<String> httpResponse;

  PostNewsListDto dummyDto;

  @BeforeEach
  void prepareDto() {
    when(httpResponse.body()).thenReturn(Dummy.NEWSAPIORGRESPONSE.content());

    NewsApiOrgResponseConverter converter = new NewsApiOrgResponseConverter();
    dummyDto = converter.convertList(httpResponse);
  }

  @BeforeEach
  void prepareTasks() {
    newsApiOrgApiTasks = spy(new NewsApiOrgApiTasks(newsApiOrgApiFetcher, uiReaderApiFetcher, converter));
  }


  @Test
  void testConvertAndPostToCore() throws Exception {
    doNothing().when(logger).info(anyString());

    doReturn(httpResponse).when(uiReaderApiFetcher).execute();

    Class[] types = new Class[]{Logger.class, HttpResponse.class};
    Whitebox.invokeMethod(newsApiOrgApiTasks, "convertAndPostToCore", types, logger, httpResponse);
    verify(logger, atLeastOnce()).info(anyString());
    verify(uiReaderApiFetcher, atLeastOnce()).execute();
  }

  @Test
  void testRequestToEndPoint() throws Exception {
    doNothing().when(logger).info(anyString());
    doReturn(httpResponse).when(newsApiOrgApiFetcher).execute();

    Class[] types = new Class[]{Logger.class};
    Whitebox.invokeMethod(newsApiOrgApiTasks, "requestToEndPoint", types, logger);

    verify(logger, atLeastOnce()).info(anyString());
    verify(newsApiOrgApiFetcher).execute();
  }

  @Test
  void testBadExecutedRequest() throws Exception {
    doReturn(404).when(httpResponse).statusCode();
    doReturn(httpResponse).when(newsApiOrgApiFetcher).execute();

    doNothing().when(logger).info(anyString());

    newsApiOrgApiTasks.execute(logger);
    verify(logger, atLeastOnce()).info(anyString());
    verify(newsApiOrgApiFetcher).execute();
    verify(uiReaderApiFetcher, times(0)).execute();
  }

  @Test
  void testGoodExecutedRequest() throws IOException, InterruptedException, URISyntaxException {
    doReturn(200).when(httpResponse).statusCode();
    doReturn(httpResponse).when(newsApiOrgApiFetcher).execute();
    doReturn(httpResponse).when(uiReaderApiFetcher).execute();

    doNothing().when(logger).info(anyString());

    newsApiOrgApiTasks.execute(logger);
    verify(logger, atLeastOnce()).info(anyString());
    verify(newsApiOrgApiFetcher).execute();
    verify(uiReaderApiFetcher).execute();
  }
}
