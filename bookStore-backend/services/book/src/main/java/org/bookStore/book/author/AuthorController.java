package org.bookStore.book.author;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bookStore.book.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponse<List<AuthorResponse>>> getAllAuthors() {
        List<AuthorResponse> authors = authorService.getAllAuthors();

        return ResponseEntity.ok(new ApiResponse<>(
                "Authors obtained successfully!", authors));
    }
}

