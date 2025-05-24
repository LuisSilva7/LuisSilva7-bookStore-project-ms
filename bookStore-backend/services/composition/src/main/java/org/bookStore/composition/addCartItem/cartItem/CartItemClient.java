package org.bookStore.composition.addCartItem.cartItem;

import org.bookStore.composition.addCartItem.AddCartItemRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "cart-item-service",
        url = "${application.config.cart-item-url}"
)
public interface CartItemClient {

    @PostMapping
    void createCartItem(@RequestBody AddCartItemRequest request);
}

