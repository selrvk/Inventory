package com.selrvk.inventory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class CreateOrderPanel extends HBox {

    private final Product product;
    private TextField productTextBox;

    public CreateOrderPanel(Product product){
        this.getChildren().clear();
        this.product = product;
        printComponents();
    }

    private void printComponents(){
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(5,0,0,30));

        this.productTextBox = new TextField();
        this.productTextBox.setPrefSize(50,20);
        this.productTextBox.setPromptText("" + product.getStock());
        this.productTextBox.setUserData(product);
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

        this.getChildren().addAll(productTextBox, idLabel, nameLabel, stockLabel, srpLabel, buyingPriceLabel);
        this.setSpacing(20);

    }

    public TextField getProductTextBox(){return this.productTextBox;}
}
