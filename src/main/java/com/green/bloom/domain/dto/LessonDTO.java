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
public class LessonDTO {
	
	private long lessonNo;
	private LocalDate lessonMake;
	private String lessonName;
	private String lessonCate;
	private String lessonTitle;
	private String lessonCurrent;

}
