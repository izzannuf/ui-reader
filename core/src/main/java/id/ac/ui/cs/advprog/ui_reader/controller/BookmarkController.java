package id.ac.ui.cs.advprog.ui_reader.controller;

import id.ac.ui.cs.advprog.ui_reader.model.News;
import id.ac.ui.cs.advprog.ui_reader.repository.NewsRepository;
import id.ac.ui.cs.advprog.ui_reader.service.NewsApiService;
import id.ac.ui.cs.advprog.ui_reader.util.exception.RequestErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bookmarks")
public class BookmarkController {

    @Autowired
    private NewsApiService newsApiService;

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping(path = "/all-bookmarks")
    public String listBookmakrs(Model model) {
        return "bookmarks";
    }



}
