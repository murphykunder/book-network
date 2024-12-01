package com.alimurph.book.feedback;

import com.alimurph.book.book.Book;
import com.alimurph.book.book.BookRepository;
import com.alimurph.book.common.PageResponse;
import com.alimurph.book.exception.OperationNotPermittedException;
import com.alimurph.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FeedbackService {

    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;
    private final BookRepository bookRepository;

    public FeedbackService(FeedbackMapper feedbackMapper, FeedbackRepository feedbackRepository, BookRepository bookRepository) {
        this.feedbackMapper = feedbackMapper;
        this.feedbackRepository = feedbackRepository;
        this.bookRepository = bookRepository;
    }

    public Long saveFeedback(FeedbackRequest request, Authentication authentication) throws OperationNotPermittedException {
        User user = (User) authentication.getPrincipal();
        Book book = bookRepository.findById(request.bookId()).orElseThrow(() -> new EntityNotFoundException("No book found for ID = " + request.bookId()));
        if(!book.isShareable()){
            throw new OperationNotPermittedException("You cannot give feedback for this book as it is not shared by the owner");
        }
        if(book.isArchived()){
            throw new OperationNotPermittedException("You cannot give feedback for this book as it is archived");
        }
        if(Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You cannot give feedback for your own book");
        }
        Feedback feedback = feedbackMapper.toFeedback(request, book);
        return feedbackRepository.save(feedback).getId();
    }

    public PageResponse<FeedbackResponse> findAllFeedbacksForBook(Long bookId, int page, int size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Feedback> feedbacks = feedbackRepository.findAllByBookId(pageable, bookId);
        List<FeedbackResponse> feedbackResponses = feedbacks.stream().map((feedback) -> feedbackMapper.toFeedbackResponse(feedback, user)).toList();
        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );
    }
}
