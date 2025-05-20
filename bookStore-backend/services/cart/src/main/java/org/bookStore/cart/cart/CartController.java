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
    // private UserRepository userRepository;

    @PostMapping("/cart")
    public ResponseEntity<Cart> createCart(@RequestBody Cart createCart){
        Cart updatedCart = cartService.createCart(createCart);

        return new ResponseEntity<>(updatedCart, HttpStatus.CREATED);
    }

    @GetMapping("/cart")
    public ResponseEntity<List<Cart>> getAllCart(){

        List<Cart> existCart = cartService.getAllCart();

        return new ResponseEntity<>(existCart, HttpStatus.OK);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<Cart> getCartIDByUserID(@PathVariable Long userID){

        Cart cartID = cartService.getCartIDByUserID(userID);

        if (cartID != null) {
            return new ResponseEntity<>(cartID, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
