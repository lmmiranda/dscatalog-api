package com.devsuperior.dscatalogapi.exceptionhandler;

import com.devsuperior.dscatalogapi.exceptionhandler.excpetions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class DsCatalogApiExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<HandleError> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        HandleError handleError = new HandleError();

        handleError.setTimestamp(Instant.now());
        handleError.setStatus(HttpStatus.NOT_FOUND.value());
        handleError.setError("Resource not found");
        handleError.setMessage(ex.getMessage());
        handleError.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(handleError);
    }
}
