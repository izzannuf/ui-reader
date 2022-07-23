package id.ac.ui.cs.advprog.apifetcher.util.converter;

import id.ac.ui.cs.advprog.apifetcher.model.constraint.ApiKey;
import id.ac.ui.cs.advprog.apifetcher.model.dto.NewsDataResponseDto;
import id.ac.ui.cs.advprog.apifetcher.model.dto.NewsDataDto;
import id.ac.ui.cs.advprog.apifetcher.model.dto.PostNewsDto;
import id.ac.ui.cs.advprog.apifetcher.model.dto.PostNewsListDto;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import java.util.List;
import java.util.stream.Collectors;

public class NewsDataResponseConverter {

  public List<NewsDataDto> getListOfRawNews(String body) {
    var gson = new Gson();
    var newsDataApiDto = gson.fromJson(body, NewsDataResponseDto.class);
    return newsDataApiDto.results;
  }

  public PostNewsListDto convertList(HttpResponse<String> response) {
    List<NewsDataDto> newsDataDtos = getListOfRawNews(response.body());
    List<PostNewsDto> postNewsDtos = newsDataDtos.stream()
            .map(NewsDataDto::toPostNewsDto)
            .collect(Collectors.toList());

    var postNewsListDto = new PostNewsListDto();
    postNewsListDto.setApiKey(ApiKey.CORE.apiKey());
    postNewsListDto.setPostNewsDtoList(postNewsDtos);
    return postNewsListDto;
  }
}
