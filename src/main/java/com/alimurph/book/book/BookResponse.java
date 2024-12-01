package com.alimurph.book.book;

public class BookResponse {

    private Long id;
    private String title;
    private String authorName;
    private String isbn;  // book identification number
    private String synopsis;
    private String owner;
    private String bookCover;
    private double rate;   // average rating of all the feedbacks
    private boolean archived;
    private boolean shareable;

    public BookResponse() {
    }

    public BookResponse(Long id, String title, String authorName, String isbn, String synopsis, String owner, String bookCover, double rate, boolean archived, boolean shareable) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.synopsis = synopsis;
        this.owner = owner;
        this.bookCover = bookCover;
        this.rate = rate;
        this.archived = archived;
        this.shareable = shareable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
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

    public static class Builder {

        private Long id;
        private String title;
        private String authorName;
        private String isbn;
        private String synopsis;
        private String owner;
        private String bookCover;
        private double rate;
        private boolean archived;
        private boolean shareable;

        public Builder setId(Long id) {
            this.id = id;
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

        public Builder setOwner(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder setBookCover(String bookCover) {
            this.bookCover = bookCover;
            return this;
        }

        public Builder setRate(double rate) {
            this.rate = rate;
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

        public BookResponse createBookResponse() {
            return new BookResponse(id, title, authorName, isbn, synopsis, owner, bookCover, rate, archived, shareable);
        }
    }
}
