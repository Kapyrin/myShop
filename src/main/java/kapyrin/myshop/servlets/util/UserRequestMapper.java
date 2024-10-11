package kapyrin.myshop.servlets.util;

import jakarta.servlet.http.HttpServletRequest;
import kapyrin.myshop.entities.User;
import kapyrin.myshop.entities.Role;
import kapyrin.myshop.exception.entities.RoleException;
import kapyrin.myshop.service.impl.RoleServiceImpl;

public enum UserRequestMapper {
    INSTANCE;

    private RoleServiceImpl roleService;

    public UserRequestMapper init(RoleServiceImpl roleService) {
        this.roleService = roleService;
        return this;
    }

    public User extractUserFromRequest(HttpServletRequest request, boolean update) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String roleParam = request.getParameter("role");

        Role role = roleService.getByRoleName(roleParam).orElseThrow(() -> new RoleException("Role not found"));

        User.UserBuilder userBuilder = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .address(address)
                .role(role);

        if (update) {
            String userIdParam = request.getParameter("userId");
            if (userIdParam != null && !userIdParam.isEmpty()) {
                long userId = Long.parseLong(userIdParam);
                userBuilder.id(userId);
            }
        }

        return userBuilder.build();
    }
}
