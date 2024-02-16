package com.green.bloom.weather.dto.day;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemData {
	
	private String baseDate;               // 발표일자
	private String baseTime;               // 발표시각
	private String category;               // 자료구분코드
	private String fcstDate;			   // 예측일자
	private String fcstTime;			   // 예측 시간
	private int nx;               		   // X좌표
	private int ny;               		   // Y좌표
	private String fcstValue;              // 예보값
	private String obsrValue;			   // 실황값
	
}
