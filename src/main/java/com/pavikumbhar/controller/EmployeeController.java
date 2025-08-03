package com.pavikumbhar.controller;

import com.pavikumbhar.config.LabelMapConfig;
import com.pavikumbhar.model.EmployeeDTO;
import com.pavikumbhar.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {


    private final EmployeeService employeeService;
    private final LabelMapConfig labelMapConfig;

    @GetMapping("/active-with-cities")
    public Flux<EmployeeDTO> getActiveEmployeesWithCities() {
        log.info("CourseMap:{}", labelMapConfig.getCourseMap());
        log.info("CertificationMap:{}", labelMapConfig.getCertificationMap());
        log.info("ProgramMap:{}", labelMapConfig.getProgramMap());
        return employeeService.getActiveEmployeesWithCities();
    }

}
