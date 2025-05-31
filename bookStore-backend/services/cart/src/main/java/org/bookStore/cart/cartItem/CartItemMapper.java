package org.bookStore.cart.cartItem;

import org.bookStore.cart.cart.Cart;
import org.springframework.stereotype.Service;

@Service
public class CartItemMapper {

    public CartItemResponse toCartItemResponse(CartItem cartItem) {
        if(cartItem == null) return null;

        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getQuantity(),
                cartItem.getUnitPrice(),
                cartItem.getSubTotal(),
                cartItem.getBookId()
        );
    }

    public CartItem toCartItem(CreateCartItemRequest request, Cart cart) {
        if (request == null) return null;

        CartItem item = new CartItem();
        item.setBookId(request.bookId());
        item.setQuantity(request.quantity());
        item.setUnitPrice(request.unitPrice());
        item.setCart(cart);

        if (request.unitPrice() != null && request.quantity() > 0) {
            item.setSubTotal(request.unitPrice() * request.quantity());
        } else {
            item.setSubTotal(0.0);
        }

        return item;
    }
}
