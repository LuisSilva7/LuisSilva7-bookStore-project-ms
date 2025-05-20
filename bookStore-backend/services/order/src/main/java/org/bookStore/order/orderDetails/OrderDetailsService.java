package org.bookStore.order.orderDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    public List<OrderDetails> getOrderDetailsByOrderId(Long orderId) {
        return orderDetailsRepository.findOrderDetailsByOrderId(orderId);
    }
}
