package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.common.dto.DomainValidationResponse;
import bank_mega.corsys.application.common.dto.ValidationResponse;
import bank_mega.corsys.domain.exception.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class AdviceApi {

    // ========== Validation Exceptions (400) ==========

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing
                ));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ValidationResponse.<Map<String, String>>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(errors)
                        .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationResponse<Map<String, String>>> handleConstraintViolation(
            ConstraintViolationException exception) {

        Map<String, String> errors = exception.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (existing, replacement) -> existing
                ));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ValidationResponse.<Map<String, String>>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(errors)
                        .build());
    }

    // ========== Not Found Exceptions (404) ==========

    @ExceptionHandler({
            UserNotFoundException.class,
            RoleNotFoundException.class,
            EmailTemplateNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<DomainValidationResponse> handleNotFoundExceptions(RuntimeException exception) {
        return buildDomainResponse(HttpStatus.NOT_FOUND, exception);
    }

    // ========== Conflict Exceptions (409) ==========

    @ExceptionHandler({
            UserAlreadyExistsException.class,
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<DomainValidationResponse> handleConflictExceptions(RuntimeException exception) {
        return buildDomainResponse(HttpStatus.CONFLICT, exception);
    }

    // ========== Unauthorized Exceptions (401) ==========

    @ExceptionHandler(AuthorizationServiceException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<DomainValidationResponse> handleUnauthorized(AuthorizationServiceException exception) {
        return buildDomainResponse(HttpStatus.UNAUTHORIZED, exception);
    }

    // ========== Domain Rule Violations (422) ==========

    @ExceptionHandler(DomainRuleViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)  // 422 is more specific than 500
    public ResponseEntity<DomainValidationResponse> handleDomainRuleViolation(DomainRuleViolationException exception) {
        return buildDomainResponse(HttpStatus.UNPROCESSABLE_ENTITY, exception);
    }

    // ========== Catch-All (500) ==========

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<DomainValidationResponse> handleGenericException(Exception exception) {
        log.error("Unexpected error occurred", exception);
        return buildDomainResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later.");
    }

    // ========== Helper Methods ==========

    private ResponseEntity<DomainValidationResponse> buildDomainResponse(HttpStatus status, RuntimeException exception) {
        return ResponseEntity
                .status(status)
                .body(DomainValidationResponse.builder()
                        .status(status.value())
                        .type(exception.getClass().getSimpleName())
                        .message(exception.getMessage())
                        .timestamp(Instant.now())
                        .build());
    }

    private ResponseEntity<DomainValidationResponse> buildDomainResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(DomainValidationResponse.builder()
                        .status(status.value())
                        .type(status.getReasonPhrase())
                        .message(message)
                        .timestamp(Instant.now())
                        .build());
    }
}