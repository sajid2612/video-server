package com.sajid.file.server.builder;

import com.sajid.file.server.dto.FileData;
import com.sajid.file.server.util.Base64EncoderDecoder;
import com.sajid.file.server.util.DateUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseBuilder {
	public FileData buildFileData(Path p) throws IOException, ParseException {
		FileData fileData = new FileData();
		fileData.setName(p.getFileName().toString());
		fileData.setFileId(Base64EncoderDecoder.encode(p.getFileName().toString()));
		fileData.setSize(Files.size(p));
		fileData.setCreated_at(DateUtil.toDateTime(p));
		return fileData;
	}
}
