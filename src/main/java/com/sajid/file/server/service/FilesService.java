package com.sajid.file.server.service;

import static com.sajid.file.server.constant.ServerConstant.FALLBACK_STORAGE_PATH;
import static com.sajid.file.server.constant.ServerConstant.FILE_ALREADY_EXISTS;
import static com.sajid.file.server.constant.ServerConstant.FILE_NOT_FOUND;
import static com.sajid.file.server.constant.ServerConstant.VOLUME_PATH;

import com.sajid.file.server.builder.ResponseBuilder;
import com.sajid.file.server.dto.FileData;
import com.sajid.file.server.exception.DownloadFileNotFoundException;
import com.sajid.file.server.exception.FileConflictException;
import com.sajid.file.server.util.Base64EncoderDecoder;
import com.sajid.file.server.util.SafeStreamReader;
import com.sajid.file.server.validator.RequestValidator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilesService {
	private static String storageFolderLocation = getFolderPath();
	private final RequestValidator requestValidator;

	private static String getFolderPath() {
		if (Files.isDirectory(Paths.get(VOLUME_PATH))) {
			storageFolderLocation = VOLUME_PATH;
		} else {
			storageFolderLocation = FALLBACK_STORAGE_PATH;
			File directory = new File(storageFolderLocation);
			if (!directory.exists()){
				directory.mkdir();
			}
		}
		return storageFolderLocation;
	}

	public ResponseEntity<String> uploadFile(MultipartFile data, HttpServletResponse httpServletResponse) {
		requestValidator.validateUploadFileReq(data);
		String targetLocation = storageFolderLocation + File.separator + data.getOriginalFilename();
		File targetFile = new File(targetLocation);
		if (targetFile.exists()) {
			throw new FileConflictException(FILE_ALREADY_EXISTS);
		}
		InputStream inputStream = SafeStreamReader.read(data);
		try (
				OutputStream outStream = new FileOutputStream(targetFile)
		) {
			byte[] buf = new byte[8*1024*1024];
			int length;
			while ((length = inputStream.read(buf)) > 0) {
				outStream.write(buf, 0, length);
			}
		} catch (IOException ioe) {
			log.error("Exception occurred during upload "+ioe);
			return ResponseEntity.internalServerError()
					.body("File upload failed");
		}

		log.info("File Uploaded successfully>>>");
		return ResponseEntity.created(URI.create(targetLocation))
				.header("Location", targetLocation)
				.body("File uploaded successfully");
	}

	public ResponseEntity<String> downloadFile(String fileId, HttpServletResponse httpServletResponse) {
		String fileName = Base64EncoderDecoder.decode(fileId);
		String sourceLocation = storageFolderLocation + File.separator + fileName;
		File sourceFile = new File(sourceLocation);
		if (!sourceFile.exists()) {
			throw new DownloadFileNotFoundException(FILE_NOT_FOUND);
		}

		try (
			InputStream inputStream = new FileInputStream(sourceLocation);
		) {
			byte[] buf = new byte[8*1024*1024];
			int length;
			while ((length = inputStream.read(buf)) != -1) {
				httpServletResponse.getOutputStream().write(buf, 0, length);
			}
		} catch (IOException ioe) {
			log.error("Exception occurred during download "+ioe);
			return ResponseEntity.internalServerError()
					.body("File download failed");
		}
		log.info("File downloaded successfully - ");
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename="+fileName)
				.header("Content-Type", URLConnection.guessContentTypeFromName(fileName))
				.body("File downloaded successfully");
	}

	public ResponseEntity<String> deleteFile(String fileId) {
		String fileName = Base64EncoderDecoder.decode(fileId);
		String sourceLocation = storageFolderLocation + File.separator + fileName;
		File sourceFile = new File(sourceLocation);
		if (!sourceFile.exists()) {
			throw new DownloadFileNotFoundException(FILE_NOT_FOUND);
		}
		if (sourceFile.delete()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.status(500).body("File can not be deleted due to some reasons, may be due to permission issue or file is currently being references from some where else");
		}
	}

	public List<FileData> listUploadedFiles() {
		List<FileData> fileDataList = new ArrayList<>();
		try {
			List<Path> list = Files.list(Paths.get(storageFolderLocation)).collect(Collectors.toList());
			if (list.size() == 0) {
				return Collections.emptyList();
			} else {
				for (Path p : list) {
					fileDataList.add(ResponseBuilder.buildFileData(p));
				}
			}
		} catch (IOException | ParseException e) {
			log.error("Exception occurred during listing files "+e);
		}
		return fileDataList;
	}

	public List<FileData> searchFiles(String name, Long sizeUpto, String videoType) {
		return listUploadedFiles().stream()
				.filter(fileData -> name == null || fileData.getName().contains(name))
				.filter(fileData -> sizeUpto == null || fileData.getSize().compareTo(sizeUpto) < 0)
				.filter(fileData -> videoType == null || filterByVideoType(videoType, fileData.getName()))
				.collect(Collectors.toList());
	}

	private boolean filterByVideoType(String videoType, String name) {
		String videoType1 =  URLConnection.getFileNameMap().getContentTypeFor(name);
		System.out.println(videoType);
		return videoType1 != null && videoType1.contains(videoType);
	}

}
