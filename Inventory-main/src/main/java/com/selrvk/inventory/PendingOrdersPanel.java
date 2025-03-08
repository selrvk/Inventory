package com.selrvk.inventory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class PendingOrdersPanel extends HBox {

    private final Orders order;
    private Button confirmOrderButton, expandOrderDetailsButton;

    PendingOrdersPanel(Orders order){

        this.getChildren().clear();
        this.order = order;
        printComponents();

    }

    public void printComponents(){

        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(5,0,0,30));

        this.confirmOrderButton = new Button("Confirm");
        this.confirmOrderButton.setUserData((order.getOrder_id()));
        this.expandOrderDetailsButton = new Button("Expand Details");
        this.expandOrderDetailsButton.setUserData((order.getOrder_id()));

        Label idLabel = new Label("ID: " + order.getOrder_id());
        idLabel.setFont(new Font("Montserrat", 20));
        Label dateLabel = new Label("Date: " + order.getDate());
        dateLabel.setFont(new Font("Montserrat", 20));
        Label customerLabel = new Label("Customer: " + order.getCustomer_name());
        customerLabel.setFont(new Font("Montserrat", 20));

        this.getChildren().addAll(confirmOrderButton, expandOrderDetailsButton,dateLabel, idLabel, customerLabel);
        this.setSpacing(20);
    }

    public Button getConfirmOrderButton(){return this.confirmOrderButton;}
    public Button getExpandOrderDetailsButton(){return this.expandOrderDetailsButton;}
}
