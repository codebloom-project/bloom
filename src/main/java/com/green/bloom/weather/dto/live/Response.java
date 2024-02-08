package com.green.bloom.weather.dto.live;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response{
	private Header header;
	private Body body;
}
