package backend.repository;

import entity.Account;

import java.util.List;

public interface IQLAccountRepository {
    List<Account> getAll();

    boolean themAccount(String email, String username, String fullName, int departmentId, int positionId);

    boolean xoaAccountTheoId(int id);

    boolean suaUsernameTheoId(int id, String newUsername);
}
