package kapyrin.myshop.service;

import java.util.Optional;

public interface ServiceWithOneParameterInSomeMethod<T> extends ServiceInterface <T>{
    void deleteById(long id);

    Optional<T> getById(long id);
}
