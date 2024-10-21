package kapyrin.myshop.dao.DAOInterface;

import java.util.Optional;

public interface RepositoryWithTwoParametersInSomeMethods<T> extends Repository<T> {
    void deleteById(long oneEntityId, long anotherEntityId);
    Optional<T> getByIds(long oneEntityId, long anotherEntityId);
}
