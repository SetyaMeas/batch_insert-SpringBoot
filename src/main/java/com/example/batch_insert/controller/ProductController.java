package com.example.batch_insert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.batch_insert.insert_strategy.InsertStrategy;
import com.example.batch_insert.insert_strategy.InsertStrategyContext;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private InsertStrategyContext context;

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("hibernate")
    public ResponseEntity<?> hibernateSaveAll(@RequestParam int total) {
        context.setStrategy(
            applicationContext.getBean(
                "hibernateInsertStrategy",
                InsertStrategy.class
            )
        );
        context.executeSaveAll(total);

        return ResponseEntity.ok(context.getInsertResult());
    }

    @PostMapping("jdbc")
    public ResponseEntity<?> jdbcSaveAll(@RequestParam int total) {
        context.setStrategy(
            applicationContext.getBean(
                "jdbcInsertStrategy",
                InsertStrategy.class
            )
        );
        context.executeSaveAll(total);

        return ResponseEntity.ok(context.getInsertResult());
    }

    @PostMapping("jdbc-threading")
    public ResponseEntity<?> jdbcThreading(@RequestParam int total) {
        context.setStrategy(
            applicationContext.getBean(
                "jdbcThreadingStrategy",
                InsertStrategy.class
            )
        );
        context.executeSaveAll(total);

        return ResponseEntity.ok(context.getInsertResult());
    }
}
