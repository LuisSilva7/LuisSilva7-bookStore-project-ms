package org.bookStore.composition;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/composition/cart")
@RequiredArgsConstructor
public class CartCompositionController {

    private final ProductClient productClient;
    private final CartClient cartClient;
    private final CartItemClient cartItemClient;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest request) {
        // 1. Verifica se o produto existe
        ProductDTO product;
        try {
            product = productClient.getBookById(request.bookId());
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Book does not exist.");
        }

        // 2. Verifica stock
        if (product.stock() < request.quantity()) {
            return ResponseEntity.badRequest().body("Stock is lower than quantity asked for.");
        }

        // 3. Adiciona ao carrinho
        try {
            cartItemClient.createCartItem(request);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding book to cart.");
        }

        // 4. Vai buscar carrinho atualizado
        CartDTO cart = cartClient.getCartByUserId(request.userId());

        // 5. Enriquecer carrinho com dados dos produtos
        double total = 0.0;
        for (CartItemDTO item : cart.items()) {
            ProductDTO p = productClient.getBookById(item.bookId());
            item.title(p.title());
            item.setPrice(p.getPrice());
            item.setTotal(p.getPrice() * item.getQuantity());
            total += item.getTotal();
        }

        cart.totalPrice(total);
        return ResponseEntity.ok(cart);
    }
}

