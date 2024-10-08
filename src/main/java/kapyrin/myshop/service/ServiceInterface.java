package kapyrin.myshop.service;

import java.util.List;

public interface ServiceInterface<T> {
    void add(T entity);

    void update(T entity);

    void deleteByEntity(T entity);

    List<T> getAll();


}

