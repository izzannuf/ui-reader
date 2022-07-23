package id.ac.ui.cs.advprog.ui_reader.controller;

import id.ac.ui.cs.advprog.ui_reader.repository.NewsRepository;
import id.ac.ui.cs.advprog.ui_reader.service.NewsApiService;
import id.ac.ui.cs.advprog.ui_reader.util.exception.RequestErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/read")
public class ReadController {

    @Autowired
    private NewsApiService newsApiService;

    @Autowired
    private NewsRepository newsRepository;

    @RequestMapping(path = "/{id}")
    public String viewDetailed(Authentication authentication, @PathVariable("id") String id, Model model) {
        try {
            var email = authentication.getName();
            var news = newsApiService.getNewsById(id);

            news.setViewCount(news.getViewCount()+1);

            newsRepository.save(news);

            String newsProviderId = news.getProvider().getId();
            String newsTitle = news.getTitle();

            model.addAttribute("userEmail", email);
            model.addAttribute("isUserSubcribed", newsApiService.isUserSubcribed(email, newsProviderId));
            model.addAttribute("newsProviderId", newsProviderId);
            model.addAttribute("article", news);
            model.addAttribute("articleId", id);
            model.addAttribute("articleTitle", newsTitle);

            System.out.println(news.getTitle());
            System.out.println(news.getDescription());
            System.out.println(news.getContent());
            System.out.println(news.getImageUrl());
            System.out.println(news.getVideoUrl());
            System.out.println(news.getCategory());
            System.out.println(news.getSource());
            System.out.println(news.getPublishedDate());
            System.out.println(news.getProvider());

            return "reader-design";
        } catch (RequestErrorException e) {
            return "login";
        }
    }

}
