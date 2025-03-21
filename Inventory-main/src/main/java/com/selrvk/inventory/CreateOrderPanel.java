package com.selrvk.inventory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CreateOrderPanel extends HBox {

    private final Product product;
    private TextField productTextBox;

    public CreateOrderPanel(Product product){
        this.getChildren().clear();
        this.product = product;
        this.setPrefWidth(850);
        this.setPrefHeight(70);
        this.setSpacing(20);
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , new BorderWidths(1))));
        printComponents();
    }

    private void printComponents(){
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(0,0,0,16));

        this.productTextBox = new TextField();
        this.productTextBox.setPrefSize(45,20);
        this.productTextBox.setPromptText("" + product.getStock());
        this.productTextBox.setUserData(product);

        Label idLabel = new Label("" + product.getId());
        idLabel.setFont(new Font("Montserrat", 20));
        idLabel.setMaxWidth(50);

        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(new Font("Montserrat", 18));
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(230);

        Label stockLabel = new Label("" + product.getStock());
        stockLabel.setFont(new Font("Montserrat", 20));
        stockLabel.setMaxWidth(80);

        Label srpLabel = new Label("" + product.getSrp());
        srpLabel.setFont(new Font("Montserrat", 20));
        srpLabel.setMaxWidth(80);

        Label buyingPriceLabel = new Label("" + product.getBuyingPrice());
        buyingPriceLabel.setFont(new Font("Montserrat", 20));
        buyingPriceLabel.setMaxWidth(120);

        HBox.setHgrow(idLabel, Priority.ALWAYS);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        HBox.setHgrow(stockLabel, Priority.ALWAYS);
        HBox.setHgrow(srpLabel, Priority.ALWAYS);
        HBox.setHgrow(buyingPriceLabel, Priority.ALWAYS);

        this.getChildren().addAll(productTextBox, idLabel, nameLabel, stockLabel, srpLabel, buyingPriceLabel);
    }

    public TextField getProductTextBox(){return this.productTextBox;}
}
