package com.example.ShopHandmade.controller;

import com.example.ShopHandmade.dto.order.CreateOrderInputDTO;
import com.example.ShopHandmade.dto.order.GetAllOrderByAccountIdOutputDTO;
import com.example.ShopHandmade.service.order.IOrderService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("")
public class OrderController {

    private IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/order/{accountId}")
    public ResponseEntity<Page<GetAllOrderByAccountIdOutputDTO>> getAllOrderByAccountId(
            @PathVariable("accountId") short accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Short id = accountId;
        Pageable pageable = PageRequest.of(page, size);
        Page<GetAllOrderByAccountIdOutputDTO> listOrder = this.orderService.getAllOrdersByAccountId(id, pageable);
        return ResponseEntity.ok(listOrder);
    }

    @PostMapping("/api/order/{accountId}")
    public ResponseEntity<String> createOrder(@PathVariable("accountId") short accountId,
            @RequestBody CreateOrderInputDTO createOrderInputDTO) {
        System.out.println("accountId: " + accountId);
        System.out.println(createOrderInputDTO.toString());

        return ResponseEntity.ok("Created successfully");
    }

}
