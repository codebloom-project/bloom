package com.green.bloom.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.green.bloom.domain.entity.EmployeeEntity;
import com.green.bloom.domain.entity.ScheduleEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {

    private long no;
    private boolean categori;
    private String title;
    private String content;
    private LocalDateTime start;
    private LocalDateTime end;
    private long empNo;

    public ScheduleEntity toEntity() {
    	return ScheduleEntity.builder()
    			.no(no)
    			.categori(categori)
    			.title(title)
    			.content(content)
    			.start(start)
    			.end(end)
    			.build();
    }
}
