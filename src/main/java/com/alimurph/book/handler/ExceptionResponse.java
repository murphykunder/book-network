package com.alimurph.book.handler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)   // only include non-empty fields in the response
public class ExceptionResponse {

    private String businessErrorCode;
    private String businessErrorDescription;
    private String error;
    private Set<String> validationErrors;
    private Map<String, String> errors;

    public ExceptionResponse(String businessErrorCode, String businessErrorDescription, String error, Set<String> validationErrors, Map<String, String> errors) {
        this.businessErrorCode = businessErrorCode;
        this.businessErrorDescription = businessErrorDescription;
        this.error = error;
        this.validationErrors = validationErrors;
        this.errors = errors;
    }

    public ExceptionResponse() {
    }

    public String getBusinessErrorCode() {
        return businessErrorCode;
    }

    public void setBusinessErrorCode(String businessErrorCode) {
        this.businessErrorCode = businessErrorCode;
    }

    public String getBusinessErrorDescription() {
        return businessErrorDescription;
    }

    public void setBusinessErrorDescription(String businessErrorDescription) {
        this.businessErrorDescription = businessErrorDescription;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Set<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Set<String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public static class Builder {

        private String businessErrorCode;
        private String businessErrorDescription;
        private String error;
        private Set<String> validationErrors;
        private Map<String, String> errors;

        public Builder setBusinessErrorCode(String businessErrorCode) {
            this.businessErrorCode = businessErrorCode;
            return this;
        }

        public Builder setBusinessErrorDescription(String businessErrorDescription) {
            this.businessErrorDescription = businessErrorDescription;
            return this;
        }

        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        public Builder setValidationErrors(Set<String> validationErrors) {
            this.validationErrors = validationErrors;
            return this;
        }

        public Builder setErrors(Map<String, String> errors) {
            this.errors = errors;
            return this;
        }

        public ExceptionResponse createExceptionResponse() {
            return new ExceptionResponse(businessErrorCode, businessErrorDescription, error, validationErrors, errors);
        }
    }
}
