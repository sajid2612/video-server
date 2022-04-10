package com.sajid.file.server.exception;

public class InvalidFileDownloadRequestException extends RuntimeException {
	public InvalidFileDownloadRequestException(String errorMessage) {
		super(errorMessage);
	}
}
