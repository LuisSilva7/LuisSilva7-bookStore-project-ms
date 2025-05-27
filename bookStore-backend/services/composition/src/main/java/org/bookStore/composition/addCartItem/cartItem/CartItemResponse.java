package org.bookStore.composition.addCartItem.cartItem;

import lombok.Data;

@Data
public class CartItemResponse {
    private Long bookId;
    private String title;
    private int quantity;
    private Double unitPrice;
    private Double subTotal;
}
