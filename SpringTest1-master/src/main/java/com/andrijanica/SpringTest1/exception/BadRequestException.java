package com.andrijanica.SpringTest1.exception;

import lombok.Getter;

import static com.andrijanica.SpringTest1.exception.ErrorCode.BAD_REQUEST;


/**
 * Exception class used for resources not found case
 */
@Getter
public class BadRequestException extends GeneralException {

    public BadRequestException() {
        super(BAD_REQUEST);
    }

    public BadRequestException(String message) {
        super(BAD_REQUEST, message);
    }

}
