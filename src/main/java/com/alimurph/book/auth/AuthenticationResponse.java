package com.alimurph.book.auth;

public class AuthenticationResponse {

    private String jwtToken;

    public AuthenticationResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public AuthenticationResponse() {
    }

    public static class Builder {

        private String jwtToken;

        public Builder setJwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
            return this;
        }

        public AuthenticationResponse createAuthenticationResponse() {
            return new AuthenticationResponse(jwtToken);
        }
    }
}
