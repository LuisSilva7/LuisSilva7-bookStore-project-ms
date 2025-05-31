package org.bookStore.user.auth;

public record RegisterResponse(
        Long id,
        String fullname,
        String email,
        String username
) {}
