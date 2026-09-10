import java.time.LocalDateTime;

public class Group {

    public Integer groupId;
    public String groupName;
    // Foreign key
    public Account creator;
    public LocalDateTime createDate;

}