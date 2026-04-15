package com.example.batch_insert.insert_strategy.strategy;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.batch_insert.insert_strategy.InsertStrategy;
import com.example.batch_insert.model.InsertType;
import com.example.batch_insert.model.Product;
import com.example.batch_insert.shared.InsertResult;

@Component("jdbcInsertStrategy")
public class JDBCInsertStrategy implements InsertStrategy {

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_insert}")
    private int batchSize;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public InsertResult saveAll(List<Product> products) {
        String sql = "INSERT INTO batch_insert.tbl_product (insert_history_id, title) VALUES (?, ?)";

        LocalDateTime startAt = LocalDateTime.now();

        jdbcTemplate.batchUpdate(
            sql,
            products,
            batchSize,
            (PreparedStatement ps, Product p) -> {
                ps.setInt(1, p.getProductInsertHistory().getId());
                ps.setString(2, p.getTitle());
            }
        );

        return new InsertResult(products.size(), startAt, LocalDateTime.now(), InsertType.JDBC);
    }

}
