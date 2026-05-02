package com.onlineakimbank.cardservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private ErrorDetails getErrorDetails(Exception e, WebRequest webRequest, HttpStatus httpStatus) {
        return new ErrorDetails(LocalDateTime.now(),
                e.getMessage(),
                webRequest.getDescription(false),
                httpStatus.name());
    }

    private ResponseEntity<ErrorDetails> handleException(Exception e, WebRequest webRequest, HttpStatus status) {
        return new ResponseEntity<>(getErrorDetails(e, webRequest, status), status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleEntityNotFoundException(EntityNotFoundException e,
                                                                      WebRequest webRequest) {
        return handleException(e, webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            NoSuchMethodException.class,
            InvocationTargetException.class,
            IllegalAccessException.class
    })
    public ResponseEntity<ErrorDetails> handleClassCastExceptions(Exception e, WebRequest webRequest) {
        return handleException(e, webRequest, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDetails> handleAuthenticationException(AuthenticationException e,
                                                                      WebRequest request) {
        return handleException(e, request, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({UsernameNotFoundException.class, IllegalStateException.class})
    public ResponseEntity<ErrorDetails> handleCardNotFoundException(Exception e,
                                                                        WebRequest request) {
        return handleException(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    public ResponseEntity<ErrorDetails> handleResponseStatusException(ResponseStatusException e,
                                                                      WebRequest request) {
        return handleException(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleOtherExceptions(Exception e, WebRequest request) {
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
