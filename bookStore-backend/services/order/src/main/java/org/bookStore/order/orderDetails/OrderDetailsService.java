package org.bookStore.order.orderDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    public List<OrderDetails> getOrderDetailsByOrderId(String orderId) {
        return orderDetailsRepository.findByOrder_OrderId(orderId);
    }
}
