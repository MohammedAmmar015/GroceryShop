
package com.ideas2it.groceryshop.exception;

public class FileStorage extends RuntimeException {
    public FileStorage(String message) {
        super(message);
    }

    public FileStorage(String message, Throwable cause) {
        super(message, cause);
    }
}

