package com.micropos.order.repository;

import com.micropos.model.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * 暂时是使用的in-memory repository
 * 不足: 1. 只能启动一个实例, 不然会导致订单重复;  2. 不能持久化
 */
@Component
public class InMemoryOrderRepository implements OrderRepository {
    static private int counts = 0;
    static private final HashMap<Integer, Order> orders = new HashMap<>();

    public synchronized Flux<Order> findAll() {
        return Flux.fromIterable(orders.values());
    }

    public synchronized Mono<Order> findById(Integer id) {
        return Mono.justOrEmpty(orders.getOrDefault(id, null));
    }

    public synchronized Mono<Order> addOrder(Order order) {
        counts += 1;
        order.setId(counts);
        orders.put(order.getId(), order);
        return Mono.just(order);
    }

    public synchronized Mono<Boolean> deleteOrder(Integer id) {
        return Mono.just(orders.remove(id) != null);
    }
}
