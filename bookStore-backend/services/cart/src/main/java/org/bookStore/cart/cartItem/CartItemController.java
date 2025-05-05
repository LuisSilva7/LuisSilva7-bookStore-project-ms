package org.bookStore.cart.cartItem;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/cart-item")
    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem){
        CartItem createdCartItem = cartItemService.createCartItem(cartItem);

        return new ResponseEntity<>(createdCartItem, HttpStatus.CREATED);
    }

    @GetMapping("/cart-item")
    public ResponseEntity<List<CartItem>> getAllCartItems(){
        List<CartItem> cartItems = cartItemService.getAllCartItems();

        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @GetMapping("/cart-item/{id}")
    public ResponseEntity<CartItem> getCartItemByID(@PathVariable Long cartItemID){

        CartItem existCartItem  = cartItemService.getCartItemByID(cartItemID);

        if(existCartItem != null) {
            return new ResponseEntity<>(existCartItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cart-item/user/{username}")
    public ResponseEntity<List<CartItem>> getCartItemByUsername(@PathVariable String username) {
        List<CartItem> cartItems = cartItemService.getCartItemsByUsername(username);

        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }


    @PatchMapping("/quantity/{id}")
    public ResponseEntity<CartItem> updateCartQuantity(@PathVariable Long id, @RequestBody CartItem cartItem){
        CartItem patchedCartItem = cartItemService.updateCartQuantity(id, cartItem);

        return new ResponseEntity<>(patchedCartItem,HttpStatus.CREATED);
    }

    @PatchMapping("/subtotal/{id}")
    public ResponseEntity<CartItem> updateCartSubTotal(@PathVariable Long id, @RequestBody CartItem cartItem){
        CartItem patchedCartItem = cartItemService.updateCartSubTotal(id, cartItem);

        return new ResponseEntity<>(patchedCartItem,HttpStatus.CREATED);
    }

    @DeleteMapping("/clear-cart")
    public ResponseEntity<String> clearCart(){
        cartItemService.clearCart();

        return ResponseEntity.ok("Cart cleared and ID reset.");
    }

    @PostMapping("/reset")
    public void resetAutoIncrement() {
        cartItemService.resetAutoIncrement();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CartItem> deleteCartItemById(@PathVariable Long ID){
        cartItemService.deleteCartItemById(ID);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
