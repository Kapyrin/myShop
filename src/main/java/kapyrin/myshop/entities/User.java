package kapyrin.myshop.entities;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
public class User {
    private long id;
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String email;
    @Setter
    private String password;
    @Setter
    private String phoneNumber;
    @Setter
    private String address;
    private int roleId;
}