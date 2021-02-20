/*
 *
 * Developed by Sara Sandager (sara590x@edu.easj.dk)
 * Licensed under the MIT License
 * 13/02/2021
 *
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    //SQLitePayment
    //Connect to the Paymentdb

    public static void connect(){
        Connection connection = null;
        try{
            //db parameters
            String url = "jdbc:sqlite:db/Paymentdb";
            // create a connection to the database
            connection = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            try{
                if(connection != null){
                    connection.close();
                }
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void main(String[] args){
        connect();
    }
}
