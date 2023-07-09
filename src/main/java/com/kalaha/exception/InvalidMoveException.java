package com.kalaha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidMoveException extends RuntimeException{

	private static final long serialVersionUID = 7703950516268544786L;
	
	public InvalidMoveException(String message) {
		super(message);
	}

}
