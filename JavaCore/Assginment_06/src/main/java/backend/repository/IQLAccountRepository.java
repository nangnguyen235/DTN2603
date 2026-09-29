package backend.repository;

import entity.Account;

import java.util.List;

public interface IQLAccountRepository extends IGenericRepository<Account, Integer> {
    // Kế thừa từ IGenericRepository: getAll(), create(), deleteById(), update(), checkExist()

    // Các method đặc thù cho Account
    boolean themAccount(String email, String username, String fullName, int departmentId, int positionId);

    boolean xoaAccountTheoId(int id);

    boolean suaUsernameTheoId(int id, String newUsername);

    boolean checkExistByEmail(String email);
}
