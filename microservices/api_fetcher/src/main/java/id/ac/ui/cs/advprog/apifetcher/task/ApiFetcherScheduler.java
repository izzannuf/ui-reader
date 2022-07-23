package id.ac.ui.cs.advprog.apifetcher.task;

import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ApiFetcherScheduler {

  @Autowired
  NewsDataApiTasks newsDataApiTasks;

  @Autowired
  NewsApiOrgApiTasks newsApiOrgApiTasks;

  private static final Logger log = LoggerFactory.getLogger(ApiFetcherScheduler.class);

  @Scheduled(fixedRate = 1200000)
  public void fetchNewsApiOrgApi() throws IOException, URISyntaxException, InterruptedException {
    newsApiOrgApiTasks.execute(log);
  }

  @Scheduled(fixedRate = 600000)
  public void fetchNewsDataApi() throws IOException, URISyntaxException, InterruptedException {
    newsDataApiTasks.execute(log);
  }
}
