package id.ac.ui.cs.advprog.apifetcher.task;

import id.ac.ui.cs.advprog.apifetcher.model.constraint.ApiKey;
import id.ac.ui.cs.advprog.apifetcher.task.base.ApiFetcher;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class NewsApiOrgApiFetcher extends ApiFetcher {
  private static final String URL = "https://newsapi.org/v2/everything";

  public NewsApiOrgApiFetcher() throws URISyntaxException {
    super(URL);
    Map<String, String> requestParams = new HashMap<>();
    requestParams.put("apiKey", ApiKey.NEWSAPIORG.apiKey());
    requestParams.put("language", "en");
    requestParams.put("domains", "businessinsider.com,buzzfeed.com,nationalgeographic.com,ycombinator.com,ign.com");

    setParams(requestParams);
  }

  @Override
  public HttpResponse<String> execute() throws IOException, InterruptedException {
    buildGetRequest();
    return sendRequest();
  }
}
