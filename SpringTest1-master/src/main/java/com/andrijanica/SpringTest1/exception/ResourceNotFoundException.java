package com.andrijanica.SpringTest1.exception;

import lombok.Getter;

import static com.andrijanica.SpringTest1.exception.ErrorCode.NOT_FOUND;

/**
 * Exception class used for resources not found case.
 */
@Getter
public class ResourceNotFoundException extends GeneralException {

    public ResourceNotFoundException() {
        super(NOT_FOUND);
    }

    public ResourceNotFoundException(String message) {
        super(NOT_FOUND, message);
    }

}
