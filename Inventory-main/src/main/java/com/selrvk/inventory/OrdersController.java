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

public class OrdersController {

    @FXML
    private Button inventoryButton;
    @FXML
    private Button orderHistoryButton;

    /* The right scrollPane */
    @FXML
    private ScrollPane pendingOrdersPane;

    /* The left scrollPane */
    @FXML
    private ScrollPane ordersProductsPane;

    private final DatabaseManager dbManager = new DatabaseManager();

    private final ObservableList<Button> confirmButtons = FXCollections.observableArrayList();
    private final ObservableList<Button> expandButtons = FXCollections.observableArrayList();
    private final ObservableList<Button> cancelButtons = FXCollections.observableArrayList();

    public void initialize(){

        pendingOrdersPane.getStyleClass().add("main-scroll-pane");
        ordersProductsPane.getStyleClass().add("order-products-panel");
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
            this.cancelButtons.add(panel.getCancelOrderButton());

            panel.getConfirmOrderButton().setOnAction(e -> confirmOrder(orders));
            panel.getExpandOrderDetailsButton().setOnAction(e -> expandDetails(orders));
            panel.getCancelOrderButton().setOnAction(e -> cancelOrder(orders));
            pendingOrdersVBox.getChildren().add(panel);
        }
        pendingOrdersPane.setContent(pendingOrdersVBox);
    }

    public void expandDetails(Orders order){

        VBox ordersProductsVBox = new VBox(20);
        ordersProductsVBox.setPadding(new Insets(5,5,5,20));

        List<OrdersProducts> ordersProducts = dbManager.getOrderProducts(order);

        for(OrdersProducts orderProduct : ordersProducts){

            OrderProductsPanel orderProductsPanel = new OrderProductsPanel(orderProduct);
            ordersProductsVBox.getChildren().add(orderProductsPanel);
        }

        ordersProductsPane.setContent(ordersProductsVBox);
    }

    public void confirmOrder(Orders order){

        dbManager.confirmOrder(order.getOrder_id());
        initialize();
    }

    public void cancelOrder(Orders order){
        dbManager.cancelOrder(order.getOrder_id());
        initialize();
    }

    public void openInventory(){

        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
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

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OrderHistory.fxml")));
            Stage stage = (Stage) orderHistoryButton.getScene().getWindow();
            stage.setTitle("Order History");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
