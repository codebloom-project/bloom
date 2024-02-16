package com.green.bloom.weather.dto.longWeek.weatherMidandFcstk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemData {
	
	private String regId; //예보구역코드
	private int tmFc;	//발표시각
	
	//강수확률
	private int rnSt3Am;
	private int rnSt3Pm;
	private int rnSt4Am;
	private int rnSt4Pm;
	private int rnSt5Am;
	private int rnSt5Pm;
	private int rnSt6Am;
	private int rnSt6Pm;
	private int rnSt7Am;
	private int rnSt7Pm;
	private int rnSt8;
	private int rnSt9;
	private int rnSt10;
	
	//하늘 날씨 예보(맑음, 구름많음, 비)
	private int wf3Am;
	private int wf3Pm;
	private int wf4Am;
	private int wf4Pm;
	private int wf5Am;
	private int wf5Pm;
	private int wf6Am;
	private int wf6Pm;
	private int wf7Am;
	private int wf7Pm;
	private int wf8;
	private int wf9;
	private int wf10;
	
}
