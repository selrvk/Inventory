package com.selrvk.inventory;

import java.sql.*;
import java.util.*;

public class DatabaseManager {

    private final String dbURL = "jdbc:sqlite:inventorydb.db";

    public Connection connect() throws SQLException{

        return DriverManager.getConnection(dbURL);

    }

    public void addNewProduct(Product product){

        String query = "INSERT INTO products (image, name, stock, brand, shelf_location) VALUES (?,?,?,?,?)";

        try(Connection connection = connect();
            PreparedStatement prstm = connection.prepareStatement(query)){

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

        String query = "DELETE FROM products WHERE id = ?";

        try(Connection connection = connect();
            PreparedStatement prstm = connection.prepareStatement(query)){

            for(Integer id : productIds){

                prstm.setInt(1, id);
                prstm.addBatch();
            }

            prstm.executeBatch();

        } catch (SQLException e){

            System.out.println(e.getMessage());
        }
    }

    public void updateProduct(int id){

        String query = "UPDATE products SET image = ?, name = ?, stock = ?, brand = ?, shelf_location = ?";

        try(Connection connection = connect();
            PreparedStatement stmt = connection.prepareStatement(query)){

            stmt.setInt(1 , id);

        } catch (SQLException e){

            System.out.println(e.getMessage());
        }

    }

    public void searchProduct(){

    }

    public List<Product> getProducts(String sql, String sLike, int iMin, int iMax){

        List<Product> products = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, sLike);
            stmt.setInt(2, iMin);
            stmt.setInt(3, iMax);

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
