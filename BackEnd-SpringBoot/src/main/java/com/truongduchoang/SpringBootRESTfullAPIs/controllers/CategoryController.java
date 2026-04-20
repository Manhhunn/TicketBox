package com.truongduchoang.SpringBootRESTfullAPIs.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.truongduchoang.SpringBootRESTfullAPIs.dto.request.CategoryCreateRequest;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.request.CategoryUpdateRequest;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.response.CategoryResponse;
import com.truongduchoang.SpringBootRESTfullAPIs.models.ApiResponse;
import com.truongduchoang.SpringBootRESTfullAPIs.services.CategoryService;

import jakarta.validation.Valid;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/api/categories")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CategoryCreateRequest request) {
        CategoryResponse category = categoryService.createCategory(request);
        ApiResponse<CategoryResponse> result = new ApiResponse<>(
                HttpStatus.CREATED,
                "Create category successfully",
                category,
                null);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/api/categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        ApiResponse<List<CategoryResponse>> result = new ApiResponse<>(
                HttpStatus.OK,
                "Get categories successfully",
                categories,
                null);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/categories/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        CategoryResponse category = categoryService.getCategoryById(id);
        ApiResponse<CategoryResponse> result = new ApiResponse<>(
                HttpStatus.OK,
                "Get category successfully",
                category,
                null);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/api/categories/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryUpdateRequest request) {
        CategoryResponse category = categoryService.updateCategory(id, request);
        ApiResponse<CategoryResponse> result = new ApiResponse<>(
                HttpStatus.OK,
                "Update category successfully",
                category,
                null);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/categories/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        ApiResponse<Void> result = new ApiResponse<>(
                HttpStatus.NO_CONTENT,
                "Delete category successfully",
                null,
                null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
    }
}
