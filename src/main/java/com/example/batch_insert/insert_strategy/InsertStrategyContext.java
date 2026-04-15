package com.example.batch_insert.insert_strategy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.batch_insert.model.Product;
import com.example.batch_insert.model.ProductInsertHistory;
import com.example.batch_insert.repository.ProductInsertHistoryRepo;
import com.example.batch_insert.shared.InsertResult;
import com.example.batch_insert.util.ProductDataBuilder;

import lombok.Getter;

@Component
@Scope("prototype")
public class InsertStrategyContext {

    private InsertStrategy insertStrategy;

    @Getter
    private InsertResult insertResult;

    @Autowired
    private ProductInsertHistoryRepo productInsertHistoryRepo;

    public InsertStrategyContext() {}

    public void setStrategy(InsertStrategy insertStrategy) {
        this.insertStrategy = insertStrategy;
    }

    public void executeSaveAll(int totalProduct) {
        ProductInsertHistory history = createHistory();

        List<Product> products = ProductDataBuilder.builder()
            .setProductInsertHistory(history)
            .setTotal(totalProduct)
            .build();

        insertResult = insertStrategy.saveAll(products);

        updateHistory(history);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private ProductInsertHistory createHistory() {
        ProductInsertHistory history = new ProductInsertHistory();
        history = productInsertHistoryRepo.save(history);

        return history;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void updateHistory(ProductInsertHistory history) {
        history.setInsertType(insertResult.getInsertType().toString());
        history.setStartAt(insertResult.getStartAt());
        history.setEndAt(insertResult.getEndAt());
        
        productInsertHistoryRepo.save(history);
    }
}
