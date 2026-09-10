package entity;

import java.time.LocalDate;

public class GroupAccount {
    private Group group;
    private Account account;
    private LocalDate joinDate;

    public GroupAccount(Group group, Account account, LocalDate joinDate) {
        this.group = group;
        this.account = account;
        this.joinDate = joinDate;
    }

    public Group getGroup() {
        return group;
    }

    public Account getAccount() {
        return account;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }
}
