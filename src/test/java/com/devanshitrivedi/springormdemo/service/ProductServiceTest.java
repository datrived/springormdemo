package com.devanshitrivedi.springormdemo.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import com.devanshitrivedi.springormdemo.entity.Product;
import com.devanshitrivedi.springormdemo.repository.ProductRepository;
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
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductServiceTest {

    private static Product p1;
    private static Product p2;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        p1 = new Product("P1", "Created Dummy Product", 100);
        p2 = new Product("P2", "Created For Update Dummy Product", 200);
    }

    @Test
    public void findAllTest_WhenNoRecord() {

       Mockito.when(productRepository.findAll()).thenReturn(Arrays.asList());
       assertThat(productService.findAll().size(), is(0));
       Mockito.verify(productRepository, Mockito.times(1)).findAll();

    }

    @Test
    public void findAllTest_WhenRecord() {

        Mockito.when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));
        assertThat(productService.findAll().size(), is(2));
        assertThat(productService.findAll().get(0), is(p1));
        assertThat(productService.findAll().get(1),is(p2));
        Mockito.verify(productRepository, Mockito.times(3)).findAll();

    }

    @Test
    public void findById() {

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(p1));
        assertThat(productService.findById(1L), is(Optional.of(p1)));
        Mockito.verify(productRepository, Mockito.times(1)).findById(1L);
    }


    @Test
    void createOrUpdate() {

        Mockito.when(productRepository.save(p1)).thenReturn(p1);
        assertThat(productService.createOrUpdate(p1), is(p1));
        Mockito.verify(productRepository, Mockito.times(1)).save(p1);

        Mockito.when(productRepository.save(p2)).thenReturn(p2);
        assertThat(productService.createOrUpdate(p2).getName(), is("P2"));
        Mockito.verify(productRepository, Mockito.times(1)).save(p2);
    }

    @Test
    void deleteById() {
        productService.deleteById(1L);
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(1L);
    }
}