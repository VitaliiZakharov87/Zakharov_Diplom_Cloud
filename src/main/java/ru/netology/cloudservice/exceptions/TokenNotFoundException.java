package ru.netology.cloudservice.exceptions;

import org.springframework.http.HttpStatus;
import ru.netology.cloudservice.service.Operations;

public class TokenNotFoundException extends CloudException {
    public TokenNotFoundException(Operations operation, String param) {
        super(
                HttpStatus.NOT_FOUND,
                operation,
                param,
                "Request failed (%s) token: %s");
    }
}
