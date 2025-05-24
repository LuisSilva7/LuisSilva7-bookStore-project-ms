package org.bookStore.composition.addCartItem.cart;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "cart-service",
        url = "${application.config.cart-url}"
)
public interface CartClient {

    @GetMapping("/user/{userId}")
    CartResponse getCartByUserId(@PathVariable("userId") Long userId);
}

