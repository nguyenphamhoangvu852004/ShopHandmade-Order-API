package com.example.ShopHandmade.repository.orderItem;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemRepositoryCus {
    @Autowired
    private EntityManager em;

    public String sayHelloOrder(){
        return "Hello Order";
    }
}
