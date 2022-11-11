package com.ideas2it.groceryshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFound extends RuntimeException {
    public MyFileNotFound(String message) {
        super(message);
    }

    public MyFileNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
