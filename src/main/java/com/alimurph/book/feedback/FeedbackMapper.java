package com.alimurph.book.feedback;

import com.alimurph.book.book.Book;
import com.alimurph.book.book.BookRepository;
import com.alimurph.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {

    public Feedback toFeedback(FeedbackRequest request, Book book) {
        return new Feedback.Builder()
                .setBook(book)
                .setRating(request.rating())
                .setComment(request.comment())
                .createFeedback();
    }

    public FeedbackResponse toFeedbackResponse(Feedback feedback, User user) {
        return new FeedbackResponse.Builder()
                .setRating(feedback.getRating())
                .setComment(feedback.getComment())
                .setOwnFeedback(Objects.equals(feedback.getCreatedBy(), user.getId()))
                .createFeedbackResponse();
    }
}
