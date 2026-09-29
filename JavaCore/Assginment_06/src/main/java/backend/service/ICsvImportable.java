package backend.service;

import java.util.List;

/**
 * Interface đánh dấu entity có thể parse từ CSV
 * T: kiểu entity
 */
public interface ICsvImportable<T> {
    /**
     * Validate và parse 1 dòng CSV thành entity
     * @param line dòng CSV đọc được
     * @param validEntities danh sách entity hợp lệ (nếu ko lỗi thì add vào đây)
     * @return null nếu ko có lỗi, hoặc chuỗi "line + error_messages" nếu có lỗi
     */
    String validateAndParse(String line, List<T> validEntities);
}
