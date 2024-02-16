package com.green.bloom.weather.dto.longWeek.weatherMidFcstk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemData {
	
	private String wfSv; //기상전망
	private int stnId;	//지점번호
	private int tmFc;	//발표시각
	
}
