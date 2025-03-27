package com.example.ShopHandmade.service.order;

import com.example.ShopHandmade.dto.order.GetAllOrderByAccountIdOutputDTO;
import com.example.ShopHandmade.dto.orderItem.GetAllOrderItemOutputDTO;
import com.example.ShopHandmade.dto.product.GetProductInfoOutputDTO;
import com.example.ShopHandmade.entity.OrderEntity;
import com.example.ShopHandmade.entity.OrderItemEntity;
import com.example.ShopHandmade.repository.order.IOrderRepository;
import com.example.ShopHandmade.repository.order.OrderRepositoryCus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements IOrderService {
    private RestTemplate restTemplate = new RestTemplate();
    private IOrderRepository orderRepository;
    private OrderRepositoryCus orderRepositoryCus;

    public OrderServiceImp(IOrderRepository orderRepository, OrderRepositoryCus orderRepositoryCus) {
        this.orderRepository = orderRepository;
        this.orderRepositoryCus = orderRepositoryCus;
    }

    @Override
    public Page<GetAllOrderByAccountIdOutputDTO> getAllOrdersByAccountId(short userId, Pageable pageable) {
        try {
            Page<OrderEntity> orderPage = this.orderRepositoryCus.getAllOrdersByAccountId(userId, pageable);

            List<GetAllOrderByAccountIdOutputDTO> listOrderDTO = orderPage.getContent().stream().map(order -> {
                List<GetAllOrderItemOutputDTO> listOrderItemDTO = order.getListOrderItems().stream()
                        .map(item -> {

                            String url = "http://localhost:8081/api/v1/product";

                            ResponseEntity<GetProductInfoOutputDTO> response = restTemplate
                                    .getForEntity(url + "/" + item.getProductId(), GetProductInfoOutputDTO.class);
                            GetProductInfoOutputDTO product = response.getBody();
                            System.out.println(product.toString());
                            return GetAllOrderItemOutputDTO.builder()
                                    .id(item.getId())
                                    .product(product)
                                    .quantity(item.getQuantity())
                                    .build();
                        })
                        .collect(Collectors.toList());

                return GetAllOrderByAccountIdOutputDTO.builder()
                        .id(order.getId())
                        .orderDate(order.getOrderDate())
                        .status(order.getStatus())
                        .listOrderItems(listOrderItemDTO)
                        .build();
            }).collect(Collectors.toList());

            return new PageImpl<>(listOrderDTO, pageable, orderPage.getTotalElements());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Page.empty();
        }
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        try {
            List<OrderEntity> orders = this.orderRepository.findAll();
            return orders;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public OrderEntity getOrderById(short orderId) {
        try {
            OrderEntity order = this.orderRepository.findById(orderId).get();
            return order;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public OrderEntity createOrder(OrderEntity order) {
        try {
            if (isOrderExist(order.getId()))
                throw new Exception("Order already exist");
            OrderEntity newOrder = this.orderRepository.save(order);
            return newOrder;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public OrderEntity updateOrder(OrderEntity order) {
        try {
            OrderEntity newOrder = this.orderRepository.save(order);
            return newOrder;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteOrder(short orderId) {
        try {
            this.orderRepository.deleteById(orderId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean isOrderExist(short orderId) {
        return this.orderRepository.existsById(orderId);
    }

}
