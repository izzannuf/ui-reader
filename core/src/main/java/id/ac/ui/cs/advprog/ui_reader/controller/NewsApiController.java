package id.ac.ui.cs.advprog.ui_reader.controller;

import id.ac.ui.cs.advprog.ui_reader.model.News;
import id.ac.ui.cs.advprog.ui_reader.model.dto.PostNewsListDto;
import id.ac.ui.cs.advprog.ui_reader.service.NewsApiService;
import id.ac.ui.cs.advprog.ui_reader.util.exception.RequestErrorException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/news")
public class NewsApiController {

  @Autowired
  private NewsApiService newsApiService;

  @GetMapping(produces = {"application/json"})
  @ResponseBody
  public ResponseEntity<Iterable<News>> getAllNews() {
    return ResponseEntity.ok(newsApiService.getAllNews());
  }

  @PostMapping(produces = {"application/json"})
  @ResponseBody
  public ResponseEntity<Iterable<News>> postNewsList(@RequestBody PostNewsListDto postNewsListDto) {
    Iterable<News> newsList;
    try {
      newsList = newsApiService.createNews(postNewsListDto);
    } catch (RequestErrorException e) {
      return e.getResponseEntity();
    }
    return ResponseEntity.ok(newsList);
  }

  @PostMapping(path = "/{id}", produces = {"application/json"})
  @ResponseBody
  public ResponseEntity userSubscribe(HttpServletRequest request, @PathVariable("id") String providerId, Authentication authentication, @RequestParam String email) {
    try {
      if (authentication != null && authentication.getName().equals(email)) {
        newsApiService.subscribeUser(email, providerId);

        var headers = new HttpHeaders();
        headers.add("Location", request.getHeader("Referer"));
        return new ResponseEntity<>(headers,HttpStatus.FOUND);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (RequestErrorException e) {
      return e.getResponseEntity();
    }
  }

}
