package com.example.ShopHandmade.service;


import com.example.ShopHandmade.dto.PageWrapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    private final RestTemplate restTemplate = new RestTemplate();
    public PageWrapper<GetProductResponse> getProducts(int page, int size) {
        String url = "http://localhost:8081/api/v1/product?page=" + page + "&size=" + size;

        ResponseEntity<PageWrapper<GetProductResponse>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<PageWrapper<GetProductResponse>>() {}
        );

//        System.out.println(response.getBody().getContent().toString());

        return response.getBody();
    }

    public GetDetailProductResponse getProductById(int id){
        String url = "http://localhost:8081/api/v1/product/"+id;

        ResponseEntity<GetDetailProductResponse> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<GetDetailProductResponse>() {}
        );
        return response.getBody();
    }
}
