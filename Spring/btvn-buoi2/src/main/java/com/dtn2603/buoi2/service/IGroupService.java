package com.dtn2603.buoi2.service;

import com.dtn2603.buoi2.dto.AccountResponse;

import java.util.List;

public interface IGroupService {
    List<AccountResponse> getAllAccounts(Integer id);
}
