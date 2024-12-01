package com.alimurph.book.book;

import com.alimurph.book.common.PageResponse;
import com.alimurph.book.exception.OperationNotPermittedException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("book")
@Tag(name="Book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/save")
    public ResponseEntity<Long> saveBook(@RequestBody @Valid BookRequest request, Authentication connectedUser){
        return ResponseEntity.ok(bookService.save(request, connectedUser));
    }

    @GetMapping("/{book-id}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable("book-id") Long bookId){
        return ResponseEntity.ok(bookService.findBookById(bookId));
    }

    /*
        Get a list of all displayable books not owned by the user
    */
    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.findAllDisplayableBooks(page, size, connectedUser));
    }

    /*
        Get all books by owner
     */
    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name="page", defaultValue = "0", required = false) int page,
            @RequestParam(name="size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.findAllBooksByOwner(page, size, connectedUser));
    }

    /*
        Find all borrowed books
     */
    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name="page", defaultValue = "0", required = false) int page,
            @RequestParam(name="size", defaultValue = "10", required = false) int size,
            Authentication authentication
    ){
        return ResponseEntity.ok(bookService.findAllBorrowedBooks(page, size, authentication));
    }

    /*
        Find all books owned by this user which have been returned (irrespective of the return approval status)
     */
    @GetMapping("/returned")
    public ResponseEntity<PageResponse<ReturnedBookResponse>> findAllReturnedBooks(
            @RequestParam(name="page", defaultValue = "0", required = false) int page,
            @RequestParam(name="size", defaultValue = "10", required = false) int size,
            Authentication authentication
    ){
        return ResponseEntity.ok(bookService.findAllReturnedBooks(page, size, authentication));
    }

    /*
        Update book shareable status
     */
    @PatchMapping("/shareable/{book-id}")
    public ResponseEntity<Long> updateShareableStatus(@PathVariable(name = "book-id") Long bookId, Authentication authentication) throws OperationNotPermittedException {
        return ResponseEntity.ok(bookService.updateShareableStatus(bookId, authentication));
    }

    /*
        Update book archive status
     */
    @PatchMapping("/archived/{book-id}")
    public ResponseEntity<Long> updateArchivedStatus(@PathVariable(name="book-id") Long bookId, Authentication authentication) throws OperationNotPermittedException {
        return ResponseEntity.ok(bookService.updateArchivedStatus(bookId, authentication));
    }

    /*
        Borrow a book
     */
    @PostMapping("/borrow/{book-id}")
    public ResponseEntity<Long> borrowBook(@PathVariable(name = "book-id") Long bookId, Authentication authentication) throws OperationNotPermittedException {
        return ResponseEntity.ok(bookService.borrowBook(bookId, authentication));
    }

    /*
        Return borrowed book
     */
    @PatchMapping("/return/{book-id}")
    public ResponseEntity<Long> returnBook(@PathVariable(name="book-id") Long bookId, Authentication authentication) throws OperationNotPermittedException {
        return ResponseEntity.ok(bookService.returnBook(bookId, authentication));
    }

    /*
        Approve books returned
     */
    @PatchMapping("/return/approve/{book-id}")
    public ResponseEntity<Long> approveReturnedBook(@PathVariable(name = "book-id") Long bookId, Authentication authentication) throws OperationNotPermittedException {
        return ResponseEntity.ok(bookService.approveReturnedBook(bookId, authentication));
    }

    @PostMapping(value = "/cover/{book-id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPicture(
            @PathVariable("book-id") Long bookId,
            @Parameter @RequestPart("file") MultipartFile file,
            Authentication authentication
    ){
        bookService.uploadBookCoverPicture(file, authentication, bookId);
        return ResponseEntity.accepted().build();
    }
}
