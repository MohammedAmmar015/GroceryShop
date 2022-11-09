package com.ideas2it.groceryshop.exceptionhandler;

import com.ideas2it.groceryshop.dto.ErrorDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
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
    @ExceptionHandler(NotFoundException.class)
    public ErrorDto handleNotFoundException(NotFoundException notFoundException) {
        ErrorDto error = new ErrorDto();
        error.setErrorMessage( notFoundException.getMessage());
        error.setStatusCode(404);
        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExistedException.class)
    public ErrorDto handleAlReadyExists(ExistedException existed) {
        ErrorDto error = new ErrorDto();
        error.setErrorMessage(existed.getMessage());
        error.setStatusCode(404);
        return error;
    }
}
