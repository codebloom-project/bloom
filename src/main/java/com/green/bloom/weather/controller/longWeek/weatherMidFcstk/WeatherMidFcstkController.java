package com.green.bloom.weather.controller.longWeek.weatherMidFcstk;

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
import com.green.bloom.weather.dto.longWeek.weatherMidFcstk.WeatherMidFcstkResponseDTO;

@Controller
public class WeatherMidFcstkController {
	
	@Value("${data.go.kr.service-key-cho}")
	String serviceKey;
	
	//중기전망 조회
	@GetMapping("/data/WeatherMidFcstk")
	public String getMidFcst(WeatherMidFcstkInfo dto, Model model) throws IOException{
		
		LocalDateTime today=LocalDateTime.now();
		int hour = today.getHour();
		
		int hoursToSubtract = 0;

		if (hour >= 6 && hour < 18) {
		    hoursToSubtract = hour - 6;
		} else if (hour > 18) {
		    hoursToSubtract = 1;
		} else if (hour < 6) {
		    today = today.minusDays(1);
		    hoursToSubtract = 6 + hour;
		}

		today = today.minusHours(hoursToSubtract);

		// 날짜 및 시간 포맷 지정
		DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("HHmm");
		
		// 현재 날짜 및 시간을 문자열로 변환
		String date = today.format(dateFormatter);
		String time = today.format(timeFormatter);
		
		// tmFc 값 생성
        String tmFcValue = date + time;
		
		System.out.println("date:"+date);
		System.out.println("time:"+time);
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst"); //URL
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+serviceKey); //Service Key
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); //페이지번호
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); //한 페이지 결과 수
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); //요청자료형식(XML/JSON) Default: XML
        urlBuilder.append("&" + URLEncoder.encode("stnId","UTF-8") + "=" + URLEncoder.encode("109", "UTF-8")); //
		urlBuilder.append("&" + URLEncoder.encode("tmFc","UTF-8") + "=" + URLEncoder.encode(tmFcValue, "UTF-8")); /* 조회하고싶은 날짜*/
        
        RestTemplate restTemplate = new RestTemplate();
        URL url = new URL(urlBuilder.toString());
		
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
        ObjectMapper objectMapper=new ObjectMapper();
        WeatherMidFcstkResponseDTO result = objectMapper.readValue(sb.toString(), WeatherMidFcstkResponseDTO.class);
        System.out.println("resultLongWeek: "+result.getResponse().getBody().getItems().getItem());
        model.addAttribute("midfcstks", result.getResponse().getBody().getItems().getItem());
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "/weather/weatherLongWeekData.html";
        
		
	}
}
