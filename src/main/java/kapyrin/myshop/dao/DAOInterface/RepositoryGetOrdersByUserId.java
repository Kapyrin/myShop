package kapyrin.myshop.dao.DAOInterface;

import kapyrin.myshop.entity.ShopOrder;

import java.util.List;

public interface RepositoryGetOrdersByUserId<T> extends RepositoryWithOneParameterInSomeMethods<T>{
List<ShopOrder> getAllOrdersByUserId(long userId);
}
