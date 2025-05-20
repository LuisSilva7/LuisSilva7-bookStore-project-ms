package org.bookStore.shipping.shipping;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingOrderRepository extends JpaRepository<ShippingOrder, Long> {
}
