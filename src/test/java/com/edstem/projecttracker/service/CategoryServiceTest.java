package com.edstem.projecttracker.service;

import com.edstem.projecttracker.contract.request.CategoryRequest;
import com.edstem.projecttracker.contract.response.CategoryResponse;
import com.edstem.projecttracker.model.Category;
import com.edstem.projecttracker.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryServiceTest {
    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void testCreateCategory() {
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(new Category());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CategoryResponse>>any()))
                .thenReturn(CategoryResponse.builder().categoryId(1L).name("Name").build());
        categoryService.createCategory(new CategoryRequest("Name"));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<CategoryResponse>>any());
        verify(categoryRepository).save(Mockito.<Category>any());
    }

    @Test
    void testViewCategories() {
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
        List<CategoryResponse> actualViewCategoriesResult = categoryService.viewCategories();
        verify(categoryRepository).findAll();
        assertTrue(actualViewCategoriesResult.isEmpty());
    }
}
