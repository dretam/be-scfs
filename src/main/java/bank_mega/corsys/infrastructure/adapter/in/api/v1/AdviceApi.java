package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.common.dto.DomainValidationResponse;
import bank_mega.corsys.application.common.dto.ValidationResponse;
import bank_mega.corsys.domain.exception.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AdviceApi {

    // Global Exception Advice

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationResponse<Map<String, String>>> methodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        Map<String, String> responseErrors = new HashMap<>();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    responseErrors.put(
                            error.getField(),
                            error.getDefaultMessage()
                    );
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ValidationResponse.<Map<String, String>>builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(responseErrors)
                                .build()
                );
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationResponse<Map<String, String>>> constraintViolationException(ConstraintViolationException exception) {
        Map<String, String> responseErrors = new HashMap<>();
        exception.getConstraintViolations().forEach(constraintViolation -> {
            if (constraintViolation.getPropertyPath().toString().isEmpty()) {
                responseErrors.put(
                        constraintViolation.getRootBeanClass().getSimpleName(),
                        constraintViolation.getMessage()
                );
            } else {
                responseErrors.put(
                        constraintViolation.getPropertyPath().toString(),
                        constraintViolation.getMessage()
                );
            }
        });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ValidationResponse.<Map<String, String>>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(responseErrors)
                        .build());
    }

    @ExceptionHandler(AuthorizationServiceException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ValidationResponse<String>> authorizationServiceException(AuthorizationServiceException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ValidationResponse.<String>builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message(exception.getMessage())
                        .build());
    }

    // Domain Advice Exception

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<DomainValidationResponse> userNotFoundException(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(DomainValidationResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .type(UserNotFoundException.class.getSimpleName())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<DomainValidationResponse> roleNotFoundException(RoleNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(DomainValidationResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .type(RoleNotFoundException.class.getSimpleName())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(UserEmailInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<DomainValidationResponse> userEmailInvalidException(UserEmailInvalidException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(DomainValidationResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .type(UserEmailInvalidException.class.getSimpleName())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(UserExistingPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<DomainValidationResponse> userEmailInvalidException(UserExistingPasswordException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(DomainValidationResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .type(UserExistingPasswordException.class.getSimpleName())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(DomainRuleViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<DomainValidationResponse> domainRuleViolationException(DomainRuleViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(DomainValidationResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .type(DomainRuleViolationException.class.getSimpleName())
                        .message(exception.getMessage())
                        .build());
    }

}
