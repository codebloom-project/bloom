package com.green.bloom.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.green.bloom.domain.dto.SchedulePayDTO;
import com.green.bloom.domain.dto.SchedulePaySaveDTO;
import com.green.bloom.domain.dto.SchedulePayUpdateDTO;

@Mapper
public interface SchedulePayMapper {
	
	void save(SchedulePaySaveDTO dto);
	
	List<SchedulePayDTO> findAll();

	int countAll();
	
	List<SchedulePayDTO> findAllLimit(@Param(value = "offset") int offset,@Param(value = "limit") int limit);

	void updateScheduleCurrent(SchedulePayUpdateDTO dto);

	List<SchedulePayDTO> findLimit(@Param(value = "offset") int offset,@Param(value = "limit") int limit,@Param(value = "empNo") long empNo);

	int count(@Param(value = "empNo")long empNo);



}
