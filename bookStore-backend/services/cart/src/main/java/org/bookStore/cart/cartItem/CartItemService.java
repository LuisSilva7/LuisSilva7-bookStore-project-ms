package org.bookStore.cart.cartItem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;

    public CartItem createCartItem(CartItem cartItem){
        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getAllCartItems(){
        return cartItemRepository.findAll();
    }

    public CartItem getCartItemByID(Long id){
        return cartItemRepository.findById(id).orElse(null);
    }

    public CartItem updateCartQuantity(Long id, CartItem cartItem){
        CartItem existItem = cartItemRepository.findById(id).orElse(null);

        if (existItem != null) {
            existItem.setQuantity(cartItem.getQuantity());
            cartItemRepository.save(existItem);

            return existItem;
        } else {
            return null;
        }
    }

    public CartItem updateCartSubTotal(Long id, CartItem cartItem){
        CartItem existItem = cartItemRepository.findById(id).orElse(null);

        if (existItem != null) {
            existItem.setSubTotal(cartItem.getSubTotal());
            cartItemRepository.save(existItem);

            return existItem;
        } else {
            return null;
        }
    }

    public CartItem deleteCartItemById(Long id){
        CartItem existItem = cartItemRepository.findById(id).orElse(null);

        if(existItem !=null){
            cartItemRepository.delete(existItem);
        }
        return null;
    }

    public void clearCart(){
        cartItemRepository.deleteAll();
    }

    public void resetAutoIncrement() {
        cartItemRepository.resetAutoIncrement();
    }

    public List<CartItem> getCartItemsByUsername(String username) {
        return cartItemRepository.findByUser_Username(username);
    }
}
