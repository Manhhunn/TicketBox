package com.truongduchoang.SpringBootRESTfullAPIs.dto.response;

import java.time.LocalDateTime;

import com.truongduchoang.SpringBootRESTfullAPIs.models.enums.CategoryStatus;

public class CategoryResponse {
    private Long categoryId;
    private String categoryName;
    private String description;
    private CategoryStatus status;
    private LocalDateTime createdAt;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
