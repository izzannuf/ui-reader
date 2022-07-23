package id.ac.ui.cs.advprog.apifetcher.util.converter;

import com.google.gson.Gson;
import id.ac.ui.cs.advprog.apifetcher.model.constraint.ApiKey;
import id.ac.ui.cs.advprog.apifetcher.model.dto.NewsApiOrgDto;
import id.ac.ui.cs.advprog.apifetcher.model.dto.NewsApiOrgResponseDto;
import id.ac.ui.cs.advprog.apifetcher.model.dto.PostNewsDto;
import id.ac.ui.cs.advprog.apifetcher.model.dto.PostNewsListDto;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class NewsApiOrgResponseConverter {
  public List<NewsApiOrgDto> getListOfRawNews(String body) {
    var gson = new Gson();
    var newsApiOrgResponseDto = gson.fromJson(body, NewsApiOrgResponseDto.class);
    return newsApiOrgResponseDto.getArticles();
  }

  public PostNewsListDto convertList(HttpResponse<String> response) {
    List<NewsApiOrgDto> newsApiOrgDtos = getListOfRawNews(response.body());
    List<PostNewsDto> postNewsDtos = newsApiOrgDtos.stream()
        .map(NewsApiOrgDto::toPostNewsDto)
        .collect(Collectors.toList());

    var postNewsListDto = new PostNewsListDto();
    postNewsListDto.setApiKey(ApiKey.CORE.apiKey());
    postNewsListDto.setPostNewsDtoList(postNewsDtos);
    return postNewsListDto;
  }
}
