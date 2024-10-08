package kapyrin.myshop.service.impl;

import kapyrin.myshop.dao.DAOInterfaces.RepositoryWithTwoParametersInSomeMethods;
import kapyrin.myshop.entities.ProductOrder;
import kapyrin.myshop.service.ServiceWithTwoParameterInSomeMethod;

import java.util.List;
import java.util.Optional;

public enum ProductOrderServiceImpl implements ServiceWithTwoParameterInSomeMethod<ProductOrder> {
    INSTANCE;
    private RepositoryWithTwoParametersInSomeMethods<ProductOrder> productOrderRepository;

    public ProductOrderServiceImpl initRepository(RepositoryWithTwoParametersInSomeMethods<ProductOrder> productOrderRepository) {
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
}
