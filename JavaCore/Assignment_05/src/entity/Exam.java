package entity;

import java.time.LocalDate;

public class Exam {
    private int examId;
    private String code;
    private String title;
    private CategoryQuestion categoryQuestion;
    private int duration;
    private Account creator;
    private LocalDate createDate;

    public Exam(
            int examId,
            String code,
            String title,
            CategoryQuestion categoryQuestion,
            int duration,
            Account creator,
            LocalDate createDate) {

        this.examId = examId;
        this.code = code;
        this.title = title;
        this.categoryQuestion = categoryQuestion;
        this.duration = duration;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getExamId() {
        return examId;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public CategoryQuestion getCategoryQuestion() {
        return categoryQuestion;
    }

    public int getDuration() {
        return duration;
    }

    public Account getCreator() {
        return creator;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
}
