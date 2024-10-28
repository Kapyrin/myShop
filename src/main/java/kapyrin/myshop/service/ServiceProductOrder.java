package kapyrin.myshop.service;

import kapyrin.myshop.entity.Product;

import java.util.List;

public interface ServiceProductOrder <T> extends ServiceWithTwoParameterInSomeMethod<T> {
    List<Product> productFromProductOrder(long productOrderId);
}
