package com.example.batch_insert.insert_strategy;

import java.util.List;

import com.example.batch_insert.model.Product;
import com.example.batch_insert.shared.InsertResult;

public interface InsertStrategy {
    InsertResult saveAll(List<Product> products);
}
