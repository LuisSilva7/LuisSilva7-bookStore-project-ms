package org.bookStore.order_query.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.common.events.*;
import org.bookStore.order_query.book.BookItem;
import org.bookStore.order_query.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final OrderMapper orderMapper;

    public PageResponse<OrderResponse> getOrdersBetweenDates(LocalDate startDate, LocalDate endDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderDate").descending());
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        Page<Order> orderPage = orderRepository.findByOrderDateBetween(start, end, pageable);

        List<OrderResponse> content = orderPage.getContent()
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();

        return new PageResponse<>(
                content,
                orderPage.getNumber(),
                orderPage.getSize(),
                orderPage.getTotalElements(),
                orderPage.getTotalPages(),
                orderPage.isFirst(),
                orderPage.isLast()
        );
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
    public void updateFinalOrderStatus(OrderInfoFinalEvent event) {
        Optional<Order> optionalOrder = orderRepository.findByOrderId(event.orderId());

        if (optionalOrder.isEmpty()) {
            log.warn("Cannot update status. Order with ID {} not found.", event.orderId());
            return;
        }

        Order order = optionalOrder.get();
        order.setStatus(event.status());
        orderRepository.save(order);
    }

    @Transactional
    public void updateCancelledOrderStatus(OrderInfoCancelledEvent event) {
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
