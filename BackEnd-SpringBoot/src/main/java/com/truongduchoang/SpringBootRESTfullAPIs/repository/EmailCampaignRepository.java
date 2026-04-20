package com.truongduchoang.SpringBootRESTfullAPIs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truongduchoang.SpringBootRESTfullAPIs.models.EmailCampaign;

@Repository
public interface EmailCampaignRepository extends JpaRepository<EmailCampaign, Long> {
}
