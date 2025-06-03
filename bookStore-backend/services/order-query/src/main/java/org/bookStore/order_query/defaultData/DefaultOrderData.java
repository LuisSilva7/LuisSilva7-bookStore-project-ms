package org.bookStore.order_query.defaultData;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bookStore.common.utils.OrderStatus;
import org.bookStore.order_query.book.BookItem;
import org.bookStore.order_query.order.Order;
import org.bookStore.order_query.order.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DefaultOrderData implements CommandLineRunner {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (orderRepository.count() == 0) {
            List<Order> orders = List.of(
                    Order.builder()
                            .orderId("ORD001")
                            .orderDate(LocalDateTime.now().minusDays(3))
                            .totalPrice(45.50)
                            .status(OrderStatus.FINALIZED)
                            .userId(1L)
                            .firstname("Joana")
                            .address("Rua Central 123")
                            .city("Braga")
                            .postalCode("4700-000")
                            .bookItems(List.of(
                                    BookItem.builder()
                                            .bookId(101L)
                                            .unitPrice(22.75)
                                            .quantity(2)
                                            .build()
                            ))
                            .build(),

                    Order.builder()
                            .orderId("ORD002")
                            .orderDate(LocalDateTime.now().minusDays(2))
                            .totalPrice(30.00)
                            .status(OrderStatus.FINALIZED)
                            .userId(2L)
                            .firstname("Pedro")
                            .address("Av. Liberdade 45")
                            .city("Porto")
                            .postalCode("4000-001")
                            .bookItems(List.of(
                                    BookItem.builder()
                                            .bookId(102L)
                                            .unitPrice(30.00)
                                            .quantity(1)
                                            .build()
                            ))
                            .build()
            );

            orders.forEach(order -> order.getBookItems().forEach(book -> book.setOrder(order)));

            orderRepository.saveAll(orders);

            System.out.println("Default orders were created!");
        } else {
            System.out.println("Default orders already exist!");
        }
    }
}