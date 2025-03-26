package com.example.ShopHandmade.service;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetDetailProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private Inventory inventory;
    private List<Image> images;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Inventory {
        private Long id;
        private Integer stock;
        private String status;
        private String updatedAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Image {
        private Long id;
        private String src;
        private String alt;
        private Integer position;
    }
}

