package com.selrvk.inventory;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.util.*;

import java.io.ByteArrayInputStream;

public class ProductsPanel extends HBox {

    private final Product product;
    private CheckBox productCheckBox;

    public ProductsPanel(Product product){

        this.product = product;
        printComponents();
    }

    public void printComponents(){

        this.productCheckBox = new CheckBox();
        this.productCheckBox.setUserData(product.getId());

        this.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(product.getImg())));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        Label idLabel = new Label("ID: " + product.getId());
        idLabel.setFont(new Font("Montserrat", 20));
        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(new Font("Montserrat", 20));
        Label stockLabel = new Label("Stock: " + product.getStock());
        stockLabel.setFont(new Font("Montserrat", 20));

        this.getChildren().addAll(productCheckBox, imageView, idLabel, nameLabel, stockLabel);
        this.setSpacing(20);
    }

    public CheckBox getCheckBox(){

        return this.productCheckBox;
    }
}
