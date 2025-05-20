package org.bookStore.order.orderDetails;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findOrderDetailsByOrderId(Long orderId);
}
