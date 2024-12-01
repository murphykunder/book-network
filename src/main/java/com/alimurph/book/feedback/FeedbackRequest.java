package com.alimurph.book.feedback;

import jakarta.validation.constraints.*;

public record FeedbackRequest(
        @NotNull(message="200")
        Long bookId,
        @NotNull(message="201")
        @Positive(message = "202")
        @Min(value = 0, message = "203")
        @Max(value = 5, message = "204")
        double rating,
        String comment
) {
}
