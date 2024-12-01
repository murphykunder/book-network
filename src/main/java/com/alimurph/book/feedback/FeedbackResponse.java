package com.alimurph.book.feedback;

public class FeedbackResponse {

    private double rating;
    private String comment;
    private boolean ownFeedback; // indicates if the feedback was given by the connected user

    public FeedbackResponse() {
    }

    public FeedbackResponse(double rating, String comment, boolean ownFeedback) {
        this.rating = rating;
        this.comment = comment;
        this.ownFeedback = ownFeedback;
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

    public boolean isOwnFeedback() {
        return ownFeedback;
    }

    public void setOwnFeedback(boolean ownFeedback) {
        this.ownFeedback = ownFeedback;
    }

    public static class Builder {

        private double rating;
        private String comment;
        private boolean ownFeedback;

        public Builder setRating(double rating) {
            this.rating = rating;
            return this;
        }

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder setOwnFeedback(boolean ownFeedback) {
            this.ownFeedback = ownFeedback;
            return this;
        }

        public FeedbackResponse createFeedbackResponse() {
            return new FeedbackResponse(rating, comment, ownFeedback);
        }
    }
}
