package com.dtn2603.buoi2.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupAccountRequest {

    private Integer groupId;
    private Integer accountId;
}
