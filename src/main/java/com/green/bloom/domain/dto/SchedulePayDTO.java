package com.green.bloom.domain.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SchedulePayDTO {
	
	private long sdocNo;
	private LocalDate sdocMake;
	private String sdocName;
	private String sdocCate;
	private String sdocCurrent;

}
