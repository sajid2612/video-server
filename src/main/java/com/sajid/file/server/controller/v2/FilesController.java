package com.sajid.file.server.controller.v2;

import com.sajid.file.server.dto.FileData;
import com.sajid.file.server.service.FilesService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController("v2")
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/v2/files")
public class FilesController {
	private final FilesService filesService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void uploadFile(@RequestPart MultipartFile data, HttpServletResponse httpServletResponse) {
		filesService.uploadFile(data, httpServletResponse);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FileData> searchFiles(
			@RequestParam(value = "filter.name", required = false) String name,
			@RequestParam(value = "filter.sizeUpto", required = false) Long sizeUpto,
			@RequestParam(value = "filter.videoType", required = false) String videoType) {
		return filesService.searchFiles(name, sizeUpto, videoType);
	}

	@DeleteMapping(value = "/{fileId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteFile(@PathVariable("fileId") String fileId) {
		filesService.deleteFile(fileId);
	}

}


