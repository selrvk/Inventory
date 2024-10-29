package com.selrvk.inventory;

import java.sql.*;
import java.io.*;
import java.util.*;

public class DatabaseManager {

    private final String dbURL = "jdbc:sqlite:inventorydb.db";

    public Connection connect() throws SQLException{

        System.out.println("Connection made");
        return DriverManager.getConnection(dbURL);

    }

    public void addNewProduct(){

    }

    public void removeProduct(){

    }

    public void updateProduct(){


    }

    public List<Product> getProducts(){

        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try {

            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){

                Product product = new Product(
                        rs.getInt("id"),
                        rs.getBytes("image"),
                        rs.getString("name"),
                        rs.getInt("stock"),
                        rs.getString("brand"),
                        rs.getString("shelf_location")
                );

                products.add(product);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return products;
    }

}
