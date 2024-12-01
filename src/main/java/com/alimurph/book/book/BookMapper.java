package com.alimurph.book.book;

import com.alimurph.book.history.BookTransactionHistory;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {
    public Book toBook(BookRequest request) {
        return new Book.Builder()
                .setId(request.id())
                .setTitle(request.title())
                .setAuthorName(request.authorName())
                .setIsbn(request.isbn())
                .setSynopsis(request.synopsis())
                .setShareable(request.shareable())
                .setArchived(false)
                .setBookCover(request.bookCover())
                .createBook();
    }

    public BookResponse toBookResponse(Book book) {
        return new BookResponse.Builder()
                .setId(book.getId())
                .setTitle(book.getTitle())
                .setAuthorName(book.getAuthorName())
                .setIsbn(book.getIsbn())
                .setSynopsis(book.getSynopsis())
                .setArchived(book.isArchived())
                .setShareable(book.isShareable())
                .setRate(book.getRate())
                .setOwner(book.getOwner().fullName())
                // .setBookCover(FileUtils.readFileFromLocation(book.getBookCover()))
                .setBookCover(book.getBookCover()) 
                .createBookResponse();
    }

    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory bookTransactionHistory) {
        Book book = bookTransactionHistory.getBook();
        return new BorrowedBookResponse.Builder()
                .setId(book.getId())
                .setIsbn(book.getIsbn())
                .setTitle(book.getTitle())
                .setAuthorName(book.getAuthorName())
                .setRate(book.getRate())
                .setReturned(bookTransactionHistory.isReturned())
                .setReturnedApproved(bookTransactionHistory.isReturnedApproved())
                .createBorrowedBookResponse();
    }

    public ReturnedBookResponse toReturnedBookResponse(BookTransactionHistory bookTransactionHistory) {
        Book book = bookTransactionHistory.getBook();
        return new ReturnedBookResponse.Builder()
                .setId(book.getId())
                .setIsbn(book.getIsbn())
                .setTitle(book.getTitle())
                .setAuthorName(book.getAuthorName())
                .setRate(book.getRate())
                .setReturned(bookTransactionHistory.isReturned())
                .setReturnedApproved(bookTransactionHistory.isReturnedApproved())
                .createReturnedBookResponse();
    }
}
