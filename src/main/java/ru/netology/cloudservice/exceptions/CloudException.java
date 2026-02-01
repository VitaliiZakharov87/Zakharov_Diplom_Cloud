package ru.netology.cloudservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.netology.cloudservice.dto.ResultMessageDto;
import ru.netology.cloudservice.service.Operations;

import static java.lang.String.format;

@Getter
public abstract class CloudException extends InternalError {
    protected HttpStatus status;
    protected ResultMessageDto error;

    public CloudException(
            HttpStatus status,
            Operations operation,
            String param,
            String message) {
        this.error = new ResultMessageDto(format(message, operation.getOp(), param));
        this.status = status;
    }
}
