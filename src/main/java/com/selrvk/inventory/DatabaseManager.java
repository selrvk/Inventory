package com.selrvk.inventory;

import java.sql.*;
import java.io.*;
import java.io.File.*;
import java.util.*;

public class DatabaseManager {

    private final String dbURL = "jdbc:sqlite:inventorydb.db";

    public Connection connect() throws SQLException{

        return DriverManager.getConnection(dbURL);

    }

    public void addNewProduct(Product product){

        String sql = "INSERT INTO products (image, name, stock, brand, shelf_location) VALUES (?,?,?,?,?)";

        try(Connection connection = connect();
            PreparedStatement prstm = connection.prepareStatement(sql)){

            prstm.setBytes(1, product.getImg());
            prstm.setString(2, product.getName());
            prstm.setInt(3, product.getStock());
            prstm.setString(4, product.getBrand());
            prstm.setString(5, product.getLocation());

            prstm.executeUpdate();

        } catch (SQLException e){

            System.out.println(e.getMessage());
        }
    }

    public void removeProduct(List<Integer> productIds){

        String sql = "DELETE FROM products WHERE id = ?";

        try(Connection connection = connect();
            PreparedStatement prstm = connection.prepareStatement(sql)){

            for(Integer id : productIds){

                prstm.setInt(1, id);
                prstm.addBatch();
            }

            prstm.executeBatch();

        } catch (SQLException e){

            System.out.println(e.getMessage());
        }
    }

    public void updateProduct(){

    }

    public void searchProduct(){

    }

    public List<Product> getProducts(String sql, String... sParam){

        List<Product> products = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(sql)){

            for (int i = 0; i < sParam.length; i++) {

                stmt.setString(i + 1, sParam[i]);
            }

            ResultSet rs = stmt.executeQuery();

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
