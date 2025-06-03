package org.bookStore.composition.common.book;

import org.bookStore.composition.authToken.FeignClientConfig;
import org.bookStore.composition.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "book-service",
        url = "${application.config.book-url}",
        configuration = FeignClientConfig.class
)
public interface BookClient {
    @GetMapping("/{id}")
    ApiResponse<BookResponse> getBookById(@PathVariable("id") Long bookId);
}
