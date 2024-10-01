package kapyrin.myshop.service;

import kapyrin.myshop.dao.Repository;
import kapyrin.myshop.entities.User;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final Repository userRepository;

    public UserService(Repository<User> userRepository) {
        this.userRepository = userRepository;
    }

    public void add(User user) {
        userRepository.add(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public void deleteByEntity(User user) {
        userRepository.deleteByEntity(user);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public Optional<User> getUserById(long id) {
        return userRepository.getById(id);
    }


}

