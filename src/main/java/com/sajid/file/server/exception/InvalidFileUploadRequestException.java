package com.sajid.file.server.exception;

public class InvalidFileUploadRequestException extends RuntimeException {
	public InvalidFileUploadRequestException(String errorMessage) {
		super(errorMessage);
	}
}
