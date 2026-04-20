package com.truongduchoang.SpringBootRESTfullAPIs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truongduchoang.SpringBootRESTfullAPIs.models.EventApproval;

@Repository
public interface EventApprovalRepository extends JpaRepository<EventApproval, Long> {
}
