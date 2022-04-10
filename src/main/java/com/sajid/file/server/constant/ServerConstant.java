package com.sajid.file.server.constant;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ServerConstant {
	//Storage location
	public static final String VOLUME_PATH = "/var/lib/data";
	public static final String USER_DIRECTORY = System.getProperty("user.dir");
	public static final String FALLBACK_STORAGE_PATH = USER_DIRECTORY + File.separator + "storage";

	public static List<String> SUPPORTED_CONTENT_TYPES = Arrays.asList("video/mp4", "video/mpeg");

	//Error messages
	public static final String FILE_NOT_FOUND = "File not found";
	public static final String FILE_ALREADY_EXISTS = "Conflict found, file already exists";
}
