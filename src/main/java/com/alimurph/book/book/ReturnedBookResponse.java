package com.alimurph.book.book;

public class ReturnedBookResponse {

    private Long id;
    private String title;
    private String authorName;
    private String isbn;
    private double rate;
    private boolean returned;
    private boolean returnedApproved;

    public ReturnedBookResponse() {
    }

    public ReturnedBookResponse(Long id, String title, String authorName, String isbn, double rate, boolean returned, boolean returnedApproved) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.rate = rate;
        this.returned = returned;
        this.returnedApproved = returnedApproved;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
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

    public static class Builder {

        private Long id;
        private String title;
        private String authorName;
        private String isbn;
        private double rate;
        private boolean returned;
        private boolean returnedApproved;

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

        public Builder setRate(double rate) {
            this.rate = rate;
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

        public ReturnedBookResponse createReturnedBookResponse() {
            return new ReturnedBookResponse(id, title, authorName, isbn, rate, returned, returnedApproved);
        }
    }
}
