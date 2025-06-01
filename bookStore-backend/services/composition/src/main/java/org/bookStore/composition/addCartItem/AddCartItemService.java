package org.bookStore.composition.addCartItem;

import lombok.RequiredArgsConstructor;
import org.bookStore.composition.addCartItem.book.BookClient;
import org.bookStore.composition.addCartItem.book.BookResponse;
import org.bookStore.composition.addCartItem.book.UpdateBookQuantityRequest;
import org.bookStore.composition.addCartItem.cart.CartClient;
import org.bookStore.composition.addCartItem.cart.CartResponse;
import org.bookStore.composition.addCartItem.cartItem.AddCartItemRequest;
import org.bookStore.composition.addCartItem.cartItem.CartItemClient;
import org.bookStore.composition.addCartItem.cartItem.CartItemResponse;
import org.bookStore.composition.exception.custom.AddCartItemException;
import org.bookStore.composition.exception.custom.BookNotFoundException;
import org.bookStore.composition.exception.custom.BookUpdateException;
import org.bookStore.composition.exception.custom.InsufficientBookStockException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddCartItemService {

    private final BookClient bookClient;
    private final CartClient cartClient;
    private final CartItemClient cartItemClient;

    public CartResponse addCartItem(Long userId, AddCartItemRequest request) {
        BookResponse book;
        try {
            book = bookClient.getBookById(request.bookId()).getData();
        } catch (Exception e) {
            throw new BookNotFoundException("Book not found with ID: " + request.bookId());
        }

        if (book.quantity() < request.quantity()) {
            throw new InsufficientBookStockException("Requested quantity exceeds available stock.");
        }

        try {
            cartItemClient.createCartItem(userId, request);
        } catch (Exception e) {
            throw new AddCartItemException("Error adding book to cart.");
        }

        CartResponse cart = cartClient.getCartByUserId(userId).getData();

        List<CartItemResponse> enrichedItems = cart.cartItems().stream()
                .map(item -> {
                    BookResponse itemBook = bookClient.getBookById(item.bookId()).getData();

                    return new CartItemResponse(
                            item.id(),
                            item.quantity(),
                            itemBook.price(),
                            itemBook.price() * item.quantity(),
                            item.bookId(),
                            itemBook.title()
                    );
                })
                .toList();

        return new CartResponse(
                cart.id(),
                cart.userId(),
                enrichedItems
        );
    }
}