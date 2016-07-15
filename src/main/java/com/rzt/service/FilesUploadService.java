package com.rzt.service;

import org.springframework.web.multipart.MultipartFile;

public interface FilesUploadService {

	String uploadFiles( MultipartFile file );
}
