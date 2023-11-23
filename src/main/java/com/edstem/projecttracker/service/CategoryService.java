package com.edstem.projecttracker.service;

import com.edstem.projecttracker.contract.request.CategoryRequest;
import com.edstem.projecttracker.contract.response.CategoryResponse;
import com.edstem.projecttracker.expection.EntityNotFoundException;
import com.edstem.projecttracker.model.Category;
import com.edstem.projecttracker.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryResponse createCategory(CategoryRequest categoryRequestDto) {
        Category category = Category.builder().name(categoryRequestDto.getName()).build();
        category = categoryRepository.save(category);
        return convertToDto(category);
    }

    public CategoryResponse convertToDto(Category category) {
        return modelMapper.map(category, CategoryResponse.class);
    }

    public List<CategoryResponse> viewCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequestDto) {
        Category category =
                categoryRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        category.setName(categoryRequestDto.getName());
        category = categoryRepository.save(category);
        return convertToDto(category);
    }

    public void deleteCategory(Long id) {
        Category category =
                categoryRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Category not found", +id));
        categoryRepository.delete(category);
    }
}
