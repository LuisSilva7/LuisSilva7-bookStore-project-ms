package org.bookStore.cart.defaultData;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bookStore.cart.cart.Cart;
import org.bookStore.cart.cart.CartRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DefaultCartData implements CommandLineRunner {

    private final CartRepository cartRepository;

    @Override
    @Transactional
    public void run(String... args) {

        if (cartRepository.count() == 0) {

            List<Cart> carts = List.of(
                    Cart.builder()
                            .createdDate(LocalDate.now())
                            .userId(1L)
                            .build(),

                    Cart.builder()
                            .createdDate(LocalDate.now())
                            .userId(2L)
                            .build()
            );

            cartRepository.saveAll(carts);

            System.out.println("Default carts were created!");
        } else {
            System.out.println("Default carts already exist!");
        }
    }
}
