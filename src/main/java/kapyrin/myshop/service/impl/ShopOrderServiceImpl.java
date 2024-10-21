package kapyrin.myshop.service.impl;

import kapyrin.myshop.dao.DAOInterface.RepositoryGetOrdersByUserId;
import kapyrin.myshop.entity.ShopOrder;
import kapyrin.myshop.service.ServiceGetOrdersByUserId;

import java.util.List;
import java.util.Optional;

public enum ShopOrderServiceImpl implements ServiceGetOrdersByUserId<ShopOrder> {
    INSTANCE;
    private RepositoryGetOrdersByUserId<ShopOrder> shopOrderRepository;

    public ShopOrderServiceImpl initRepository(RepositoryGetOrdersByUserId<ShopOrder> shopOrderRepository) {
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

    @Override
    public List<ShopOrder> getAllOrdersByUserId(long userId) {
        return shopOrderRepository.getAllOrdersByUserId(userId);

    }
}
