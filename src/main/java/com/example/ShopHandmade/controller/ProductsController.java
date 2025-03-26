package com.example.ShopHandmade.controller;

import com.example.ShopHandmade.service.CategoryService;
import com.example.ShopHandmade.service.GetDetailProductResponse;
import com.example.ShopHandmade.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ProductsController {
    private final CategoryService categoryService;
    private final ProductService productService;
    public ProductsController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }


    @GetMapping("/products")
    public String productsView(Model model) {
        List<Map<String, Object>> listCategories = categoryService.getCategories();

        List<Map<String, Object>> listProducts = productService.getProducts(0, 10).getContent().stream().map(product -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", product.getId());
            map.put("name", product.getName());
            map.put("description", product.getDescription());
            map.put("price", product.getPrice());
            map.put("inventory", product.getInventory());
            map.put("images", product.getImages());
            return map;
        }).collect(Collectors.toList());

        model.addAttribute("categories", listCategories);
        model.addAttribute("products", listProducts);

        return "product";
    }

    @GetMapping("/products/detail/{id}")
    public String detailProductView(@PathVariable("id") int id, Model model){
        GetDetailProductResponse detailProductResponse = this.productService.getProductById(id);
        System.out.println(detailProductResponse.toString());
        model.addAttribute("product", detailProductResponse);
        return "detailProduct";
    }
}
