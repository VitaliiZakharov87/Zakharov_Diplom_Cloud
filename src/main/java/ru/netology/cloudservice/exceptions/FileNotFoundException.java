package ru.netology.cloudservice.exceptions;

import org.springframework.http.HttpStatus;
import ru.netology.cloudservice.service.Operations;

public class FileNotFoundException extends CloudException {
    public FileNotFoundException(Operations operation, String param) {
        super(
                HttpStatus.NOT_FOUND,
                operation,
                param,
                "File %s failed (not found in Catalogue) file: %s"
        );
    }
}
