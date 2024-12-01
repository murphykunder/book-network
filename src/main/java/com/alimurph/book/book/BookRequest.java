package com.alimurph.book.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
        Long id,   // if id not provided in request then its a new book, else it will be updated
        @NotNull(message = "100")
        @NotBlank(message = "100")
        String title,
        @NotNull(message = "101")
        @NotBlank(message = "101")
        String authorName,
        @NotNull(message = "102")
        @NotBlank(message = "102")
        String isbn,
        @NotNull(message = "103")
        @NotBlank(message = "103")
        String synopsis,
        String bookCover,
        boolean shareable
) {
}
