package com.alimurph.book.auth;

import jakarta.validation.constraints.*;

public class RegistrationRequest {

    @NotEmpty(message="Firstname is mandatory")
    @NotBlank(message="Firstname is mandatory")  // NotBlank will not allow blank spaces as well
    private String firstname;
    @NotEmpty(message="Lastname is mandatory")
    @NotBlank(message="Lastname is mandatory")
    private String lastname;
    @Email(message = "Invalid email format")
    @NotEmpty(message="Email is mandatory")
    @NotBlank(message="Email is mandatory")
    private String email;
    @NotEmpty(message="Password is mandatory")
    @NotBlank(message="Password is mandatory")
    @Size(min=8, message="Password should be minimum 8 characters long")
    private String password;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
