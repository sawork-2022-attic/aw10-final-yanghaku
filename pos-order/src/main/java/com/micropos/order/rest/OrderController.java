package com.micropos.order.rest;

import com.micropos.mapper.ItemMapper;
import com.micropos.api.OrderApi;
import com.micropos.dto.ItemDto;
import com.micropos.dto.OrderDto;
import com.micropos.mapper.OrderMapper;
import com.micropos.order.service.OrderService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api")
public class OrderController implements OrderApi {

    OrderService orderService;

    OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Mono<ResponseEntity<OrderDto>> addOrder(Flux<ItemDto> itemDto, ServerWebExchange exchange) {
        return orderService.addOrder(itemDto.map(itemMapper::toItem)).map(orderMapper::toOrderDto).map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<OrderDto>>> allOrders(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(orderService.getAllOrder().map(orderMapper::toOrderDto)));
    }

    @Override
    public Mono<ResponseEntity<OrderDto>> deliverOrderById(Integer orderId, ServerWebExchange exchange) {
        return orderService.deliverOrder(orderId).map(orderMapper::toOrderDto).map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<OrderDto>> getOrderById(Integer orderId, ServerWebExchange exchange) {
        return orderService.getOrder(orderId).map(orderMapper::toOrderDto).map(ResponseEntity::ok);
    }
}
