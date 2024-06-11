package kr.co.boot.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import kr.co.boot.service.FileService;

@Controller
public class FileController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired FileService service;

	
	// <Context docBase="C:/upload" path="/file"/>
	// /file 이라는 요청이 오면 C:/upload 로 연결 해라
	
	@RequestMapping(value="/")
	public String home() {
		return "home";
	}
	
	@RequestMapping(value="/upload", method = RequestMethod.POST)
	public String upload(MultipartFile uploadFile) {
		logger.info("upload file : "+uploadFile.getOriginalFilename());
		service.upload(uploadFile);
		return "redirect:/fileList";
	}
	
	@RequestMapping(value="/multiUpload")
	public String multiUpload(MultipartFile[] files) {
		logger.info("file length : "+files.length);
		service.multiUpload(files);
		return "redirect:/fileList";
	}
	
	@RequestMapping(value="/fileList")
	public String fileList(Model model) {
		List<Map<String,String>> list = service.fileList();
		logger.info("list : {}",list);
		model.addAttribute("list", list);
		return "result";
	}
	
	@RequestMapping(value="/download")
	public ResponseEntity<Resource> download(String fileName) {
		logger.info("download fileName : "+fileName);
		return service.download(fileName);
	}
	
	
	@RequestMapping(value="/delete")
	public String delete(String file) {
		logger.info("delete file name");
		service.delete(file);
		return "redirect:/fileList";
	}
	

}