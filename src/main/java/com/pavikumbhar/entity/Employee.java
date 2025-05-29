package com.pavikumbhar.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table
@Getter
@Setter
public class Employee {

    @Id
    private Long id;
    private String name;
    private String email;
    private boolean active;
    private List<Address> addresses;

}