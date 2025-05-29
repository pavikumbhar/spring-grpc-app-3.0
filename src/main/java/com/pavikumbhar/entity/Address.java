package com.pavikumbhar.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Getter
@Setter
public class Address {

    @Id
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private Employee employee;

}