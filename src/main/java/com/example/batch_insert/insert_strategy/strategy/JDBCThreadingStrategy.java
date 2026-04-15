package com.example.batch_insert.insert_strategy.strategy;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.batch_insert.insert_strategy.InsertStrategy;
import com.example.batch_insert.model.InsertType;
import com.example.batch_insert.model.Product;
import com.example.batch_insert.shared.InsertResult;
import com.example.batch_insert.util.ListUtil;

@Component("jdbcThreadingStrategy")
public class JDBCThreadingStrategy implements InsertStrategy{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_insert}")
    private int batchSize;

    private void saveAllJdbcBatch(List<Product> products) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO batch_insert.tbl_product (insert_history_id, title) VALUES (?, ?)",
            products,
            batchSize,
            (PreparedStatement ps, Product p) -> {
                ps.setInt(1, p.getProductInsertHistory().getId());
                ps.setString(2, p.getTitle());
            }
        );
    }
    
    // @Transactional
    @Override
    public InsertResult saveAll(List<Product> products) {

        
        List<List<Product>> productListOfSubList = ListUtil.createSubList(products, batchSize);
        
        int coreCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(coreCount);

        List<Callable<Void>> callables = productListOfSubList.stream()
            .map(subList -> (Callable<Void>) () -> {
                saveAllJdbcBatch(subList);
                return null;
            }).toList();
        LocalDateTime startAt = LocalDateTime.now();
        try {
            List<Future<Void>> futures = executorService.invokeAll(callables);

            for (Future<Void> future : futures) {
                future.get(); // blocking till each thread is completed
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            executorService.shutdown();
        }

        return new InsertResult(products.size(), startAt, LocalDateTime.now(), InsertType.JDBC_THREADING);
    }
}
