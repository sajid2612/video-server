package com.sajid.file.server.exception;

public class DownloadFileNotFoundException extends RuntimeException {
	public DownloadFileNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
