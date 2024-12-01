package com.alimurph.book.history;

import com.alimurph.book.book.Book;
import com.alimurph.book.common.BaseEntity;
import com.alimurph.book.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class BookTransactionHistory extends BaseEntity {

    // a user can borrow many books and a book can be borrowed by many users - so manytomany relationship
    // this class is built to save this many-to-many relationship aLong with additional fields returned, returnApproved

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private boolean returned;  // indicate if the book has been returned
    private boolean returnedApproved; //indicate if the book return has been approved

    public BookTransactionHistory() {
    }

    public BookTransactionHistory(Long id, Long createdBy, LocalDateTime createdDate, Long lastModifiedBy, LocalDateTime lastModifiedDate, User user, Book book, boolean returned, boolean returnedApproved) {
        super(id, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.user = user;
        this.book = book;
        this.returned = returned;
        this.returnedApproved = returnedApproved;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public boolean isReturnedApproved() {
        return returnedApproved;
    }

    public void setReturnedApproved(boolean returnedApproved) {
        this.returnedApproved = returnedApproved;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public static class Builder {

        private Long id;
        private Long createdBy;
        private LocalDateTime createdDate;
        private Long lastModifiedBy;
        private LocalDateTime lastModifiedDate;
        private User user;
        private Book book;
        private boolean returned;
        private boolean returnedApproved;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setCreatedBy(Long createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder setCreatedDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder setLastModifiedBy(Long lastModifiedBy) {
            this.lastModifiedBy = lastModifiedBy;
            return this;
        }

        public Builder setLastModifiedDate(LocalDateTime lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setBook(Book book) {
            this.book = book;
            return this;
        }

        public Builder setReturned(boolean returned) {
            this.returned = returned;
            return this;
        }

        public Builder setReturnedApproved(boolean returnedApproved) {
            this.returnedApproved = returnedApproved;
            return this;
        }

        public BookTransactionHistory createBookTransactionHistory() {
            return new BookTransactionHistory(id, createdBy, createdDate, lastModifiedBy, lastModifiedDate, user, book, returned, returnedApproved);
        }
    }
}
