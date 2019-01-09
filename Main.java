/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shopsystem;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

/**
 *
 * @author Udith Shalinda
 */
public class Main {
     JFrame frame = new JFrame();
    JPanel oldpanel;
    
     void priview(){
        //frame.setPreferredSize(new Dimension(700, 700));
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        oldpanel = new Search();
        frame.getContentPane().add(oldpanel);
//        frame.getContentPane().remove(oldpanel);
//        frame.getContentPane().add(new Pricepage());
        frame.pack();
        frame.setVisible(true);
    }
    
    int readfromdatabase(int code, int amount) {
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost/shopsystem";

        //  Database credentials
        final String USER = "root";
        final String PASS = "";
        Connection connection = null;
        Statement statement = null;
        int price=0;
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            statement = connection.createStatement();
            String sql;
            sql = "SELECT * FROM details where code = "+code;
            ResultSet rs = statement.executeQuery(sql);
            
            while (rs.next()) {
                price = rs.getInt("Price");
                String name = rs.getString("Name");
                //(price*amount,name);    
                System.out.print("price : " + price);
                System.out.println("\t First: " + name);
                
            }
         

            //STEP 6: Clean-up environment
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        secondjpanel();
        return price*amount;
    }
    void secondjpanel(){
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new Updatelist());
        frame.validate();
        frame.pack();
        frame.setVisible(true);
    }
    
     public static void main(String[] args) {
        Main main = new Main();
        main.priview();
    }
     
}
