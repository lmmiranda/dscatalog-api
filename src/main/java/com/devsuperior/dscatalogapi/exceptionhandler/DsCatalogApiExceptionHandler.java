package com.devsuperior.dscatalogapi.exceptionhandler;

import com.devsuperior.dscatalogapi.exceptionhandler.excpetions.BusinessException;
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
        HandleError handleError = buildHandleError(HttpStatus.NOT_FOUND, "Resource not found", ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(handleError);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<HandleError> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        HandleError handleError = buildHandleError(HttpStatus.BAD_REQUEST, "Business exception", ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handleError);
    }

    private HandleError buildHandleError(HttpStatus httpStatus, String error, String message, String path) {
        HandleError handleError = new HandleError();

        handleError.setTimestamp(Instant.now());
        handleError.setStatus(httpStatus.value());
        handleError.setError(error);
        handleError.setMessage(message);
        handleError.setPath(path);

        return handleError;
    }
}
