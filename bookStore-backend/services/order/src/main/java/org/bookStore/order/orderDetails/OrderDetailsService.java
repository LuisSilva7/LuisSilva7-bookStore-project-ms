package org.bookStore.order.orderDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    public OrderDetails createOrderDetails(OrderDetails orderDetails){
        return orderDetailsRepository.save(orderDetails);
    }
}
