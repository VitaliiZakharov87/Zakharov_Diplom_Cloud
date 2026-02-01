package ru.netology.cloudservice.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.cloudservice.dto.ResultMessageDto;

@RestControllerAdvice(annotations = CloudExceptionHandlerAdvice.class)
public class ControllerExceptionHandlerAdvice {

    private ResponseEntity<ResultMessageDto> r(CloudException e) {
        HttpStatus status = e.status;
        ResultMessageDto error = e.getError();
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(FileInternalServerException.class)
    public ResponseEntity<ResultMessageDto> internalServerErrorHandler(FileInternalServerException e) {
        return r(e);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ResultMessageDto> notFoundInDatabaseErrorHandler(FileNotFoundException e) {
        return r(e);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ResultMessageDto> tokenNotFoundErrorHandler(TokenNotFoundException e) {
        return r(e);
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<ResultMessageDto> tokenNotFoundErrorHandler(UserNotAuthorizedException e) {
        return r(e);
    }
}
