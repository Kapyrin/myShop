package kapyrin.myshop.service.impl;

import kapyrin.myshop.dao.DAOInterfaces.RepositoryWithOneParameterInSomeMethods;
import kapyrin.myshop.dao.impl.RoleDAOImpl;
import kapyrin.myshop.entities.Role;
import kapyrin.myshop.service.ServiceInterface;
import kapyrin.myshop.service.ServiceWithOneParameterInSomeMethod;

import java.util.List;
import java.util.Optional;

public enum RoleServiceImpl implements ServiceWithOneParameterInSomeMethod<Role> {
    INSTANCE;
    private RepositoryWithOneParameterInSomeMethods roleRepository;

    public RoleServiceImpl initRepository(RepositoryWithOneParameterInSomeMethods<Role> repository) {
        this.roleRepository = repository;
        return this;
    }

    @Override
    public void add(Role role) {
        roleRepository.add(role);
    }

    @Override
    public void update(Role role) {
        roleRepository.update(role);
    }

    @Override
    public void deleteById(long id) {
        roleRepository.deleteById(id);

    }

    @Override
    public void deleteByEntity(Role role) {
        roleRepository.deleteByEntity(role);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.getAll();
    }

    @Override
    public Optional<Role> getById(long id) {
        return roleRepository.getById(id);
    }

    public Optional<Role> getByRoleName(String name) {
        if (roleRepository instanceof RoleDAOImpl) {
            return ((RoleDAOImpl) roleRepository).getByRoleName(name);
        }
        return Optional.empty();
    }
}
