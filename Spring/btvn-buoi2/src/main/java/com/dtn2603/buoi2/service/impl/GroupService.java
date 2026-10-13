package com.dtn2603.buoi2.service.impl;

import com.dtn2603.buoi2.dto.AccountResponse;
import com.dtn2603.buoi2.repository.IGroupAccountRepository;
import com.dtn2603.buoi2.service.IGroupService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupService implements IGroupService {
    private final IGroupAccountRepository groupAccountRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AccountResponse> getAllAccounts(Integer id) {
        return groupAccountRepository.findAccountsByGroupId(id)
                .stream()
                .map(account -> modelMapper.map(account, AccountResponse.class))
                .collect(Collectors.toList());
    }
}
