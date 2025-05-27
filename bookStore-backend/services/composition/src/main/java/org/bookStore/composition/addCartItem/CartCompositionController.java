package org.bookStore.composition.addCartItem;

import lombok.RequiredArgsConstructor;
import org.bookStore.composition.addCartItem.book.BookClient;
import org.bookStore.composition.addCartItem.book.BookResponse;
import org.bookStore.composition.addCartItem.book.BookUpdateRequest;
import org.bookStore.composition.addCartItem.cart.CartClient;
import org.bookStore.composition.addCartItem.cart.CartResponse;
import org.bookStore.composition.addCartItem.cartItem.CartItemClient;
import org.bookStore.composition.addCartItem.cartItem.CartItemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/composition/cart/items")
@RequiredArgsConstructor
public class CartCompositionController {

    private final BookClient bookClient;
    private final CartClient cartClient;
    private final CartItemClient cartItemClient;

    @PostMapping
    public ResponseEntity<?> addToCart(@RequestHeader("x-userid") Long userId,
                                       @RequestBody AddCartItemRequest request) {

        BookResponse bookResponse;
        try {
            bookResponse = bookClient.getBookById(request.bookId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(404).body("Book does not exist.");
        }

        if (bookResponse.quantity() < request.quantity()) {
            return ResponseEntity.badRequest().body("Stock is lower than quantity asked for.");
        }

        try {
            cartItemClient.createCartItem(userId, request);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding book to cart.");
        }
        System.out.println("00000000");
        try {
            System.out.println("01010101010101");
            int newQuantity = bookResponse.quantity() - request.quantity();

            bookClient.updateBookQuantity(request.bookId(),
                    new BookUpdateRequest(newQuantity));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body("Error updating book to cart.");
        }

        CartResponse cart = cartClient.getCartByUserId(userId);

        for (CartItemResponse item : cart.cartItems()) {
            BookResponse book = bookClient.getBookById(item.getBookId());

            item.setTitle(book.title());
            item.setUnitPrice(book.price());
            item.setSubTotal(book.price() * item.getQuantity());
        }

        return ResponseEntity.ok(cart);
    }
}

