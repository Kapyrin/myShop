package kapyrin.myshop.service.impl;

import kapyrin.myshop.dao.DAOInterface.ProductOrderRepository;
import kapyrin.myshop.dao.DAOInterface.RepositoryWithTwoParametersInSomeMethods;
import kapyrin.myshop.entity.Product;
import kapyrin.myshop.entity.ProductOrder;
import kapyrin.myshop.service.ServiceProductOrder;

import java.util.List;
import java.util.Optional;

public enum ProductOrderServiceImpl implements ServiceProductOrder<ProductOrder> {
    INSTANCE;
    private ProductOrderRepository<ProductOrder> productOrderRepository;

    public ProductOrderServiceImpl initRepository(ProductOrderRepository<ProductOrder> productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
        return this;
    }

    @Override
    public Optional<ProductOrder> getByIds(long orderId, long productId) {
        return productOrderRepository.getByIds(orderId, productId);
    }

    @Override
    public void deleteByIds(long orderId, long productId) {
        productOrderRepository.deleteById(orderId, productId);
    }

    @Override
    public void add(ProductOrder productOrder) {
        productOrderRepository.add(productOrder);
    }

    @Override
    public void update(ProductOrder productOrder) {
        productOrderRepository.update(productOrder);
    }

    @Override
    public void deleteByEntity(ProductOrder productOrder) {
        productOrderRepository.deleteByEntity(productOrder);
    }

    @Override
    public List<ProductOrder> getAll() {
        return productOrderRepository.getAll();
    }

    @Override
    public List<Product> productFromProductOrder(long productOrderId) {
        return productOrderRepository.productFromProductOrder(productOrderId);
    }
}
