package com.ideas2it.groceryshop.exceptionhandler;

import com.ideas2it.groceryshop.dto.ErrorDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.UnexpectedTypeException;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author  RUBAN
 * @version  1.0
 * @since  05/11/22
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
        error.setErrorMessage(notFoundException.getMessage());
        error.setStatusCode(404);
        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleIOException(IOException ioException) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setStatusCode(404);
        errorDto.setErrorMessage(ioException.getMessage());
        return errorDto;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Existed.class)
    public ErrorDto handleAlReadyExistsException(Existed existed) {
        ErrorDto error = new ErrorDto();
        error.setErrorMessage(existed.getMessage());
        error.setStatusCode(404);
        return error;
    }

    /**
     * This method is used to handle BadCredentialsException
     *
     * @param badCredentialsException it contains error message
     * @return errorDto it contains error message and error status code
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParseException.class)
    public ErrorDto parseException(ParseException parseException) {
        ErrorDto error = new ErrorDto();
        error.setErrorMessage(parseException.getMessage());
        error.setStatusCode(500);
        return error;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorDto handlerBadCredentials(BadCredentialsException badCredentialsException) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMessage(badCredentialsException.getMessage());
        errorDto.setStatusCode(401);
        return errorDto;
    }

    /**
     * This method is used to handle UsernameNotFoundException
     *
     * @param usernameNotFoundException it contains error message
     * @return errorDto it contains error message and error status code
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorDto handlerUsernameNotFound
            (UsernameNotFoundException usernameNotFoundException) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMessage(usernameNotFoundException.getMessage());
        errorDto.setStatusCode(404);
        return errorDto;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) error;
            String fieldName =   fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        return errors;
    }

    /**
     * This method is used to handle HttpMessageNotReadableException
     *
     * @param httpMessageNotReadableException it contains error message
     * @return errorDto it contains error message and error status code
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorDto handlerMessageNotReadableException
    (HttpMessageNotReadableException httpMessageNotReadableException) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMessage(httpMessageNotReadableException.getMessage());
        errorDto.setStatusCode(400);
        return errorDto;
    }

    /**
     * This method is used to handle SQLIntegrityConstraintViolationException
     *
     * @param sqlIntegrityConstraintViolationException it contains error message
     * @return errorDto it contains error message and error status code
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ErrorDto handlerSQLIntegrityConstraintViolation
            (SQLIntegrityConstraintViolationException
                     sqlIntegrityConstraintViolationException){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMessage
                (sqlIntegrityConstraintViolationException.getMessage());
        errorDto.setStatusCode(400);
        return errorDto;
    }
}
