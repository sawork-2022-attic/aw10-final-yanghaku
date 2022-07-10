package com.micropos.carts.rest;

import com.micropos.dto.OrderDto;
import com.micropos.api.CartApi;
import com.micropos.dto.ItemDto;
import com.micropos.carts.model.Cart;
import com.micropos.carts.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.session.WebSessionManager;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class CartsController implements CartApi {
    WebSessionManager webSessionManager;
    CartService cartService;

    WebClient webClient = WebClient.create("http://127.0.0.1:8080");

    @Autowired
    public void setWebSessionManager(WebSessionManager webSessionManager) {
        this.webSessionManager = webSessionManager;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    private Mono<Cart> sessionCart(ServerWebExchange exchange) {
        String cartKey = "cart";
        return webSessionManager.getSession(exchange).map(s -> {
            Cart cart = s.getAttribute(cartKey);
            if (cart == null) {
                cart = new Cart();
                s.getAttributes().put(cartKey, cart);
            }
            return cart;
        });
    }

    @Override
    public Mono<ResponseEntity<OrderDto>> checkOutCart(ServerWebExchange exchange) {
        return sessionCart(exchange).flatMap(c ->
                webClient.post()
                        .uri("/api/order/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(c.getItems())
                        .retrieve()
                        .bodyToMono(OrderDto.class)
                        .map(ResponseEntity::ok)
        );
    }

    @Override
    public Mono<ResponseEntity<Boolean>> deleteProductInCart(String productId, ServerWebExchange exchange) {
        return sessionCart(exchange).flatMap(c -> cartService.deleteProductInCart(c, productId).map(ResponseEntity::ok));
    }

    @Override
    public Mono<ResponseEntity<Boolean>> emptyCart(ServerWebExchange exchange) {
        return sessionCart(exchange).flatMap(c -> cartService.emptyCart(c).map(ResponseEntity::ok));
    }

    @Override
    public Mono<ResponseEntity<Flux<ItemDto>>> getCart(ServerWebExchange exchange) {
        return sessionCart(exchange).map(c -> ResponseEntity.ok(cartService.getCart(c)));
    }

    @Override
    public Mono<ResponseEntity<Boolean>> updateProductInCart(String productId, Integer quantity, ServerWebExchange exchange) {
        return sessionCart(exchange).flatMap(c -> cartService.updateProductInCart(c, productId, quantity).map(ResponseEntity::ok));
    }
}
