package org.bookStore.composition.addCartItem;

import lombok.RequiredArgsConstructor;
import org.bookStore.composition.addCartItem.book.BookClient;
import org.bookStore.composition.addCartItem.book.BookResponse;
import org.bookStore.composition.addCartItem.cart.CartClient;
import org.bookStore.composition.addCartItem.cart.CartResponse;
import org.bookStore.composition.addCartItem.cartItem.CartItemClient;
import org.bookStore.composition.addCartItem.cartItem.CartItemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/composition/cart/items")
@RequiredArgsConstructor
public class CartCompositionController {

    private final BookClient bookClient;
    private final CartClient cartClient;
    private final CartItemClient cartItemClient;

    @PostMapping
    public ResponseEntity<?> addToCart(@RequestBody AddCartItemRequest request) {
        // 1. Verifica se o produto existe
        BookResponse bookResponse;
        try {
            bookResponse = bookClient.getBookById(request.bookId());
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Book does not exist.");
        }

        // 2. Verifica stock
        if (bookResponse.stock() < request.quantity()) {
            return ResponseEntity.badRequest().body("Stock is lower than quantity asked for.");
        }

        // 3. Adiciona ao carrinho
        try {
            cartItemClient.createCartItem(request);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding book to cart.");
        }

        // 4. Vai buscar carrinho atualizado
        CartResponse cart = cartClient.getCartByUserId(request.userId());

        // 5. Enriquecer carrinho com dados de todos os produtos
        double total = 0.0;
        for (CartItemResponse item : cart.items()) {
            BookResponse b = bookClient.getBookById(item.bookId());
            item.title(b.title());
            item.setPrice(p.getPrice());
            item.setTotal(p.getPrice() * item.getQuantity());
            total += item.getTotal();
        }

        cart.totalPrice(total);
        return ResponseEntity.ok(cart);
    }
}

