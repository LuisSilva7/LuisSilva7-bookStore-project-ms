package org.bookStore.order.defaultData;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DefaultDataRunner implements CommandLineRunner {

    private final DefaultOrderData defaultOrderData;
    private final DefaultOrderDetailsData defaultOrderDetailsData;

    @Override
    @Transactional
    public void run(String... args) {

        defaultOrderData.orderData();
        defaultOrderDetailsData.orderDetailsData();
    }
}
