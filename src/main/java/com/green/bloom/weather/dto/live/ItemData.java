package com.green.bloom.weather.dto.live;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemData {
	
	private String baseDate;               // "20231207",
	private String baseTime;               // "0600",
	private String category;               // "PTY",
	private String fcstDate;
	private String fcstTime;
	private int nx;               // 65,
	private int ny;               // 126,
	private String fcstValue;               // "0"
	private String obsrValue;
	
}
