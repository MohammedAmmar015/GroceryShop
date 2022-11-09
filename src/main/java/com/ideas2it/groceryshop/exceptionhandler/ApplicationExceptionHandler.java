package com.ideas2it.groceryshop.exceptionhandler;

import com.ideas2it.groceryshop.dto.ErrorDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    @ExceptionHandler(NotFound.class)
    public ErrorDto handleNotFoundException(NotFound notFoundException) {
        ErrorDto error = new ErrorDto();
        error.setErrorMessage( notFoundException.getMessage());
        error.setStatusCode(404);
        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Existed.class)
    public ErrorDto handleAlReadyExists(Existed existed) {
        ErrorDto error = new ErrorDto();
        error.setErrorMessage(existed.getMessage());
        error.setStatusCode(404);
        return error;
    }
}
