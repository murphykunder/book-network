package com.alimurph.book.feedback;

import com.alimurph.book.book.Book;
import com.alimurph.book.common.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Feedback extends BaseEntity {
    @Column(nullable = false)
    private double rating;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false) // many feedbacks can beLong to one book
    private Book book;

    public Feedback() {
    }

    public Feedback(Long id, Long createdBy, LocalDateTime createdDate, Long lastModifiedBy, LocalDateTime lastModifiedDate, double rating, String comment, Book book) {
        super(id, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.rating = rating;
        this.comment = comment;
        this.book = book;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        private double rating;
        private String comment;
        private Book book;

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

        public Builder setRating(double rating) {
            this.rating = rating;
            return this;
        }

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder setBook(Book book) {
            this.book = book;
            return this;
        }

        public Feedback createFeedback() {
            return new Feedback(id, createdBy, createdDate, lastModifiedBy, lastModifiedDate, rating, comment, book);
        }
    }
}
