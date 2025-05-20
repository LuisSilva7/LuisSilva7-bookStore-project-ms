package org.bookStore.order.defaultData;

import lombok.RequiredArgsConstructor;
import org.bookStore.order.order.Order;
import org.bookStore.order.order.OrderRepository;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DefaultOrderData {

    private final OrderRepository orderRepository;

    public void orderData() {

        if (orderRepository.count() == 0) {

            List<Order> orders = List.of(
                    Order.builder()
                            .orderDate(LocalDateTime.now())
                            .totalPrice(60.0)
                            .cartId(1L)
                            .shippingOrderId(1L)
                            .build(),

                    Order.builder()
                            .orderDate(LocalDateTime.now())
                            .totalPrice(30.0)
                            .cartId(2L)
                            .shippingOrderId(2L)
                            .build()
            );

            orderRepository.saveAll(orders);

            System.out.println("Default orders were created!");
        } else {
            System.out.println("Default orders already exist!");
        }
    }
}
