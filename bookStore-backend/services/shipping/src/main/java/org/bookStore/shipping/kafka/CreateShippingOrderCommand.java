package org.bookStore.shipping.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateShippingOrderCommand {
    private String orderId;
    private String userId;
}