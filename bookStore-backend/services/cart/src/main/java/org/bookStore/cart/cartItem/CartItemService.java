package org.bookStore.cart.cartItem;

import lombok.RequiredArgsConstructor;
import org.bookStore.cart.cart.Cart;
import org.bookStore.cart.cart.CartRepository;
import org.bookStore.cart.exception.custom.CartItemDoesNotBelongToUserCartException;
import org.bookStore.cart.exception.custom.CartItemNotFoundException;
import org.bookStore.cart.exception.custom.CartNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final CartItemMapper cartItemMapper;

    @Transactional
    public CartItemResponse createCartItem(Long userId, CreateCartItemRequest request) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setCreatedDate(LocalDate.now());
                    return cartRepository.save(newCart);
                });

        Optional<CartItem> existingItemOpt = cartItemRepository
                .findByCart_IdAndBookId(cart.getId(), request.bookId());

        CartItem itemToSave;

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();

            int newQuantity = existingItem.getQuantity() + request.quantity();
            existingItem.setQuantity(newQuantity);

            if (existingItem.getUnitPrice() != null) {
                existingItem.setSubTotal(existingItem.getUnitPrice() * newQuantity);
            } else {
                existingItem.setSubTotal(0.0);
            }

            itemToSave = existingItem;
        } else {
            itemToSave = cartItemMapper.toCartItem(request, cart);
        }

        CartItem savedItem = cartItemRepository.save(itemToSave);

        return cartItemMapper.toCartItemResponse(savedItem);
    }

    public CartItemResponse getCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartItemNotFoundException("CartItem not found with id: " + id));

        return cartItemMapper.toCartItemResponse(cartItem);
    }

    public CartItemResponse updateCartItemQuantity(Long userId,
                                                   Long cartItemId,
                                                   UpdateCartItemQuantityRequest request) {
        CartItem existingCartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("CartItem not found with id: " + cartItemId));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with userId: " + userId));

        boolean belongsToCart = cart.getCartItems().stream()
                .anyMatch(item -> item.getId().equals(cartItemId));

        if (!belongsToCart) {
            throw new CartItemDoesNotBelongToUserCartException("CartItem does not belong to the user's cart.");
        }

        existingCartItem.setQuantity(request.quantity());
        existingCartItem.setSubTotal(existingCartItem.getUnitPrice() * request.quantity());

        CartItem updatedItem = cartItemRepository.save(existingCartItem);

        return cartItemMapper.toCartItemResponse(updatedItem);
    }

    @Transactional
    public void deleteCartItemById(Long userId, Long id) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with userId: " + userId));

        CartItem toRemove = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found with id: " + id));

        cart.getCartItems().remove(toRemove);
        cartRepository.save(cart);
    }
}
