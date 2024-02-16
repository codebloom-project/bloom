package com.green.bloom.weather.dto.longWeek.weatherMidTa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.green.bloom.weather.dto.week.Items;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Body{
	private String dataType;
	private Items items;
	private int pageNo;
	private int numOfRows;
	private int totalCount;
}
