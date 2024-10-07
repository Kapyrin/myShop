package kapyrin.myshop.service.impl;

import kapyrin.myshop.dao.DAOInterfaces.RepositoryWithOneParameterInSomeMethods;
import kapyrin.myshop.entities.Role;
import kapyrin.myshop.service.ServiceInterface;

import java.util.List;
import java.util.Optional;

public enum RoleServiceImpl implements ServiceInterface<Role> {
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
}
