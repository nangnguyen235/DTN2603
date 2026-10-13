package com.dtn2603.buoi2.controller;


import com.dtn2603.buoi2.dto.AccountResponse;
import com.dtn2603.buoi2.service.IGroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {

    private final IGroupService groupService;

    @GetMapping("/{id}/accounts")
    public ResponseEntity<List<AccountResponse>> getAllAccounts(@PathVariable Integer id) {
        return ResponseEntity.ok(groupService.getAllAccounts(id));
    }
}
