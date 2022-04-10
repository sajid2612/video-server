package com.sajid.file.server.dto;

import lombok.Data;

@Data
public class FileData {
	private String fileId;
	private String name;
	private Long size;
	private String created_at;
}
