package com.sajid.file.server.exception;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

	@ExceptionHandler(InvalidFileUploadRequestException.class)
	public ResponseEntity<?> invalidUploadRequest(InvalidFileUploadRequestException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidFileDownloadRequestException.class)
	public ResponseEntity<?> invalidDownloadRequest(InvalidFileDownloadRequestException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FileConflictException.class)
	public ResponseEntity<?> fileConflicted(FileConflictException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(DownloadFileNotFoundException.class)
	public ResponseEntity<?> fileNotFound(DownloadFileNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

}
