package org.bookStore.cart.cartItem;

import lombok.RequiredArgsConstructor;
import org.bookStore.cart.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<ApiResponse<CartItemResponse>> createCartItem(@RequestHeader("x-userid") Long userId,
                                                                        @RequestBody CreateCartItemRequest request) {
        CartItemResponse createdCartItem = cartItemService.createCartItem(userId, request);

        return ResponseEntity.ok(new ApiResponse<>(
                "CartItem created successfully!", createdCartItem));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CartItemResponse>> getCartItemById(@PathVariable("id") Long cartItemId) {
        CartItemResponse cartItem = cartItemService.getCartItemById(cartItemId);

        return ResponseEntity.ok(new ApiResponse<>(
                "CartItem with id: " + cartItemId + " obtained successfully!", cartItem));
    }

    @PutMapping("/quantity/{id}")
    public ResponseEntity<ApiResponse<CartItemResponse>> updateCartItemQuantity(
            @RequestHeader("x-userid") Long userId,
            @PathVariable("id") Long cartItemId,
            @RequestBody UpdateCartItemQuantityRequest request) {
        CartItemResponse updatedCartItem = cartItemService.updateCartItemQuantity(userId, cartItemId, request);

        return ResponseEntity.ok(new ApiResponse<>(
                "CartItem updated successfully!", updatedCartItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCartItemById(@RequestHeader("x-userid") Long userId,
                                                             @PathVariable Long id) {
        cartItemService.deleteCartItemById(userId, id);

        return ResponseEntity.ok(new ApiResponse<>(
                "CartItem with id: " + id + " deleted successfully!", null));
    }
}
