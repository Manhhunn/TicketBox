package com.truongduchoang.SpringBootRESTfullAPIs.dto.request;

import com.truongduchoang.SpringBootRESTfullAPIs.models.enums.CategoryStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryCreateRequest {
    @NotBlank(message = "Tên danh mục không được bỏ trống")
    @Size(max = 100, message = "Tên danh mục không được vượt quá 100 ký tự")
    private String categoryName;

    @Size(max = 255, message = "Mô tả danh mục không được vượt quá 255 ký tự")
    private String description;

    private CategoryStatus status;

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
}
