<form action="/editUser" method="POST">
    <input type="hidden" name="userId" value="${user.id}"/>

    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" value="${user.firstName}" required>
    <br> <br>

    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" value="${user.lastName}" required>
    <br> <br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" value="${user.email}" required>
    <br> <br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" value="${user.password}" required>
    <br>

    <label for="phoneNumber">Phone Number:</label>
    <input type="text" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}">
    <br> <br>

    <label for="address">Address:</label>
    <input type="text" id="address" name="address" value="${user.address}">
    <br> <br>

    <label for="role">Role:</label>
    <select id="role" name="role">
        <option value="admin">Admin</option>
        <option value="manager">Manager</option>
        <option value="customer">Customer</option>
    </select><br><br>
    <br> <br>

    <button type="submit">Save Changes</button>
</form>
