package kapyrin.myshop.service;

import java.util.List;
import java.util.Optional;

public interface ServiceInterface<T> {
    void add(T entity);

    void update(T entity);

    void deleteById(long id);

    void deleteByEntity(T entity);

    List<T> getAll();

    Optional<T> getById(long id);
}

