package com.pavikumbhar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    @JsonIgnore
    private String addressesStr;
    private List<AddressDTO> addresses;
}
