package com.pavikumbhar.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pavikumbhar.JsonParserUtil;
import com.pavikumbhar.model.AddressDTO;
import com.pavikumbhar.model.EmployeeDTO;
import com.pavikumbhar.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Flux<EmployeeDTO> getActiveEmployeesWithCities() {
        return employeeRepository.findActiveEmployeesWithCities()
                .flatMap(employeeDTO -> Mono.justOrEmpty(employeeDTO.getAddressesStr())
                        .filter(StringUtils::hasText) // Ensure the address string is non-empty
                        .map(addressesStr -> JsonParserUtil.parseJson(addressesStr, new TypeReference<List<AddressDTO>>() {}))
                        .doOnNext(employeeDTO::setAddresses) // Set parsed addresses into EmployeeDTO
                        .thenReturn(employeeDTO) // Return the updated EmployeeDTO
                );
    }

}
