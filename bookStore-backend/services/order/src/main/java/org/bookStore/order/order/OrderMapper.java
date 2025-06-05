package org.bookStore.order.order;

import org.bookStore.common.utils.CreateOrderDetailsRequest;
import org.bookStore.order.events.OrderCreatedEvent;
import org.bookStore.order.orderDetails.OrderDetails;
import org.bookStore.order.orderDetails.OrderDetailsMapper;
import org.bookStore.order.orderDetails.OrderDetailsResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.bookStore.order.order.OrderStatus.PROCESSING;

@Service
public class OrderMapper {

    public Order toOrder(OrderCreatedEvent event) {
        Order order = Order.builder()
                .orderId(event.orderId())
                .orderDate(LocalDateTime.now())
                .totalPrice(calculateTotal(event.orderDetails()))
                .status(PROCESSING)
                .cartId(event.userId())
                .orderDetails(null)
                .build();

        List<OrderDetails> details = event.orderDetails().stream()
                .map(detail -> OrderDetailsMapper.toOrderDetails(detail, order))
                .toList();

        order.setOrderDetails(details);
        return order;
    }

    public OrderResponse toOrderResponse(Order order) {
        List<OrderDetailsResponse> details = order.getOrderDetails()
                .stream()
                .map(OrderDetailsMapper::toOrderDetailsResponse)
                .toList();

        return new OrderResponse(
                order.getOrderId(),
                order.getOrderDate(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCartId(),
                order.getShippingOrderId(),
                details
        );
    }

    private static double calculateTotal(List<CreateOrderDetailsRequest> details) {
        return details.stream()
                .mapToDouble(d -> d.unitPrice() * d.quantity())
                .sum();
    }
}
