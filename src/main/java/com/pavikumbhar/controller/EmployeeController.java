package com.pavikumbhar.controller;

import com.pavikumbhar.model.EmployeeDTO;
import com.pavikumbhar.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {


    private final EmployeeService employeeService;

    @GetMapping("/active-with-cities")
    public Flux<EmployeeDTO> getActiveEmployeesWithCities() {
        return employeeService.getActiveEmployeesWithCities();
    }

}
