package com.green.bloom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.bloom.domain.dto.ScheduleDTO;
import com.green.bloom.domain.entity.ScheduleEntityRepository;
import com.green.bloom.service.ScheduleService;

@Controller
public class ScheduleController {
	
	@Autowired
	ScheduleEntityRepository scheduleRepo;
	
	@Autowired
	ScheduleService scheduleService;
  
	@GetMapping("/schedule")
	public String schedule() {
		return "schedule/schedule";
	}
	
	@GetMapping("/schedule-manager")
	public String manager(SchedulePageRequestDTO SpageRequestDTO,Model model){
		model.addAttribute("result", scheduleService.getList(SpageRequestDTO));
		scheduleService.scheduleList(model);
		return "schedule/schedule-manager";
	}
	
	@PostMapping("/schedule")
	public String schedule(ScheduleDTO scheduleDTO) {
		scheduleService.save(scheduleDTO);
		return "redirect:/schedule-manager";
	}
	
	@GetMapping("/calendar")
    @ResponseBody
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }
	
	@PutMapping("/schedule-manager/{no}")
	@ResponseBody
	public ResponseEntity<String> editSchedule(@PathVariable(name = "no") long no, @RequestBody ScheduleDTO scheduleDTO) {
	        scheduleService.edit(no, scheduleDTO);
	        return new ResponseEntity<>("일정이 수정되었습니다.", HttpStatus.OK);
	}
	
	@DeleteMapping("/schedule-manager/{no}")
	@ResponseBody
	public ResponseEntity<String> deleteSchedule(@PathVariable(name="no") long no) {
	        scheduleService.delete(no);
	        return new ResponseEntity<>("일정이 삭제되었습니다.", HttpStatus.OK);
	}
}
