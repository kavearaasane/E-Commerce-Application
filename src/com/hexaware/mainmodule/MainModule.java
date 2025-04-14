package com.hexaware.mainmodule;

import com.hexaware.entity.Customer;
import com.hexaware.entity.Product;
import com.hexaware.myexpections.CustomerNotExits;
import com.hexaware.myexpections.DeletingProductNotExists;
import com.hexaware.services.ServiceImplementation;
import com.hexaware.utils.JDBCUtil;

import java.util.*;

public class MainModule {

    public static void main(String[] args) throws DeletingProductNotExists, CustomerNotExits {
        int authentication;
        int choice;
        Random random = new Random();
        System.out.println(JDBCUtil.getConnection());
        Scanner scanner = new Scanner(System.in);
        /*
        created a object for class ServiceImplementation and created tables
         */
        ServiceImplementation implementation = new ServiceImplementation();
        /*
        System.out.println(implementation.createTableCustomer());
        System.out.println(implementation.createTableProduct());
        System.out.println(implementation.createTableCart());
        System.out.println(implementation.createTableOrders());
        System.out.println(implementation.createTableOrderItems());
        */


        System.out.println("Welcome to Amazon");
        System.out.println("Press 1 for Customer Sign if already an customer");
        System.out.println("Press 2 for New User Login ");
        System.out.println("Press 3 for Management Login");
        authentication = scanner.nextInt();
        if (authentication == 1) {
            System.out.println("Please Log in");
            System.out.println("Enter your Customer Id");
            int customerId = scanner.nextInt();
            System.out.println("Enter your Name");
            String customerName = scanner.next();
            System.out.println("Enter your Password");
            String password = scanner.next();
            Customer customer = implementation.customerLogin(customerId, customerName, password);
            if (customer != null) {
                boolean isRunning = true;
                while (isRunning) {
                    System.out.println("Press 1 to View Available Products");
                    System.out.println("Press 2 to Add product to Cart");
                    System.out.println("Press 3 to Remove product from Cart");
                    System.out.println("Press 4 to View products from Cart");
                    System.out.println("Press 5 to Place Order");
                    System.out.println("Press 0 to Exit");
                    System.out.println("Enter your Menu");
                    choice = scanner.nextInt();
                    if (choice == 1) {
                        System.out.println(implementation.displayProducts());
                    } else if (choice == 2) {
                        int quantity;
                        System.out.println("Available Products are");
                        System.out.println(implementation.displayProducts());
                        System.out.println("Enter Product id to be added to Cart");
                        int productId = scanner.nextInt();
                        Product product = implementation.fetchproduct(productId);
                        if (product == null) {
                            System.out.println("No product found with the given Product ID. Please try again.");
                            return;
                        }
                        System.out.println("Enter Quantity Required");
                        quantity = scanner.nextInt();
                        System.out.println(implementation.addToCart(customer, product, quantity));

                    } else if (choice == 3) {
                        System.out.println("Enter the Product ID to be deleted");
                        int productId = scanner.nextInt();
                        Product product = implementation.fetchproduct(productId);
                        System.out.println(implementation.removeFromCart(customer, product));
                    } else if (choice == 4) {
                        System.out.println("Products in Cart");
                        System.out.println(implementation.getAllFromCart(customer));
                    }

                else if(choice==5)
                {
                    System.out.println("Enter your address for shipping:");
                    scanner.next();
                    String shippingAddress = scanner.nextLine();
                    List<Product> productList = implementation.getAllFromCart(customer);
                    List<Map<Product, Integer>> productsInCart = new ArrayList<>();
                    for (Product product : productList) {
                        Map<Product, Integer> productQuantityMap = new HashMap<>();
                        productQuantityMap.put(product, 1);
                        productsInCart.add(productQuantityMap);
                    }
                    boolean isOrderPlaced = implementation.placeOrder(customer, productsInCart, shippingAddress);
                    if (isOrderPlaced) {
                        System.out.println("Order placed successfully!");
                    } else {
                        System.out.println("Failed to place the order. Please try again.");
                    }

                }
                    else if (choice == 0) {
                        isRunning = false;
                    } else {
                        System.out.println("Invalid Option");
                    }
                }
            } else {
                System.out.println("Customer Does Not Exits");
            }
        } else if (authentication == 2) {

            Customer customer = new Customer();
            int customerId = random.nextInt(100);
            customer.setCustomerId(customerId);
            System.out.println("Enter your Name");
            String name = scanner.next();
            System.out.println("Enter your Email");
            String email = scanner.next();
            System.out.println("Enter your Password");
            String newPassword = scanner.next();
            customer.setCustomerId(customerId);
            customer.setName(name);
            customer.setEmail(email);
            customer.setPassword(newPassword);
            customer.setPassword(newPassword);
            System.out.println(implementation.createCustomer(customer));

        } else if (authentication == 3) {
            int authenticationChoice;
            boolean isRunning = true;
            while (isRunning) {
                System.out.println("Press 1 to Create a New Product");
                System.out.println("Press 2 to Delete a Product");
                System.out.println("Press 3 to Delete a Customer");
                System.out.println("Press 4 to Display Orders Placed by Customers");
                System.out.println("Press 0 to Exit");
                authenticationChoice = scanner.nextInt();
                if (authenticationChoice == 1) {
                    System.out.println("Enter ProductID");
                    int productId = scanner.nextInt();
                    System.out.println("Enter Product Name");
                    String name = scanner.next();
                    System.out.println("Enter Product Price");
                    int price = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter Product Description");
                    String description = scanner.nextLine();
                    System.out.println("Enter Stock available");
                    int stockQuality = scanner.nextInt();
                    System.out.println(implementation.createProduct(new Product(productId, name, price, description, stockQuality)));
                } else if (authenticationChoice == 2) {
                    System.out.println("Enter Product Id to be deleted:");
                    int productId = scanner.nextInt();
                    System.out.println(implementation.deleteProduct(productId));
                } else if (authenticationChoice == 3) {
                    System.out.println("Enter Customer ID:");
                    int customerId = scanner.nextInt();
                    System.out.println(implementation.deleteCustomer(customerId));

                }

           else if(authenticationChoice==4)
            {
                System.out.println("Enter Customer ID:");
                int customerId= scanner.nextInt();
                System.out.println(implementation.getOrdersByCustomer(customerId));
            }

                else if (authenticationChoice == 0) {
                    isRunning = false;
                } else {
                    System.out.println("Invalid Option");
                }
            }

        }

    }
}

