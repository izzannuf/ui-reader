package id.ac.ui.cs.advprog.apifetcher.task;

import id.ac.ui.cs.advprog.apifetcher.model.constraint.ApiKey;
import id.ac.ui.cs.advprog.apifetcher.task.base.ApiFetcher;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class NewsDataApiFetcher extends ApiFetcher {
  private static final String URL = "https://newsdata.io/api/1/news";

  public NewsDataApiFetcher() throws URISyntaxException {
    super(URL);
    Map<String, String> requestParams = new HashMap<>();
    requestParams.put("apikey", ApiKey.NEWSDATA.apiKey());
    requestParams.put("language", "en");
    requestParams.put("domain", "wired,techradar,google,forbes,foxnews");

    setParams(requestParams);
  }

  @Override
  public HttpResponse<String> execute() throws IOException, InterruptedException {
    buildGetRequest();
    return sendRequest();
  }
}
