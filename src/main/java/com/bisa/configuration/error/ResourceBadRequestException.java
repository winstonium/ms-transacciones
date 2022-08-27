package com.bisa.configuration.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResourceBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String message;
    private final HttpStatus httpStatus;

    public ResourceBadRequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}