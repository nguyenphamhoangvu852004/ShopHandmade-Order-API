package com.example.ShopHandmade.controller;

import com.example.ShopHandmade.dto.order.CreateOrderInputDTO;
import com.example.ShopHandmade.dto.order.DeleteOrderOutputDTO;
import com.example.ShopHandmade.dto.order.GetAllOrderByAccountIdOutputDTO;
import com.example.ShopHandmade.dto.order.GetAllOrderOutputDTO;
import com.example.ShopHandmade.dto.order.GetDetailOrderOutputDTO;
import com.example.ShopHandmade.dto.order.UpdateStatusOrderInputDTO;
import com.example.ShopHandmade.service.order.IOrderService;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.None;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    // lấ tất cả order theo accountId
    @GetMapping("/api/order/{accountId}")
    public ResponseEntity<Page<GetAllOrderByAccountIdOutputDTO>> getAllOrderByAccountId(
            @PathVariable("accountId") short accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "orderDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        Short id = accountId;
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<GetAllOrderByAccountIdOutputDTO> listOrder = this.orderService.getAllOrdersByAccountId(id, pageable);
        return ResponseEntity.ok(listOrder);
    }

    // tạo order moi
    @PostMapping("/api/order/{accountId}")
    public ResponseEntity<String> createOrder(@PathVariable("accountId") int accountId,
            @RequestBody CreateOrderInputDTO createOrderInputDTO) {

        System.out.println("Req nhan vao trong api ne" + createOrderInputDTO.toString());
        String result = this.orderService.createOrder(Short.parseShort(String.valueOf(accountId)), createOrderInputDTO);
        if (result.equals("Created")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    // cập nhật trạng thái đơn hàng
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

    // lấy tất cả luôn
    @GetMapping("/api/order")
    public ResponseEntity<?> getAllOrder(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "orderDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {

        Sort sort = Sort.by(sortDir.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<GetAllOrderOutputDTO> listOrder = this.orderService.getAllOrders(pageable);
        // Page<GetAllOrderOutputDTO> listOrder = null;

        if (listOrder == null || listOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("List order is empty");
        }

        return ResponseEntity.ok(listOrder);
    }

    // Lấy chi tiết 1 order
    @GetMapping("/api/order/detail/{orderId}")
    public ResponseEntity<GetDetailOrderOutputDTO> getDetailOrder(@PathVariable("orderId") short orderId) {
        GetDetailOrderOutputDTO order = this.orderService.getDetailOrderByOrderId(orderId);

        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/api/order/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable("orderId") short orderId) {
        DeleteOrderOutputDTO order = this.orderService.deleteOrder(orderId);
        if (order.getMessage().equals("Failed")) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(order.getMessage());

    }
}
