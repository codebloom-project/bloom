package com.green.bloom.domain.NoticeEntity;

import java.sql.Time;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class NoticeDTO {

	private Long id;
	private String title;
	private String content;
	private LocalTime UpdateDate;
	private LocalTime createdDate;
	
	public Notice toEntity() {
		return Notice.builder()
				.id(id)
				.title(title)
				.content(content)
				.updateDate(UpdateDate)
				.createdDate(createdDate)
				.build();
	}
}
