package com.selrvk.inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class OrdersController {

    @FXML
    private Button inventoryButton;
    @FXML
    private Button orderHistoryButton;
    @FXML
    private ScrollPane ordersScrollPane;

    public void initialize(){

        inventoryButton.setOnAction(e -> openInventory());
        orderHistoryButton.setOnAction(e -> openOrderHistory());
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
