package com.selrvk.inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class OrderHistoryController {

    @FXML
    private Button pendingOrdersButton;
    @FXML
    private Button inventoryButton;
    @FXML
    private ScrollPane orderHistoryPane;
    @FXML
    private ScrollPane orderHistoryProductsPane;

    private final DatabaseManager dbManager = new DatabaseManager();

    private final ObservableList<Button> expandButtons = FXCollections.observableArrayList();

    public void initialize(){

        inventoryButton.setOnAction(e -> openInventory());
        pendingOrdersButton.setOnAction(e -> openPendingOrders());

        printOrderHistory();
    }

    public void printOrderHistory(){

        VBox pendingOrdersVBox = new VBox(20);

        List<Orders> orderHistory = dbManager.getOrderHistory();

        for(Orders orders : orderHistory){

            OrderHistoryPanel panel = new OrderHistoryPanel(orders);
            this.expandButtons.add(panel.getExpandOrderDetailsButton());
            panel.getExpandOrderDetailsButton().setOnAction(e -> expandDetails(orders));
            pendingOrdersVBox.getChildren().add(panel);
        }
        orderHistoryPane.setContent(pendingOrdersVBox);
    }

    public void expandDetails(Orders order){

        VBox ordersProductsVBox = new VBox(20);
        ordersProductsVBox.setPadding(new Insets(5,5,5,20));

        List<OrdersProducts> ordersProducts = dbManager.getOrderHistoryProducts(order);

        for(OrdersProducts orderProduct : ordersProducts){

            OrderProductsPanel orderProductsPanel = new OrderProductsPanel(orderProduct);

            ordersProductsVBox.getChildren().add(orderProductsPanel);
        }
        orderHistoryProductsPane.setContent(ordersProductsVBox);
    }

    public void openInventory(){

        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.FXML")));
            Stage stage = (Stage) inventoryButton.getScene().getWindow();
            stage.setTitle("Main Page");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openPendingOrders(){

        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Orders.FXML")));
            Stage stage = (Stage) pendingOrdersButton.getScene().getWindow();
            stage.setTitle("Pending Orders");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
