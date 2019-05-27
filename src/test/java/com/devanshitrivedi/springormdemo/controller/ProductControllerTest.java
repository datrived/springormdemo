package com.devanshitrivedi.springormdemo.controller;

import com.devanshitrivedi.springormdemo.entity.Product;
import com.devanshitrivedi.springormdemo.service.ProductService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductControllerTest {

    private static Product p1;
    private static Product p2;
    private static Product p3;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        p1 = new Product("P1", "Created Dummy Product", 100);
        p2 = new Product("P2", "Created For Update Dummy Product", 200);
        p3 = new Product("P3", "Updated Dummy Product",300);

    }

    @Test
    void findAll_whenNoRecord() {

        Mockito.when(productService.findAll()).thenReturn(Arrays.asList());
        assertThat(productController.findAll().size(), is(0));
        Mockito.verify(productService, Mockito.times(1)).findAll();
    }

    @Test
    void findAll_whenRecord() {

        Mockito.when(productService.findAll()).thenReturn(Arrays.asList(p1, p2));
        assertThat(productController.findAll().size(), is(2));
        Mockito.verify(productService, Mockito.times(1)).findAll();
    }

    @Test
    void create() {

        ResponseEntity<Product> p = productController.create(p1);
        Mockito.verify(productService, Mockito.times(1)).createOrUpdate(p1);
    }

    @Test
    void findById_WhenMatch() {

        Mockito.when(productService.findById(1L)).thenReturn(Optional.of(p1));
        ResponseEntity<Product> p = productController.findById(1L);
        assertThat(p.getBody(), is(p1) );
    }

    @Test
    void findById_WhenNoMatch() {

        Mockito.when(productService.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Product> p = productController.findById(1L);
        assertThat(p.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void update_WhenNotFound() {

        Mockito.when(productService.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Product> p = productController.update(1L, p1);
        assertThat(p.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void update_WhenFound() {

        Mockito.when(productService.findById(1L)).thenReturn(Optional.of(p1));

        //Since the Controller internally saves p1 taking args of p3.
        Mockito.when(productService.createOrUpdate(p1)).thenReturn(p3);
        assertThat(productController.update(1L, p3).getBody().getName(), is("P3"));
        Mockito.verify(productService, Mockito.times(1)).createOrUpdate(p1);
    }

    @Test
    void deleteById_WhenNotFound() {

        Mockito.when(productService.findById(1L)).thenReturn(Optional.empty());
        productController.deleteById(1L);
        Mockito.verify(productService, Mockito.times(0)).deleteById(1L);
    }

    @Test
    void deleteById_WhenFound() {

        Mockito.when(productService.findById(1L)).thenReturn(Optional.of(p1));
        productController.deleteById(1L);
        Mockito.verify(productService, Mockito.times(1)).deleteById(1L);

    }
}