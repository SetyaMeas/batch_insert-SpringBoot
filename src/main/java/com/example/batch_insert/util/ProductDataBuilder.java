package com.example.batch_insert.util;

import java.util.List;
import java.util.stream.IntStream;

import com.example.batch_insert.model.Product;
import com.example.batch_insert.model.ProductInsertHistory;

public class ProductDataBuilder {
    private int total;
    private ProductInsertHistory productInsertHistory;

    private ProductDataBuilder() {}

    public static ProductDataBuilder builder() {
        return new ProductDataBuilder();
    }

    public ProductDataBuilder setTotal(int total) {
        this.total = total;
        return this;
    }

    public ProductDataBuilder setProductInsertHistory(ProductInsertHistory productInsertHistory) {
        this.productInsertHistory = productInsertHistory;
        return this;
    }

    public List<Product> build() {
        return IntStream
            .range(1, total + 1)
            .mapToObj(i -> {
                Product product = new Product();
                product.setTitle("Product " + i);
                product.setProductInsertHistory(productInsertHistory);
                return product;
            })
            .toList();
    }
}
