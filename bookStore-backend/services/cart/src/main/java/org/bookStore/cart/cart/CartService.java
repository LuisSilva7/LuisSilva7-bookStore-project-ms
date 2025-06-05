package org.bookStore.cart.cart;

import lombok.extern.slf4j.Slf4j;
import org.bookStore.cart.outbox.OutboxEventService;
import org.bookStore.cart.response.PageResponse;
import org.bookStore.common.commands.ClearCartCommand;
import org.bookStore.common.events.BookQuantityUpdatedEvent;
import org.bookStore.common.events.CartClearedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import lombok.RequiredArgsConstructor;
import org.bookStore.cart.exception.custom.CartNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final OutboxEventService outboxEventService;

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

    /*@Transactional
    public CartResponse clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with userId: " + userId));

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return cartMapper.toCartResponse(cart);
    }*/

    @Transactional
    public void clearCartByUserId(ClearCartCommand command) {
        Cart cart = cartRepository.findByUserId(command.userId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found with userId: " + command.userId()));

        cart.getCartItems().clear();
        cartRepository.save(cart);

        log.info("Cart successfully cleared for userId={}", command.userId());

        CartClearedEvent event = new CartClearedEvent(
                command.orderId(),
                command.userId()
        );

        outboxEventService.saveEvent(event.orderId(), CartClearedEvent.class.getSimpleName(), event);
    }
}
