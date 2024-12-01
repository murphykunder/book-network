package com.alimurph.book.handler;

import com.alimurph.book.exception.OperationNotPermittedException;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exception){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        new ExceptionResponse.Builder()
                                .setBusinessErrorCode(BusinessErrorCodes.ACCOUNT_LOCKED.getCode())
                                .setBusinessErrorDescription(BusinessErrorCodes.ACCOUNT_LOCKED.getDescription())
                                .setError(exception.getMessage())
                                .createExceptionResponse()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exception){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        new ExceptionResponse.Builder()
                                .setBusinessErrorCode(BusinessErrorCodes.ACCOUNT_DISABLED.getCode())
                                .setBusinessErrorDescription(BusinessErrorCodes.ACCOUNT_DISABLED.getDescription())
                                .setError(exception.getMessage())
                                .createExceptionResponse()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exception){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        new ExceptionResponse.Builder()
                                .setBusinessErrorCode(BusinessErrorCodes.BAD_CREDENTIALS.getCode())
                                .setBusinessErrorDescription(BusinessErrorCodes.BAD_CREDENTIALS.getDescription())
                                .setError(BusinessErrorCodes.BAD_CREDENTIALS.getDescription())
                                .createExceptionResponse()
                );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exception){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ExceptionResponse.Builder()
                                .setError(exception.getMessage())
                                .createExceptionResponse()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exception){

        Set<String> errors = new HashSet<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
        	errors.add(error.getDefaultMessage());
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ExceptionResponse.Builder()
                                .setValidationErrors(errors)
                                .createExceptionResponse()
                );
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ExceptionResponse.Builder()
                                .setBusinessErrorDescription(exception.getMessage())
                                .setError(exception.getMessage())
                                .createExceptionResponse()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception){
        // log this error for debugging purpose
        exception.printStackTrace(); // TODO replace with logger

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ExceptionResponse.Builder()
                                .setBusinessErrorDescription("Internal error, contact the admin")
                                .setError(exception.getMessage())
                                .createExceptionResponse()
                );
    }
    
}
