package com.truongduchoang.SpringBootRESTfullAPIs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truongduchoang.SpringBootRESTfullAPIs.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderCode(String orderCode);

    boolean existsByOrderCode(String orderCode);

    boolean existsByEventEventId(Long eventId);
}
