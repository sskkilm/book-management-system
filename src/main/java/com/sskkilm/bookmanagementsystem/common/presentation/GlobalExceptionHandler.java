package com.sskkilm.bookmanagementsystem.common.presentation;

import com.sskkilm.bookmanagementsystem.common.error.ApiException;
import com.sskkilm.bookmanagementsystem.common.error.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.INVALID_PARAMETER;
import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.UNKNOWN;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse<String>> handleApiException(ApiException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(new ErrorResponse<>(
                        e.getErrorCode().name(),
                        e.getMessage(),
                        "none")
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> detail = parseDetail(e);
        return ResponseEntity
                .status(INVALID_PARAMETER.getStatus())
                .body(new ErrorResponse<>(
                        INVALID_PARAMETER.name(),
                        INVALID_PARAMETER.getMessage(),
                        detail
                ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> detail = parseDetail(e);
        return ResponseEntity
                .status(INVALID_PARAMETER.getStatus())
                .body(new ErrorResponse<>(
                        INVALID_PARAMETER.name(),
                        INVALID_PARAMETER.getMessage(),
                        detail
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<String>> handleException(Exception e) {
        return ResponseEntity
                .status(UNKNOWN.getStatus())
                .body(new ErrorResponse<>(
                        UNKNOWN.name(),
                        UNKNOWN.getMessage(),
                        e.getMessage()
                ));
    }

    private static Map<String, String> parseDetail(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null ?
                                fieldError.getDefaultMessage() : "요청 파라미터가 유효하지 않습니다.",
                        (first, second) -> first
                ));
    }

    private static Map<String, String> parseDetail(ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        violation -> getParameter(violation.getPropertyPath()),
                        violation -> violation.getMessage() != null ?
                                violation.getMessage() : "요청 파라미터가 유효하지 않습니다.",
                        (first, second) -> first
                ));
    }

    private static String getParameter(Path path) {
        String string = path.toString();
        return string.split("\\.")[1];
    }
}
