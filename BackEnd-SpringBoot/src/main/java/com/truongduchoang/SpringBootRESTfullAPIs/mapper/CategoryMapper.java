package com.truongduchoang.SpringBootRESTfullAPIs.mapper;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.truongduchoang.SpringBootRESTfullAPIs.dto.request.CategoryCreateRequest;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.request.CategoryUpdateRequest;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.response.CategoryResponse;
import com.truongduchoang.SpringBootRESTfullAPIs.models.Category;
import com.truongduchoang.SpringBootRESTfullAPIs.models.enums.CategoryStatus;

@Component
public class CategoryMapper {
    public Category toEntity(CategoryCreateRequest request) {
        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        category.setStatus(request.getStatus() != null ? request.getStatus() : CategoryStatus.ACTIVE);
        return category;
    }

    public void updateEntity(Category category, CategoryUpdateRequest request) {
        if (StringUtils.hasText(request.getCategoryName())) {
            category.setCategoryName(request.getCategoryName());
        }
        if (request.getDescription() != null) {
            category.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            category.setStatus(request.getStatus());
        }
    }

    public CategoryResponse toResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setCategoryId(category.getCategoryId());
        response.setCategoryName(category.getCategoryName());
        response.setDescription(category.getDescription());
        response.setStatus(category.getStatus());
        response.setCreatedAt(category.getCreatedAt());
        return response;
    }
}
