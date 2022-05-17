package com.andrijanica.SpringTest1.exception.handler;

import com.andrijanica.SpringTest1.exception.ErrorCode;;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Model for exception response.
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {
    protected ErrorCode code;
    protected String message;

    public ErrorResponse() {
        this.code = ErrorCode.UNDEFINED;
    }
}
