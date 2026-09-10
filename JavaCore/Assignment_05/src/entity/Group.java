package entity;

import java.time.LocalDate;

public class Group {
    private int groupId;
    private String groupName;
    private Account creator;
    private LocalDate createDate;

    public Group(int groupId, String groupName, Account creator, LocalDate createDate) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public Account getCreator() {
        return creator;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
}
