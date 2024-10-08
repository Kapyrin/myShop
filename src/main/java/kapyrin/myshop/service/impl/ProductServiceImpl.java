package kapyrin.myshop.service.impl;

import kapyrin.myshop.dao.DAOInterfaces.RepositoryWithOneParameterInSomeMethods;
import kapyrin.myshop.entities.Product;
import kapyrin.myshop.service.ServiceWithOneParameterInSomeMethod;

import java.util.List;
import java.util.Optional;

public enum ProductServiceImpl implements ServiceWithOneParameterInSomeMethod<Product> {
    INSTANCE;
    private RepositoryWithOneParameterInSomeMethods productRepository;

    public ProductServiceImpl initRepository(RepositoryWithOneParameterInSomeMethods<Product> productRepository) {
        this.productRepository = productRepository;
        return this;
    }

    @Override
    public void deleteById(long id) {
        productRepository.deleteById(id);

    }

    @Override
    public Optional<Product> getById(long id) {
       return productRepository.getById(id);
    }

    @Override
    public void add(Product product) {
        productRepository.add(product);

    }

    @Override
    public void update(Product product) {
        productRepository.update(product);
    }

    @Override
    public List<Product> getAll() {
       return productRepository.getAll();
    }

    @Override
    public void deleteByEntity(Product entity) {
        productRepository.deleteByEntity(entity);
    }
}
