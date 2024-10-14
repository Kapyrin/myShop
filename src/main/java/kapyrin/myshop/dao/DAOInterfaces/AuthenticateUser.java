package kapyrin.myshop.dao.DAOInterfaces;

import java.util.Optional;

public interface AuthenticateUser<T> extends RepositoryWithOneParameterInSomeMethods<T>{
    Optional<T> authenticate(String email, String password);
}
