package com.hexaware.myexpections;

public class NoItemInCart extends Exception{
    public NoItemInCart(String message)
    {
        super("No Item in Cart is added");
    }
}
