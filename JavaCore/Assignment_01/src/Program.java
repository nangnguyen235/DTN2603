import java.time.LocalDateTime;

public class Program {

    public static void main(String[] args) {

        // =========================
        // Q2: Tạo Object
        // =========================

        // QLDepartment.java
        Department dep1 = new Department();
        dep1.departmentId = 1;
        dep1.departmentName = "Marketing";

        Department dep2 = new Department();
        dep2.departmentId = 2;
        dep2.departmentName = "Sale";

        Department dep3 = new Department();
        dep3.departmentId = 3;
        dep3.departmentName = "BOD";


        // Position
        Position pos1 = new Position();
        pos1.positionId = 1;
        pos1.positionName = Position.PositionEnum.DEV;

        Position pos2 = new Position();
        pos2.positionId = 2;
        pos2.positionName = Position.PositionEnum.PM;

        Position pos3 = new Position();
        pos3.positionId = 3;
        pos3.positionName = Position.PositionEnum.SCRUM_MASTER;


        // Account
        Account acc1 = new Account();
        acc1.accountId = 1;
        acc1.email = "nguyenan@gmail.com";
        acc1.userName = "nguyenan";
        acc1.fullName = "Nguyen An";
        acc1.departmentId = 1;
        acc1.positionId = 1;

        Account acc2 = new Account();
        acc2.accountId = 2;
        acc2.email = "tranbinh@gmail.com";
        acc2.userName = "tranbinh";
        acc2.fullName = "Tran Binh";
        acc2.departmentId = 2;
        acc2.positionId = 2;

        Account acc3 = new Account();
        acc3.accountId = 3;
        acc3.email = "levan@gmail.com";
        acc3.userName = "levan";
        acc3.fullName = "Le Van";
        acc3.departmentId = 3;
        acc3.positionId = 3;


        // Group
        Group group1 = new Group();
        group1.groupId = 1;
        group1.groupName = "Java Group";
        group1.creatorId = 1;

        Group group2 = new Group();
        group2.groupId = 2;
        group2.groupName = "SQL Group";
        group2.creatorId = 2;

        Group group3 = new Group();
        group3.groupId = 3;
        group3.groupName = "Testing Group";
        group3.creatorId = 3;


        // GroupAccount
        GroupAccount ga1 = new GroupAccount();
        ga1.groupId = 1;
        ga1.accountId = 1;

        GroupAccount ga2 = new GroupAccount();
        ga2.groupId = 2;
        ga2.accountId = 2;

        GroupAccount ga3 = new GroupAccount();
        ga3.groupId = 3;
        ga3.accountId = 3;


        // TypeQuestion
        TypeQuestion tq1 = new TypeQuestion();
        tq1.typeId = 1;
        tq1.typeName = TypeQuestion.TypeQuestionEnum.ESSAY;

        TypeQuestion tq2 = new TypeQuestion();
        tq2.typeId = 2;
        tq2.typeName = TypeQuestion.TypeQuestionEnum.MULTIPLE_CHOICE;

        TypeQuestion tq3 = new TypeQuestion();
        tq3.typeId = 3;
        tq3.typeName = TypeQuestion.TypeQuestionEnum.ESSAY;


        // CategoryQuestion
        CategoryQuestion cq1 = new CategoryQuestion();
        cq1.categoryId = 1;
        cq1.categoryName = "Java";

        CategoryQuestion cq2 = new CategoryQuestion();
        cq2.categoryId = 2;
        cq2.categoryName = "SQL";

        CategoryQuestion cq3 = new CategoryQuestion();
        cq3.categoryId = 3;
        cq3.categoryName = "Postman";


        // Question
        Question q1 = new Question();
        q1.questionId = 1;
        q1.content = "Java là gì?";
        q1.categoryId = 1;
        q1.typeId = 1;
        q1.creatorId = 1;

        Question q2 = new Question();
        q2.questionId = 2;
        q2.content = "SQL là gì?";
        q2.categoryId = 2;
        q2.typeId = 2;
        q2.creatorId = 2;

        Question q3 = new Question();
        q3.questionId = 3;
        q3.content = "Postman dùng để làm gì?";
        q3.categoryId = 3;
        q3.typeId = 2;
        q3.creatorId = 3;


        // Answer
        Answer ans1 = new Answer();
        ans1.answerId = 1;
        ans1.content = "Ngôn ngữ lập trình";
        ans1.questionId = 1;
        ans1.isCorrect = Answer.IsCorrect.TRUE;

        Answer ans2 = new Answer();
        ans2.answerId = 2;
        ans2.content = "Hệ quản trị cơ sở dữ liệu";
        ans2.questionId = 2;
        ans2.isCorrect = Answer.IsCorrect.TRUE;

        Answer ans3 = new Answer();
        ans3.answerId = 3;
        ans3.content = "Game";
        ans3.questionId = 1;
        ans3.isCorrect = Answer.IsCorrect.FALSE;


        // Exam
        Exam exam1 = new Exam();
        exam1.examId = 1;
        exam1.code = "EX001";
        exam1.title = "Java Basic";
        exam1.categoryId = 1;
        exam1.duration = 60;
        exam1.creatorId = 1;

        Exam exam2 = new Exam();
        exam2.examId = 2;
        exam2.code = "EX002";
        exam2.title = "SQL Basic";
        exam2.categoryId = 2;
        exam2.duration = 90;
        exam2.creatorId = 2;

        Exam exam3 = new Exam();
        exam3.examId = 3;
        exam3.code = "EX003";
        exam3.title = "Postman Basic";
        exam3.categoryId = 3;
        exam3.duration = 45;
        exam3.creatorId = 3;


        // ExamQuestion
        ExamQuestion eq1 = new ExamQuestion();
        eq1.examId = 1;
        eq1.questionId = 1;

        ExamQuestion eq2 = new ExamQuestion();
        eq2.examId = 2;
        eq2.questionId = 2;

        ExamQuestion eq3 = new ExamQuestion();
        eq3.examId = 3;
        eq3.questionId = 3;


        // =========================
        // Q3: In thông tin Object
        // =========================

        System.out.println("Thông tin các QLDepartment.java:");
        System.out.println("QLDepartment.java 1: ID: " + dep1.departmentId
                + " Name: " + dep1.departmentName);

        System.out.println("QLDepartment.java 2: ID: " + dep2.departmentId
                + " Name: " + dep2.departmentName);

        System.out.println("QLDepartment.java 3: ID: " + dep3.departmentId
                + " Name: " + dep3.departmentName);


        System.out.println("\nThông tin các Position:");
        System.out.println("Position 1: ID: " + pos1.positionId
                + " Name: " + pos1.positionName);

        System.out.println("Position 2: ID: " + pos2.positionId
                + " Name: " + pos2.positionName);

        System.out.println("Position 3: ID: " + pos3.positionId
                + " Name: " + pos3.positionName);


        System.out.println("\nThông tin các Account:");
        System.out.println("Account 1: ID: " + acc1.accountId
                + " Email: " + acc1.email
                + " UserName: " + acc1.userName
                + " FullName: " + acc1.fullName
                + " DepartmentID: " + acc1.departmentId
                + " PositionID: " + acc1.positionId);

        System.out.println("Account 2: ID: " + acc2.accountId
                + " Email: " + acc2.email
                + " UserName: " + acc2.userName
                + " FullName: " + acc2.fullName
                + " DepartmentID: " + acc2.departmentId
                + " PositionID: " + acc2.positionId);

        System.out.println("Account 3: ID: " + acc3.accountId
                + " Email: " + acc3.email
                + " UserName: " + acc3.userName
                + " FullName: " + acc3.fullName
                + " DepartmentID: " + acc3.departmentId
                + " PositionID: " + acc3.positionId);


        System.out.println("\nThông tin các Group:");
        System.out.println("Group 1: ID: " + group1.groupId
                + " Name: " + group1.groupName
                + " CreatorID: " + group1.creatorId);

        System.out.println("Group 2: ID: " + group2.groupId
                + " Name: " + group2.groupName
                + " CreatorID: " + group2.creatorId);

        System.out.println("Group 3: ID: " + group3.groupId
                + " Name: " + group3.groupName
                + " CreatorID: " + group3.creatorId);


        System.out.println("\nThông tin các GroupAccount:");
        System.out.println("GroupAccount 1: GroupID: " + ga1.groupId
                + " AccountID: " + ga1.accountId);

        System.out.println("GroupAccount 2: GroupID: " + ga2.groupId
                + " AccountID: " + ga2.accountId);

        System.out.println("GroupAccount 3: GroupID: " + ga3.groupId
                + " AccountID: " + ga3.accountId);


        System.out.println("\nThông tin các TypeQuestion:");
        System.out.println("TypeQuestion 1: ID: " + tq1.typeId
                + " Name: " + tq1.typeName);

        System.out.println("TypeQuestion 2: ID: " + tq2.typeId
                + " Name: " + tq2.typeName);

        System.out.println("TypeQuestion 3: ID: " + tq3.typeId
                + " Name: " + tq3.typeName);


        System.out.println("\nThông tin các CategoryQuestion:");
        System.out.println("CategoryQuestion 1: ID: " + cq1.categoryId
                + " Name: " + cq1.categoryName);

        System.out.println("CategoryQuestion 2: ID: " + cq2.categoryId
                + " Name: " + cq2.categoryName);

        System.out.println("CategoryQuestion 3: ID: " + cq3.categoryId
                + " Name: " + cq3.categoryName);


        System.out.println("\nThông tin các Question:");
        System.out.println("Question 1: ID: " + q1.questionId
                + " Content: " + q1.content);

        System.out.println("Question 2: ID: " + q2.questionId
                + " Content: " + q2.content);

        System.out.println("Question 3: ID: " + q3.questionId
                + " Content: " + q3.content);


        System.out.println("\nThông tin các Answer:");
        System.out.println("Answer 1: ID: " + ans1.answerId
                + " Content: " + ans1.content
                + " IsCorrect: " + ans1.isCorrect);

        System.out.println("Answer 2: ID: " + ans2.answerId
                + " Content: " + ans2.content
                + " IsCorrect: " + ans2.isCorrect);

        System.out.println("Answer 3: ID: " + ans3.answerId
                + " Content: " + ans3.content
                + " IsCorrect: " + ans3.isCorrect);


        System.out.println("\nThông tin các Exam:");
        System.out.println("Exam 1: ID: " + exam1.examId
                + " Code: " + exam1.code
                + " Title: " + exam1.title
                + " Duration: " + exam1.duration);

        System.out.println("Exam 2: ID: " + exam2.examId
                + " Code: " + exam2.code
                + " Title: " + exam2.title
                + " Duration: " + exam2.duration);

        System.out.println("Exam 3: ID: " + exam3.examId
                + " Code: " + exam3.code
                + " Title: " + exam3.title
                + " Duration: " + exam3.duration);


        System.out.println("\nThông tin các ExamQuestion:");
        System.out.println("ExamQuestion 1: ExamID: " + eq1.examId
                + " QuestionID: " + eq1.questionId);

        System.out.println("ExamQuestion 2: ExamID: " + eq2.examId
                + " QuestionID: " + eq2.questionId);

        System.out.println("ExamQuestion 3: ExamID: " + eq3.examId
                + " QuestionID: " + eq3.questionId);
    }
}