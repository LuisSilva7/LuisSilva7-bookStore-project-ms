package org.bookStore.user.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Full name is required.")
        String fullname,

        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be valid.")
        String email,

        @NotBlank(message = "Username is required.")
        String username,

        @NotBlank(message = "Password is required.")
        @Size(min = 6, message = "Password must be at least 6 characters.")
        String password

) {}
