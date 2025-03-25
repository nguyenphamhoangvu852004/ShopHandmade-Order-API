package com.example.ShopHandmade.controller;

import com.example.ShopHandmade.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService =categoryService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
