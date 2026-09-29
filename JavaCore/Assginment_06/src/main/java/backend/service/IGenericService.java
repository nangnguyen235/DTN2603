package backend.service;

import java.util.List;

/**
 * Generic Service Interface
 * T: kiểu entity
 * ID: kiểu dữ liệu của khóa chính
 */
public interface IGenericService<T, ID> {
    List<T> getAll();

    boolean create(T entity);

    boolean deleteById(ID id);

    boolean update(ID id, T entity);

    boolean checkExist(ID id);

    String importCSV(String url);
}
