package com.green.bloom.weather.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.bloom.weather.dto.live.WeatherLiveResponseDTO;

@Controller
public class WeatherLiveController {
	
	@Value("${data.go.kr.service-key}")
	String serviceKey;
	
	private final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/search";
	private final String FORMAT = "jason";
	
	//초단기 실황조회
	//@ResponseBody
	@GetMapping("/data/weather")
	public String getUltraSrtNcst(WeatherLiveInfo dto, Model model) throws IOException {
		
		//String result=getCoordinates("서울시 노원구");
		//System.out.println(">>:"+result);
		
		//Map<String, Double> rs =CoordinateConverter.convertCoordinates("toXY", 37.654, 127.0567);
		//System.out.println("To xy"+rs);
		
		
		LocalDateTime today=LocalDateTime.now();
		int minute = today.getMinute();
		//현재 기상청 데이터 갱신시간이 매시 30분에 갱신되고 기준시간은 매시 정각으로 설정된다.
		//30분 전에는 12:10 기준시간 1200데이터는 아직 갱신되지 않아서 데이터가 존재하지 않는다.
		if(minute<30) {
			today=today.minusHours(1);
		}
		DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("HHmm");
		
		String date = today.format(dateFormatter);
		String time = today.format(timeFormatter);
		
		System.out.println("date:"+date);
		System.out.println("time:"+time);
		
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); //URL
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+serviceKey); //Service Key
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); //페이지번호
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); //한 페이지 결과 수
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); //요청자료형식(XML/JSON) Default: XML
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); //‘21년 6월 28일 발표
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")); //06시 발표(정시단위) 
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(dto.getX(), "UTF-8")); //예보지점의 X 좌표값
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(dto.getY(), "UTF-8")); //예보지점의 Y 좌표값
        
        RestTemplate restTemplate = new RestTemplate();
        URL url = new URL(urlBuilder.toString());
        //restTemplate.getForObject(url, null);
        
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        //JSON 형식으로 된 문자열 -->객체
        ObjectMapper objectMapper=new ObjectMapper();
        WeatherLiveResponseDTO result = objectMapper.readValue(sb.toString(), WeatherLiveResponseDTO.class);
        System.out.println("result: "+result.getResponse().getBody().getItems().getItem());
        model.addAttribute("livelist", result.getResponse().getBody().getItems().getItem());
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "/weather/weatherLiveData.html";
        
		
	}

}
