package com.example.batch_insert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.batch_insert.model.ProductInsertHistory;

public interface ProductInsertHistoryRepo extends JpaRepository<ProductInsertHistory, Long> {

}
