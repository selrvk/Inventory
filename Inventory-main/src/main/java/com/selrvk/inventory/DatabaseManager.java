package com.selrvk.inventory;

import java.sql.*;
import java.util.*;

public class DatabaseManager {

    private static String username , password;

    public Connection connect() throws SQLException{
        String dbURL = "jdbc:mysql://192.168.1.2:3306/inventory";
        return DriverManager.getConnection(dbURL, username, password);
    }

    public Connection verifyConnection(String url, String un, String pw) throws SQLException{

        return DriverManager.getConnection(url, un, pw);
    }

    public void addNewProduct(Product product){

        String query = "INSERT INTO products (image, name, stock, srp, buying_price, manufacturer) VALUES (?,?,?,?,?,?)";

        try(Connection connection = connect();
            PreparedStatement prstm = connection.prepareStatement(query)){

            prstm.setBytes(1, product.getImg());
            prstm.setString(2, product.getName());
            prstm.setInt(3, product.getStock());
            prstm.setInt(4, product.getSrp());
            prstm.setInt(5, product.getBuyingPrice());
            prstm.setString(6, product.getManufacturer());

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

    public void updateProduct(Product product){

        String query = "UPDATE products SET image = ?, name = ?, stock = ?, srp = ?, buying_price = ?, manufacturer = ? WHERE id = ?";

        try(Connection connection = connect();
            PreparedStatement stmt = connection.prepareStatement(query)){

            stmt.setBytes(1 , product.getImg());
            stmt.setString(2, product.getName());
            stmt.setInt(3, product.getStock());
            stmt.setInt(4, product.getSrp() );
            stmt.setInt(5, product.getBuyingPrice());
            stmt.setString(6, product.getManufacturer());
            stmt.setInt(7, product.getId());

            stmt.executeUpdate();

        } catch (SQLException e){

            System.out.println(e.getMessage());
        }

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
                        rs.getInt("srp"),
                        rs.getInt("buying_price"),
                        rs.getString("manufacturer")
                );

                products.add(product);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return products;
    }

    public Product getProduct(int id){

        String sql = "SELECT * FROM products WHERE id = ?";

        try(Connection connection = connect();
            PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                return new Product(
                        rs.getInt("id"),
                        rs.getBytes("image"),
                        rs.getString("name"),
                        rs.getInt("stock"),
                        rs.getInt("srp"),
                        rs.getInt("buying_price"),
                        rs.getString("manufacturer")
                );
            }

        } catch (SQLException e){

            System.out.println(e.getMessage());
        }

        return null;
    }



    public void setUsername(String username){ DatabaseManager.username = username; }

    public void setPassword(String password){ DatabaseManager.password = password; }
}
