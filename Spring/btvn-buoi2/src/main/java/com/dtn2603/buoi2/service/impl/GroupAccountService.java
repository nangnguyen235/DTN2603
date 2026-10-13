package com.dtn2603.buoi2.service.impl;

import com.dtn2603.buoi2.dto.GroupAccountRequest;
import com.dtn2603.buoi2.dto.GroupAccountResponse;
import com.dtn2603.buoi2.entity.Account;
import com.dtn2603.buoi2.entity.Group;
import com.dtn2603.buoi2.entity.GroupAccount;
import com.dtn2603.buoi2.repository.IAccountRepository;
import com.dtn2603.buoi2.repository.IGroupAccountRepository;
import com.dtn2603.buoi2.repository.IGroupRepository;
import com.dtn2603.buoi2.service.IGroupAccountService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class GroupAccountService implements IGroupAccountService {
    private final IGroupAccountRepository groupAccountRepository;
    private final IGroupRepository groupRepository;
    private final IAccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    public GroupAccountResponse addAccountToGroup(GroupAccountRequest groupAccountRequest) {
        Group group = groupRepository.findById(groupAccountRequest.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group id not found"));

        Account account = accountRepository.findById(groupAccountRequest.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account id not found"));

        boolean isExistAccount = groupAccountRepository.existsByGroupGroupIdAndAccountAccountId(
                groupAccountRequest.getGroupId(), groupAccountRequest.getAccountId()
        );

        if (isExistAccount) {
            throw new IllegalArgumentException("Account already in group");
        }

        GroupAccount groupAccountSaved = new GroupAccount();
        groupAccountSaved.setAccount(account);
        groupAccountSaved.setGroup(group);
        return modelMapper.map(groupAccountRepository.save(groupAccountSaved), GroupAccountResponse.class);
    }
}
