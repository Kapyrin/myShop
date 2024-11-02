package kapyrin.myshop.dao.DAOInterface;

import kapyrin.myshop.entity.Product;

import java.util.List;

public interface ProductOrderRepository <T>extends RepositoryWithTwoParametersInSomeMethods<T>{
    List<Product> productFromProductOrder(long productOrderId);
}
