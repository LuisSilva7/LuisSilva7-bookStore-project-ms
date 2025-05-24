package org.bookStore.composition.addCartItem.book;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "book-service",
        url = "${application.config.book-url}"
)
public interface BookClient {
    @GetMapping("/{id}")
    BookResponse getBookById(@PathVariable("id") Long id);
}
