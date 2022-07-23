package id.ac.ui.cs.advprog.apifetcher.task;

import id.ac.ui.cs.advprog.apifetcher.util.converter.NewsApiOrgResponseConverter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NewsApiOrgApiTasks {
  NewsApiOrgApiFetcher newsApiOrgApiFetcher;
  UIReaderApiFetcher uiReaderApiFetcher;
  NewsApiOrgResponseConverter converter;

  public NewsApiOrgApiTasks() throws URISyntaxException {
    newsApiOrgApiFetcher = new NewsApiOrgApiFetcher();
    converter = new NewsApiOrgResponseConverter();
    uiReaderApiFetcher = new UIReaderApiFetcher();
  }

  private boolean isStatusCodeOK(int statusCode) {
    return statusCode / 100 == 2;
  }

  private HttpResponse<String> requestToEndPoint(Logger logger) throws IOException, InterruptedException {
    logger.info("Fetching from News API Org API");
    return newsApiOrgApiFetcher.execute();
  }

  private void convertAndPostToCore(Logger logger, HttpResponse<String> httpResponse)
      throws IOException, InterruptedException {
    var postNewsListDto = converter.convertList(httpResponse);

    logger.info("Posting News API Org response to core.");
    uiReaderApiFetcher.setPayload(postNewsListDto);
    HttpResponse<String> result = uiReaderApiFetcher.execute();
    logger.info("News API Org POST request ended with code {}", result.statusCode());
  }

  public void execute(Logger logger) throws IOException, InterruptedException {
    var httpResponse = requestToEndPoint(logger);
    if (!isStatusCodeOK(httpResponse.statusCode())) {
      logger.info("Fetching from News API Org failed with {}", httpResponse.statusCode());
    } else {
      convertAndPostToCore(logger, httpResponse);
    }
  }
}
