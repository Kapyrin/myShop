package kapyrin.myshop.entities;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"customer", "status"})
public class ShopOrder {
    private Long id;
    private User customer;
    private java.sql.Date orderCreationDate;
    private java.sql.Date orderCloseDate;
    private OrderStatus status;
}
