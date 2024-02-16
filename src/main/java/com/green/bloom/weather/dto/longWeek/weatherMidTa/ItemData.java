package com.green.bloom.weather.dto.longWeek.weatherMidTa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemData {
	
	private String regId; //예보구역코드
	private int tmFc;	//발표시각
	
	//최저 최고 기온
	private int taMin3;
	private int taMax3;
	private int taMin4;
	private int taMax4;
	private int taMin5;
	private int taMax5;
	private int taMin6;
	private int taMax6;
	private int taMin7;
	private int taMax7;
	private int taMin8;
	private int taMax8;
	private int taMin9;
	private int taMax9;
	private int taMin10;
	private int taMax10;
	
}
