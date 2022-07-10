package com.micropos.delivery.repository;


import com.micropos.model.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Component
public class InMemoryOrderRepository implements DeliveryRepository {
    private final HashMap<Integer, String> status = new HashMap<>();

    private final HashMap<Integer, Order> orders = new HashMap<>();

    @Override
    public synchronized Mono<String> findOrderStatusById(Integer orderId) {
        return Mono.just(status.get(orderId));
    }

    @Override
    public synchronized Mono<Order> findOrderById(Integer orderId) {
        return Mono.just(orders.get(orderId));
    }

    @Override
    public synchronized Mono<Boolean> AddOrder(Order order, String s) {
        if (orders.containsKey(order.getId())) {
            return Mono.just(false);
        }
        status.put(order.getId(), s);
        orders.put(order.getId(), order);
        return Mono.just(true);
    }

    @Override
    public synchronized Mono<Boolean> updateOrderById(Integer orderId, String s) {
        if (status.containsKey(orderId)) {
            status.put(orderId, s);
            return Mono.just(true);
        } else {
            return Mono.just(false);
        }
    }
}
