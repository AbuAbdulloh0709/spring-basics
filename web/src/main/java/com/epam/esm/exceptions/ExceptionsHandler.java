package com.epam.esm.exceptions;

import com.epam.esm.converter.MessageConvertor;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.*;

import static com.epam.esm.exceptions.ExceptionCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(DuplicateEntityException.class)
    public final ResponseEntity<Object> handleDuplicateEntityExceptions(DuplicateEntityException ex) {
        List<String> details = Collections.singletonList(MessageConvertor.toLocale(ex.getLocalizedMessage()));
        ErrorResponse errorResponse = new ErrorResponse(CONFLICT_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, CONFLICT);
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public final ResponseEntity<Object> handleNoSuchEntityExceptions(NoSuchEntityException ex) {
        List<String> details = Collections.singletonList(MessageConvertor.toLocale(ex.getLocalizedMessage()));
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(IncorrectParameterException.class)
    public final ResponseEntity<Object> handleIncorrectParameterExceptions(IncorrectParameterException ex) {
        Iterator<Map.Entry<String, Object[]>> exceptions = ex.getExceptionResult().getExceptionMessages().entrySet().iterator();
        List<String> details = new ArrayList<>();
        while (exceptions.hasNext()) {
            Map.Entry<String, Object[]> exception = exceptions.next();
            String message = MessageConvertor.toLocale(exception.getKey());
            String detail = String.format(message, exception.getValue());
            details.add(detail);
        }

        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredentialsException() {
        List<String> details = Collections.singletonList(MessageConvertor.toLocale("exception.invalidAuthorization"));
        ErrorResponse errorResponse = new ErrorResponse(UNAUTHORIZED_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, UNAUTHORIZED);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public final ResponseEntity<Object> handleUnsupportedOperationExceptions() {
        List<String> details = Collections.singletonList(MessageConvertor.toLocale("exception.unsupportedOperation"));
        ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, JsonProcessingException.class})
    public final ResponseEntity<Object> handleBadRequestExceptions() {
        List<String> details = Collections.singletonList(MessageConvertor.toLocale("exception.badRequest"));
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<Object> handleBadRequestException() {
        List<String> details = Collections.singletonList(MessageConvertor.toLocale("exception.noHandler"));
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<Object> methodNotAllowedExceptionException() {
        List<String> details = Collections.singletonList(MessageConvertor.toLocale("exception.notSupported"));
        ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
    }
}
