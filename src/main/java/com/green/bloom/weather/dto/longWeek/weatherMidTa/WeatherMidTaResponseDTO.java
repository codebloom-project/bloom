package com.green.bloom.weather.dto.longWeek.weatherMidTa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherMidTaResponseDTO {
	
	@JsonProperty("response")
	private Response response;

}
