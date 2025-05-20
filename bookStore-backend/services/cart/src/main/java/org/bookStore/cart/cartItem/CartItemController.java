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
    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem) {
        CartItem createdCartItem = cartItemService.createCartItem(cartItem);
        return new ResponseEntity<>(createdCartItem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
        CartItem item = cartItemService.getCartItemById(id);
        return (item != null)
                ? ResponseEntity.ok(item)
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("/quantity/{id}")
    public ResponseEntity<CartItem> updateCartQuantity(@PathVariable Long id, @RequestBody CartItem cartItem) {
        CartItem updatedItem = cartItemService.updateCartQuantity(id, cartItem);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCartItemById(@PathVariable Long id) {
        cartItemService.deleteCartItemById(id);
        return ResponseEntity.noContent().build();
    }
}
