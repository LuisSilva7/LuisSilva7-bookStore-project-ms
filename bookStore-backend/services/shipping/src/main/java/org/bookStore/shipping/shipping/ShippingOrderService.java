package org.bookStore.shipping.shipping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShippingOrderService {

    private final ShippingOrderRepository shippingOrderRepository;

    public ShippingOrder createShippingOrder(ShippingOrder shippingOrder){
        return shippingOrderRepository.save(shippingOrder);
    }

    public List<ShippingOrder> getAllShippingOrders(){
        return shippingOrderRepository.findAll();
    }
}
