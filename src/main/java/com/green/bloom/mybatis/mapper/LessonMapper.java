package com.green.bloom.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.Authentication;

import com.green.bloom.domain.dto.LessonDTO;
import com.green.bloom.domain.dto.LessonSaveDTO;
import com.green.bloom.domain.dto.LessonUpdateDTO;
import com.green.bloom.domain.dto.SchedulePayDTO;
import com.green.bloom.domain.dto.SchedulePayUpdateDTO;

@Mapper
public interface LessonMapper {
	
	void save(LessonSaveDTO dto);

	List<LessonDTO> findAll();
	
	int countAll();
	
	List<LessonDTO> findAllLimit(@Param(value = "offset") int offset,@Param(value = "limit") int limit);

	void updateLessonCurrent(LessonUpdateDTO dto);

	int count(@Param(value = "empNo")long empNo);

	List<SchedulePayDTO> findLimit(@Param(value = "offset") int offset,@Param(value = "limit") int limit,@Param(value = "empNo") long empNo);
	

}
