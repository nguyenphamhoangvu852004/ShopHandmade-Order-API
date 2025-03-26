package com.example.ShopHandmade.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
   private RestTemplate restTemplate = new RestTemplate();

   @ModelAttribute("categories")
   public List<Map<String,Object>> getCategories(){
       String url = "http://localhost:8081/api/v1/category";
       return restTemplate.getForObject(url,List.class);
   }
}
