package com.dtn2603.buoi2.entity.service;

import com.dtn2603.buoi2.dto.GroupAccountRequest;
import com.dtn2603.buoi2.dto.GroupAccountResponse;

public interface IGroupAccountService {
    GroupAccountResponse addAccountToGroup(GroupAccountRequest groupAccountRequest);
}
