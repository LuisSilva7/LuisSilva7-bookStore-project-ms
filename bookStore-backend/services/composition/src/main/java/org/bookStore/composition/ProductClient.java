package org.bookStore.composition;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "book-service",
        url = "${application.config.book-url}"
)
public interface ProductClient {
    @GetMapping("/{id}")
    ProductDTO getBookById(@PathVariable("id") Long id);
}

