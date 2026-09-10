USE dtn2603_testing_system;

-- =========================================================
-- QUESTION 1
-- Nhập tên phòng ban và in ra tất cả account thuộc phòng ban đó
-- =========================================================

DELIMITER $$

CREATE PROCEDURE sp_q1_get_account_by_department(
    IN p_department_name VARCHAR(100)
)
BEGIN
    SELECT
        a.account_id,
        a.full_name
    FROM `account` a
    JOIN department d
        ON a.department_id = d.department_id
    WHERE d.department_name = p_department_name;
END $$

DELIMITER ;

CALL sp_q1_get_account_by_department('Marketing');


-- =========================================================
-- QUESTION 2
-- In ra số lượng account trong mỗi group
-- =========================================================

DELIMITER $$

CREATE PROCEDURE sp_q2_count_account_each_group()
BEGIN
    SELECT
        g.group_name,
        COUNT(ga.account_id) AS so_luong_account
    FROM `group` g
    LEFT JOIN group_account ga
        ON g.group_id = ga.group_id
    GROUP BY g.group_id, g.group_name;
END $$

DELIMITER ;

CALL sp_q2_count_account_each_group();


-- =========================================================
-- QUESTION 3
-- Thống kê mỗi type question có bao nhiêu question
-- được tạo trong tháng hiện tại
-- =========================================================

DELIMITER $$

CREATE PROCEDURE sp_q3_count_question_current_month()
BEGIN
    SELECT
        tq.type_name,
        COUNT(q.question_id) AS so_luong_question
    FROM type_question tq
    LEFT JOIN question q
        ON tq.type_id = q.type_id
        AND MONTH(q.create_date) = MONTH(CURDATE())
        AND YEAR(q.create_date) = YEAR(CURDATE())
    GROUP BY tq.type_id, tq.type_name;
END $$

DELIMITER ;

CALL sp_q3_count_question_current_month();


-- =========================================================
-- QUESTION 4
-- Trả ra ID của type question có nhiều câu hỏi nhất
-- =========================================================

DELIMITER $$

CREATE PROCEDURE sp_q4_get_type_question_max()
BEGIN
    SELECT
        type_id
    FROM question
    GROUP BY type_id
    ORDER BY COUNT(question_id) DESC
    LIMIT 1;
END $$

DELIMITER ;

CALL sp_q4_get_type_question_max();


-- =========================================================
-- QUESTION 5
-- Sử dụng logic Question 4 để tìm tên type question
-- =========================================================

DELIMITER $$

CREATE PROCEDURE sp_q5_get_type_question_name()
BEGIN
    DECLARE v_type_id INT;

    SELECT type_id
    INTO v_type_id
    FROM question
    GROUP BY type_id
    ORDER BY COUNT(question_id) DESC
    LIMIT 1;

    SELECT
        type_id,
        type_name
    FROM type_question
    WHERE type_id = v_type_id;
END $$

DELIMITER ;

CALL sp_q5_get_type_question_name();


-- =========================================================
-- QUESTION 6
-- Nhập 1 chuỗi:
-- Tìm group có group_name chứa chuỗi
-- hoặc account có user_name chứa chuỗi
-- =========================================================

DELIMITER $$

CREATE PROCEDURE sp_q6_search_group_or_user(
    IN p_keyword VARCHAR(100)
)
BEGIN

    SELECT
        'GROUP' AS result_type,
        g.group_id AS id,
        g.group_name AS name
    FROM `group` g
    WHERE g.group_name LIKE CONCAT('%', p_keyword, '%')

    UNION ALL

    SELECT
        'USER' AS result_type,
        a.account_id AS id,
        a.user_name AS name
    FROM `account` a
    WHERE a.user_name LIKE CONCAT('%', p_keyword, '%');

END $$

DELIMITER ;

CALL sp_q6_search_group_or_user('admin');


-- =========================================================
-- QUESTION 7
-- Nhập full_name, email
-- user_name = phần trước @
-- position_id = DEV
-- department_id = phòng chờ việc
-- =========================================================

DELIMITER $$

CREATE PROCEDURE sp_q7_create_account(
    IN p_full_name VARCHAR(100),
    IN p_email VARCHAR(100)
)
BEGIN
    DECLARE v_user_name VARCHAR(100);
    DECLARE v_position_id INT;
    DECLARE v_department_id INT;

    -- Lấy phần trước dấu @ làm user_name
    SET v_user_name = SUBSTRING_INDEX(p_email, '@', 1);

    -- Lấy position DEV
    SELECT position_id
    INTO v_position_id
    FROM `position`
    WHERE position_name = 'DEV'
    LIMIT 1;

    -- Lấy phòng chờ việc
    -- Đổi 'Waiting QLDepartment.java' thành tên phòng thực tế của bạn
    SELECT department_id
    INTO v_department_id
    FROM department
    WHERE department_name = 'Waiting QLDepartment.java'
    LIMIT 1;

    -- Tạo account
    INSERT INTO `account`(
        email,
        user_name,
        full_name,
        department_id,
        position_id
    )
    VALUES(
        p_email,
        v_user_name,
        p_full_name,
        v_department_id,
        v_position_id
    );

    -- In account vừa tạo
    SELECT
        account_id,
        email,
        user_name,
        full_name,
        department_id,
        position_id,
        create_date
    FROM `account`
    WHERE account_id = LAST_INSERT_ID();

END $$

DELIMITER ;

CALL sp_q7_create_account(
    'Nguyen Van A',
    'nguyenvana@gmail.com'
);


-- =========================================================
-- QUESTION 8
-- Nhập ESSAY hoặc MULTIPLE_CHOICE
-- Tìm question có content dài nhất
-- =========================================================

DELIMITER $$

CREATE PROCEDURE sp_q8_get_longest_question(
    IN p_type_name VARCHAR(50)
)
BEGIN
    SELECT
        q.question_id,
        q.content,
        tq.type_name,
        CHAR_LENGTH(q.content) AS content_length
    FROM question q
    JOIN type_question tq
        ON q.type_id = tq.type_id
    WHERE tq.type_name = p_type_name
    ORDER BY CHAR_LENGTH(q.content) DESC
    LIMIT 1;
END $$

DELIMITER ;

CALL sp_q8_get_longest_question('ESSAY');

CALL sp_q8_get_longest_question('MULTIPLE_CHOICE');


-- =========================================================
-- QUESTION 9
-- Xóa exam dựa vào ID
-- =========================================================

DELIMITER $$

CREATE PROCEDURE sp_q9_delete_exam(
    IN p_exam_id INT
)
BEGIN
    DECLARE v_count_exam_question INT DEFAULT 0;
    DECLARE v_count_exam INT DEFAULT 0;

    -- Xóa các question liên kết với exam
    DELETE FROM exam_question
    WHERE exam_id = p_exam_id;

    SET v_count_exam_question = ROW_COUNT();

    -- Xóa exam
    DELETE FROM exam
    WHERE exam_id = p_exam_id;

    SET v_count_exam = ROW_COUNT();

    -- In số record đã xóa
    SELECT
        v_count_exam_question AS exam_question_removed,
        v_count_exam AS exam_removed;
END $$

DELIMITER ;

CALL sp_q9_delete_exam(5);


-- =========================================================
-- QUESTION 10
-- Tìm các exam được tạo từ 3 năm trước và xóa
-- Sử dụng store Question 9
-- =========================================================

DELIMITER $$

CREATE PROCEDURE sp_q10_delete_exam_3_years_ago()
BEGIN
    DECLARE v_exam_id INT;
    DECLARE v_done INT DEFAULT 0;

    DECLARE v_total_exam_question INT DEFAULT 0;
    DECLARE v_total_exam INT DEFAULT 0;

    DECLARE cur CURSOR FOR
        SELECT exam_id
        FROM exam
        WHERE create_date < DATE_SUB(CURDATE(), INTERVAL 3 YEAR);

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_done = 1;

    OPEN cur;

    delete_loop: LOOP

        FETCH cur INTO v_exam_id;

        IF v_done = 1 THEN
            LEAVE delete_loop;
        END IF;

        -- Đếm exam_question trước khi gọi store Q9
        SELECT COUNT(*)
        INTO @count_exam_question
        FROM exam_question
        WHERE exam_id = v_exam_id;

        -- Gọi store Question 9
        CALL sp_q9_delete_exam(v_exam_id);

        SET v_total_exam_question =
            v_total_exam_question + @count_exam_question;

        SET v_total_exam =
            v_total_exam + 1;

    END LOOP;

    CLOSE cur;

    SELECT
        v_total_exam_question AS total_exam_question_removed,
        v_total_exam AS total_exam_removed;

END $$

DELIMITER ;

CALL sp_q10_delete_exam_3_years_ago();


-- =========================================================
-- QUESTION 11
-- Xóa department theo tên
-- Account thuộc department đó chuyển sang
-- department mặc định: Waiting QLDepartment.java
-- =========================================================

DELIMITER $$

CREATE PROCEDURE sp_q11_delete_department(
    IN p_department_name VARCHAR(100)
)
BEGIN
    DECLARE v_department_id INT;
    DECLARE v_default_department_id INT;
    DECLARE v_account_count INT DEFAULT 0;

    -- Tìm department cần xóa
    SELECT department_id
    INTO v_department_id
    FROM department
    WHERE department_name = p_department_name
    LIMIT 1;

    -- Tìm department chờ việc
    SELECT department_id
    INTO v_default_department_id
    FROM department
    WHERE department_name = 'Waiting QLDepartment.java'
    LIMIT 1;

    -- Đếm số account sẽ được chuyển
    SELECT COUNT(*)
    INTO v_account_count
    FROM `account`
    WHERE department_id = v_department_id;

    -- Chuyển account sang phòng chờ việc
    UPDATE `account`
    SET department_id = v_default_department_id
    WHERE department_id = v_department_id;

    -- Xóa department
    DELETE FROM department
    WHERE department_id = v_department_id;

    -- In kết quả
    SELECT
        v_account_count AS accounts_moved,
        'QLDepartment.java deleted successfully' AS message;

END $$

DELIMITER ;

CALL sp_q11_delete_department('Marketing');


-- =========================================================
-- QUESTION 12
-- In ra mỗi tháng có bao nhiêu question
-- được tạo trong năm nay
-- =========================================================

DELIMITER $$

CREATE PROCEDURE sp_q12_count_question_each_month()
BEGIN
    SELECT
        MONTH(create_date) AS thang,
        COUNT(question_id) AS so_luong_question
    FROM question
    WHERE YEAR(create_date) = YEAR(CURDATE())
    GROUP BY MONTH(create_date)
    ORDER BY MONTH(create_date);
END $$

DELIMITER ;

CALL sp_q12_count_question_each_month();


-- =========================================================
-- QUESTION 13
-- In ra mỗi tháng có bao nhiêu question
-- trong 6 tháng gần đây nhất
-- Nếu không có -> "Không có câu hỏi nào trong tháng"
-- =========================================================

DELIMITER $$

CREATE PROCEDURE sp_q13_count_question_last_6_months()
BEGIN

    SELECT
        DATE_FORMAT(m.month_date, '%m/%Y') AS thang,

        CASE
            WHEN COUNT(q.question_id) = 0
            THEN 'Không có câu hỏi nào trong tháng'

            ELSE CAST(COUNT(q.question_id) AS CHAR)
        END AS so_luong_question

    FROM
    (
        SELECT DATE_FORMAT(
            DATE_SUB(CURDATE(), INTERVAL 5 MONTH),
            '%Y-%m-01'
        ) AS month_date

        UNION ALL

        SELECT DATE_FORMAT(
            DATE_SUB(CURDATE(), INTERVAL 4 MONTH),
            '%Y-%m-01'
        )

        UNION ALL

        SELECT DATE_FORMAT(
            DATE_SUB(CURDATE(), INTERVAL 3 MONTH),
            '%Y-%m-01'
        )

        UNION ALL

        SELECT DATE_FORMAT(
            DATE_SUB(CURDATE(), INTERVAL 2 MONTH),
            '%Y-%m-01'
        )

        UNION ALL

        SELECT DATE_FORMAT(
            DATE_SUB(CURDATE(), INTERVAL 1 MONTH),
            '%Y-%m-01'
        )

        UNION ALL

        SELECT DATE_FORMAT(
            CURDATE(),
            '%Y-%m-01'
        )
    ) m

    LEFT JOIN question q
        ON DATE_FORMAT(q.create_date, '%Y-%m')
        = DATE_FORMAT(m.month_date, '%Y-%m')

    GROUP BY m.month_date
    ORDER BY m.month_date;

END $$

DELIMITER ;

CALL sp_q13_count_question_last_6_months();