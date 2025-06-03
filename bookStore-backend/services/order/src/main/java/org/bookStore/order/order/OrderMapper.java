package org.bookStore.order.order;

import org.bookStore.common.utils.CreateOrderDetailsRequest;
import org.bookStore.order.command.events.OrderCreatedEvent;
import org.bookStore.order.orderDetails.OrderDetails;
import org.bookStore.order.orderDetails.OrderDetailsMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.bookStore.order.order.OrderStatus.PENDING;

@Service
public class OrderMapper {

    public Order toOrder(OrderCreatedEvent event) {
        Order order = Order.builder()
                .orderId(event.orderId())
                .orderDate(LocalDateTime.now())
                .totalPrice(calculateTotal(event.orderDetails()))
                .status(PENDING)
                .cartId(event.userId())
                .orderDetails(null)
                .build();

        List<OrderDetails> details = event.orderDetails().stream()
                .map(detail -> OrderDetailsMapper.toOrderDetails(detail, order))
                .toList();

        order.setOrderDetails(details);
        return order;
    }

    private static double calculateTotal(List<CreateOrderDetailsRequest> details) {
        return details.stream()
                .mapToDouble(d -> d.unitPrice() * d.quantity())
                .sum();
    }
}
