package com.example.ShopHandmade.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageWrapper<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private boolean last;
    private boolean first;
    private int size;
    private int number;

}
