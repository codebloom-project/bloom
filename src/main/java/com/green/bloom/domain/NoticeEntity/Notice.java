package com.green.bloom.domain.NoticeEntity;

import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DynamicUpdate//변경된것만 쿼리에 반영
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@Table(name="Notice")
@Entity
public class Notice {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false, columnDefinition="longtext")
	private String content;
	
	@CreationTimestamp
	private LocalTime createdDate;
	
	@UpdateTimestamp//컬럼중에 전체또는 일부가 수정되면 자동으로 update
	private LocalTime updateDate;
	
	public NoticeDTO toDto() {
		return NoticeDTO.builder()
				.id(id)
				.title(title)
				.content(content)
				.build();
	}

	//Entity에서 제목 내용을 수정하기위한 편의 메서드
	public Notice updateTitleAndContent(NoticeUpdateDTO dto) {
		title=dto.getTitle();
		content=dto.getContent();
		return this;		
	}
}
