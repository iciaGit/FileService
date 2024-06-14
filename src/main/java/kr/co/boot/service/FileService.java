package kr.co.boot.service;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${spring.servlet.multipart.location}") String root;

	public void upload(MultipartFile file) {
		// 1. 파일명 추출
		String fileName = file.getOriginalFilename();		
		// 2. 새파일명 생성
		String ext = fileName.substring(fileName.lastIndexOf("."));
		String newFileName = UUID.randomUUID().toString()+ext;
		logger.info(fileName+" -> "+newFileName);
		
		// 3. 파일 저장		
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(root+"/"+newFileName);
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void multiUpload(MultipartFile[] files) {
		for (MultipartFile file : files) {
			upload(file);
		}
		
	}

	public List<Map<String,String>> fileList() {
		
		List<Map<String,String>> fileList = new ArrayList<Map<String,String>>();
		
		try {
			for (String fileName : new File(root).list()) {
				Map<String, String> map = new HashMap<String, String>();
				String type = Files.probeContentType(Paths.get(root+"/"+fileName));
				map.put("name", fileName);
				
				// type 이 null 이 아니고 image 를 포함하고 있으면 "true"
				if(type != null && type.contains("image")) {
					map.put("image", "true");
				}else {
					map.put("image", "false");
				}				
				
				fileList.add(map);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("fileList : {}",fileList);	
		return fileList;
	}

	public ResponseEntity<Resource> download(String fileName) {

		// 특정 경로에서 파일을 읽어와 Resource 로 만든다.
		Resource resource = new FileSystemResource(root+"/"+fileName);
		HttpHeaders header = new HttpHeaders();
		

		try {
			// content-type: image,text,binary,...
			// application/octet-stream = binary
			header.add("content-type", "application/octet-stream");//content-type

			// content-Disposition: 추출하려는 컨텐트 형태(inline:문자열, attachment:다운 파일)
			// 단, 한글일 경우 깨짐 -> encoding 필요
			String oriFile = URLEncoder.encode("이미지_"+fileName, "UTF-8");			
			header.add("content-Disposition", "attachment;filename=\""+oriFile+"\"");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//보낼 내용, 헤더, 상태(200 또는 HttpStatus.OK 는 정상이라는 뜻)		
		return new ResponseEntity<Resource>(resource,header,HttpStatus.OK);
	}
	
	
	
	public void delete(String file) {
		File delFile = new File(root+"/"+file);
		if(delFile.exists()) {
			delFile.delete();
		}		
	}}
