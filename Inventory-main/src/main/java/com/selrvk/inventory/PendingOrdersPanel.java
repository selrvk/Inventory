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
    private Button confirmOrderButton, expandOrderDetailsButton;

    PendingOrdersPanel(Orders order){

        this.getChildren().clear();
        this.order = order;
        this.setPrefWidth(850);
        this.setPrefHeight(70);
        this.setSpacing(20);
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , new BorderWidths(1))));
        printComponents();

    }

    public void printComponents(){

        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(5,0,0,30));

        this.confirmOrderButton = new Button("Confirm");
        this.confirmOrderButton.setUserData((order.getOrder_id()));

        this.expandOrderDetailsButton = new Button("Expand Details");
        this.expandOrderDetailsButton.setUserData((order.getOrder_id()));

        Label idLabel = new Label("" + order.getOrder_id());
        idLabel.setFont(new Font("Montserrat", 20));
        idLabel.setMaxWidth(100);

        Label dateLabel = new Label("" + order.getDate());
        dateLabel.setFont(new Font("Montserrat", 20));
        dateLabel.setMaxWidth(150);

        Label customerLabel = new Label(order.getCustomer_name());
        customerLabel.setFont(new Font("Montserrat", 20));
        customerLabel.setMaxWidth(300);

        HBox.setHgrow(idLabel, Priority.ALWAYS);
        HBox.setHgrow(dateLabel, Priority.ALWAYS);
        HBox.setHgrow(customerLabel, Priority.ALWAYS);

        this.getChildren().addAll(confirmOrderButton, expandOrderDetailsButton,dateLabel, idLabel, customerLabel);
    }

    public Button getConfirmOrderButton(){return this.confirmOrderButton;}
    public Button getExpandOrderDetailsButton(){return this.expandOrderDetailsButton;}
}
