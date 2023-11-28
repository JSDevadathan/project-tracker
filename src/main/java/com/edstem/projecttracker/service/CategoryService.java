package com.edstem.projecttracker.service;

import com.edstem.projecttracker.contract.request.CategoryRequest;
import com.edstem.projecttracker.contract.response.CategoryResponse;
import com.edstem.projecttracker.model.Category;
import com.edstem.projecttracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder().name(categoryRequest.getName()).build();
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryResponse.class);
    }

    public List<CategoryResponse> viewCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }
}
