package kapyrin.myshop.dao;

import java.util.List;

public interface Repository<T> {
    List<T> getAll();


    T get(long id);

    void create(T entity);

    void update(T entity, long id);

    void delete(T entity);
}
