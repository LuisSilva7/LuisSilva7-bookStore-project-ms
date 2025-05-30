package org.bookStore.composition.addCartItem.cartItem;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "cart-item-service",
        url = "${application.config.cart-item-url}"
)
public interface CartItemClient {

    @PostMapping
    void createCartItem(@RequestHeader("x-userid") Long userId, @RequestBody @Valid AddCartItemRequest request);
}

