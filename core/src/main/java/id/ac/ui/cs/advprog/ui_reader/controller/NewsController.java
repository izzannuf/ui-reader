package id.ac.ui.cs.advprog.ui_reader.controller;

import id.ac.ui.cs.advprog.ui_reader.model.News;
import id.ac.ui.cs.advprog.ui_reader.repository.NewsRepository;
import id.ac.ui.cs.advprog.ui_reader.service.NewsApiService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.Date;

@Controller
@RequestMapping(path = "")
public class NewsController {

    @Autowired
    NewsApiService newsApiService;

    @Autowired
    NewsRepository newsRepository;

    @GetMapping(path="")
    public String getHomePage(Model model,  @Param("keyword") String keyword, @Param("category") String category,
                              @Param("startDate") String startDate, @Param("endDate") String endDate) {
        List<News> allNews = new LinkedList<>();

        for(News e : newsRepository.findAll()){
            allNews.add(e);
        }
        var popularHomeNews = newsApiService.getPopularHomeNews(allNews);
        var latestHomeNews = newsApiService.getLatestHomeNews(allNews);
        var newsHomePage = newsApiService.getNewsHomePage(keyword, category, startDate, endDate);
        model.addAttribute("popularNews", popularHomeNews);
        model.addAttribute("latestNews", latestHomeNews);
        model.addAttribute("news", newsHomePage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        var isThereAnyNews = "";
        if (keyword == null) {
            isThereAnyNews = "home";
        }
        else if (!newsHomePage.isEmpty()) {
            isThereAnyNews = "search";
        }
        else if (newsHomePage.isEmpty()){
            isThereAnyNews = "not_found";
        }

        model.addAttribute("isThereAnyNews", isThereAnyNews);
        return "homepage";
    }
    @GetMapping(path="/all-news")
    public String getAllNews(Model model, @Param("keyword") String keyword, @Param("category") String category,
                             @Param("startDate") String startDate, @Param("endDate") String endDate) {
        List<News> listNews = new LinkedList<>();

        for(News e : newsRepository.findAll()){
            listNews.add(e);
        }

        var allNews = newsApiService.getNewsFullImage(keyword, listNews, category, startDate, endDate);

        model.addAttribute("allNews", allNews);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        var isThereAnyNews = "";
        if (!allNews.isEmpty()) {
            isThereAnyNews = "search";
        }
        else if (allNews.isEmpty() && keyword != null){
            isThereAnyNews = "not_found";
        }

        model.addAttribute("isThereAnyNews", isThereAnyNews);
        return "all_news";
    }
}
