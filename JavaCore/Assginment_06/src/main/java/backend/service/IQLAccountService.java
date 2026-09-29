package backend.service;

import entity.Account;

import java.util.List;

public interface IQLAccountService extends IGenericService<Account, Integer> {
    // Kế thừa từ IGenericService: getAll(), create(), deleteById(), update(), checkExist(), importCSV()

    // Các method đặc thù cho Account
    boolean themAccount(String email, String username, String fullName, int departmentId, int positionId);

    boolean xoaAccountTheoId(int id);

    boolean suaUsernameTheoId(int id, String newUsername);

    boolean checkExistByEmail(String email);
}
