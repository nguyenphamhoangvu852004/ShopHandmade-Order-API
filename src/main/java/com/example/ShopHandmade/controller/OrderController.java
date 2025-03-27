package com.example.ShopHandmade.controller;

import com.example.ShopHandmade.dto.order.GetAllOrderByAccountIdOutputDTO;
import com.example.ShopHandmade.service.order.IOrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
public class OrderController {

    private IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/order/{accountId}")
    public ResponseEntity<List<GetAllOrderByAccountIdOutputDTO>> getAllOrderByAccountId(
            @PathVariable("accountId") short accountId) {
        Short id = accountId;
        List<GetAllOrderByAccountIdOutputDTO> listOrder = this.orderService.getAllOrdersByAccountId(id);
        return ResponseEntity.ok(listOrder);
    }

}
