package com.green.bloom.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.green.bloom.domain.dto.DriveDTO;
import com.green.bloom.domain.entity.DriveEntity;
import com.green.bloom.service.DriveService;
import com.green.bloom.service.impl.DriveServiceProcess;
import com.green.bloom.utils.FileUploadUtil;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;

import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequiredArgsConstructor
public class DriveController {
	
	private final S3Client s3Client;
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	@Value("${cloud.aws.s3.upload-temp}")
	private String temp;
	
	@Value("${cloud.aws.s3.upload-path}")
	private String upload;

	private final DriveService dService;
	private final FileUploadUtil fileUtil3;

	
	@GetMapping("/addDrive")
	public String addDrive() {
		return "drive/addDrive.html";
	}
	@GetMapping("/drive-list")
	public String driveList(PageRequestDTO pageRequestDTO,Model model) {
		model.addAttribute("result", dService.getList(pageRequestDTO));
		dService.drivelist(model);
		return "drive/drive-list.html";
	}
	@PostMapping("/drive")
	public String postMethodName(DriveDTO dto) {
		//temp->src 이동
		String destinationKey=fileUtil3.s3TempToSrc(dto.getNewName());
		dService.save(dto.updateBucketKey(destinationKey));
		
		return "redirect:/drive-list";
	}
	@ResponseBody
	@PostMapping("/files-temp-upload")
	public Map<String, String> fileTempUpload(@RequestParam(name = "driveFile") MultipartFile driveFile) {
		return fileUtil3.s3TempUpload(driveFile);
		
	}
	@ResponseBody
	@GetMapping("/files/{newName}/{orgName}")
    public ResponseEntity<InputStreamResource> downloadFile(
    		@PathVariable(name="newName") String newName, @PathVariable(name="orgName") String orgName) throws IOException {
        InputStream fileStream = s3Client.getObject(builder->builder.bucket(bucket).key(upload+newName));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", orgName);
        String encodedOrgName = URLEncoder.encode(orgName, "UTF-8");
        headers.setContentDispositionFormData("attachment", encodedOrgName);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(fileStream));
    }
	
}
