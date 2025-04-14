package com.hexaware.myexpections;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super("Please Register");
    }

}
