package com.sajid.file.server.validator;

import static com.sajid.file.server.constant.ServerConstant.SUPPORTED_CONTENT_TYPES;

import com.sajid.file.server.exception.InvalidFileUploadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class RequestValidator {
	public void validateUploadFileReq(MultipartFile file) {
		if (file.getOriginalFilename() == null ||  file.getOriginalFilename().isEmpty()) {
			throw new InvalidFileUploadRequestException("File name can not be blank");
		}
		if (!SUPPORTED_CONTENT_TYPES.contains(file.getContentType())) {
			throw new InvalidFileUploadRequestException("Unsupported video type");
		}
	}
}
