package org.bookStore.order.defaultData;

import lombok.RequiredArgsConstructor;
import org.bookStore.order.order.Order;
import org.bookStore.order.order.OrderRepository;
import org.bookStore.order.orderDetails.OrderDetails;
import org.bookStore.order.orderDetails.OrderDetailsRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DefaultOrderDetailsData {

    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    public void orderDetailsData() {

        if (orderDetailsRepository.count() == 0) {

            List<Order> orders = orderRepository.findAll(PageRequest.of(0, 5)).getContent();

            Order order1 = orders.get(0);
            Order order2 = orders.get(1);
            Order order3 = orders.get(2);
            Order order4 = orders.get(3);
            Order order5 = orders.get(4);

            List<OrderDetails> details = List.of(

                    OrderDetails.builder()
                            .bookId(1L)
                            .quantity(2)
                            .unitPrice(24.99)
                            .subTotal(2 * 24.99)
                            .order(order1)
                            .build(),

                    OrderDetails.builder()
                            .bookId(2L)
                            .quantity(1)
                            .unitPrice(29.99)
                            .subTotal(29.99)
                            .order(order1)
                            .build(),

                    OrderDetails.builder()
                            .bookId(3L)
                            .quantity(1)
                            .unitPrice(19.99)
                            .subTotal(19.99)
                            .order(order2)
                            .build(),

                    OrderDetails.builder()
                            .bookId(4L)
                            .quantity(2)
                            .unitPrice(22.50)
                            .subTotal(2 * 22.50)
                            .order(order2)
                            .build(),

                    OrderDetails.builder()
                            .bookId(5L)
                            .quantity(1)
                            .unitPrice(27.90)
                            .subTotal(27.90)
                            .order(order3)
                            .build(),

                    OrderDetails.builder()
                            .bookId(6L)
                            .quantity(2)
                            .unitPrice(31.40)
                            .subTotal(2 * 31.40)
                            .order(order3)
                            .build(),

                    OrderDetails.builder()
                            .bookId(7L)
                            .quantity(1)
                            .unitPrice(28.99)
                            .subTotal(28.99)
                            .order(order4)
                            .build(),

                    OrderDetails.builder()
                            .bookId(8L)
                            .quantity(2)
                            .unitPrice(18.75)
                            .subTotal(2 * 18.75)
                            .order(order4)
                            .build(),

                    OrderDetails.builder()
                            .bookId(1L)
                            .quantity(1)
                            .unitPrice(24.99)
                            .subTotal(24.99)
                            .order(order5)
                            .build(),

                    OrderDetails.builder()
                            .bookId(5L)
                            .quantity(1)
                            .unitPrice(27.90)
                            .subTotal(27.90)
                            .order(order5)
                            .build()
            );

            orderDetailsRepository.saveAll(details);

            System.out.println("Default order details were created!");
        } else {
            System.out.println("Default order details already exist!");
        }
    }
}
