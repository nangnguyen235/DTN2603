package com.dtn2603.buoi2.controller;

import com.dtn2603.buoi2.dto.AccountResponse;
import com.dtn2603.buoi2.dto.GroupResponse;
import com.dtn2603.buoi2.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private final IAccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccounts(){
        return ResponseEntity.ok(accountService.getAccounts());
    }


    @GetMapping("/{id}/groups")
    public ResponseEntity<List<GroupResponse>> getGroupsByAccountId(@PathVariable int id){
        return ResponseEntity.ok(accountService.getGroups(id));
    }
}
