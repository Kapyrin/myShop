package kapyrin.myshop.dao.DAOInterfaces;

import java.util.Optional;

public interface RepositoryWithTwoParametersInSomeMethods<T> extends Repository<T> {
    void deleteById(long oneEntityId, long anotherEntityId);
    Optional<T> getByIds(long oneEntityId, long anotherEntityId);
}
