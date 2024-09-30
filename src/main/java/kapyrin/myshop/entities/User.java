package kapyrin.myshop.entities;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private long roleId;
}