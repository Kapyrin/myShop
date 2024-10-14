package kapyrin.myshop.service.impl;


import kapyrin.myshop.dao.DAOInterfaces.AuthenticateUser;
import kapyrin.myshop.entities.User;
import kapyrin.myshop.service.ServiceAuthenticate;

import java.util.List;
import java.util.Optional;

public enum UserServiceImpl implements ServiceAuthenticate<User> {
    INSTANCE;
    private AuthenticateUser userRepository;

    public UserServiceImpl initRepository(AuthenticateUser<User> userRepository) {
        this.userRepository = userRepository;
        return this;
    }

    @Override
    public void add(User user) {
        userRepository.add(user);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteByEntity(User user) {
        userRepository.deleteByEntity(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public Optional<User> getById(long id) {
        return userRepository.getById(id);
    }

    @Override
    public Optional<User> authenticate(String email, String password) {
        return userRepository.authenticate(email, password);

    }
}