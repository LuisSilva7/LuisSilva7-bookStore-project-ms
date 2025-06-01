package org.bookStore.shipping.shipping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookStore.shipping.kafka.ShippingOrderCreatedEvent;
import org.bookStore.shipping.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingOrderService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void createShippingOrder(String orderId, String userId) {
        // Simular a lógica de criação do envio
        log.info("📦 Criado shipment para orderId={} userId={}", orderId, userId);

        // Publicar evento para tópico que o order-service vai escutar
        ShippingOrderCreatedEvent event = new ShippingOrderCreatedEvent(orderId, userId);
        kafkaTemplate.send("shipping-created", event);
        log.info("📤 [Kafka] ShippingOrderCreatedEvent publicado.");
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
