package org.bookStore.cart.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Cart createCart(Cart cart){
        return cartRepository.save(cart);
    }

    public List<Cart> getAllCart(){
        return cartRepository.findAll();
    }

    public Cart getCartIDByUserID(Long userID){

        return cartRepository.getCartIDByUserID(userID);
    }
}
