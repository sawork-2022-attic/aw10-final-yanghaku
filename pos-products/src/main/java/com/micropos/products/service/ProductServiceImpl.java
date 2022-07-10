package com.micropos.products.service;

import com.micropos.model.Product;
import com.micropos.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.Cacheable;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable("products")
    public Flux<Product> products() {
        return productRepository.allProducts();
    }

    @Override
    public Mono<Product> getProduct(String id) {
        return productRepository.findProduct(id);
    }

    @Override
    public Mono<Product> randomProduct() {
        return products().last();
    }
}
