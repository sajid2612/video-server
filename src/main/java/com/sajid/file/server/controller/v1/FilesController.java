package com.sajid.file.server.controller.v1;

import com.sajid.file.server.dto.FileData;
import com.sajid.file.server.service.FilesService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController("v1")
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/v1/files")
public class FilesController {
	private final FilesService filesService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FileData> listUploadFiles() {
		return filesService.listUploadedFiles();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> uploadFile(@RequestPart MultipartFile data, HttpServletResponse httpServletResponse) {
		return filesService.uploadFile(data, httpServletResponse);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/{fileId}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> downloadFile(@PathVariable("fileId") String fileId, HttpServletResponse httpServletResponse) {
		return filesService.downloadFile(fileId, httpServletResponse);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/{fileId}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteFile(@PathVariable("fileId") String fileId) {
		return filesService.deleteFile(fileId);
	}
}


