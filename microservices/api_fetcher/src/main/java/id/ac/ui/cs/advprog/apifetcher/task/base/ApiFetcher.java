package id.ac.ui.cs.advprog.apifetcher.task.base;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public abstract class ApiFetcher {
  protected URI uri;
  protected HttpRequest.Builder requestBuilder;
  protected HttpRequest request;

  protected final Map<String, String> commonRequestHeaders;

  protected ApiFetcher(String url) throws URISyntaxException {
    this.uri = new URI(url);
    commonRequestHeaders = new HashMap<>();
    commonRequestHeaders.put("Content-Type", "application/json");
  }

  protected void setParams(Map<String, String> params) throws URISyntaxException {
    StringBuilder newParams = null;

    for (Map.Entry<String,String> entry : params.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();

      String param = key + "=" + value;
      if (newParams == null) {
        newParams = new StringBuilder(param);
      } else {
        newParams.append("&").append(param);
      }
    }

    if (newParams == null) {
      newParams = new StringBuilder("");
    }
    this.uri = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), newParams.toString(), uri.getFragment());
  }

  public Map<String, String> getParams() {
    String[] rawParams = this.uri.getQuery().split("&");
    Map<String, String> params = new HashMap<>();
    for (String str : rawParams) {
      String[] value = str.split("=");
      params.put(value[0], value[1]);
    }
    return params;
  }

  protected void addHeaders(Map<String, String> headers) {
    for (Map.Entry<String,String> entry : headers.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      this.requestBuilder = requestBuilder.header(key, value);
    }
  }

  protected void buildGetRequest() {
    requestBuilder = HttpRequest.newBuilder()
        .uri(this.uri)
        .GET();
  }

  protected void buildPostRequest() {
    requestBuilder = HttpRequest.newBuilder()
        .uri(this.uri)
        .POST(HttpRequest.BodyPublishers.noBody());
  }

  protected HttpResponse<String> sendRequest() throws IOException, InterruptedException {
    HttpClient httpClient = HttpClient
        .newBuilder()
        .build();
    request = requestBuilder.build();
    return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }

//  Implement the proposed api request here
  abstract public HttpResponse<String> execute() throws IOException, InterruptedException;
}
