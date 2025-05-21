package org.bookStore.cart.cartItem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public CartItem getCartItemById(Long id) {
        CartItem item = cartItemRepository.findById(id).orElse(null);
        item.setSubTotal(item.getSubTotal());
        return item;
    }

    public CartItem updateCartItemQuantity(Long id, CartItem cartItem) {
        CartItem existingItem = cartItemRepository.findById(id).orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(cartItem.getQuantity());
            return cartItemRepository.save(existingItem);
        }

        return null;
    }

    public void deleteCartItemById(Long id) {
        cartItemRepository.deleteById(id);
    }
}
