package com.micropos.products.repository;

import com.micropos.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Flux<Product> allProducts();

    Mono<Product> findProduct(String productId);
}