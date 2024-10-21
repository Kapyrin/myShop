package kapyrin.myshop.service;

import kapyrin.myshop.entity.ShopOrder;

import java.util.List;

public interface ServiceGetOrdersByUserId<T> extends ServiceWithOneParameterInSomeMethod<T>{
     List<ShopOrder> getAllOrdersByUserId(long userId);
}
