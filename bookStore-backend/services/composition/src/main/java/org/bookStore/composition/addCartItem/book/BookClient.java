package org.bookStore.composition.addCartItem.book;

import org.bookStore.composition.authToken.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "book-service",
        url = "${application.config.book-url}",
        configuration = FeignClientConfig.class
)
public interface BookClient {
    @GetMapping("/{id}")
    BookResponse getBookById(@PathVariable("id") Long id);

    @PutMapping("/update-quantity/{id}")
    BookResponse updateBookQuantity(@PathVariable("id") Long id, @RequestBody BookUpdateRequest book);
}
