package kapyrin.myshop.dao.DAOInterfaces;

import java.util.Optional;

public interface RepositoryWithOneParameterInSomeMethods<T> extends Repository<T> {
    void deleteById(long id);

    Optional<T> getById(long id);
}
