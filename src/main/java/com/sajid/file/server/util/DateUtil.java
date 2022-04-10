package com.sajid.file.server.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtil {
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String toDateTime(Path p) throws IOException, ParseException {
		return format.format(Files.readAttributes(p, BasicFileAttributes.class).creationTime().toMillis());
	}
}
