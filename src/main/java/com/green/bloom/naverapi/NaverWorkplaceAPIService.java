package com.green.bloom.naverapi;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.bloom.domain.entity.EmployeeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NaverWorkplaceAPIService {
	
	private final OpenApiUtil naverApi;
	
	@Value("${naver.ncp.access-key}")
	private String accessKey;
	@Value("${naver.ncp.secret-key}")
	private String secretKey;
	@Value("${naver.ncp.company-id}")
	private String companyId;
	
	/**
	 * 
	 * @param method  		"GET", "POST"
	 * @param url 			ex)"/organization/apigw/v2/company/"+companyId+"/department";
	 * @param timestamp     Long.toString(System.currentTimeMillis())
	 * @return
	 */
	private String makeSignature(String method,String url,String timestamp){
		String space = " ";
		String newLine = "\n";
		//String method = _method;
		//String url = _url;			//url (include query string)
		//String timestamp = _timestamp;//current timestamp (epoch)
		//String accessKey = accessKey;	//access key id (from portal or Sub Account)
		//String secretKey = secretKey;
		//System.out.println("url: " + url);
		String message = new StringBuilder()
			.append(method)
			.append(space)
			.append(url)
			.append(newLine)
			.append(timestamp)
			.append(newLine)
			.append(accessKey)
			.toString();
		
		SecretKeySpec signingKey;
		String encodeBase64String = null;
		
		try {
			signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);

			byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
			encodeBase64String = Base64.encodeBase64String(rawHmac);
			//encodeBase64String = java.util.Base64.getEncoder().encodeToString(rawHmac);
			//System.out.println("encodeBase64String1:" + encodeBase64String1);
			//System.out.println("encodeBase64String:" + encodeBase64String);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	return encodeBase64String;
	}

	//requestHeaders 생성 메소드
	private Map<String, String> createNaverWorkspaceRequestHeaders(String method, String url) {
		String timestamp = Long.toString(System.currentTimeMillis()); 
		
		//signature 생성 메소드
		String signature = makeSignature(method, url, timestamp);
		System.out.println(signature);
		
		//요청 헤더 정보
		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("Content-Type", "application/json");
		requestHeaders.put("x-ncp-apigw-timestamp", timestamp);
		requestHeaders.put("x-ncp-iam-access-key", accessKey);
		requestHeaders.put("x-ncp-apigw-signature-v2", signature);
		
		return requestHeaders;
	}
	
	private String empNoFormatter(long empNo) {
		return String.format("%03d", empNo);
	}
	
	public void employeesRegistration(EmployeeEntity employeeEntity) {
		String host = "https://workplace.apigw.ntruss.com";
		String externalKey = "emp"+empNoFormatter(employeeEntity.getEmpNo());
		String method = "POST";
		String url = "/organization/apigw/v2/company/" + companyId + "/employee/" + externalKey;
		Map<String, String> requestHeaders = createNaverWorkspaceRequestHeaders(method, url);
		
		String deptExternalKey = null;
		switch (employeeEntity.getEmpDept()) {
		case "행정": deptExternalKey = "dept001"; break;
		case "Codebloom": deptExternalKey = "dept000"; break;
		case "인사": deptExternalKey = "dept002"; break;
		case "재무": deptExternalKey = "dept003"; break;
		case "강사": deptExternalKey = "dept004";
		}
		
		int _birthYmd = Integer.parseInt(employeeEntity.getEmpBirth());
		String birthYmd = String.format("%4d-%02d-%02d",_birthYmd / 10000, (_birthYmd % 10000) / 100, _birthYmd % 100);
		
		EmpRequestBody empRequestBody = EmpRequestBody.builder()
				.loginId(employeeEntity.getEmpUsername().split("@")[0] + "@talesweaver.by-works.com")
				.emailAddress(employeeEntity.getEmpUsername())
				.employYmd(employeeEntity.getEmpHireDate().format(DateTimeFormatter.ISO_DATE))
				.cellphoneNo(employeeEntity.getEmpPhone())
				.birthYmd(birthYmd)
				.name(employeeEntity.getEmpName())
				.deptExternalKey(deptExternalKey)
				.empTypeExternalKey("type001")
				.passwordSettingType("ADMIN")
				.initializePassword("1234")
				.build();
		
		ObjectMapper mapper = new ObjectMapper();
		String body = null;
		
		try {
			body = mapper.writeValueAsString(empRequestBody);
			System.out.println("JSON>>>:" + body);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		String result = naverApi.request(host+url, requestHeaders, method, body);
		System.out.println("result:"+result);
	}
	
	@Getter
	@Builder
	static class EmpRequestBody{
		private String loginId;				//아이디 @talesweaver.by-works.com
		private String emailAddress;		//이메일
		private String employYmd;			//입사일 'yyyy-MM-dd'
		private String cellphoneNo;			//전화번호
		private String birthYmd;			//생년월일 'yyyy-MM-dd'
		private String name;				//이름
		private String deptExternalKey;		//부서 번호
		private String empTypeExternalKey;	//고용 형태"emp003"
		private String passwordSettingType;	//"ADMIN"
		private String initializePassword;	//"1234"
	}
}