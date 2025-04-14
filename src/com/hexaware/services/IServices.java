package com.hexaware.services;

import com.hexaware.entity.Customer;
import com.hexaware.entity.Product;
import com.hexaware.myexpections.CustomerNotExits;
import com.hexaware.myexpections.DeletingProductNotExists;

import java.lang.String;
import java.util.List;
import java.util.Map;

public interface IServices{
    public Customer customerLogin(int customerId, String customerName, String password) throws CustomerNotExits;
    public boolean createTableCustomer();
    public boolean createTableProduct();
    public boolean createTableCart();
    public boolean createTableOrders();
    public boolean createTableOrderItems();
    public boolean createProduct(Product product);
    public boolean createCustomer(Customer customer);
    public boolean deleteProduct(int productId) throws DeletingProductNotExists;
    public boolean deleteCustomer(int customerId) throws CustomerNotExits;
    public boolean addToCart(Customer customer,Product product,int quantity);
    public boolean removeFromCart(Customer customer,Product product) throws DeletingProductNotExists;
    public List<Product> getAllFromCart(Customer customer);
    public boolean placeOrder(Customer customer, List<Map<Product, Integer>> products, String shippingAddress);
    public List<Map<Product, Integer>> getOrdersByCustomer(int customerId);
    public List<Product> displayProducts();
    public Product fetchproduct(int productId);

}
