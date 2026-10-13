package com.dtn2603.buoi2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupAccountResponse {
    private GroupResponse group;
    private AccountResponse account;
}
