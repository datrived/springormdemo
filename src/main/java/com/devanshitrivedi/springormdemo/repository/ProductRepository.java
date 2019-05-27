package com.devanshitrivedi.springormdemo.repository;

import com.devanshitrivedi.springormdemo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
