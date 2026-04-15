package com.example.batch_insert.insert_strategy.strategy;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.example.batch_insert.insert_strategy.InsertStrategy;
import com.example.batch_insert.model.InsertType;
import com.example.batch_insert.model.Product;
import com.example.batch_insert.repository.ProductRepo;
import com.example.batch_insert.shared.InsertResult;

@Component("hibernateInsertStrategy")
@Primary
public class HibernateInsertStrategy implements InsertStrategy {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public InsertResult saveAll(List<Product> products) {
        LocalDateTime startAt = LocalDateTime.now();

        productRepo.saveAll(products);
            
        return new InsertResult(products.size(), startAt, LocalDateTime.now(), InsertType.HIBERNATE);
    }
}
