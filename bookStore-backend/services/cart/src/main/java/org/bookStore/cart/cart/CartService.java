package org.bookStore.cart.cart;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Cart createCart(Cart cart) {
        cart.setCreatedDate(LocalDate.now());
        return cartRepository.save(cart);
    }

    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }

    public Cart getCartIDByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Transactional
    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);

        if (cart != null) {
            cart.getCartItems().clear();
            cartRepository.save(cart);
        }
    }
}
