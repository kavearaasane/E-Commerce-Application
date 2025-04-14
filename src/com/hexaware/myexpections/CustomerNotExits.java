package com.hexaware.myexpections;

public class CustomerNotExits extends Exception {
    public CustomerNotExits(String message)
    {
        super("Customer Does Not Exits");
    }
}
