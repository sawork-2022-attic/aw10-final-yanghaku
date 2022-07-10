package com.micropos.products.service;

import com.micropos.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<Product> products();

    Mono<Product> getProduct(String id);

    Mono<Product> randomProduct();
}
