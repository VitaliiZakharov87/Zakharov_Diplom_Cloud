package ru.netology.cloudservice.exceptions;

import org.springframework.http.HttpStatus;
import ru.netology.cloudservice.service.Operations;

public class FileInternalServerException extends CloudException {
    public FileInternalServerException(Operations operation, String param) {
        super(HttpStatus.INTERNAL_SERVER_ERROR,
                operation,
                param,
                "File %s failed (internal error) file: %s"
        );
    }
}
