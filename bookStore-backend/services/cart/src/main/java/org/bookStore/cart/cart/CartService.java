package org.bookStore.cart.cart;

import org.bookStore.cart.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.bookStore.cart.exception.custom.CartNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartResponse createCart(CreateCartRequest request) {
        Cart created = cartRepository.save(cartMapper.toCart(request));

        return cartMapper.toCartResponse(created);
    }

    public PageResponse<CartResponse> getAllCart(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Cart> carts = cartRepository.findAll(pageable);

        List<CartResponse> response = carts.stream()
                .map(cartMapper::toCartResponse)
                .toList();

        return new PageResponse<>(
                response,
                carts.getNumber(),
                carts.getSize(),
                carts.getTotalElements(),
                carts.getTotalPages(),
                carts.isFirst(),
                carts.isLast()
        );
    }


    public CartResponse getCartIDByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with userId: " + userId));

        return cartMapper.toCartResponse(cart);
    }

    @Transactional
    public CartResponse clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with userId: " + userId));

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return cartMapper.toCartResponse(cart);
    }
}
