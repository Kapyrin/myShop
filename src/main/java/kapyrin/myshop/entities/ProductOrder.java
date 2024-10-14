package kapyrin.myshop.entities;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"order", "product"})
public class ProductOrder {
    private ShopOrder order;
    private Product product;
    private Integer quantity;
}
