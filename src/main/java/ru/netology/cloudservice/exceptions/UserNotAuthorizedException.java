package ru.netology.cloudservice.exceptions;

import org.springframework.http.HttpStatus;
import ru.netology.cloudservice.service.Operations;

public class UserNotAuthorizedException extends CloudException {
    public UserNotAuthorizedException(Operations operation, String param) {
        super(
                HttpStatus.UNAUTHORIZED,
                operation,
                param,
                "Login failed (%s) user: %s"
        );
    }
}
