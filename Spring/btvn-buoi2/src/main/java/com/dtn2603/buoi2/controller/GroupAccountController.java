package com.dtn2603.buoi2.controller;


import com.dtn2603.buoi2.dto.GroupAccountRequest;
import com.dtn2603.buoi2.dto.GroupAccountResponse;
import com.dtn2603.buoi2.service.IGroupAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group-accounts")
@AllArgsConstructor
public class GroupAccountController {

    private final IGroupAccountService groupAccountService;

    @PostMapping
    public ResponseEntity<GroupAccountResponse> addAccountToGroup(@RequestBody GroupAccountRequest groupAccountRequest) {
        return new ResponseEntity<>(groupAccountService.addAccountToGroup(groupAccountRequest), HttpStatus.CREATED);
    }
}
