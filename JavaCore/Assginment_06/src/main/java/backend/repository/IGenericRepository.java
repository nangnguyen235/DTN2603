package backend.repository;

import java.util.List;

/**
 * Generic Repository Interface
 * T: kiểu entity (Account, Department, Position,...)
 * ID: kiểu dữ liệu của khóa chính (Integer, String,...)
 */
public interface IGenericRepository<T, ID> {
    List<T> getAll();

    boolean create(T entity);

    boolean deleteById(ID id);

    boolean update(ID id, T entity);

    boolean checkExist(ID id);
}
