package kapyrin.myshop.entities;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Product {
    private Long id;
    private String productName;
    private String productDescription;
    private Double price;
    private Integer productRemain;
}
