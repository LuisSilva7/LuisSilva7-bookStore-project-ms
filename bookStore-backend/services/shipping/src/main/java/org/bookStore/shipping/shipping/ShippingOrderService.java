package org.bookStore.shipping.shipping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.common.commands.CreateShippingOrderCommand;
import org.bookStore.common.events.ShippingInfoEvent;
import org.bookStore.common.events.ShippingOrderCreatedEvent;
import org.bookStore.shipping.exception.custom.ShippingOrderNotFoundException;
import org.bookStore.shipping.outbox.OutboxEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingOrderService {

    private final ShippingOrderMapper shippingOrderMapper;
    private final ShippingOrderRepository repository;
    private final ShippingOrderRepository shippingOrderRepository;
    private final OutboxEventService outboxEventService;

    @Transactional
    public ShippingOrder createShippingOrder(CreateShippingOrderCommand command) {
        ShippingOrder shipping = shippingOrderMapper.toShippingOrder(command);
        ShippingOrder saved = repository.save(shipping);

        ShippingOrderCreatedEvent event = new ShippingOrderCreatedEvent(
                command.orderId(),
                command.userId(),
                saved.getId(),
                command.orderDetails()
        );

        outboxEventService.saveEvent(
                event.orderId(),
                ShippingOrderCreatedEvent.class.getSimpleName(),
                event
        );

        log.info("ShippingOrderCreatedEvent saved to outbox for orderId={}", command.orderId());

        ShippingInfoEvent shippingInfo = new ShippingInfoEvent(
                command.orderId(),
                command.firstName(),
                command.address(),
                command.city(),
                command.postalCode()
        );

        outboxEventService.saveEvent(
                shippingInfo.orderId(),
                ShippingInfoEvent.class.getSimpleName(),
                shippingInfo
        );

        log.info("ShippingInfoEvent saved to outbox for orderId={}", command.orderId());

        return saved;
    }

    public ShippingOrderResponse getShippingOrderByOrderId(String id) {
        ShippingOrder shippingOrder = shippingOrderRepository.findByOrderId(id)
                .orElseThrow(() -> new ShippingOrderNotFoundException("ShippingOrder not found with Id: " + id));

        return shippingOrderMapper.toShippingOrderResponse(shippingOrder);
    }
}
