package com.example.ShopHandmade.controller;

import com.example.ShopHandmade.dto.order.CreateOrderInputDTO;
import com.example.ShopHandmade.dto.order.GetAllOrderByAccountIdOutputDTO;
import com.example.ShopHandmade.dto.order.UpdateStatusOrderInputDTO;
import com.example.ShopHandmade.service.order.IOrderService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        String result = this.orderService.createOrder(accountId, createOrderInputDTO);
        if (result.equals("Created")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/api/order/status")
    public ResponseEntity<String> updateStatusOrderById(
            @RequestBody UpdateStatusOrderInputDTO updateStatusOrderInputDTO) {
        String result = this.orderService.updateStatusByOrderId(updateStatusOrderInputDTO.getOrderId(),
                updateStatusOrderInputDTO.getStatus());
        if (result.equals("Update status order success")) {
            return ResponseEntity.ok(result);
        }
        if (result.equals("Order not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

}
