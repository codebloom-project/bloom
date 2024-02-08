package com.green.bloom.weather.dto.live;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Items{
	
	private List<ItemData> item;
	
}
