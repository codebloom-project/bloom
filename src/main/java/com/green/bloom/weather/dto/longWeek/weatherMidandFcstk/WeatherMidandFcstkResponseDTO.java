package com.green.bloom.weather.dto.longWeek.weatherMidandFcstk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherMidandFcstkResponseDTO {
	
	@JsonProperty("response")
	private Response response;

}
