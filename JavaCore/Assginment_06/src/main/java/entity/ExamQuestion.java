package entity;

public class ExamQuestion {
    private Exam exam;
    private Question question;

    public ExamQuestion(Exam exam, Question question) {
        this.exam = exam;
        this.question = question;
    }

    public Exam getExam() {
        return exam;
    }

    public Question getQuestion() {
        return question;
    }
}
