package kapyrin.myshop.service.impl;

import kapyrin.myshop.dao.DAOInterface.ShopOrderRepository;
import kapyrin.myshop.entity.ShopOrder;
import kapyrin.myshop.service.ServiceShopOrder;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public enum ShopOrderServiceImpl implements ServiceShopOrder<ShopOrder> {
    INSTANCE;
    private ShopOrderRepository<ShopOrder> shopOrderRepository;

    public ShopOrderServiceImpl initRepository(ShopOrderRepository<ShopOrder> shopOrderRepository) {
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

    @Override
    public List<ShopOrder> getOrdersByProductId(Long productId) {
        return shopOrderRepository.getOrdersByProductId(productId);
    }

    @Override
    public void deleteOrdersBeforeDate(Date date) {
        shopOrderRepository.deleteOrdersBeforeDate(date);

    }

    @Override
    public void closeOrder(long orderId) {
        shopOrderRepository.closeOrder(orderId);
    }

    @Override
    public void updateOrderStatus(long orderId, long statusId) {
        shopOrderRepository.updateOrderStatus(orderId, statusId);
    }
}
