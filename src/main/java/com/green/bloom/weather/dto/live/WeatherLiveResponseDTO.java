package com.green.bloom.weather.dto.live;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) //json 속성을 모두 매핑 하려고하는데 만약에 없으면 무시하세요
public class WeatherLiveResponseDTO {
	
	@JsonProperty("response")
	private Response response;

}
