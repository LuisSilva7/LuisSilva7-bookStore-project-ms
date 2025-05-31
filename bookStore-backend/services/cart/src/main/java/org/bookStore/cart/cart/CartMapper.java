package org.bookStore.cart.cart;

import lombok.RequiredArgsConstructor;
import org.bookStore.cart.cartItem.CartItemMapper;
import org.bookStore.cart.cartItem.CartItemResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartMapper {

    private final CartItemMapper cartItemMapper;

    public CartResponse toCartResponse(Cart cart) {
        if (cart == null) return null;

        List<CartItemResponse> cartItemsResponse = null;
        if (cart.getCartItems() != null) {
            cartItemsResponse = cart.getCartItems().stream()
                    .map(cartItemMapper::toCartItemResponse)
                    .toList();
        }

        return new CartResponse(
                cart.getId(),
                cart.getUserId(),
                cartItemsResponse
        );
    }

    public Cart toCart(CreateCartRequest request) {
        if (request == null) return null;

        Cart cart = new Cart();
        cart.setCreatedDate(LocalDate.now());
        cart.setUserId(request.userId());

        return cart;
    }
}
