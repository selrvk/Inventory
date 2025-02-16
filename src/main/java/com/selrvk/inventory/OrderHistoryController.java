package com.selrvk.inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class OrderHistoryController {

    @FXML
    private Button pendingOrdersButton;
    @FXML
    private Button inventoryButton;
    @FXML
    private ScrollPane orderHistoryScrollPane;

    public void initialize(){

        inventoryButton.setOnAction(e -> openInventory());
        pendingOrdersButton.setOnAction(e -> openPendingOrders());
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
