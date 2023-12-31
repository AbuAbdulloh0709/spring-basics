package com.epam.esm.exceptions;

import com.epam.esm.converter.MessageConvertor;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Iterator;
import java.util.Map;

import static com.epam.esm.exceptions.ExceptionCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(DuplicateEntityException.class)
    public final ResponseEntity<Object> handleDuplicateEntityExceptions(DuplicateEntityException ex) {
        String details = MessageConvertor.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(CONFLICT_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, CONFLICT);
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public final ResponseEntity<Object> handleNoSuchEntityExceptions(NoSuchEntityException ex) {
        String details = MessageConvertor.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(IncorrectParameterException.class)
    public final ResponseEntity<Object> handleIncorrectParameterExceptions(IncorrectParameterException ex) {
        Iterator<Map.Entry<String, Object[]>> exceptions = ex.getExceptionResult().getExceptionMessages().entrySet().iterator();
        StringBuilder details = new StringBuilder();
        while (exceptions.hasNext()) {
            Map.Entry<String, Object[]> exception = exceptions.next();
            String message = MessageConvertor.toLocale(exception.getKey());
            String detail = String.format(message, exception.getValue());
            details.append(detail).append(' ');
        }

        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST_EXCEPTION.toString(), details.toString());
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public final ResponseEntity<Object> handleUnsupportedOperationExceptions() {
        String details = MessageConvertor.toLocale("exception.unsupportedOperation");
        ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, JsonProcessingException.class})
    public final ResponseEntity<Object> handleBadRequestExceptions() {
        String details = MessageConvertor.toLocale("exception.badRequest");
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<Object> handleBadRequestException() {
        String details = MessageConvertor.toLocale("exception.noHandler");
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<Object> methodNotAllowedExceptionException() {
        String details = MessageConvertor.toLocale("exception.notSupported");
        ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
    }
}
