package com.alimurph.book.feedback;

import com.alimurph.book.common.PageResponse;
import com.alimurph.book.exception.OperationNotPermittedException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedback")
@Tag(name = "Feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/save")
    public ResponseEntity<Long> saveFeedback(@RequestBody @Valid FeedbackRequest request, Authentication authentication) throws OperationNotPermittedException {
        return ResponseEntity.ok(feedbackService.saveFeedback(request, authentication));
    }

    /*
        Find all feedbacks for the book
     */
    @GetMapping("/book/{book-id}")
    public ResponseEntity<PageResponse<FeedbackResponse>> findAllFeedbacksForBook(
            @PathVariable(name = "book-id") Long bookId, Authentication authentication,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(feedbackService.findAllFeedbacksForBook(bookId, page, size, authentication));
    }
}
