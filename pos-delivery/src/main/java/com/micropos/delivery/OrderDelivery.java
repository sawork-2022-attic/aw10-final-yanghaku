package com.micropos.delivery;

import com.micropos.delivery.repository.DeliveryRepository;
import com.micropos.model.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Consumer;

public class OrderDelivery implements Consumer<Order> {
    DeliveryRepository deliveryRepository;

    @Autowired
    public void setDeliveryRepository(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public void accept(Order order) {
        System.out.println("delivery order " + order);
        deliveryRepository.AddOrder(order, "INIT").block();
    }
}
