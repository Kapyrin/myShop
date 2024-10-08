package kapyrin.myshop.service.impl;

import kapyrin.myshop.dao.DAOInterfaces.RepositoryWithOneParameterInSomeMethods;
import kapyrin.myshop.entities.ShopOrder;
import kapyrin.myshop.service.ServiceWithOneParameterInSomeMethod;

import java.util.List;
import java.util.Optional;

public enum ShopOrderServiceImpl implements ServiceWithOneParameterInSomeMethod<ShopOrder> {
    INSTANCE;
    private RepositoryWithOneParameterInSomeMethods<ShopOrder> shopOrderRepository;

    public ShopOrderServiceImpl initRepository(RepositoryWithOneParameterInSomeMethods<ShopOrder> shopOrderRepository) {
        this.shopOrderRepository = shopOrderRepository;
        return this;
    }

    @Override
    public void deleteById(long id) {
        shopOrderRepository.deleteById(id);
    }

    @Override
    public Optional<ShopOrder> getById(long id) {
        return shopOrderRepository.getById(id);
    }

    @Override
    public void add(ShopOrder shopOrder) {
        shopOrderRepository.add(shopOrder);

    }

    @Override
    public void update(ShopOrder shopOrder) {
        shopOrderRepository.update(shopOrder);
    }

    @Override
    public void deleteByEntity(ShopOrder shopOrder) {
        shopOrderRepository.deleteByEntity(shopOrder);
    }

    @Override
    public List<ShopOrder> getAll() {
        return shopOrderRepository.getAll();
    }
}
