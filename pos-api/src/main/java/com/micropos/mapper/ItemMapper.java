package com.micropos.mapper;

import com.micropos.dto.ItemDto;
import com.micropos.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface ItemMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Collection<ItemDto> toCartDto(Collection<Item> items);

    Collection<Item> toCart(Collection<ItemDto> cartItems);

    ItemDto toItemDto(Item cartItem);

    Item toItem(ItemDto cartItem);
}
