package com.selrvk.inventory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PendingOrdersPanel extends HBox {

    private final Orders order;
    private Button confirmOrderButton, expandOrderDetailsButton, cancelOrderButton;

    PendingOrdersPanel(Orders order){

        this.getStyleClass().add("not-main-panels-ig");
        this.getChildren().clear();
        this.order = order;
        this.setPrefWidth(830);
        this.setPrefHeight(100);
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , new BorderWidths(1))));
        printComponents();

    }

    public void printComponents(){

        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(0,20,0,30));

        this.confirmOrderButton = new Button("Confirm");
        this.confirmOrderButton.setUserData((order.getOrder_id()));
        this.confirmOrderButton.setPrefWidth(100);

        this.expandOrderDetailsButton = new Button("Expand");
        this.expandOrderDetailsButton.getStyleClass().add("expand-order-button");
        this.expandOrderDetailsButton.setUserData((order.getOrder_id()));
        this.expandOrderDetailsButton.setPrefWidth(100);

        this.cancelOrderButton = new Button("Cancel");
        this.cancelOrderButton.getStyleClass().add("cancel-order-button");
        this.cancelOrderButton.setUserData((order.getOrder_id()));
        this.cancelOrderButton.setPrefWidth(100);

        Label idLabel = new Label("" + order.getOrder_id());
        idLabel.setFont(new Font("Montserrat", 20));
        idLabel.setPrefWidth(100);

        Label dateLabel = new Label("" + order.getDate());
        dateLabel.setFont(new Font("Montserrat", 20));
        dateLabel.setPrefWidth(170);

        Label customerLabel = new Label(order.getCustomer_name());
        customerLabel.setFont(new Font("Montserrat", 20));
        customerLabel.setWrapText(true);
        customerLabel.setPrefWidth(170);

        HBox.setHgrow(idLabel, Priority.ALWAYS);
        HBox.setHgrow(dateLabel, Priority.ALWAYS);
        HBox.setHgrow(customerLabel, Priority.ALWAYS);

        this.getChildren().addAll(dateLabel, idLabel, customerLabel, cancelOrderButton, expandOrderDetailsButton, confirmOrderButton);
    }

    public Button getConfirmOrderButton(){return this.confirmOrderButton;}
    public Button getExpandOrderDetailsButton(){return this.expandOrderDetailsButton;}
    public Button getCancelOrderButton(){return this.cancelOrderButton;}
}
