package backend.controller;

import backend.service.IQLAccountService;
import backend.service.impl.QLAccountServiceImpl;
import entity.Account;

import java.util.List;

public class QLAccountController {
    private IQLAccountService service;

    public QLAccountController() {
        service = new QLAccountServiceImpl();
    }

    public List<Account> getAll() {
        List<Account> accounts = service.getAll();
        return accounts;
    }

    public boolean themAccount(String email, String username, String fullName, int departmentId, int positionId) {
        return service.themAccount(email, username, fullName, departmentId, positionId);
    }

    public boolean xoaAccountTheoId(int id) {
        return service.xoaAccountTheoId(id);
    }

    public boolean suaUsernameTheoId(int id, String newUsername) {
        return service.suaUsernameTheoId(id, newUsername);
    }
}
