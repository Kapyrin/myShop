package kapyrin.myshop.service;

import java.sql.Date;
import java.util.List;

public interface ServiceShopOrder<T> extends ServiceWithOneParameterInSomeMethod<T>{
    List<T> getAllOrdersByUserId(long userId);

    List<T> getOrdersByProductId(Long productId);

    void deleteOrdersBeforeDate(Date date);

    void closeOrder(long orderId);

    void updateOrderStatus(long orderId, long statusId);

}
