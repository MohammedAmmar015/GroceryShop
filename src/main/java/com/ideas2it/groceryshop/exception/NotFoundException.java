package com.ideas2it.groceryshop.exception;

/**
 * @author  RUBAN
 * @version  1.0 05/11/22
 *
 */
public class NotFoundException extends Exception{
    public NotFoundException() {
        super();
    }

    public NotFoundException(String exception) {
        super(exception);
    }

    public NotFoundException(Throwable error) {
        super(error);
    }

    public NotFoundException(String exception, Throwable error) {
        super(exception, error);
    }


}
