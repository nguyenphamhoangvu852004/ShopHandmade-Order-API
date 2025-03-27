package com.example.ShopHandmade.repository.order;

import com.example.ShopHandmade.entity.OrderEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryCus {
    private EntityManager em;

    public OrderRepositoryCus(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Page<OrderEntity> getAllOrdersByAccountId(short accountId, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o FROM OrderEntity o WHERE o.accountId = :accountId");

        List<OrderEntity> listOrder = em.createQuery(sql.toString(), OrderEntity.class)
                .setParameter("accountId", accountId)
                .setFirstResult((int) pageable.getOffset()) // Phân trang
                .setMaxResults(pageable.getPageSize()) // Giới hạn số kết quả
                .getResultList();

        long totalOrders = (long) em.createQuery("SELECT COUNT(o) FROM OrderEntity o WHERE o.accountId = :accountId")
                .setParameter("accountId", accountId)
                .getSingleResult();

        return new PageImpl<>(listOrder, pageable, totalOrders);
    }

}
