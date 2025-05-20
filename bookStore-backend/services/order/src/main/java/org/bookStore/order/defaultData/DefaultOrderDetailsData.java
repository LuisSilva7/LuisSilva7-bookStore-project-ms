package org.bookStore.order.defaultData;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bookStore.order.order.Order;
import org.bookStore.order.order.OrderRepository;
import org.bookStore.order.orderDetails.OrderDetails;
import org.bookStore.order.orderDetails.OrderDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DefaultOrderDetailsData implements CommandLineRunner {

    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Override
    @Transactional
    public void run(String... args) {

        if (orderDetailsRepository.count() == 0) {

            Order order1 = orderRepository.findById(1L).orElseThrow();
            Order order2 = orderRepository.findById(2L).orElseThrow();

            List<OrderDetails> details = List.of(
                    OrderDetails.builder()
                            .bookId(1L)
                            .quantity(2)
                            .unitPrice(20.0)
                            .subTotal(40.0)
                            .order(order1)
                            .build(),

                    OrderDetails.builder()
                            .bookId(2L)
                            .quantity(1)
                            .unitPrice(20.0)
                            .subTotal(20.0)
                            .order(order1)
                            .build(),

                    OrderDetails.builder()
                            .bookId(3L)
                            .quantity(1)
                            .unitPrice(30.0)
                            .subTotal(30.0)
                            .order(order2)
                            .build()
            );

            orderDetailsRepository.saveAll(details);

            System.out.println("Default order details were created!");
        } else {
            System.out.println("Default order details already exist!");
        }
    }
}
