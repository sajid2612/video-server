package com.sajid.file.server.exception;

public class FileConflictException extends RuntimeException {
	public FileConflictException(String errorMessage) {
		super(errorMessage);
	}
}
