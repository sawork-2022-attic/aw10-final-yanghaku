package com.micropos.order.service.impl;

import com.micropos.model.Item;
import com.micropos.model.Order;
import com.micropos.order.repository.OrderRepository;
import com.micropos.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;

    StreamBridge streamBridge;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public Flux<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Mono<Order> addOrder(Flux<Item> items) {
        return items.collectList().map(list -> {
            Order order = new Order();
            order.setItems(list);
            return order;
        }).flatMap(o -> orderRepository.addOrder(o));
    }

    @Override
    public Mono<Order> getOrder(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public Mono<Order> deliverOrder(Integer id) {
        return getOrder(id).mapNotNull(o -> {
            boolean result = streamBridge.send("delivery", o);
            System.out.println("delivery order " + o + " result = " + result);
            return o;
        }).flatMap(o -> orderRepository.deleteOrder(id).mapNotNull(b -> o));
    }
}
