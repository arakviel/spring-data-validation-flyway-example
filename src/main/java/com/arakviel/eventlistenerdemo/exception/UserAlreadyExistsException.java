package com.arakviel.eventlistenerdemo.exception;

import java.io.Serial;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5676859663763949991L;

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
