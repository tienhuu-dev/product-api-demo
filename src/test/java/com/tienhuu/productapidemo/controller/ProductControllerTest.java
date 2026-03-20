package com.tienhuu.productapidemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienhuu.productapidemo.entity.Product;
import com.tienhuu.productapidemo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean  // ← thay MockBean bằng MockitoBean
    private ProductService productService;

    private Product input;
    private Product saved;

    @BeforeEach
    void initData() {
        input = new Product();
        input.setName("Keyboard");
        input.setPrice(80.0);
        input.setQuantity(30);

        saved = new Product();
        saved.setId(1L);
        saved.setName("Keyboard");
        saved.setPrice(80.0);
        saved.setQuantity(30);
    }

    @Test
    void createProduct_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(input);

        Mockito.when(productService.createProduct(ArgumentMatchers.any()))
                .thenReturn(saved);

        // WHEN & THEN
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Keyboard"));
    }

    @Test
    void getAllProducts_success() throws Exception {
        // GIVEN
        Product p = new Product();
        p.setId(1L);
        p.setName("Laptop");
        p.setPrice(1500.0);
        p.setQuantity(10);

        Mockito.when(productService.getAllProducts())
                .thenReturn(List.of(p));

        // WHEN & THEN
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"));
    }

    @Test
    void getAllProducts_wrongName_fail() throws Exception {
        // GIVEN
        Product p = new Product();
        p.setId(1L);
        p.setName("Laptop");

        Mockito.when(productService.getAllProducts())
                .thenReturn(List.of(p));

        // WHEN & THEN
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                // Sai cố tình → test này sẽ fail khi chạy
                .andExpect(jsonPath("$[0].name").value("Ahihi"));
    }
}
