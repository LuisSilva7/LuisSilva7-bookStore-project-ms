package org.bookStore.cart.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartClearedEvent {
    private String orderId;
    private String userId;
}