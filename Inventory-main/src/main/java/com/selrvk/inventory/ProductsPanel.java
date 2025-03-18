package com.selrvk.inventory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

public class ProductsPanel extends HBox {

    private final Product product;
    private CheckBox productCheckBox;
    private Button updateButton;

    public ProductsPanel(Product product){

        this.getChildren().clear();
        this.product = product;
        this.setPrefWidth(850);
        this.setPrefHeight(70);
        printComponents();
    }

    public void printComponents(){

        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(5,0,0,30));

        this.productCheckBox = new CheckBox();
        this.productCheckBox.setUserData(product.getId());

        this.updateButton = new Button("Update");
        this.updateButton.setUserData((product.getId()));

        // ID label
        Label idLabel = new Label("" + product.getId());
        idLabel.setFont(new Font("Montserrat", 20));
        idLabel.setMaxWidth(50);

        // Name label
        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(new Font("Montserrat", 18));
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(250);

        // Stock label
        Label stockLabel = new Label("" + product.getStock());
        stockLabel.setFont(new Font("Montserrat", 20));
        stockLabel.setMaxWidth(80);

        // SRP label
        Label srpLabel = new Label("" + product.getSrp());
        srpLabel.setFont(new Font("Montserrat", 20));
        srpLabel.setMaxWidth(80);

        // Buying price label
        Label buyingPriceLabel = new Label("" + product.getBuyingPrice());
        buyingPriceLabel.setFont(new Font("Montserrat", 20));
        buyingPriceLabel.setMaxWidth(120);

        HBox.setHgrow(idLabel, Priority.ALWAYS);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        HBox.setHgrow(stockLabel, Priority.ALWAYS);
        HBox.setHgrow(srpLabel, Priority.ALWAYS);
        HBox.setHgrow(buyingPriceLabel, Priority.ALWAYS);


        this.getChildren().addAll(productCheckBox, idLabel, nameLabel, stockLabel, srpLabel, buyingPriceLabel, updateButton);
        this.setSpacing(20);
    }

    public CheckBox getCheckBox(){ return this.productCheckBox; }
    public Button getUpdateButton(){ return this.updateButton; }
}
