package com.micropos.order.repository;

import com.micropos.model.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository {
    Flux<Order> findAll();

    Mono<Order> findById(Integer id);

    Mono<Order> addOrder(Order order);

    Mono<Boolean> deleteOrder(Integer id);
}
