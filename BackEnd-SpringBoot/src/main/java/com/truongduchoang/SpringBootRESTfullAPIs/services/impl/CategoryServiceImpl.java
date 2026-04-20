package com.truongduchoang.SpringBootRESTfullAPIs.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.truongduchoang.SpringBootRESTfullAPIs.dto.request.CategoryCreateRequest;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.request.CategoryUpdateRequest;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.response.CategoryResponse;
import com.truongduchoang.SpringBootRESTfullAPIs.errors.BadRequestException;
import com.truongduchoang.SpringBootRESTfullAPIs.errors.DuplicateResourceException;
import com.truongduchoang.SpringBootRESTfullAPIs.errors.ResourceNotFoundException;
import com.truongduchoang.SpringBootRESTfullAPIs.mapper.CategoryMapper;
import com.truongduchoang.SpringBootRESTfullAPIs.models.Category;
import com.truongduchoang.SpringBootRESTfullAPIs.repository.CategoryRepository;
import com.truongduchoang.SpringBootRESTfullAPIs.repository.EventRepository;
import com.truongduchoang.SpringBootRESTfullAPIs.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            EventRepository eventRepository,
            CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        if (categoryRepository.existsByCategoryNameIgnoreCase(request.getCategoryName())) {
            throw new DuplicateResourceException("Category name already exists", "CATEGORY_NAME_ALREADY_EXISTS");
        }
        Category category = categoryMapper.toEntity(request);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.toResponse(findCategoryById(id));
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
        Category category = findCategoryById(id);

        if (request.getCategoryName() != null && !StringUtils.hasText(request.getCategoryName())) {
            throw new BadRequestException("Category name cannot be blank", "CATEGORY_NAME_BLANK");
        }
        if (StringUtils.hasText(request.getCategoryName())
                && !request.getCategoryName().equalsIgnoreCase(category.getCategoryName())
                && categoryRepository.existsByCategoryNameIgnoreCaseAndCategoryIdNot(request.getCategoryName(), id)) {
            throw new DuplicateResourceException("Category name already exists", "CATEGORY_NAME_ALREADY_EXISTS");
        }

        categoryMapper.updateEntity(category, request);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = findCategoryById(id);
        if (eventRepository.existsByCategoryCategoryId(id)) {
            throw new BadRequestException("Category is being used by events", "CATEGORY_IN_USE");
        }
        categoryRepository.delete(category);
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found", "CATEGORY_NOT_FOUND"));
    }
}
