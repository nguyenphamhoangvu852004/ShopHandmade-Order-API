package com.example.ShopHandmade.service.order;

import com.example.ShopHandmade.dto.order.AccountOutputDTO;
import com.example.ShopHandmade.dto.order.CreateOrderInputDTO;
import com.example.ShopHandmade.dto.order.GetAllOrderByAccountIdOutputDTO;
import com.example.ShopHandmade.dto.order.GetAllOrderOutputDTO;
import com.example.ShopHandmade.dto.order.GetDetailOrderOutputDTO;
import com.example.ShopHandmade.dto.orderItem.CreateOrderItemInputDTO;
import com.example.ShopHandmade.dto.orderItem.GetAllOrderItemOutputDTO;
import com.example.ShopHandmade.dto.orderItem.GetDetailOrderItemOutputDTO;
import com.example.ShopHandmade.dto.product.GetProductInfoOutputDTO;
import com.example.ShopHandmade.entity.OrderEntity;
import com.example.ShopHandmade.entity.OrderItemEntity;
import com.example.ShopHandmade.entity.OrderEntity.ORDER_STATUS;
import com.example.ShopHandmade.repository.order.IOrderRepository;
import com.example.ShopHandmade.repository.order.OrderRepositoryCus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements IOrderService {

    @Value("${product.service.url}")
    private String productServiceUrl;
    @Value("${account.service.url}")
    private String accountServiceUrl;

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
                ResponseEntity<AccountOutputDTO> accountResponse = restTemplate
                        .getForEntity(accountServiceUrl + "/user-by?id=" + String.valueOf(userId),
                                AccountOutputDTO.class);
                AccountOutputDTO account = accountResponse.getBody();

                List<GetAllOrderItemOutputDTO> listOrderItemDTO = order.getListOrderItems().stream()
                        .map(item -> {
                            ResponseEntity<GetProductInfoOutputDTO> response = restTemplate
                                    .getForEntity(productServiceUrl + "/" + item.getProductId(),
                                            GetProductInfoOutputDTO.class);
                            GetProductInfoOutputDTO product = response.getBody();
                            return GetAllOrderItemOutputDTO.builder()
                                    .id(item.getId())
                                    .product(product)
                                    .quantity(item.getQuantity())
                                    .totalPrice(product.getPrice() * item.getQuantity())
                                    .build();
                        })
                        .collect(Collectors.toList());

                return GetAllOrderByAccountIdOutputDTO.builder()
                        .id(order.getId())
                        .orderDate(order.getOrderDate())
                        .status(order.getStatus())
                        .address(order.getAddress())
                        .account(account)
                        .totalAmount(

                                listOrderItemDTO.stream().mapToDouble(item -> item.getTotalPrice()).sum())
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
    public Page<GetAllOrderOutputDTO> getAllOrders(Pageable pageable) {
        try {
            List<OrderEntity> listOrder = this.orderRepository.findAll(pageable).getContent();
            List<GetAllOrderOutputDTO> listOrderDTO = new ArrayList<>();
            for (OrderEntity orderEntity : listOrder) {
                List<GetAllOrderItemOutputDTO> listOrderItemDTO = orderEntity.getListOrderItems().stream()
                        .map(item -> {
                            ResponseEntity<GetProductInfoOutputDTO> response = restTemplate
                                    .getForEntity(productServiceUrl + "/" + item.getProductId(),
                                            GetProductInfoOutputDTO.class);
                            GetProductInfoOutputDTO product = response.getBody();
                            return GetAllOrderItemOutputDTO.builder()
                                    .id(item.getId())
                                    .product(product)
                                    .totalPrice(product.getPrice() * item.getQuantity())
                                    .quantity(item.getQuantity())
                                    .build();
                        })
                        .collect(Collectors.toList());

                listOrderDTO.add(GetAllOrderOutputDTO.builder()
                        .id(orderEntity.getId())
                        .orderDate(orderEntity.getOrderDate())
                        .status(orderEntity.getStatus())
                        .address(orderEntity.getAddress())
                        .listOrderItems(listOrderItemDTO)
                        .totalAmount(
                                listOrderItemDTO.stream().mapToDouble(item -> item.getTotalPrice()).sum())
                        .build());
            }
            if (listOrderDTO.isEmpty()) {
                return new PageImpl<>(new ArrayList<>(), pageable, 0);
            } else {
                return new PageImpl<>(listOrderDTO, pageable, this.orderRepository.count());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public OrderEntity getOrderById(short orderId) {
        try {
            OrderEntity order = this.orderRepository.findById(orderId).get();
            if (order == null) {
                return null;
            }
            return order;
        } catch (Exception e) {
            org.slf4j.Logger logger = LoggerFactory.getLogger(OrderServiceImp.class);
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public String createOrder(short accountId, CreateOrderInputDTO order) {
        try {
            List<CreateOrderItemInputDTO> listOrderItemInputDTO = order.getListOrderItem();
            List<OrderItemEntity> listOrderItemEntity = new ArrayList<>();

            OrderEntity newOrder = OrderEntity.builder()
                    .accountId(accountId)
                    .orderDate(LocalDateTime.now())
                    .address(order.getAddress())
                    .status(ORDER_STATUS.PENDING)
                    .build();

            for (CreateOrderItemInputDTO item : listOrderItemInputDTO) {
                String url = productServiceUrl + "/" + item.getProductId();
                ResponseEntity<GetProductInfoOutputDTO> response = restTemplate.getForEntity(url,
                        GetProductInfoOutputDTO.class);

                if (response.getBody() == null) {
                    return "Product not found: " + item.getProductId();
                }

                listOrderItemEntity.add(OrderItemEntity.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .order(newOrder)
                        .build());
            }

            newOrder.setListOrderItems(listOrderItemEntity);

            this.orderRepository.save(newOrder);

            return "Created";
        } catch (Exception e) {
            return "Failed";
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

    @Override
    public String updateStatusByOrderId(short orderId, String status) {
        try {
            boolean isExist = this.orderRepository.existsById(orderId);
            if (!isExist) {
                return "Order not found";
            }
            return this.orderRepositoryCus.updateStatusByOrderId(orderId, status);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public GetDetailOrderOutputDTO getDetailOrderByOrderId(short orderId) {
        OrderEntity order = this.orderRepository.findById(orderId).get();

        if (order == null) {
            return null;
        }

        GetDetailOrderOutputDTO orderDTO = GetDetailOrderOutputDTO.builder()
                .id(orderId)
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .address(order.getAddress())
                .listDetailOrderItem(new ArrayList<>())
                .totalAmount(0.0)
                .build();

        List<GetDetailOrderItemOutputDTO> listOrderItem = order.getListOrderItems().stream()
                .map(
                        item -> {
                            ResponseEntity<GetProductInfoOutputDTO> response = restTemplate
                                    .getForEntity(productServiceUrl + "/" + item.getProductId(),
                                            GetProductInfoOutputDTO.class);
                            GetProductInfoOutputDTO product = response.getBody();
                            return GetDetailOrderItemOutputDTO.builder()
                                    .id(item.getId())
                                    .product(product)
                                    .quantity(item.getQuantity())
                                    .totalPrice(product.getPrice() * item.getQuantity())
                                    .build();
                        })
                .collect(Collectors.toList());

        orderDTO.setListDetailOrderItem(listOrderItem);

        orderDTO.setTotalAmount(listOrderItem.stream().mapToDouble(item -> item.getTotalPrice()).sum());

        return orderDTO;
    }

}
