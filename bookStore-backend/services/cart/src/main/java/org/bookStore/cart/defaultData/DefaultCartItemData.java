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
            Cart cart3 = cartRepository.findById(3L).orElseThrow();
            Cart cart4 = cartRepository.findById(4L).orElseThrow();
            Cart cart5 = cartRepository.findById(5L).orElseThrow();

            List<CartItem> items = List.of(

                    CartItem.builder()
                            .bookId(1L)
                            .quantity(2)
                            .unitPrice(24.99)
                            .subTotal(2 * 24.99)
                            .cart(cart1)
                            .build(),

                    CartItem.builder()
                            .bookId(2L)
                            .quantity(1)
                            .unitPrice(29.99)
                            .subTotal(1 * 29.99)
                            .cart(cart1)
                            .build(),

                    CartItem.builder()
                            .bookId(3L)
                            .quantity(1)
                            .unitPrice(19.99)
                            .subTotal(19.99)
                            .cart(cart2)
                            .build(),

                    CartItem.builder()
                            .bookId(4L)
                            .quantity(2)
                            .unitPrice(22.50)
                            .subTotal(2 * 22.50)
                            .cart(cart2)
                            .build(),

                    CartItem.builder()
                            .bookId(5L)
                            .quantity(1)
                            .unitPrice(27.90)
                            .subTotal(27.90)
                            .cart(cart3)
                            .build(),

                    CartItem.builder()
                            .bookId(6L)
                            .quantity(2)
                            .unitPrice(31.40)
                            .subTotal(2 * 31.40)
                            .cart(cart3)
                            .build(),

                    CartItem.builder()
                            .bookId(7L)
                            .quantity(1)
                            .unitPrice(28.99)
                            .subTotal(28.99)
                            .cart(cart4)
                            .build(),

                    CartItem.builder()
                            .bookId(8L)
                            .quantity(2)
                            .unitPrice(18.75)
                            .subTotal(2 * 18.75)
                            .cart(cart4)
                            .build(),

                    CartItem.builder()
                            .bookId(1L)
                            .quantity(1)
                            .unitPrice(24.99)
                            .subTotal(24.99)
                            .cart(cart5)
                            .build(),

                    CartItem.builder()
                            .bookId(5L)
                            .quantity(1)
                            .unitPrice(27.90)
                            .subTotal(27.90)
                            .cart(cart5)
                            .build()
            );

            cartItemRepository.saveAll(items);

            System.out.println("Default cart items were created!");

        } else {
            System.out.println("Default cart items already exist!");
        }
    }
}
