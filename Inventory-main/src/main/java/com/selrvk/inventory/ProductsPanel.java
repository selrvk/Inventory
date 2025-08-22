package com.selrvk.inventory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ProductsPanel extends HBox {

    private final Product product;
    private CheckBox productCheckBox;
    private Button updateButton;

    public ProductsPanel(Product product){

        this.getStyleClass().add("products-panel");
        this.getChildren().clear();
        this.product = product;
        this.setPrefWidth(850);
        this.setPrefHeight(70);
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , new BorderWidths(1))));
        printComponents();
    }

    public void printComponents(){

        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(0,0,0,45));

        this.productCheckBox = new CheckBox();
        this.productCheckBox.setUserData(product.getId());

        this.updateButton = new Button("Update");
        this.updateButton.setUserData((product.getId()));

        // ID label
        Label idLabel = new Label("" + product.getId());
        idLabel.setFont(new Font("Montserrat", 20));
        idLabel.setMaxWidth(50);
        idLabel.setMaxHeight(70);

        // Name label
        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(new Font("Montserrat", 18));
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(230);
        nameLabel.setMaxHeight(70);

        // Stock label
        Label stockLabel = new Label("" + product.getStock());
        stockLabel.setFont(new Font("Montserrat", 20));
        stockLabel.setMaxWidth(80);
        stockLabel.setMaxHeight(70);

        // Manufacturer label
        Label manufacturerLabel = new Label(product.getManufacturer());
        manufacturerLabel.setFont(new Font("Montserrat", 20));
        manufacturerLabel.setWrapText(true);
        manufacturerLabel.setMaxWidth(230);
        manufacturerLabel.setMaxHeight(70);

        HBox.setHgrow(idLabel, Priority.ALWAYS);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        HBox.setHgrow(stockLabel, Priority.ALWAYS);
        HBox.setHgrow(manufacturerLabel, Priority.ALWAYS);

        idLabel.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(0, 1, 0, 0))));
        nameLabel.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(0, 1, 0, 0))));
        stockLabel.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(0, 1, 0, 0))));
        manufacturerLabel.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(0, 1, 0, 0))));

        this.getChildren().addAll(productCheckBox, idLabel, nameLabel, stockLabel, manufacturerLabel, updateButton);
    }

    public CheckBox getCheckBox(){ return this.productCheckBox; }
    public Button getUpdateButton(){ return this.updateButton; }
}
