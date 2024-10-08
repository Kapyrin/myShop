package kapyrin.myshop.service;

import java.util.Optional;

public interface ServiceWithTwoParameterInSomeMethod<T> extends ServiceInterface <T>{


    Optional<T> getByIds(long oneEntityId, long twoEntityId);

    void deleteByIds(long oneEntityId, long twoEntityId);
}
