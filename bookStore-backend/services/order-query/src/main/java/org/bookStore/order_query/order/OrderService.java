package org.bookStore.order_query.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.common.events.BookInfoEvent;
import org.bookStore.common.events.OrderInfoEvent;
import org.bookStore.common.events.OrderInfoFinalEvent;
import org.bookStore.common.events.ShippingInfoEvent;
import org.bookStore.order_query.book.BookItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        return orderRepository.findByOrderDateBetween(start, end);
    }

    @Transactional
    public void createOrder(OrderInfoEvent event) {
        Order order = Order.builder()
                .orderId(event.orderId())
                .orderDate(event.orderDate())
                .totalPrice(event.totalPrice())
                .status(event.status())
                .userId(event.userId())
                .build();

        orderRepository.save(order);
    }

    @Transactional
    public void updateShippingInfo(ShippingInfoEvent event) {
        Optional<Order> optionalOrder = orderRepository.findByOrderId(event.orderId());

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            order.setFirstname(event.firstName());
            order.setAddress(event.address());
            order.setCity(event.city());
            order.setPostalCode(event.postalCode());

            orderRepository.save(order);
        } else {
            log.warn("Order not found for orderId={}", event.orderId());
        }
    }

    @Transactional
    public void updateBooks(BookInfoEvent event) {
        Optional<Order> optionalOrder = orderRepository.findByOrderId(event.orderId());

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            List<BookItem> bookItems = event.books().stream()
                    .map(b -> BookItem.builder()
                            .bookId(b.bookId())
                            .unitPrice(b.unitPrice())
                            .quantity(b.quantity())
                            .order(order)
                            .build())
                    .toList();

            order.getBookItems().clear();
            order.getBookItems().addAll(bookItems);

            orderRepository.save(order);
        } else {
            log.warn("Order not found for orderId={}", event.orderId());
        }
    }

    @Transactional
    public void updateOrderStatus(OrderInfoFinalEvent event) {
        Optional<Order> optionalOrder = orderRepository.findByOrderId(event.orderId());

        if (optionalOrder.isEmpty()) {
            log.warn("Cannot update status. Order with ID {} not found.", event.orderId());
            return;
        }

        Order order = optionalOrder.get();
        order.setStatus(event.status());
        orderRepository.save(order);
    }
}
