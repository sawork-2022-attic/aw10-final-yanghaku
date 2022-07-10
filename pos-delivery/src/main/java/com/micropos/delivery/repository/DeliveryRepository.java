package com.micropos.delivery.repository;

import com.micropos.model.Order;
import reactor.core.publisher.Mono;

public interface DeliveryRepository {
    Mono<String> findOrderStatusById(Integer orderId);

    Mono<Order> findOrderById(Integer orderId);

    Mono<Boolean> AddOrder(Order order, String status);

    Mono<Boolean> updateOrderById(Integer orderId, String status);
}
