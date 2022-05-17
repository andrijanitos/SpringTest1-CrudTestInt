package com.andrijanica.SpringTest1.exception;

import lombok.Getter;

import static com.andrijanica.SpringTest1.exception.ErrorCode.ACCESS_DENIED;

/**
 * Exception class used for access denied.
 */
@Getter
public class AccessDeniedException extends GeneralException {

    public AccessDeniedException() {
        super(ACCESS_DENIED);
    }

    public AccessDeniedException(String message) {
        super(ACCESS_DENIED, message);
    }
}
