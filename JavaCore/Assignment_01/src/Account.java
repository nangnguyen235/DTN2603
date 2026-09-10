import java.time.LocalDateTime;

public class Account {

    public Integer accountId;
    public String email;
    public String userName;
    public String fullName;
    // Foreign key
    public Department department;
    public Position position;

    public LocalDateTime createDate;

}