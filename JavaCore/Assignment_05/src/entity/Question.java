package entity;

import java.time.LocalDate;

public class Question {
    private int questionId;
    private String content;
    private CategoryQuestion categoryQuestion;
    private TypeQuestion typeQuestion;
    private Account creator;
    private LocalDate createDate;

    public Question(
            int questionId,
            String content,
            CategoryQuestion categoryQuestion,
            TypeQuestion typeQuestion,
            Account creator,
            LocalDate createDate) {

        this.questionId = questionId;
        this.content = content;
        this.categoryQuestion = categoryQuestion;
        this.typeQuestion = typeQuestion;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getContent() {
        return content;
    }

    public CategoryQuestion getCategoryQuestion() {
        return categoryQuestion;
    }

    public TypeQuestion getTypeQuestion() {
        return typeQuestion;
    }

    public Account getCreator() {
        return creator;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
}
