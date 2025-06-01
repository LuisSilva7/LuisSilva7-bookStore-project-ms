package org.bookStore.book.author;

import lombok.RequiredArgsConstructor;
import org.bookStore.book.exception.custom.AuthorAlreadyExistsException;
import org.bookStore.book.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorResponse createAuthor(CreateAuthorRequest request) {
        boolean exists = authorRepository.existsByNameIgnoreCase(request.name());
        if (exists) {
            throw new AuthorAlreadyExistsException(
                    "An author with the name '" + request.name() + "' already exists.");
        }

        Author saved = authorRepository.save(authorMapper.toAuthor(request));
        return authorMapper.toAuthorResponse(saved);
    }

    public PageResponse<AuthorResponse> getAllAuthors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Author> authors = authorRepository.findAll(pageable);

        List<AuthorResponse> response = authors.stream()
                .map(authorMapper::toAuthorResponse)
                .toList();

        return new PageResponse<>(
                response,
                authors.getNumber(),
                authors.getSize(),
                authors.getTotalElements(),
                authors.getTotalPages(),
                authors.isFirst(),
                authors.isLast()
        );
    }

}
