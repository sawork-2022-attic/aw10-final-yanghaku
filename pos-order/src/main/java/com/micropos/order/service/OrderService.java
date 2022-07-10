package com.micropos.order.service;

import com.micropos.model.Item;
import com.micropos.model.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    Flux<Order> getAllOrder();

    Mono<Order> addOrder(Flux<Item> items);

    Mono<Order> getOrder(Integer id);

    Mono<Order> deliverOrder(Integer id);
}
