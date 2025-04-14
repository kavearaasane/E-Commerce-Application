package com.hexaware.services;

import com.hexaware.entity.Cart;
import com.hexaware.entity.Customer;
import com.hexaware.entity.Product;
import com.hexaware.myexpections.CustomerNotExits;
import com.hexaware.myexpections.DeletingProductNotExists;
import com.hexaware.utils.JDBCUtil;

import java.sql.*;
import java.util.*;

public class ServiceImplementation implements IServices{
    Customer customer=new Customer();
    Product product=new Product();
    Cart cart=new Cart();

    @Override
    public Customer customerLogin(int customerId, String customerName, String password) throws CustomerNotExits {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Get connection
            conn = JDBCUtil.getConnection();

            // Query to verify customer credentials
            String query = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ? AND NAME = ? AND PASSWORD = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(1, customerId);
            statement.setString(2, customerName);
            statement.setString(3, password);

            // Execute query
            resultSet = statement.executeQuery();

            // Check if a matching customer exists
            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("CUSTOMER_ID"));
                customer.setName(resultSet.getString("NAME"));
                customer.setEmail(resultSet.getString("EMAIL"));
                customer.setPassword(resultSet.getString("PASSWORD"));
                return customer;
            } else {
                // No matching customer found
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during customer login.", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Product fetchproduct(int productId) {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Get connection
            conn = JDBCUtil.getConnection();

            // Construct query
            String query = "SELECT * FROM PRODUCT WHERE PRODUCT_ID=" + productId;

            // Create statement
            statement = conn.createStatement();

            // Execute query
            resultSet = statement.executeQuery(query);

            // Process the result set
            if (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("PRODUCT_ID"));
                product.setName(resultSet.getString("NAME"));
                product.setPrice(resultSet.getInt("PRICE"));
                product.setDescription(resultSet.getString("DESCRIPTION"));
                product.setStockQuantity(resultSet.getInt("STOCK_QUANTITY"));
                // Add other fields if necessary
                return product;
            } else {
                throw new RuntimeException("Product with ID " + productId + " does not exist.");
            }

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            throw new RuntimeException("Error fetching product.", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean createTableCustomer() {
        /*
        Entity in the Customer Class
        customer_id (Primary Key)
        name
        email
        password*/

        Connection conn = null;
        Statement statement = null;
        boolean createStatus=false;
        String query = "CREATE TABLE CUSTOMER ("
                + "CUSTOMER_ID INT PRIMARY KEY NOT NULL, "
                + "NAME VARCHAR(100), "
                + "EMAIL VARCHAR(100),"
                + "PASSWORD VARCHAR(100)"
                +");";
        try{
            //get connection
            conn = JDBCUtil.getConnection();

            //create statement
            statement = conn.createStatement();

            //execute query
            createStatus=statement.execute(query);

            //close connection
            statement.close();
            conn.close();

            System.out.println("Table created successfully.");
        }catch(Exception e){
            e.printStackTrace();
        }
        return createStatus;
    }

    @Override
    public boolean createTableProduct() {
        /*
        Entity Objects
        product_id (Primary Key)
        name
        price
        description
        stockQuantity
        */

        Connection conn = null;
        Statement statement = null;
        boolean createStatus=false;
        String query = "CREATE TABLE PRODUCT ("
                + "PRODUCT_ID INT PRIMARY KEY NOT NULL, "
                + "NAME VARCHAR(100), "
                + "PRICE INT,"
                + "DESCRIPTION VARCHAR(100),"
                +"STOCK_QUANTITY INT"
                +");";
        try{
            //get connection
            conn = JDBCUtil.getConnection();

            //create statement
            statement = conn.createStatement();

            //execute query
            createStatus=statement.execute(query);

            //close connection
            statement.close();
            conn.close();

            System.out.println("Table created successfully.");
        }catch(Exception e){
            e.printStackTrace();
        }
        return createStatus;

    }

    @Override
    public boolean createTableCart() {
        /*
        cart_id (Primary Key)
        customer_id (Foreign Key)
        product_id (Foreign Key)
        quantity
         */
        Connection conn = null;
        Statement statement = null;
        boolean createStatus=false;

        String query = "CREATE TABLE CART ("
                + "CART_ID INT PRIMARY KEY NOT NULL, "
                + "CUSTOMER_ID INT NOT NULL,"
                + "PRODUCT_ID INT NULL, "
                + "QUANTITY INT NOT NULL,"
                + "FOREIGN KEY(CUSTOMER_ID) REFERENCES CUSTOMER(CUSTOMER_ID),"
                + "FOREIGN KEY(PRODUCT_ID) REFERENCES PRODUCT(PRODUCT_ID)"
                +");";
        try{
            //get connection
            conn = JDBCUtil.getConnection();

            //create statement
            statement = conn.createStatement();

            //execute query
            createStatus=statement.execute(query);

            //close connection
            statement.close();
            conn.close();

            System.out.println("Table created successfully.");
        }catch(Exception e){
            e.printStackTrace();
        }
        return createStatus;
    }

    @Override
    public boolean createTableOrders() {
        /*
        Entity objects
        order_id (Primary Key)
        customer_id (Foreign Key)
        order_date
        total_price
        shipping_address
        */
        Connection conn = null;
        Statement statement = null;
        boolean createStatus=false;
        String query = "CREATE TABLE ORDERS ("
                + "ORDER_ID INT PRIMARY KEY NOT NULL, "
                + "CUSTOMER_ID INT NOT NULL, "
                + "ORDER_DATE VARCHAR(100),"
                + "TOTAL_PRICE INT,"
                + "SHIPPING_ADDRESS VARCHAR(100)"
                +");";
        try{
            //get connection
            conn = JDBCUtil.getConnection();

            //create statement
            statement = conn.createStatement();

            //execute query
            createStatus=statement.execute(query);

            //close connection
            statement.close();
            conn.close();

            System.out.println("Table created successfully.");
        }catch(Exception e){
            e.printStackTrace();
        }
        return createStatus;
    }

    @Override
    public boolean createTableOrderItems() {
        /*
        order_item_id (Primary Key)
        order_id (Foreign Key)
        product_id (Foreign Key)
        quantity
         */
        Connection conn = null;
        Statement statement = null;
        boolean createStatus=false;
        String query = "CREATE TABLE ORDER_ITEMS ("
                + "ORDER_ITEM_ID INT PRIMARY KEY NOT NULL, "
                + "ORDER_ID INT NOT NULL, "
                + "PRODUCT_ID INT NOT NULL, "
                + "QUANTITY INT NOT NULL, "
                + "FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ORDER_ID), "
                + "FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(PRODUCT_ID)"
                + ");";

        try{
            //get connection
            conn = JDBCUtil.getConnection();

            //create statement
            statement = conn.createStatement();

            //execute query
            createStatus=statement.execute(query);

            //close connection
            statement.close();
            conn.close();

            System.out.println("Table created successfully.");
        }catch(Exception e){
            e.printStackTrace();
        }
        return createStatus;

    }

    @Override
    public boolean createProduct(Product product) {
        Connection conn = null;
        Statement statement = null;
        boolean createStatus=false;

        String query = "INSERT INTO PRODUCT (PRODUCT_ID, NAME, PRICE, DESCRIPTION, STOCK_QUANTITY ) VALUES ("
                + product.getProductId() + ", '"
                + product.getName() + "', '"
                + product.getPrice() + "', '"
                + product.getDescription()+ "', '"
                + product.getStockQuantity()+ "')";

        try{
            //get connection
            conn = JDBCUtil.getConnection();

            //create statement
            statement = conn.createStatement();

            //execute query
            createStatus=statement.execute(query);

            //close connection
            statement.close();
            conn.close();

            System.out.println("Product Created Successfully.");
        }catch(Exception e){
            e.printStackTrace();
        }
        return createStatus;

    }

    @Override
    public boolean createCustomer(Customer customer) {
        Connection conn = null;
        Statement statement = null;
        boolean createStatus=false;


        String query = "INSERT INTO CUSTOMER (CUSTOMER_ID, NAME, EMAIL, PASSWORD) VALUES ("
                + customer.getCustomerId() + ", '"
                + customer.getName() + "', '"
                + customer.getEmail() + "', '"
                + customer.getPassword() + "')";

        try{
            //get connection
            conn = JDBCUtil.getConnection();

            //create statement
            statement = conn.createStatement();

            //execute query
            createStatus=statement.execute(query);

            //close connection
            statement.close();
            conn.close();

            System.out.println("Customer created successfully.");
        }catch(Exception e){
            e.printStackTrace();
        }
        return createStatus;
    }

    @Override
    public boolean deleteProduct(int productId) throws DeletingProductNotExists {
        Connection conn = null;
        Statement statement = null;
        boolean createStatus = false;

        String query = "DELETE FROM PRODUCT WHERE PRODUCT_ID=" + productId;

        try {
            // Get connection
            conn = JDBCUtil.getConnection();

            // Create statement
            statement = conn.createStatement();

            // Execute update and get the number of rows affected
            int rowsAffected = statement.executeUpdate(query);

            // Check if any rows were deleted
            if (rowsAffected == 0) {
                throw new DeletingProductNotExists("Product with ID " + productId + " does not exist.");
            }

            createStatus = true; // Record was deleted successfully
            System.out.println("Product Deleted Successfully.");
        } catch (DeletingProductNotExists e) {
            System.err.println(e.getMessage());
            throw e; // Re-throw the custom exception
        } catch (Exception e) {
            e.printStackTrace(); // Log other exceptions
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return createStatus;

    }

    @Override
    public boolean deleteCustomer(int customerId) throws CustomerNotExits {
        Connection conn = null;
        Statement statement = null;
        boolean createStatus=false;

        String query = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID="+customerId;

        try {
            //get connection
            conn = JDBCUtil.getConnection();

            //create statement
            statement = conn.createStatement();

            //execute query
            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected == 0) {
                throw new CustomerNotExits("Customer Does Not Exits in DataBase");
            }

            createStatus = true; // Record was deleted successfully
            System.out.println("Customer Deleted Successfully.");
        }
            catch (CustomerNotExits e)
            {
            System.err.println(e.getMessage());
            throw e;
            }

        catch (Exception e) {
            e.printStackTrace(); // Log other exceptions
        }
        finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return createStatus;
    }

    @Override
    public boolean addToCart(Customer customer, Product product, int quantity) {
        Random random=new Random();
        Connection conn = null;
        Statement statement = null;
        boolean createStatus=false;
        cart.setQuantity(quantity);
        int cartId = random.nextInt(1000)+1;
        cart.setCartId(cartId);
        String query = "INSERT INTO CART (CART_ID, CUSTOMER_ID, PRODUCT_ID, QUANTITY) VALUES ("
                + cart.getCartId() + ", '"
                + customer.getCustomerId() + "', '"
                + product.getProductId() + "', '"
                + cart.getQuantity() + "')";

        try {
            //get connection
            conn = JDBCUtil.getConnection();

            //create statement
            statement = conn.createStatement();

            //execute query
            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected == 0) {
                throw new CustomerNotExits("Customer Does Not Exits in DataBase");
            }

            createStatus = true; // Record was deleted successfully
            System.out.println("Added to Cart Successfully.");
        }

        catch (Exception e) {
            e.printStackTrace(); // Log other exceptions
        }
        finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return createStatus;
    }

    @Override
    public boolean removeFromCart(Customer customer, Product product) throws DeletingProductNotExists {
        Connection conn = null;
        Statement statement = null;
        boolean createStatus = false;
        int productId=product.getProductId();

        String query = "DELETE FROM CART WHERE PRODUCT_ID=" + productId;

        try {
            // Get connection
            conn = JDBCUtil.getConnection();

            // Create statement
            statement = conn.createStatement();

            // Execute update and get the number of rows affected
            int rowsAffected = statement.executeUpdate(query);

            // Check if any rows were deleted
            if (rowsAffected == 0) {
                throw new DeletingProductNotExists("Product with ID " + productId + " does not exist.");
            }
            createStatus = true; // Record was deleted successfully
            System.out.println("Product Deleted Successfully.");
        } catch (DeletingProductNotExists e) {
            System.err.println(e.getMessage());
            throw e; // Re-throw the custom exception
        } catch (Exception e) {
            e.printStackTrace(); // Log other exceptions
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return createStatus;

    }

    @Override
    public List<Product> getAllFromCart(Customer customer) {
        int customerID = customer.getCustomerId();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Product> cartProducts = new ArrayList<>();

        try {
            // Get connection
            conn = JDBCUtil.getConnection();
            int customerId = customer.getCustomerId();

            // Query to join CART and PRODUCT tables and fetch product details for a customer
            String query = "SELECT P.PRODUCT_ID, P.NAME, P.PRICE, P.DESCRIPTION, C.QUANTITY " +
                    "FROM CART C " +
                    "JOIN PRODUCT P ON C.PRODUCT_ID = P.PRODUCT_ID " +
                    "WHERE C.CUSTOMER_ID =" + customerID;

            // Prepare statement
            statement = conn.prepareStatement(query);


            // Execute query
            resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                // Create and populate the Product object
                Product product = new Product();
                product.setProductId(resultSet.getInt("PRODUCT_ID"));
                product.setName(resultSet.getString("NAME"));
                product.setPrice(resultSet.getInt("PRICE"));
                product.setDescription(resultSet.getString("DESCRIPTION"));
                product.setStockQuantity(resultSet.getInt("QUANTITY"));

                // Add product to the list
                cartProducts.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            throw new RuntimeException("Error fetching products from the cart.", e);
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return cartProducts;
    }

    @Override
    public List<Product> displayProducts() {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<>();

        String query = "SELECT * FROM PRODUCT";

        try {
            // get connection
            conn = JDBCUtil.getConnection();

            // create statement
            statement = conn.createStatement();

            // execute query
            resultSet = statement.executeQuery(query);

            // process results
            while (resultSet.next()) {
                Product product = new Product(); // Create a new Product object for each row
                product.setProductId(resultSet.getInt("PRODUCT_ID"));
                product.setName(resultSet.getString("NAME"));
                product.setPrice(resultSet.getInt("PRICE"));
                product.setDescription(resultSet.getString("DESCRIPTION"));
                product.setStockQuantity(resultSet.getInt("STOCK_QUANTITY"));
                productList.add(product); // Add the newly created object to the list
            }

            // close connection
            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }
    @Override
    public boolean placeOrder(Customer customer, List<Map<Product, Integer>> products, String shippingAddress) {
        String orderQuery = "INSERT INTO orders (CUSTOMER_ID, ORDER_ID, TOTAL_PRICE, SHIPPING_ADDRESS) VALUES (?, ?, ?, ?)";
        String orderItemQuery = "INSERT INTO ORDER_ITEMS (ORDER_ITEM_ID, ORDER_ID, PRODUCT_ID, QUANTITY) VALUES (?, ?, ?, ?)";

        Connection connection = null;

        try {
            connection = JDBCUtil.getConnection();
            PreparedStatement orderStatement = connection.prepareStatement(orderQuery);
            PreparedStatement orderItemStatement = connection.prepareStatement(orderItemQuery);

            connection.setAutoCommit(false); // Enable transaction management

            // Generate a random ORDER_ID
            int orderId = (int) (Math.random() * 1_000_000); // Generates a random number between 0 and 999,999

            // Initialize a counter for ORDER_ITEM_ID
            int orderItemId = (int) (Math.random() * 1_000_000); // Starting point for ORDER_ITEM_ID (to ensure uniqueness)

            // Calculate the total price for the order
            double totalPrice = 0.0;
            for (Map<Product, Integer> productQuantityMap : products) {
                for (Map.Entry<Product, Integer> entry : productQuantityMap.entrySet()) {
                    Product product = entry.getKey();
                    int quantity = entry.getValue();
                    totalPrice += product.getPrice() * quantity;
                }
            }

            // Insert data into the 'orders' table
            orderStatement.setInt(1, customer.getCustomerId());
            orderStatement.setInt(2, orderId); // Use the random ORDER_ID
            orderStatement.setDouble(3, totalPrice);
            orderStatement.setString(4, shippingAddress);

            int rowsAffected = orderStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert into orders table.");
            }

            // Insert data into the 'ORDER_ITEMS' table
            for (Map<Product, Integer> productQuantityMap : products) {
                for (Map.Entry<Product, Integer> entry : productQuantityMap.entrySet()) {
                    Product product = entry.getKey();
                    int quantity = entry.getValue();

                    // Generate a unique ORDER_ITEM_ID for each product
                    orderItemStatement.setInt(1, orderItemId++); // Increment ORDER_ITEM_ID for uniqueness
                    orderItemStatement.setInt(2, orderId); // Use the random ORDER_ID
                    orderItemStatement.setInt(3, product.getProductId());
                    orderItemStatement.setInt(4, quantity);
                    orderItemStatement.addBatch(); // Add to batch
                }
            }
            orderItemStatement.executeBatch(); // Execute batch insert for order items

            // Commit the transaction
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace(); // Log exception
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback transaction on failure
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            // Ensure the connection is closed
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }


    @Override
    public List<Map<Product, Integer>> getOrdersByCustomer(int customerId) {
        List<Map<Product, Integer>> orders = new ArrayList<>();
        String query = "SELECT P.PRODUCT_ID, P.NAME, P.PRICE, P.DESCRIPTION, OI.QUANTITY " +
                "FROM ORDER_ITEMS OI " +
                "JOIN PRODUCT P ON OI.PRODUCT_ID = P.PRODUCT_ID " +
                "JOIN ORDERS O ON O.ORDER_ID = OI.ORDER_ID " +
                "WHERE O.CUSTOMER_ID = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the customerId parameter
            stmt.setInt(1, customerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Create a Product object
                    Product product = new Product();
                    product.setProductId(rs.getInt("PRODUCT_ID"));
                    product.setName(rs.getString("NAME"));
                    product.setPrice(rs.getInt("PRICE"));
                    product.setDescription(rs.getString("DESCRIPTION"));

                    // Get the quantity
                    int quantity = rs.getInt("QUANTITY");

                    // Create a map and add to the orders list
                    Map<Product, Integer> productQuantityMap = new HashMap<>();
                    productQuantityMap.put(product, quantity);
                    orders.add(productQuantityMap);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
            throw new RuntimeException("Error fetching orders for customer.", e);
        }

        return orders;
    }

}
