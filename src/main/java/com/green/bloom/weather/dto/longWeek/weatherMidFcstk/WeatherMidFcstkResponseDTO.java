package com.green.bloom.weather.dto.longWeek.weatherMidFcstk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherMidFcstkResponseDTO {
	
	@JsonProperty("response")
	private Response response;

}
