package org.bookStore.cart.cartItem;

import lombok.RequiredArgsConstructor;
import org.bookStore.cart.cart.Cart;
import org.bookStore.cart.cart.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    public CartItem createCartItem(Long userId, CartItem cartItem) {
        Cart cart = cartRepository.findByUserId(userId);

        Optional<CartItem> existingItemOpt = cartItemRepository
                .findByCart_IdAndBookId(cart.getId(), cartItem.getBookId());

        CartItem itemToSave;

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();

            int newQuantity = existingItem.getQuantity() + cartItem.getQuantity();
            existingItem.setQuantity(newQuantity);

            if (existingItem.getUnitPrice() != null) {
                existingItem.setSubTotal(existingItem.getUnitPrice() * newQuantity);
            }

            itemToSave = existingItem;
        } else {
            cartItem.setCart(cart);

            if (cartItem.getUnitPrice() != null && cartItem.getQuantity() > 0) {
                cartItem.setSubTotal(cartItem.getUnitPrice() * cartItem.getQuantity());
            }

            itemToSave = cartItem;
        }

        return cartItemRepository.save(itemToSave);
    }



    public CartItem getCartItemById(Long id) {
        CartItem item = cartItemRepository.findById(id).orElse(null);
        item.setSubTotal(item.getSubTotal());
        return item;
    }

    public CartItem updateCartItemQuantity(Long userId, CartItem cartItem) {
        Cart cart = cartRepository.findByUserId(userId);

        for(CartItem item : cart.getCartItems()) {
            if(item.getId().equals(cartItem.getId())) {
                item.setQuantity(cartItem.getQuantity());
                item.setSubTotal(item.getUnitPrice() * item.getQuantity());
                return cartItemRepository.save(item);
            }
        }

        return null;
    }

    public void deleteCartItemById(Long userId, Long id) {
        Cart cart = cartRepository.findByUserId(userId);

        for(CartItem item : cart.getCartItems()) {
            if(item.getId().equals(id)) {
                cartItemRepository.deleteById(id);
            }
        }
    }
}
