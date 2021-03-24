package com.grlife.newscrawler.controller;

import com.grlife.newscrawler.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class NewsController {

    private final NewsService service;


    @Autowired
    public NewsController(NewsService service) {
        this.service = service;
    }

    @RequestMapping("getRssData")
    public String getRssData(Model model) {

        String test = "";

        model.addAttribute("list", service.getRssData());

        return "test";
    }

}
