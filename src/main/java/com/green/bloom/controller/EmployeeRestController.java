package com.green.bloom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.bloom.service.ProcedureService;
import com.green.bloom.service.StudentService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    @Autowired
    private ProcedureService procedureService;
    
    @Autowired
    private StudentService studentService;

    @GetMapping("/employeeNames")
    public List<String> getEmployeeNames() {
        return procedureService.getEmployeeNames();
    }
    
    @GetMapping("/procedureNames")
    public List<String> getProcedureNames() {
        return studentService.getProcedureNames();
    }
}