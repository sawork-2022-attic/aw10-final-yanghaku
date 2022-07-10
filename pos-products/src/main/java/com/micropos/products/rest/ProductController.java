package com.micropos.products.rest;

import com.micropos.api.ProductsApi;
import com.micropos.dto.ProductDto;
import com.micropos.mapper.ProductMapper;
import com.micropos.products.service.ProductService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class ProductController implements ProductsApi {

    private final ProductMapper productMapper;

    private final ProductService productService;


    public ProductController(@Autowired ProductService productService) {
        this.productMapper = Mappers.getMapper(ProductMapper.class);
        this.productService = productService;
    }

    @Override
    public Mono<ResponseEntity<Flux<ProductDto>>> listProducts(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(productService.products().map(productMapper::toProductDto)));
    }

    @Override
    public Mono<ResponseEntity<ProductDto>> showProductById(String productId, ServerWebExchange exchange) {
        return productService.getProduct(productId).map(productMapper::toProductDto).map(ResponseEntity::ok);
    }
}
