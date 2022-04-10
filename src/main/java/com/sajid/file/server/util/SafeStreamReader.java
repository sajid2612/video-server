package com.sajid.file.server.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

@UtilityClass
public class SafeStreamReader {

	private static final long SIZE_10M = 10 * 1024 * 1024L;

	public static InputStream read(MultipartFile file) {
		try {
//			if (file.getSize() > SIZE_10M) {
//				return new AutoCloseInputStream(file.getInputStream());
//			} else {
				return new ByteArrayInputStream(file.getBytes());
			//}
		} catch (IOException e) {
			System.out.println("Cannot load multipart file.");
			throw new RuntimeException(e);
		}
	}
}

