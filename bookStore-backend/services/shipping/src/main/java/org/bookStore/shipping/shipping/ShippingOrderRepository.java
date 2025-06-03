package org.bookStore.shipping.shipping;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingOrderRepository extends JpaRepository<ShippingOrder, Long> {

    Optional<ShippingOrder> findByOrderId(String orderId);
}
