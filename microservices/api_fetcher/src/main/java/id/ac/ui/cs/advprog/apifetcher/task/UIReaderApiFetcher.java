package id.ac.ui.cs.advprog.apifetcher.task;

import com.google.gson.Gson;
import id.ac.ui.cs.advprog.apifetcher.model.constraint.Host;
import id.ac.ui.cs.advprog.apifetcher.model.dto.PostNewsListDto;
import id.ac.ui.cs.advprog.apifetcher.task.base.ApiFetcher;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UIReaderApiFetcher extends ApiFetcher {
  private static final String url = Host.CORE.host() + "/api/news";
  private String payload = null;

  public UIReaderApiFetcher() throws URISyntaxException {
    super(url);
  }

  public UIReaderApiFetcher(PostNewsListDto dto) throws URISyntaxException {
    super(url);
    setPayload(dto);
  }

  public void setPayload(PostNewsListDto dto) {
    Gson gson = new Gson();
    payload = gson.toJson(dto);
  }

  @Override
  protected void buildPostRequest() {
    requestBuilder = HttpRequest.newBuilder()
        .uri(this.uri)
        .POST(HttpRequest.BodyPublishers.ofString(payload));
  }

  @Override
  public HttpResponse<String> execute() throws IOException, InterruptedException {
    if (payload == null) {
      throw new NullPointerException();
    }
    buildPostRequest();
    addHeaders(commonRequestHeaders);
    return sendRequest();
  }
}
