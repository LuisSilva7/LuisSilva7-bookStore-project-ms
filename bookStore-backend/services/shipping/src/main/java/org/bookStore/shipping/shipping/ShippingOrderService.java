package org.bookStore.shipping.shipping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.common.commands.CreateShippingOrderCommand;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingOrderService {

    private final ShippingOrderMapper shippingOrderMapper;
    private final ShippingOrderRepository repository;

    public ShippingOrder createShippingOrder(CreateShippingOrderCommand command) {
        ShippingOrder shipping = shippingOrderMapper.toShippingOrder(command);
        return repository.save(shipping);
    }

    /*public PageResponse<ShippingOrder> getAllShippingOrders(int page, int size) {
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
    }*/

}
