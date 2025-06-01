package org.bookStore.cart.cart;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bookStore.cart.response.ApiResponse;
import org.bookStore.cart.response.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<ApiResponse<CartResponse>> createCart(@RequestBody @Valid CreateCartRequest request) {
        CartResponse createdCart = cartService.createCart(request);

        return ResponseEntity.ok(new ApiResponse<>(
                "Cart created successfully!", createdCart));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CartResponse>>> getAllCarts(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        PageResponse<CartResponse> carts = cartService.getAllCart(page, size);

        return ResponseEntity.ok(new ApiResponse<>(
                "Carts obtained successfully!", carts));
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<CartResponse>> getCartByUserId(@PathVariable("id") Long userId) {
        CartResponse cart = cartService.getCartIDByUserId(userId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Cart with userId: " + userId + " obtained successfully!", cart));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<CartResponse>> clearCart(@RequestHeader("x-userid") Long userId) {
        CartResponse cart = cartService.clearCart(userId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Cart with userId: " + userId + " cleared successfully!", cart));
    }
}
