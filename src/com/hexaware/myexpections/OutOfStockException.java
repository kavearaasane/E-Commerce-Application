package com.hexaware.myexpections;

public class OutOfStockException extends Exception {
    public OutOfStockException(String message)
    {
        super("Sorry the Product is Out Of Stock");
    }
}
