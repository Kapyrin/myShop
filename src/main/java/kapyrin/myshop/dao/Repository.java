package kapyrin.myshop.dao;


import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    void add (T entity);
    void update (T entity);
    void deleteById (long id);
    void deleteByEntity (T entity);
    List<T> getAll() ;
    Optional<T> getById(long id);
}
