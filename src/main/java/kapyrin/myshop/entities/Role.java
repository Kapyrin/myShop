package kapyrin.myshop.entities;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class Role {

    private Long id;

    private String userRole;

    public Role(String userRole) {
        this.userRole = userRole;
    }

    public Role(Long id, String userRole) {
        this.id = id;
        this.userRole = userRole;
    }


}