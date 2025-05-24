package org.bookStore.cart.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody CartRequest request) {
        Cart createdCart = cartService.createCart(request.getUserId());
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCart();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Cart cart = cartService.getCartIDByUserId(userId);
        return (cart != null)
                ? ResponseEntity.ok(cart)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@RequestHeader("x-userid") Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared with success.");
    }
}
