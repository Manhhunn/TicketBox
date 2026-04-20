package com.truongduchoang.SpringBootRESTfullAPIs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truongduchoang.SpringBootRESTfullAPIs.models.OrderCommission;

@Repository
public interface OrderCommissionRepository extends JpaRepository<OrderCommission, Long> {
}
