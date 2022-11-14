package com.ideas2it.groceryshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author  RUBAN
 * @version  1.0
 * @since 05/11/22
 *
 */
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFound extends RuntimeException {
    public MyFileNotFound(String message) {
        super(message);
    }

    public MyFileNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
