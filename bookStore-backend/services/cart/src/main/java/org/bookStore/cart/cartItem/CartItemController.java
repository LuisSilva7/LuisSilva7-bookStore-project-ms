package org.bookStore.cart.cartItem;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<CartItem> createCartItem(@RequestHeader("x-userid") Long userId,
                                                   @RequestBody CartItem cartItem) {
        CartItem createdCartItem = cartItemService.createCartItem(userId, cartItem);
        return new ResponseEntity<>(createdCartItem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
        CartItem item = cartItemService.getCartItemById(id);
        return (item != null)
                ? ResponseEntity.ok(item)
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("/quantity")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestHeader("x-userid") Long userId,
                                                           @RequestBody CartItem cartItem) {
        CartItem updatedItem = cartItemService.updateCartItemQuantity(userId, cartItem);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCartItemById(@RequestHeader("x-userid") Long userId,
                                                   @PathVariable Long id) {
        cartItemService.deleteCartItemById(userId, id);
        return ResponseEntity.noContent().build();
    }
}
