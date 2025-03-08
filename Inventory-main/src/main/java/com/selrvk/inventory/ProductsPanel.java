package com.selrvk.inventory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.ByteArrayInputStream;

public class ProductsPanel extends HBox {

    private final Product product;
    private CheckBox productCheckBox;
    private Button updateButton;

    public ProductsPanel(Product product){

        this.getChildren().clear();
        this.product = product;
        printComponents();
    }

    public void printComponents(){

        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(5,0,0,30));

        this.productCheckBox = new CheckBox();
        this.productCheckBox.setUserData(product.getId());

        this.updateButton = new Button("Update");
        this.updateButton.setUserData((product.getId()));

        Label idLabel = new Label("ID: " + product.getId());
        idLabel.setFont(new Font("Montserrat", 20));
        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(new Font("Montserrat", 20));
        Label stockLabel = new Label("Stock: " + product.getStock());
        stockLabel.setFont(new Font("Montserrat", 20));
        Label srpLabel = new Label("SRP: " + product.getSrp());
        srpLabel.setFont(new Font("Montserrat", 20));
        Label buyingPriceLabel = new Label("Buying Price: " + product.getBuyingPrice());
        buyingPriceLabel.setFont(new Font("Montserrat", 20));

        this.getChildren().addAll(productCheckBox, idLabel, nameLabel, stockLabel, srpLabel, buyingPriceLabel, updateButton);
        this.setSpacing(20);
    }

    public CheckBox getCheckBox(){ return this.productCheckBox; }
    public Button getUpdateButton(){ return this.updateButton; }
}
