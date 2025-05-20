package org.bookStore.cart.defaultData;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DefaultDataRunner implements CommandLineRunner {

    private final DefaultCartData defaultCartData;
    private final DefaultCartItemData defaultCartItemData;

    @Override
    @Transactional
    public void run(String... args) {

        defaultCartData.cartData();
        defaultCartItemData.cartItemData();
    }
}
