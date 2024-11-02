package kapyrin.myshop.service;

import java.util.Optional;

public interface ServiceAuthenticate<T> extends ServiceWithOneParameterInSomeMethod<T> {
    Optional<T> authenticate(String email, String password);
}
