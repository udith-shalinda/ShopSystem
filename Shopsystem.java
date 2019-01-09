/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shopsystem;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Udith Shalinda
 */
public class Shopsystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Shopsystem shop = new Shopsystem();
        //shop.detailsInsertFrame();        //to insert a product details to database;
        shop.previewframe();
        //shop.passtoadmin();
    }

    void detailsInsertFrame() {
        JFrame jframe = new JFrame("Updating database");
        jframe.getContentPane().setLayout(new FlowLayout());
        jframe.setPreferredSize(new Dimension(400, 400));
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label1 = new JLabel("Name of the product : ");
        JTextField test1 = new JTextField("", 20);
        JLabel label2 = new JLabel("Product code : ");
        JTextField test2 = new JTextField("", 20);
        JLabel label3 = new JLabel("Product price: ");
        JTextField test3 = new JTextField("", 20);
        JLabel label4 = new JLabel("Product amount : ");
        JTextField test4 = new JTextField("", 20);
        JButton button = new JButton("Submit");
        button.setBounds(20, 20, 20, 20);
        jframe.getContentPane().add(label1);
        jframe.getContentPane().add(test1);
        jframe.getContentPane().add(label2);
        jframe.getContentPane().add(test2);
        jframe.getContentPane().add(label3);
        jframe.getContentPane().add(test3);
        jframe.getContentPane().add(label4);
        jframe.getContentPane().add(test4);
        jframe.getContentPane().add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = test1.getText();
                int code = Integer.parseInt(test2.getText());
                int price = Integer.parseInt(test3.getText());
                int amount = Integer.parseInt(test4.getText());
                writedatabase(name, code, price, amount);
            }
        });
        jframe.pack();
        jframe.setVisible(true);
    }

    void writedatabase(String name, int code, int price, int amount) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/shopsystem", "root", "");
            System.out.println("connected to the database");
            statement = connection.createStatement();
            String sql = "insert into details(Name,Code,Price,Amount)values('" + name + "','" + code + "','" + price + "','" + amount + "')";
            statement.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
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
            System.out.println("done");
        }
    }

    void previewframe() {
        JFrame jframe = new JFrame("Details");
        jframe.getContentPane().setLayout(new FlowLayout());
        jframe.setPreferredSize(new Dimension(400, 400));
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel code = new JLabel("Product code : ");
        JTextField inputcode = new JTextField("", 20);
        inputcode.setBounds(100, 100, 10, 20);
        JLabel amount = new JLabel("Product amount : ");
        JTextField inputamount = new JTextField("", 20);
        JButton calculateprice = new JButton("calculate price");
        calculateprice.setBounds(20, 20, 20, 20);
        JButton Admin = new JButton("Admin");
        jframe.getContentPane().add(code);
        jframe.getContentPane().add(inputcode);
        jframe.getContentPane().add(amount);
        jframe.getContentPane().add(inputamount);
        jframe.getContentPane().add(Admin);
        jframe.add(calculateprice);
        calculateprice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //pass the values to another frame;
                readfromdatabase(Integer.parseInt(inputcode.getText()), Integer.parseInt(inputamount.getText()));
            }
        });
        Admin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passtoadmin();
            }
        });
        jframe.pack();
        jframe.setVisible(true);
    }

    void passtoadmin() {
        JFrame frame = new JFrame("pass to admin");
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel lable = new JLabel("Password : ");
        JPasswordField pass = new JPasswordField("", 20);
        JButton button = new JButton("Log in");
        lable.setBounds(25, 25, 10, 50);
        pass.setBounds(50, 50, 50, 250);
        frame.setSize(500, 500);
        frame.add(lable);
        frame.add(pass);
        frame.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String password = new String(pass.getPassword());
                if (password.equals("hello")) {
                    detailsInsertFrame();
                } else {
                    System.out.println("wrong password");
                }
            }
        });
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
                showpriceframe(price*amount,name);    
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
        return price*amount;
    }

    void showpriceframe(int price,String proname) {
        JFrame frame = new JFrame("calculate the price");
        frame.setLayout(new FlowLayout());
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel lable = new JLabel("price : " + price);
        JLabel lable2 = new JLabel("Product name : " + proname);
        JLabel moneylabel = new JLabel("Input money : ");
        JTextField inputmoney = new JTextField("", 20);
        JButton button = new JButton("calculate balance");

        frame.setSize(500, 500);
        frame.add(lable);
        frame.add(lable2);
        frame.add(moneylabel);
        frame.add(inputmoney);
        frame.add(button);
       
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //have to calculate the balance;
            }
        });
        frame.pack();
        frame.setVisible(true);
    }

}
