package kapyrin.myshop.entities;

import lombok.*;

@Getter

@ToString
public class Role {

    private int id;

    @Setter
    private String userRole;

    public Role( String userRole) {
        this.userRole = userRole;
    }
    public Role(int id, String userRole) {
        this.id = id;
        this.userRole = userRole;
    }


}