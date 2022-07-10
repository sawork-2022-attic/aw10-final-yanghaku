package com.micropos.carts.service;

import com.micropos.dto.ItemDto;
import com.micropos.carts.model.Cart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface CartService {
    Mono<Boolean> deleteProductInCart(Cart cart, String productId);

    Mono<Boolean> emptyCart(Cart cart);

    Mono<Boolean> updateProductInCart(Cart cart, String productId, Integer quantity);

    Flux<ItemDto> getCart(Cart cart);
}
