package com.micropos.mapper;

import com.micropos.dto.OrderDto;
import com.micropos.model.Order;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper
public interface OrderMapper {
    Collection<OrderDto> toOrdersDto(Collection<Order> orders);

    Collection<Order> toOrders(Collection<OrderDto> orderDtos);

    OrderDto toOrderDto(Order order);

    Order toOrder(OrderDto orderDto);
}
