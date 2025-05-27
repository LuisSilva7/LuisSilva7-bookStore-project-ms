package org.bookStore.order.order;

import lombok.RequiredArgsConstructor;
import org.bookStore.order.order.kafka.OrderCreatedEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Order order) {
        if (order.getOrderDetails() != null) {
            order.getOrderDetails().forEach(detail -> detail.setOrder(order));
        }
        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getOrderId(),
                savedOrder.getOrderDate(),
                savedOrder.getStatus()
        );

        kafkaProducerService.sendOrderCreatedEvent(event);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
