package com.micropos.carts.service.impl;

import com.micropos.dto.ItemDto;
import com.micropos.mapper.CartsMapper;
import com.micropos.carts.model.Cart;
import com.micropos.model.Item;
import com.micropos.carts.service.CartService;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CartServiceImpl implements CartService {
    private final CartsMapper cartsMapper = Mappers.getMapper(CartsMapper.class);

    @Override
    public Mono<Boolean> deleteProductInCart(Cart cart, String productId) {
        for (int i = 0; i < cart.getItems().size(); ++i) {
            if (cart.getItems().get(i).getProductId().equals(productId)) {
                cart.getItems().remove(i);
                return Mono.just(true);
            }
        }
        return Mono.just(false);
    }

    @Override
    public Mono<Boolean> emptyCart(Cart cart) {
        cart.getItems().clear();
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> updateProductInCart(Cart cart, String productId, Integer quantity) {
        for (Item item : cart.getItems()) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(quantity);
                return Mono.just(true);
            }
        }
        cart.getItems().add(new Item(productId, quantity));
        return Mono.just(true);
    }

    @Override
    public Flux<ItemDto> getCart(Cart cart) {
        return Flux.fromIterable(cartsMapper.toCartDto(cart.getItems()));
    }
}
