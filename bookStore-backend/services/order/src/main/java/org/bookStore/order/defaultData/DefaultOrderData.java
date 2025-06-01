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
                            .orderDate(LocalDateTime.now().minusDays(5))
                            .totalPrice(79.97)
                            .cartId(1L)
                            .shippingOrderId(1L)
                            .build(),

                    Order.builder()
                            .orderDate(LocalDateTime.now().minusDays(4))
                            .totalPrice(64.99)
                            .cartId(2L)
                            .shippingOrderId(2L)
                            .build(),

                    Order.builder()
                            .orderDate(LocalDateTime.now().minusDays(3))
                            .totalPrice(90.70)
                            .cartId(3L)
                            .shippingOrderId(3L)
                            .build(),

                    Order.builder()
                            .orderDate(LocalDateTime.now().minusDays(2))
                            .totalPrice(66.49)
                            .cartId(4L)
                            .shippingOrderId(4L)
                            .build(),

                    Order.builder()
                            .orderDate(LocalDateTime.now().minusDays(1))
                            .totalPrice(52.89)
                            .cartId(5L)
                            .shippingOrderId(5L)
                            .build()
            );

            orderRepository.saveAll(orders);

            System.out.println("Default orders were created!");
        } else {
            System.out.println("Default orders already exist!");
        }
    }
}
