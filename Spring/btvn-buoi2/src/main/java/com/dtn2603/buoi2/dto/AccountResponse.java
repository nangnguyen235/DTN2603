package com.dtn2603.buoi2.dto;

import com.dtn2603.buoi2.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private String email;
    private String username;
    private String fullname;
    private DepartmentResponse department;
    private PositionResponse position;
    private Gender gender;
    private Date createDate;
}
