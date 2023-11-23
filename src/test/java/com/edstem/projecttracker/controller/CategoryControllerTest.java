package com.edstem.projecttracker.controller;

import com.edstem.projecttracker.contract.request.CategoryRequest;
import com.edstem.projecttracker.contract.response.CategoryResponse;
import com.edstem.projecttracker.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {
    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    @Test
    void testCreateCategory() throws Exception {
        when(categoryService.createCategory(Mockito.<CategoryRequest>any()))
                .thenReturn(CategoryResponse.builder().categoryId(1L).name("Name").build());

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(categoryRequest);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content()
                                .string("{\"categoryId\":1,\"name\":\"Name\"}"));
    }

    @Test
    void testViewCategories() throws Exception {
        when(categoryService.viewCategories()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/category/view");
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testUpdateCategory() throws Exception {
        when(categoryService.updateCategory(Mockito.<Long>any(), Mockito.<CategoryRequest>any()))
                .thenReturn(CategoryResponse.builder().categoryId(1L).name("Name").build());

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(categoryRequest);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.put("/category/categories/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content()
                                .string("{\"categoryId\":1,\"name\":\"Name\"}"));
    }

    @Test
    void testDeleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.delete("/category/categories/{id}", 1L);
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
