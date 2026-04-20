package com.truongduchoang.SpringBootRESTfullAPIs.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.truongduchoang.SpringBootRESTfullAPIs.models.enums.CommissionConfigStatus;
import com.truongduchoang.SpringBootRESTfullAPIs.models.enums.CommissionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "commission_configs")
public class CommissionConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commission_id")
    private Long commissionId;

    @Column(name = "commission_name", nullable = false, length = 100)
    private String commissionName;

    @Enumerated(EnumType.STRING)
    @Column(name = "commission_type", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
    private CommissionType commissionType;

    @Column(name = "commission_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal commissionValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
    private CommissionConfigStatus status = CommissionConfigStatus.ACTIVE;

    @Column(name = "apply_from", nullable = false)
    private LocalDateTime applyFrom;

    @Column(name = "apply_to")
    private LocalDateTime applyTo;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "commissionConfig")
    @JsonIgnore
    private List<OrderCommission> orderCommissions = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = CommissionConfigStatus.ACTIVE;
        }
    }

    public Long getCommissionId() {
        return commissionId;
    }

    public void setCommissionId(Long commissionId) {
        this.commissionId = commissionId;
    }

    public String getCommissionName() {
        return commissionName;
    }

    public void setCommissionName(String commissionName) {
        this.commissionName = commissionName;
    }

    public CommissionType getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(CommissionType commissionType) {
        this.commissionType = commissionType;
    }

    public BigDecimal getCommissionValue() {
        return commissionValue;
    }

    public void setCommissionValue(BigDecimal commissionValue) {
        this.commissionValue = commissionValue;
    }

    public CommissionConfigStatus getStatus() {
        return status;
    }

    public void setStatus(CommissionConfigStatus status) {
        this.status = status;
    }

    public LocalDateTime getApplyFrom() {
        return applyFrom;
    }

    public void setApplyFrom(LocalDateTime applyFrom) {
        this.applyFrom = applyFrom;
    }

    public LocalDateTime getApplyTo() {
        return applyTo;
    }

    public void setApplyTo(LocalDateTime applyTo) {
        this.applyTo = applyTo;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderCommission> getOrderCommissions() {
        return orderCommissions;
    }

    public void setOrderCommissions(List<OrderCommission> orderCommissions) {
        this.orderCommissions = orderCommissions;
    }
}
