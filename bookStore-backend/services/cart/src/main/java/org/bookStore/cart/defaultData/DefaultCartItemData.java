package org.bookStore.cart.defaultData;

import lombok.RequiredArgsConstructor;
import org.bookStore.cart.cart.Cart;
import org.bookStore.cart.cart.CartRepository;
import org.bookStore.cart.cartItem.CartItem;
import org.bookStore.cart.cartItem.CartItemRepository;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DefaultCartItemData {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public void cartItemData() {

        if (cartItemRepository.count() == 0) {

            Cart cart1 = cartRepository.findById(1L).orElseThrow();
            Cart cart2 = cartRepository.findById(2L).orElseThrow();

            List<CartItem> items = List.of(
                    CartItem.builder()
                            .bookId(1L)
                            .quantity(2)
                            .unitPrice(20.0)
                            .cart(cart1)
                            .build(),

                    CartItem.builder()
                            .bookId(3L)
                            .quantity(1)
                            .unitPrice(15.5)
                            .cart(cart1)
                            .build(),

                    CartItem.builder()
                            .bookId(2L)
                            .quantity(3)
                            .unitPrice(10.0)
                            .cart(cart2)
                            .build()
            );

            cartItemRepository.saveAll(items);

            System.out.println("Default cart items were created!");
        } else {
            System.out.println("Default cart items already exist!");
        }
    }
}
