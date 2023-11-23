package com.edstem.projecttracker.controller;

import com.edstem.projecttracker.contract.request.CategoryRequest;
import com.edstem.projecttracker.contract.response.CategoryResponse;
import com.edstem.projecttracker.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create")
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequestDto) {
        return categoryService.createCategory(categoryRequestDto);
    }

    @GetMapping("/view")
    public List<CategoryResponse> viewCategories() {
        return categoryService.viewCategories();
    }

    @PutMapping("/categories/{id}")
    public CategoryResponse updateCategory(
            @PathVariable Long id, @RequestBody CategoryRequest categoryRequestDto) {
        return categoryService.updateCategory(id, categoryRequestDto);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
