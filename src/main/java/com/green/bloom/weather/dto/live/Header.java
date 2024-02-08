package com.green.bloom.weather.dto.live;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Header{
	private String resultCode;
	private String resultMsg;
}