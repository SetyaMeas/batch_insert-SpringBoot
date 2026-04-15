package com.example.batch_insert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.batch_insert.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
