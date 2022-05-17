package com.andrijanica.SpringTest1.exception.handler;

import com.andrijanica.SpringTest1.exception.GeneralException;
import com.andrijanica.SpringTest1.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static com.andrijanica.SpringTest1.exception.ErrorCode.ACCESS_DENIED;
import static com.andrijanica.SpringTest1.exception.ErrorCode.INVALID_REQUEST_DATA;
import static com.andrijanica.SpringTest1.exception.ErrorCode.UNDEFINED;
import static com.andrijanica.SpringTest1.exception.ErrorCode.VALIDATION_ERROR;
import static java.util.stream.Collectors.toList;

/**
 * Custom exception handler for REST endpoints.
 */
@ControllerAdvice
@RestController
@Getter
@Slf4j
@RequiredArgsConstructor
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final ConversionService conversionService;

    @ExceptionHandler(GeneralException.class)
    public final ResponseEntity<ErrorResponse> handleGeneralExceptionException(GeneralException ex, WebRequest request) {
        var message = ObjectUtils.isEmpty(ex.getMessage()) ? ex.getCode().name() : ex.getMessage();

        log.error("Handling GeneralException exception: {}", message);

        ErrorResponse exceptionResponse = new ErrorResponse(ex.getCode(), message);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                               WebRequest request) {
        log.error("Handling resource not found exception", ex);

        ErrorResponse exceptionResponse = new ErrorResponse(ex.getCode(), ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex,
                                                                           WebRequest request) {
        log.error("Handling access denied exception", ex);

        ErrorResponse exceptionResponse = new ErrorResponse(ACCESS_DENIED, ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Handling exception", ex);

        ErrorResponse exceptionResponse = new ErrorResponse(UNDEFINED, ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.debug("Handling validation exception: {}", ex.getMessage());

        final List<ValidationFieldError> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> conversionService.convert(objectError, ValidationFieldError.class))
                .collect(toList());

        ErrorResponse exceptionResponse = new ValidationErrorResponse(VALIDATION_ERROR.name(), errors);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.debug("Handling json convert to object validation exception: {}", ex.getMessage());

        ErrorResponse exceptionResponse = new ErrorResponse(INVALID_REQUEST_DATA, "Invalid request data");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
