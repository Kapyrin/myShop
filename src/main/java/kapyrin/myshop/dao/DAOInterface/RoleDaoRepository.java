package kapyrin.myshop.dao.DAOInterface;

import kapyrin.myshop.entity.Role;

import java.util.Optional;

public interface RoleDaoRepository <T> extends RepositoryWithOneParameterInSomeMethods<T>{
     Optional<Role> getByRoleName(String roleName);
}
