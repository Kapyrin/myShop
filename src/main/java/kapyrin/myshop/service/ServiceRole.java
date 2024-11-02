package kapyrin.myshop.service;

import kapyrin.myshop.entity.Role;

import java.util.Optional;

public interface ServiceRole <T>extends ServiceWithOneParameterInSomeMethod<T>{
    Optional<Role> getByRoleName(String roleName);
}
