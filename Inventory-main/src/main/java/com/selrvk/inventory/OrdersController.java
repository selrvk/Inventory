package com.selrvk.inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class OrdersController {

    @FXML
    private Button inventoryButton;
    @FXML
    private Button orderHistoryButton;
    @FXML
    private ScrollPane pendingOrdersPane;

    private final DatabaseManager dbManager = new DatabaseManager();

    private ObservableList<Button> confirmButtons = FXCollections.observableArrayList();
    private ObservableList<Button> expandButtons = FXCollections.observableArrayList();

    public void initialize(){

        inventoryButton.setOnAction(e -> openInventory());
        orderHistoryButton.setOnAction(e -> openOrderHistory());

        printPendingOrders();

    }

    public void printPendingOrders(){

        VBox pendingOrdersVBox = new VBox(20);

        List<Orders> pendingOrders = dbManager.getPendingOrders();

        for(Orders orders : pendingOrders){

            PendingOrdersPanel panel = new PendingOrdersPanel(orders);
            this.confirmButtons.add(panel.getConfirmOrderButton());
            this.expandButtons.add(panel.getExpandOrderDetailsButton());
            panel.getExpandOrderDetailsButton().setOnAction(e -> expandDetails(orders));
            pendingOrdersVBox.getChildren().add(panel);
        }
        pendingOrdersPane.setContent(pendingOrdersVBox);
    }

    public void expandDetails(Orders order){

        System.out.println("Viewing product with order id: " + order.getOrder_id());
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

    public void openOrderHistory(){

        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OrderHistory.FXML")));
            Stage stage = (Stage) orderHistoryButton.getScene().getWindow();
            stage.setTitle("Order History");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
