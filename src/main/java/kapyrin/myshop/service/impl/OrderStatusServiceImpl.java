package kapyrin.myshop.service.impl;

import kapyrin.myshop.dao.DAOInterfaces.RepositoryWithOneParameterInSomeMethods;
import kapyrin.myshop.entities.OrderStatus;
import kapyrin.myshop.service.ServiceWithOneParameterInSomeMethod;

import java.util.List;
import java.util.Optional;

public enum OrderStatusServiceImpl implements ServiceWithOneParameterInSomeMethod<OrderStatus> {
    INSTANCE;
    private RepositoryWithOneParameterInSomeMethods orderStatusRepository;

    public OrderStatusServiceImpl initRepository(RepositoryWithOneParameterInSomeMethods<OrderStatus> orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
        return this;
    }

    @Override
    public void add(OrderStatus status) {
        orderStatusRepository.add(status);

    }

    @Override
    public void update(OrderStatus status) {
        orderStatusRepository.update(status);
    }

    @Override
    public void deleteById(long id) {
        orderStatusRepository.deleteById(id);
    }

    @Override
    public void deleteByEntity(OrderStatus status) {
        orderStatusRepository.deleteByEntity(status);
    }

    @Override
    public List<OrderStatus> getAll() {
        return orderStatusRepository.getAll();
    }

    @Override
    public Optional<OrderStatus> getById(long id) {
        return orderStatusRepository.getById(id);
    }
}
