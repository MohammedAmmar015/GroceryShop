package com.ideas2it.groceryshop.exceptionhandler;

import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  RUBAN
 * @version  1.0 05/11/22
 *
 */
@RestController
@Component
@ControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, String> handleNotFoundException(NotFoundException notFoundException) {
        Map<String, String> error = new HashMap<>();
        error.put("Error-Info", notFoundException.getMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Existed.class)
    public Map<String, String> handleAllReadyExisted(Existed existed) {
        Map<String, String> error = new HashMap<>();
        error.put("Error-Info", existed.getMessage());
        return error;
    }
}
