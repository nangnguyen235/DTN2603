package backend.service.impl;

import backend.repository.IGenericRepository;
import backend.service.ICsvImportable;
import backend.service.IGenericService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abstract Generic Service
 * Chứa logic importCSV dùng chung cho mọi entity (Account, Department,...)
 * T: kiểu entity
 * ID: kiểu khóa chính
 */
public abstract class AbstractGenericService<T, ID> implements IGenericService<T, ID>, ICsvImportable<T> {

    protected abstract IGenericRepository<T, ID> getRepository();

    @Override
    public List<T> getAll() {
        return getRepository().getAll();
    }

    @Override
    public boolean create(T entity) {
        return getRepository().create(entity);
    }

    @Override
    public boolean deleteById(ID id) {
        return getRepository().deleteById(id);
    }

    @Override
    public boolean update(ID id, T entity) {
        return getRepository().update(id, entity);
    }

    @Override
    public boolean checkExist(ID id) {
        return getRepository().checkExist(id);
    }

    /**
     * Import CSV generic: đọc file -> validate từng dòng -> lưu entity hợp lệ vào DB
     * Logic này dùng chung cho mọi entity, chỉ cần override validateAndParse()
     */
    @Override
    public String importCSV(String url) {
        // check file có đúng định dạng ko (có đuôi là .csv ko)
        if (!url.endsWith(".csv")) {
            return "File không đúng định dạng!";
        }
        // check xem file có tồn tại ko
        File file = new File(url);
        if (!file.exists()) {
            return "File không tồn tại!";
        }

        List<T> validEntities = new ArrayList<>(); // lưu các entity hợp lệ
        List<String> listErrors = new ArrayList<>(); // tập hợp các row và message lỗi
        String header = "";

        // đọc file -> ds các entity trong file
        try (BufferedReader br = new BufferedReader(new FileReader(url))) {
            String line;
            header = br.readLine(); // đọc và bỏ qua dòng đầu tiên (header)
            while ((line = br.readLine()) != null) {
                String message = this.validateAndParse(line, validEntities);
                if (Objects.nonNull(message)) {
                    listErrors.add(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // lưu ds các entity ko gặp lỗi vào DB
        for (T entity : validEntities) {
            getRepository().create(entity);
        }

        // xuất ra file lỗi ra file csv
        if (!listErrors.isEmpty()) {
            // lưu file lỗi cùng thư mục với file import
            String errorFilePath = file.getParent() + File.separator + "import_errors.csv";
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(errorFilePath));
                bw.write(header + ",error_messages");
                bw.newLine();
                for (String error : listErrors) {
                    bw.write(error);
                    bw.newLine();
                }
                bw.flush();
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Đã xuất ra file lỗi: " + errorFilePath;
        }
        return "Import thành công! Đã thêm " + validEntities.size() + " bản ghi.";
    }
}
