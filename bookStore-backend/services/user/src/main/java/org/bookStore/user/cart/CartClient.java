package org.bookStore.user.cart;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "cart-service",
        url = "${application.config.cart-url}"
)
public interface CartClient {

    @PostMapping
    void createCart(@RequestBody @Valid CreateCartRequest request);
}
