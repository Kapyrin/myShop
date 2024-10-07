package kapyrin.myshop.entities;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderStatus {
    private Long id;
    private String statusName;
}
