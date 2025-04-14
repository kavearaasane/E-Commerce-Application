package com.hexaware.myexpections;

public class DeletingProductNotExists extends Exception{
    public DeletingProductNotExists(String message)
    {
        super("Deleting A Product Which Does Not Exits");
    }
}
