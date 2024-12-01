package com.alimurph.book.book;

import com.alimurph.book.common.PageResponse;
import com.alimurph.book.exception.OperationNotPermittedException;
import com.alimurph.book.file.FileStorageService;
import com.alimurph.book.history.BookTransactionHistory;
import com.alimurph.book.history.BookTransactionHistoryRepository;
import com.alimurph.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private BookTransactionHistoryRepository bookTransactionHistoryRepository;
    private final FileStorageService fileStorageService;

    public BookService(BookMapper bookMapper, BookRepository bookRepository, FileStorageService fileStorageService, BookTransactionHistoryRepository bookTransactionHistoryRepository) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
        this.fileStorageService = fileStorageService;
        this.bookTransactionHistoryRepository = bookTransactionHistoryRepository;
    }

    private User getConnectedUser(Authentication authentication){
        return (User) authentication.getPrincipal();
    }
    public Long save(BookRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Book book = bookMapper.toBook(request);
        book.setOwner(user);  // the connected user who would create the book will be the owner of the book
        return bookRepository.save(book).getId();
    }

    public BookResponse findBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID = " + bookId));
    }

    public PageResponse<BookResponse> findAllDisplayableBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        // create a page request object
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());
        List<BookResponse> bookResponses = books.stream().map(bookMapper::toBookResponse).toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication authentication) {
        User user = getConnectedUser(authentication);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(BookSpecification.withOwnerId(user.getId()), pageable);
        List<BookResponse> bookResponses = books.stream().map(bookMapper::toBookResponse).toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication authentication) {
        User user = getConnectedUser(authentication);
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowedBooks = bookTransactionHistoryRepository.findAllBorrowedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponses = allBorrowedBooks.stream().map(bookMapper::toBorrowedBookResponse).toList();
        return new PageResponse<>(
                bookResponses,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    public PageResponse<ReturnedBookResponse> findAllReturnedBooks(int page, int size, Authentication authentication) {
        User user = getConnectedUser(authentication);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allReturnedBooks = bookTransactionHistoryRepository.findAllReturnedBooks(pageable, user.getId());
        List<ReturnedBookResponse> bookResponses = allReturnedBooks.stream().map(bookMapper::toReturnedBookResponse).toList();
        return new PageResponse<>(
                bookResponses,
                allReturnedBooks.getNumber(),
                allReturnedBooks.getSize(),
                allReturnedBooks.getTotalElements(),
                allReturnedBooks.getTotalPages(),
                allReturnedBooks.isFirst(),
                allReturnedBooks.isLast()
        );

    }

    public Long updateShareableStatus(Long bookId, Authentication authentication) throws OperationNotPermittedException {
        User user = getConnectedUser(authentication);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found for ID = " + bookId));

        // only the owner of the book should be able to update the book status
        if(!Objects.equals(user.getId(), book.getOwner().getId())){
            throw new OperationNotPermittedException("You cannot update the shareable status of books owned by other users");
        }
        book.setShareable(!book.isShareable());
        return bookRepository.save(book).getId();
    }

    public Long updateArchivedStatus(Long bookId, Authentication authentication) throws OperationNotPermittedException {
        User user = getConnectedUser(authentication);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found for ID = " + bookId));

        // only the owner of the book should be able to update the book status
        if(!Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You cannot update the archive status of books owned by other users");
        }

        book.setArchived(!book.isArchived());
        return bookRepository.save(book).getId();
    }

    public Long borrowBook(Long bookId, Authentication authentication) throws OperationNotPermittedException {
        User user = getConnectedUser(authentication);

        // Check if the book exists
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found for ID = " + bookId));
        // check if the book is shareable
        if(!book.isShareable()){
            throw new OperationNotPermittedException("You cannot borrow this book as it is not shared by the owner");
        }
        // check if the book is not archived
        if(book.isArchived()){
            throw new OperationNotPermittedException("You cannot borrow this book as it is archived");
        }
        // check if the connected user is not the owner of the book
        if(Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You cannot borrow your own book");
        }
        // check if the book is not already borrowed by another user or by same user
        if(bookTransactionHistoryRepository.isBookBorrowed(bookId)){
            throw new OperationNotPermittedException("The requested book is already borrowed");
        }

        BookTransactionHistory bookTransactionHistory = new BookTransactionHistory.Builder()
                .setBook(book)
                .setUser(user)
                .setReturned(false)
                .setReturnedApproved(false)
                .createBookTransactionHistory();

        return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();

    }

    public Long returnBook(Long bookId, Authentication authentication) throws OperationNotPermittedException {
        User user = getConnectedUser(authentication);

        // Check if the book exists
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found for ID = " + bookId));
        // check if the book is shareable
        if(!book.isShareable()){
            throw new OperationNotPermittedException("You cannot return this book as it is not shared by the owner");
        }
        // check if the book is not archived
        if(book.isArchived()){
            throw new OperationNotPermittedException("You cannot return this book as it is archived");
        }
        // check if the connected user is not the owner of the book
        if(Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You cannot return your own book");
        }
        // check if the connected user has already returned the book
        final boolean isBookReturnedByUser = bookTransactionHistoryRepository.isBookReturnedByUser(bookId, user.getId());
        if(isBookReturnedByUser){
            throw new OperationNotPermittedException("You have already returned this book");
        }
        // check if the connected user is the same person who borrowed the book
        BookTransactionHistory bookTransactionHistory = bookTransactionHistoryRepository.findBorrowedBookByUser(bookId, user.getId())
                .orElseThrow(() -> new OperationNotPermittedException("You did not borrow this book"));

        bookTransactionHistory.setReturned(true);
        return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();

    }

    public Long approveReturnedBook(Long bookId, Authentication authentication) throws OperationNotPermittedException {
        User user = getConnectedUser(authentication);

        // Check if the book exists
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found for ID = " + bookId));
        // check if the book is shareable
        if(!book.isShareable()){
            throw new OperationNotPermittedException("You cannot approve the return of this book as it is not shared");
        }
        // check if the book is not archived
        if(book.isArchived()){
            throw new OperationNotPermittedException("You cannot approve return this book as it is archived");
        }
        // check if the connected user is the owner of the book
        if(!Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You cannot approve the return as you do not own this book");
        }
        // check if the book is returned and pending for approval
        BookTransactionHistory bookTransactionHistory = bookTransactionHistoryRepository.findReturnedBookPendingApproval(bookId, user.getId())
                .orElseThrow(() -> new OperationNotPermittedException("The book is not returned yet. You cannot approve its return"));

        bookTransactionHistory.setReturnedApproved(true);
        return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();

    }

    public void uploadBookCoverPicture(MultipartFile file, Authentication authentication, Long bookId) {
        User user = getConnectedUser(authentication);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found for ID = " + bookId));
        var bookCover = fileStorageService.saveFile(file,user.getId());
        book.setBookCover(bookCover);
        bookRepository.save(book);
    }
}
