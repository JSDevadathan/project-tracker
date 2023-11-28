package com.edstem.projecttracker.controller;

import com.edstem.projecttracker.contract.request.CategoryRequest;
import com.edstem.projecttracker.contract.response.CategoryResponse;
import com.edstem.projecttracker.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create")
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @GetMapping("/view")
    public List<CategoryResponse> viewCategories() {
        return categoryService.viewCategories();
    }
}
