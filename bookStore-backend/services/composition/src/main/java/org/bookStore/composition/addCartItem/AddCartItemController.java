package org.bookStore.composition.addCartItem;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bookStore.composition.addCartItem.cart.CartResponse;
import org.bookStore.composition.addCartItem.cartItem.AddCartItemRequest;
import org.bookStore.composition.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/composition/add-cart-item")
@RequiredArgsConstructor
public class AddCartItemController {

    private final AddCartItemService addCartItemService;

    @PostMapping
    public ResponseEntity<ApiResponse<CartResponse>> addCartItem(@RequestHeader("x-userid") Long userId,
                                                                 @RequestBody @Valid AddCartItemRequest request) {
        CartResponse response = addCartItemService.addCartItem(userId, request);

        return ResponseEntity.ok(new ApiResponse<>(
                "CartItem added successfully!", response));
    }
}

