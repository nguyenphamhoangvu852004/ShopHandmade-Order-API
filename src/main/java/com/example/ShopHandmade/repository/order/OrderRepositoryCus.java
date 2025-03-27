package com.example.ShopHandmade.repository.order;

import com.example.ShopHandmade.entity.OrderEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryCus{
    private EntityManager em;

    public OrderRepositoryCus(EntityManager em) {
        this.em = em;
    }
    @Transactional
    public List<OrderEntity> getAllOrdersByAccountId(short accountId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o FROM OrderEntity o WHERE o.accountId = :accountId");
        List<OrderEntity> listOrder = em.createQuery(sql.toString(), OrderEntity.class)
                .setParameter("accountId", accountId)
                .getResultList();
        if (listOrder.isEmpty()) {
            return null;
        }
        return listOrder;
    }

}
