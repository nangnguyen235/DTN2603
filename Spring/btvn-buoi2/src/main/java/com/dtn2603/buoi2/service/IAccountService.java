package com.dtn2603.buoi2.service;

import com.dtn2603.buoi2.dto.AccountResponse;
import com.dtn2603.buoi2.dto.GroupResponse;

import java.util.List;

public interface IAccountService {

    List<AccountResponse> getAccounts();

    List<GroupResponse>  getGroups(Integer accountId);
}
