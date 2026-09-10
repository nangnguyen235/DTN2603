public class Answer {

    public Integer answerId;
    public String content;
    // foreign key
    public Question question;
    public IsCorrect isCorrect;

    public enum IsCorrect {
        TRUE,
        FALSE
    }

}