package id.ac.ui.cs.advprog.apifetcher.task;

import id.ac.ui.cs.advprog.apifetcher.util.converter.NewsDataResponseConverter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NewsDataApiTasks {
  NewsDataApiFetcher newsDataApiFetcher;
  UIReaderApiFetcher uiReaderApiFetcher;
  NewsDataResponseConverter converter;

  public NewsDataApiTasks() throws URISyntaxException {
    newsDataApiFetcher = new NewsDataApiFetcher();
    converter = new NewsDataResponseConverter();
    uiReaderApiFetcher = new UIReaderApiFetcher();
  }

  private boolean isStatusCodeOK(int statusCode) {
    return statusCode / 100 == 2;
  }

  private HttpResponse<String> requestToEndPoint(Logger logger) throws IOException, InterruptedException {
    logger.info("Fetching from News Data API");
    return newsDataApiFetcher.execute();
  }

  private void convertAndPostToCore(Logger logger, HttpResponse<String> httpResponse)
      throws IOException, InterruptedException {
    var postNewsListDto = converter.convertList(httpResponse);

    logger.info("Posting the data to core.");
    uiReaderApiFetcher.setPayload(postNewsListDto);
    HttpResponse<String> result = uiReaderApiFetcher.execute();
    logger.info("News Data POST request ended with code {}", result.statusCode());
  }

  public void execute(Logger logger) throws IOException, InterruptedException {
    var httpResponse = requestToEndPoint(logger);
    if (!isStatusCodeOK(httpResponse.statusCode())) {
      logger.info("Fetching from News Data failed with {}", httpResponse.statusCode());
    } else {
      convertAndPostToCore(logger, httpResponse);
    }
  }
}
