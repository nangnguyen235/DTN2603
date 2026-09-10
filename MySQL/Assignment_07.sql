-- =========================================================
-- QUESTION 1
-- Tạo trigger không cho phép nhập Group có ngày tạo trước 1 năm
-- INSERT -> group -> BEFORE -> NEW
-- =========================================================

DROP TRIGGER IF EXISTS trg_question1;

DELIMITER $$

CREATE TRIGGER trg_question1
BEFORE INSERT ON `group`
FOR EACH ROW
BEGIN
    -- kiểm tra ngày tạo group
    IF NEW.create_date < DATE_SUB(CURDATE(), INTERVAL 1 YEAR) THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Group create date cannot be older than 1 year';

    END IF;
END $$

DELIMITER ;


-- =========================================================
-- QUESTION 2
-- Không cho phép thêm user vào department "Sale"
-- INSERT -> account -> BEFORE -> NEW.department_id
-- =========================================================

DROP TRIGGER IF EXISTS trg_question2;

DELIMITER $$

CREATE TRIGGER trg_question2
BEFORE INSERT ON account
FOR EACH ROW
BEGIN
    -- kiểm tra department của account có phải Sale không
    IF EXISTS (
        SELECT 1
        FROM department
        WHERE department_id = NEW.department_id
        AND department_name = 'Sale'
    ) THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'QLDepartment.java "Sale" cannot add more user';

    END IF;
END $$

DELIMITER ;


-- =========================================================
-- QUESTION 3
-- Một group có nhiều nhất 5 user
-- INSERT -> group_account -> BEFORE -> NEW.group_id
-- =========================================================

DROP TRIGGER IF EXISTS trg_question3;

DELIMITER $$

CREATE TRIGGER trg_question3
BEFORE INSERT ON group_account
FOR EACH ROW
BEGIN
    -- kiểm tra số user hiện tại trong group
    IF (
        SELECT COUNT(*)
        FROM group_account
        WHERE group_id = NEW.group_id
    ) >= 5 THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'A group can have maximum 5 users';

    END IF;
END $$

DELIMITER ;


-- =========================================================
-- QUESTION 4
-- Một bài thi có nhiều nhất 10 Question
-- INSERT -> exam_question -> BEFORE -> NEW.exam_id
-- =========================================================

DROP TRIGGER IF EXISTS trg_question4;

DELIMITER $$

CREATE TRIGGER trg_question4
BEFORE INSERT ON exam_question
FOR EACH ROW
BEGIN
    -- kiểm tra số question hiện tại của exam
    IF (
        SELECT COUNT(*)
        FROM exam_question
        WHERE exam_id = NEW.exam_id
    ) >= 10 THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'An exam can have maximum 10 questions';

    END IF;
END $$

DELIMITER ;


-- =========================================================
-- QUESTION 5
-- Không cho phép xóa account có email admin@gmail.com
-- DELETE -> account -> BEFORE -> OLD
-- =========================================================

DROP TRIGGER IF EXISTS trg_question5;

DELIMITER $$

CREATE TRIGGER trg_question5
BEFORE DELETE ON account
FOR EACH ROW
BEGIN
    -- không cho phép xóa tài khoản admin
    IF OLD.email = 'admin@gmail.com' THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot delete admin account';

    ELSE

        -- xóa các thông tin liên quan đến account
        DELETE FROM group_account
        WHERE account_id = OLD.account_id;

    END IF;
END $$

DELIMITER ;


-- =========================================================
-- QUESTION 6
-- Nếu không nhập department_id thì đưa vào waiting QLDepartment.java
-- INSERT -> account -> BEFORE -> NEW.department_id
-- =========================================================

DROP TRIGGER IF EXISTS trg_question6;

DELIMITER $$

CREATE TRIGGER trg_question6
BEFORE INSERT ON account
FOR EACH ROW
BEGIN
    DECLARE v_department_id INT;

    -- nếu không nhập department_id
    IF NEW.department_id IS NULL THEN

        -- lấy department_id của waiting QLDepartment.java
        SELECT department_id
        INTO v_department_id
        FROM department
        WHERE department_name = 'waiting QLDepartment.java';

        -- gán department_id
        SET NEW.department_id = v_department_id;

    END IF;
END $$

DELIMITER ;


-- =========================================================
-- QUESTION 7
-- Mỗi question tối đa 4 answer
-- Trong đó tối đa 2 answer đúng
-- INSERT -> answer -> BEFORE -> NEW
-- =========================================================

DROP TRIGGER IF EXISTS trg_question7;

DELIMITER $$

CREATE TRIGGER trg_question7
BEFORE INSERT ON answer
FOR EACH ROW
BEGIN

    -- kiểm tra tổng số answer của question
    IF (
        SELECT COUNT(*)
        FROM answer
        WHERE question_id = NEW.question_id
    ) >= 4 THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'A question can have maximum 4 answers';

    END IF;

    -- nếu answer mới là đáp án đúng
    IF NEW.is_correct = 1 THEN

        -- kiểm tra số đáp án đúng hiện tại
        IF (
            SELECT COUNT(*)
            FROM answer
            WHERE question_id = NEW.question_id
            AND is_correct = 1
        ) >= 2 THEN

            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'A question can have maximum 2 correct answers';

        END IF;

    END IF;

END $$

DELIMITER ;


-- =========================================================
-- QUESTION 8
-- Nam -> M
-- Nữ -> F
-- Chưa xác định -> U
-- INSERT -> account -> BEFORE -> NEW.gender
-- =========================================================

DROP TRIGGER IF EXISTS trg_question8;

DELIMITER $$

CREATE TRIGGER trg_question8
BEFORE INSERT ON account
FOR EACH ROW
BEGIN

    -- nam -> M
    IF LOWER(NEW.gender) = 'nam' THEN

        SET NEW.gender = 'M';

    -- nữ -> F
    ELSEIF LOWER(NEW.gender) = 'nữ' THEN

        SET NEW.gender = 'F';

    -- chưa xác định -> U
    ELSEIF LOWER(NEW.gender) = 'chưa xác định' THEN

        SET NEW.gender = 'U';

    END IF;

END $$

DELIMITER ;


-- =========================================================
-- QUESTION 9
-- Không cho phép xóa exam mới tạo được 2 ngày
-- DELETE -> exam -> BEFORE -> OLD.create_date
-- =========================================================

DROP TRIGGER IF EXISTS trg_question9;

DELIMITER $$

CREATE TRIGGER trg_question9
BEFORE DELETE ON exam
FOR EACH ROW
BEGIN

    -- kiểm tra exam có được tạo trong vòng 2 ngày không
    IF OLD.create_date >= DATE_SUB(NOW(), INTERVAL 2 DAY) THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot delete exam created within 2 days';

    END IF;

END $$

DELIMITER ;


-- =========================================================
-- QUESTION 10
-- Chỉ được update/delete question khi question chưa nằm trong exam
-- UPDATE -> question -> BEFORE -> OLD.question_id
-- DELETE -> question -> BEFORE -> OLD.question_id
-- =========================================================

DROP TRIGGER IF EXISTS trg_question10_update;

DELIMITER $$

CREATE TRIGGER trg_question10_update
BEFORE UPDATE ON question
FOR EACH ROW
BEGIN

    -- kiểm tra question đã nằm trong exam chưa
    IF EXISTS (
        SELECT 1
        FROM exam_question
        WHERE question_id = OLD.question_id
    ) THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot update question already in an exam';

    END IF;

END $$

DELIMITER ;


DROP TRIGGER IF EXISTS trg_question10_delete;

DELIMITER $$

CREATE TRIGGER trg_question10_delete
BEFORE DELETE ON question
FOR EACH ROW
BEGIN

    -- kiểm tra question đã nằm trong exam chưa
    IF EXISTS (
        SELECT 1
        FROM exam_question
        WHERE question_id = OLD.question_id
    ) THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot delete question already in an exam';

    END IF;

END $$

DELIMITER ;


-- =========================================================
-- QUESTION 12
-- Phân loại Duration của Exam
-- =========================================================

SELECT
    exam_id,
    exam_code,
    title,
    duration,

    CASE
        WHEN duration <= 30 THEN 'Short time'
        WHEN duration <= 60 THEN 'Medium time'
        ELSE 'Long time'
    END AS duration_type

FROM exam;


-- =========================================================
-- QUESTION 13
-- Thống kê số account trong mỗi group
-- <= 5       -> few
-- > 5 <= 20  -> normal
-- > 20       -> higher
-- =========================================================

SELECT
    g.group_id,
    g.group_name,
    COUNT(ga.account_id) AS so_luong_user,

    CASE
        WHEN COUNT(ga.account_id) <= 5 THEN 'few'
        WHEN COUNT(ga.account_id) <= 20 THEN 'normal'
        ELSE 'higher'
    END AS the_number_user_amount

FROM `group` g

LEFT JOIN group_account ga
    ON g.group_id = ga.group_id

GROUP BY
    g.group_id,
    g.group_name;


-- =========================================================
-- QUESTION 14
-- Thống kê số user trong mỗi department
-- Nếu không có user -> "Không có User"
-- =========================================================

SELECT
    d.department_id,
    d.department_name,

    CASE
        WHEN COUNT(a.account_id) = 0
            THEN 'Không có User'
        ELSE CAST(COUNT(a.account_id) AS CHAR)
    END AS so_luong_user

FROM department d

LEFT JOIN account a
    ON d.department_id = a.department_id

GROUP BY
    d.department_id,
    d.department_name;