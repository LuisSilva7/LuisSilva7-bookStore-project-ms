package org.bookStore.order.order;

import lombok.RequiredArgsConstructor;
import org.bookStore.order.order.kafka.OrderCreatedEvent;
import org.bookStore.order.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                savedOrder.getId(),
                savedOrder.getOrderDate(),
                savedOrder.getStatus()
        );

        kafkaProducerService.sendOrderCreatedEvent(event);
    }

    public PageResponse<Order> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Order> orders = orderRepository.findAll(pageable);

        return new PageResponse<>(
                orders.getContent(),
                orders.getNumber(),
                orders.getSize(),
                orders.getTotalElements(),
                orders.getTotalPages(),
                orders.isFirst(),
                orders.isLast()
        );
    }

}
