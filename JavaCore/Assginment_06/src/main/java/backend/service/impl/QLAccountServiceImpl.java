package backend.service.impl;

import backend.repository.IQLAccountRepository;
import backend.repository.impl.QLAccountRepositoryImpl;
import backend.service.IQLAccountService;
import entity.Account;

import java.util.List;

public class QLAccountServiceImpl implements IQLAccountService {
    private IQLAccountRepository repository;

    public QLAccountServiceImpl() {
        repository = new QLAccountRepositoryImpl();
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = repository.getAll();
        return accounts;
    }

    @Override
    public boolean themAccount(String email, String username, String fullName, int departmentId, int positionId) {
        return repository.themAccount(email, username, fullName, departmentId, positionId);
    }

    @Override
    public boolean xoaAccountTheoId(int id) {
        return repository.xoaAccountTheoId(id);
    }

    @Override
    public boolean suaUsernameTheoId(int id, String newUsername) {
        return repository.suaUsernameTheoId(id, newUsername);
    }
}
