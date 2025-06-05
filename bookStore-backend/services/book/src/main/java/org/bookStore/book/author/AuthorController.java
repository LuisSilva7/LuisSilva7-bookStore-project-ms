package org.bookStore.book.author;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bookStore.book.response.ApiResponse;
import org.bookStore.book.response.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorResponse>> createAuthor(@RequestBody @Valid CreateAuthorRequest request) {
        AuthorResponse createdAuthor = authorService.createAuthor(request);

        return ResponseEntity.ok(new ApiResponse<>(
                "Author created successfully!", createdAuthor));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AuthorResponse>>> getAllAuthors(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        PageResponse<AuthorResponse> authors = authorService.getAllAuthors(page, size);

        return ResponseEntity.ok(new ApiResponse<>(
                "Authors obtained successfully!", authors));
    }
}

