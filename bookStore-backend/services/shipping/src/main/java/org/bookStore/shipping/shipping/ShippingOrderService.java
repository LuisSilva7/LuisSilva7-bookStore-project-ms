package org.bookStore.shipping.shipping;

import lombok.RequiredArgsConstructor;
import org.bookStore.shipping.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingOrderService {

    private final ShippingOrderRepository shippingOrderRepository;

    public ShippingOrder createShippingOrder(ShippingOrder shippingOrder) {
        return shippingOrderRepository.save(shippingOrder);
    }

    public PageResponse<ShippingOrder> getAllShippingOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ShippingOrder> shippingOrders = shippingOrderRepository.findAll(pageable);

        return new PageResponse<>(
                shippingOrders.getContent(),
                shippingOrders.getNumber(),
                shippingOrders.getSize(),
                shippingOrders.getTotalElements(),
                shippingOrders.getTotalPages(),
                shippingOrders.isFirst(),
                shippingOrders.isLast()
        );
    }

}
