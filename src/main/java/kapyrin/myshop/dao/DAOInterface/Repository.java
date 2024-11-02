package kapyrin.myshop.dao.DAOInterface;


import java.util.List;

public interface Repository<T> {
    void add(T entity);

    void update(T entity);

    List<T> getAll();

    void deleteByEntity(T entity);
}
