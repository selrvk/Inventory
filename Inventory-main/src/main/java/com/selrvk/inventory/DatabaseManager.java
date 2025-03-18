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

        String query = "INSERT INTO products (name, stock, srp, buying_price, manufacturer) VALUES (?,?,?,?,?)";

        try(Connection connection = connect();
            PreparedStatement prstm = connection.prepareStatement(query)){

            prstm.setString(1, product.getName());
            prstm.setInt(2, product.getStock());
            prstm.setInt(3, product.getSrp());
            prstm.setInt(4, product.getBuyingPrice());
            prstm.setString(5, product.getManufacturer());

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

        String query = "UPDATE products SET name = ?, stock = ?, srp = ?, buying_price = ?, manufacturer = ? WHERE id = ?";

        try(Connection connection = connect();
            PreparedStatement stmt = connection.prepareStatement(query)){

            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getStock());
            stmt.setInt(3, product.getSrp() );
            stmt.setInt(4, product.getBuyingPrice());
            stmt.setString(5, product.getManufacturer());
            stmt.setInt(6, product.getId());

            stmt.executeUpdate();

        } catch (SQLException e){

            System.out.println(e.getMessage());
        }

    }

    public List<Product> getProducts(String query, String sLike, int iMin, int iMax){

        List<Product> products = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(query)){

            stmt.setString(1, sLike);
            stmt.setInt(2, iMin);
            stmt.setInt(3, iMax);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                Product product = new Product(
                        rs.getInt("id"),
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
            if(rs.next()){
                return new Product(
                        rs.getInt("id"),
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

    public void createOrder(Orders order){

        int order_id;
        String queryOrder = "INSERT INTO pending_orders (order_date, customer_name) VALUES (?,?)";
        String queryOrderProducts = "INSERT INTO pending_orders_products (order_id, product_id, product_name, order_quantity, product_price) VALUES (?,?,?,?,?)";

        try(Connection connection = connect();
            PreparedStatement prstmOrder = connection.prepareStatement(queryOrder, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement prstmOrderProducts = connection.prepareStatement(queryOrderProducts)){

            prstmOrder.setDate(1, order.getDate());
            prstmOrder.setString(2, order.getCustomer_name());

            prstmOrder.executeUpdate();

            try(ResultSet orderID = prstmOrder.getGeneratedKeys()){
                if(orderID.next()){
                    order_id = orderID.getInt(1);

                    for (OrdersProducts ordersProducts : order.getProductList()) {
                        prstmOrderProducts.setInt(1, order_id);
                        prstmOrderProducts.setInt(2, ordersProducts.getProduct_id());
                        prstmOrderProducts.setString(3, ordersProducts.getProduct_name());
                        prstmOrderProducts.setInt(4, ordersProducts.getOrder_quantity());
                        prstmOrderProducts.setInt(5, ordersProducts.getProduct_price());

                        prstmOrderProducts.executeUpdate();
                    }
                }
            }

        } catch (SQLException e){

            System.out.println(e.getMessage());
        }
    }

    public void confirmOrder(int order_id){

        String queryUpdateStock = "UPDATE products SET stock = stock - ? WHERE id = ?";
        String queryGetPendingOrder = "SELECT * FROM pending_orders WHERE order_id = ?";
        String queryGetPendingOrderProducts = "SELECT * FROM pending_orders_products WHERE order_id = ?";

        String queryConfirmOrder = "INSERT INTO completed_orders (order_id, order_date, customer_name) VALUES (?,?,?)";
        String queryConfirmOrderProducts = "INSERT INTO completed_orders_products (order_id, product_name, order_quantity, product_price) VALUES (?,?,?,?)";

        String queryRemovePendingOrder = "DELETE FROM pending_orders WHERE order_id = ?";
        String queryRemovePendingOrderProducts = "DELETE FROM pending_orders_products WHERE order_id = ?";

        try(Connection connection = connect();
            PreparedStatement prstmUpdateProductStock = connection.prepareStatement(queryUpdateStock);
            PreparedStatement prstmGetPendingOrder = connection.prepareStatement(queryGetPendingOrder);
            PreparedStatement prstmGetPendingOrderProducts = connection.prepareStatement(queryGetPendingOrderProducts, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            PreparedStatement prstmConfirmOrder = connection.prepareStatement(queryConfirmOrder);
            PreparedStatement prstmQueryRemovePendingOrder = connection.prepareStatement(queryRemovePendingOrder);
            PreparedStatement prstmQueryRemovePendingOrderProducts = connection.prepareStatement(queryRemovePendingOrderProducts);
            PreparedStatement prstmConfirmOrderProducts = connection.prepareStatement(queryConfirmOrderProducts)){

            // Get from pendingOrders
            prstmGetPendingOrder.setInt(1, order_id);
            ResultSet pendingOrderSet = prstmGetPendingOrder.executeQuery();

            if(pendingOrderSet.next()){
                // Get from pendingOrderProducts
                prstmGetPendingOrderProducts.setInt(1, order_id);
                ResultSet pendingOrderProductsSet = prstmGetPendingOrderProducts.executeQuery();

                while(pendingOrderProductsSet.next()){
                    prstmQueryRemovePendingOrder.setInt(1, order_id);
                    prstmQueryRemovePendingOrder.executeUpdate();
                    prstmQueryRemovePendingOrderProducts.setInt(1, order_id);
                    prstmQueryRemovePendingOrderProducts.executeUpdate();
                }

                //Move to completedOrders
                prstmConfirmOrder.setInt(1,pendingOrderSet.getInt("order_id"));
                prstmConfirmOrder.setDate(2,pendingOrderSet.getDate("order_date"));
                prstmConfirmOrder.setString(3,pendingOrderSet.getString("customer_name"));
                prstmConfirmOrder.executeUpdate();

                pendingOrderProductsSet.beforeFirst();
                //Move to completedOrdersProducts
                while(pendingOrderProductsSet.next()){

                    prstmConfirmOrderProducts.setInt(1, pendingOrderProductsSet.getInt("order_id"));
                    prstmConfirmOrderProducts.setString(2, pendingOrderProductsSet.getString("product_name"));
                    prstmConfirmOrderProducts.setInt(3, pendingOrderProductsSet.getInt("order_quantity"));
                    prstmConfirmOrderProducts.setInt(4, pendingOrderProductsSet.getInt("product_price"));
                    prstmConfirmOrderProducts.executeUpdate();
                }

                pendingOrderProductsSet.beforeFirst();

                while (pendingOrderProductsSet.next()){

                    prstmUpdateProductStock.setInt(1, pendingOrderProductsSet.getInt("order_quantity"));
                    prstmUpdateProductStock.setInt(2, pendingOrderProductsSet.getInt("product_id"));
                    prstmUpdateProductStock.executeUpdate();
                }
            }

        } catch (SQLException e){

            System.out.println(e.getMessage());
        }
    }

    public List<Orders> getPendingOrders(){

        String query = "SELECT * FROM pending_orders";
        List<Orders> orders = new ArrayList<>();

        try (Connection connection = connect();
            PreparedStatement stmt = connection.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){

                Orders order = new Orders(
                                rs.getInt("order_id"),
                                rs.getDate("order_date"),
                                rs.getString("customer_name")
                );
                orders.add(order);
            }

        } catch (SQLException e){

            System.out.println(e.getMessage());
        }
        return orders;
    }

    public List<OrdersProducts> getOrderProducts(Orders order){

        String query = "SELECT * FROM pending_orders_products WHERE order_id = ?";
        List<OrdersProducts> ordersProducts = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(query)){

            stmt.setInt(1, order.getOrder_id());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){

                OrdersProducts orderProduct = new OrdersProducts(
                        rs.getInt("order_id"),
                        rs.getString(   "product_name"),
                        rs.getInt("product_id"),
                        rs.getInt("order_quantity"),
                        rs.getInt("product_price")
                );
                ordersProducts.add(orderProduct);
            }

        } catch (SQLException e){

            System.out.println(e.getMessage());
        }

        return ordersProducts;
    }

    public List<Orders> getOrderHistory(){

        String query = "SELECT * FROM completed_orders";
        List<Orders> orders = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){

                Orders order = new Orders(
                        rs.getInt("order_id"),
                        rs.getDate("order_date"),
                        rs.getString("customer_name")
                );
                orders.add(order);
            }

        } catch (SQLException e){

            System.out.println(e.getMessage());
        }
        return orders;
    }

    public List<OrdersProducts> getOrderHistoryProducts(Orders order){

        String query = "SELECT * FROM completed_orders_products WHERE order_id = ?";
        List<OrdersProducts> ordersProducts = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(query)){

            stmt.setInt(1, order.getOrder_id());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){

                OrdersProducts orderProduct = new OrdersProducts(
                        rs.getInt("order_id"),
                        rs.getString(   "product_name"),
                        rs.getInt("product_id"),
                        rs.getInt("order_quantity"),
                        rs.getInt("product_price")
                );
                ordersProducts.add(orderProduct);
            }

        } catch (SQLException e){

            System.out.println(e.getMessage());
        }

        return ordersProducts;
    }
}
