package kapyrin.myshop.service.impl;

import kapyrin.myshop.dao.DAOInterface.RoleDaoRepository;
import kapyrin.myshop.entity.Role;
import kapyrin.myshop.service.ServiceRole;

import java.util.List;
import java.util.Optional;

public enum RoleServiceImpl implements ServiceRole<Role> {
    INSTANCE;
    private RoleDaoRepository roleRepository;

    public RoleServiceImpl initRepository(RoleDaoRepository<Role> repository) {
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
        return roleRepository.getByRoleName(name);

    }
}
