## Crud app in java

- docker run --name mysql-shop -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=shop -p 3306:3306 -d mysql:latest
- apply shop_schema.sql
- run main to fill the database
- to start from idea: Run-> Edit configuration -> TomCat 10,
  Deployment <span style="color:yellow; font-size:15px;">/</span>
- On start page you can log in or create user;
- Based on your role:
    - As a customer - you will see your own orders, you can create new order adding products
    - As a manager - you will see all customers and their orders, you can add and change products, sort orders by product, 
  changing order statuses or close orders, delete orders before chosen date. 
    - As an admin - you will see all users(including managers and admins), search for user by id and edit or delete users. 
- You can switch a language on each page and log out