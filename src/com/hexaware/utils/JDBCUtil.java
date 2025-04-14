package com.hexaware.utils;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * This is a utility class for JDBC connection.
 * @author w3schools
 */
public class JDBCUtil {
    //JDBC and database properties.
// private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Ecommerce_kave";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Hexaware@12345";
    public static Connection getConnection(){
        Connection conn = null;
        try{
//Register the JDBC driver
// Class.forName(DB_DRIVER);
//Open the connection
            conn = DriverManager.
                    getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            if(conn != null){
                System.out.println("Successfully connected.");
            }else{
                System.out.println("Failed to connect.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}