package com.sajid.file.server.util;

import com.sajid.file.server.exception.InvalidFileDownloadRequestException;
import java.util.Base64;

public class Base64EncoderDecoder {

	public static String encode(String orignialStr) {
		String encodedString = Base64.getEncoder().withoutPadding().encodeToString(orignialStr.getBytes());
		System.out.println(encodedString);
		return encodedString;
	}


	public static String decode(String encodedString) {
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
			String decodedString = new String(decodedBytes);
			System.out.println(decodedString);
			return decodedString;
		} catch (Exception e) {
			throw new InvalidFileDownloadRequestException("Invalid file id");
		}
	}
}
