package com.alimurph.book.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Long> {
    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.user.id = :userId
            """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Long userId);

    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.book.owner.id = :userId
            """)
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, Long userId);

    @Query("""
            SELECT 
            (COUNT(*) > 0) AS isBookBorrowed
            FROM BookTransactionHistory history
            WHERE history.book.id = :bookId
            AND returned = false
            AND returnedApproved = false
            """)
    boolean isBookBorrowed(Long bookId);

    @Query("""
            SELECT 
            (COUNT(*) > 0) AS isBookReturnedByUser
            FROM BookTransactionHistory history
            WHERE history.book.id = :bookId
            AND history.user.id = :userId
            AND returned = true
            """)
    boolean isBookReturnedByUser(Long bookId, Long userId);

    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.book.id = :bookId
            AND history.user.id = :userId
            AND returned = false
            AND returnedApproved = false
            """)
    Optional<BookTransactionHistory> findBorrowedBookByUser(Long bookId, Long userId);

    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.book.id = :bookId
            AND history.book.owner.id = :ownerId
            AND returned = true
            AND returnedApproved = false
            """)
    Optional<BookTransactionHistory> findReturnedBookPendingApproval(Long bookId, Long ownerId);
}
