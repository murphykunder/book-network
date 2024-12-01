package com.alimurph.book.book;

import com.alimurph.book.common.BaseEntity;
import com.alimurph.book.feedback.Feedback;
import com.alimurph.book.history.BookTransactionHistory;
import com.alimurph.book.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Book extends BaseEntity {

    private String title;
    private String authorName;
    private String isbn;  // book identification number
    @Lob
    private String synopsis;
    @Lob
    private String bookCover;
    private boolean archived = false;
    private boolean shareable = true;

    @ManyToOne  // many books could be owned by 1 use
    @JoinColumn(name="owner_id")
    private User owner;   // each book will have 1 owner and a user can have many books
    @OneToMany(mappedBy = "book")  // a book can have many feedbacks
    private List<Feedback> feedbacks;
    @OneToMany(mappedBy = "book")  // a book can have many transaction histories based on the number of times it is borrowed
    private List<BookTransactionHistory> histories;

    public Book() {
    }

    public Book(Long id, Long createdBy, LocalDateTime createdDate, Long lastModifiedBy, LocalDateTime lastModifiedDate, String title, String authorName, String isbn, String synopsis, String bookCover, boolean archived, boolean shareable, User owner, List<Feedback> feedbacks, List<BookTransactionHistory> histories) {
        super(id, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.synopsis = synopsis;
        this.bookCover = bookCover;
        this.archived = archived;
        this.shareable = shareable;
        this.owner = owner;
        this.feedbacks = feedbacks;
        this.histories = histories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isShareable() {
        return shareable;
    }

    public void setShareable(boolean shareable) {
        this.shareable = shareable;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<BookTransactionHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<BookTransactionHistory> histories) {
        this.histories = histories;
    }

    @Transient   // so that this field its not inserted in dB
    public double getRate(){
        if (feedbacks == null || feedbacks.isEmpty()){
            return 0.0;
        }

        var rate = feedbacks.stream()
                .mapToDouble(Feedback::getRating)
                .average()
                .orElse(0.0);

        double roundedRate = Math.round(rate * 10.0) / 10.0;
        return roundedRate;
    }

    public static class Builder {

        private Long id;
        private Long createdBy;
        private LocalDateTime createdDate;
        private Long lastModifiedBy;
        private LocalDateTime lastModifiedDate;
        private String title;
        private String authorName;
        private String isbn;
        private String synopsis;
        private String bookCover;
        private boolean archived;
        private boolean shareable;
        private User owner;
        private List<Feedback> feedbacks;
        private List<BookTransactionHistory> histories;

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

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setAuthorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public Builder setIsbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder setSynopsis(String synopsis) {
            this.synopsis = synopsis;
            return this;
        }

        public Builder setBookCover(String bookCover) {
            this.bookCover = bookCover;
            return this;
        }

        public Builder setArchived(boolean archived) {
            this.archived = archived;
            return this;
        }

        public Builder setShareable(boolean shareable) {
            this.shareable = shareable;
            return this;
        }

        public Builder setOwner(User owner) {
            this.owner = owner;
            return this;
        }

        public Builder setFeedbacks(List<Feedback> feedbacks) {
            this.feedbacks = feedbacks;
            return this;
        }

        public Builder setHistories(List<BookTransactionHistory> histories) {
            this.histories = histories;
            return this;
        }

        public Book createBook() {
            return new Book(id, createdBy, createdDate, lastModifiedBy, lastModifiedDate, title, authorName, isbn, synopsis, bookCover, archived, shareable, owner, feedbacks, histories);
        }
    }
}
