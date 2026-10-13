package com.dtn2603.buoi2.entity.service.impl;

import com.dtn2603.buoi2.dto.AccountResponse;
import com.dtn2603.buoi2.dto.GroupResponse;
import com.dtn2603.buoi2.repository.IAccountRepository;
import com.dtn2603.buoi2.repository.IGroupAccountRepository;
import com.dtn2603.buoi2.service.IAccountService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService {
    private final IAccountRepository accountRepository;
    private final IGroupAccountRepository groupAccountRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AccountResponse> getAccounts() {
        return accountRepository.findAll()
                .stream().map(account -> modelMapper.map(account, AccountResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupResponse> getGroups(Integer id) {
        return groupAccountRepository.findGroupsByAccountId(id)
                .stream().map(group -> modelMapper.map(group, GroupResponse.class))
                .collect(Collectors.toList());
    }
}
