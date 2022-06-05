package com.jumbo.store.exception;

import com.jumbo.store.payload.ExceptionResponse;
import com.mongodb.MongoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class JumboStoreExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(AuthenticationException ex) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), Instant.now(), null);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(AccessDeniedException ex) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage(), Instant.now(), null);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> messages = new ArrayList<>(fieldErrors.size());
        for (FieldError error : fieldErrors) {
            messages.add(error.getField() + " - " + error.getDefaultMessage());
        }
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        Instant.now(), messages);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(MethodArgumentTypeMismatchException ex) {
        String message = "Parameter '" + ex.getParameter().getParameterName() + "' must be '"
                + Objects.requireNonNull(ex.getRequiredType()).getSimpleName() + "'";
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), message, Instant.now(), null);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(HttpRequestMethodNotSupportedException ex) {
        String message = "Method '" + ex.getMethod() + "' not supported. List of all supported methods - "
                + ex.getSupportedHttpMethods();
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), message, Instant.now(), null);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(HttpMessageNotReadableException ex) {
        String message = "Please provide Request Body in valid JSON format";
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), message, Instant.now(), null);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MongoException.class})
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(MongoException ex) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Instant.now(), null);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(UsernameNotFoundException ex) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Instant.now(), null);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }
}
