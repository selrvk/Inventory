package com.selrvk.inventory;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.ByteArrayInputStream;

public class ProductsPanel extends HBox {

    private final Product product;

    public ProductsPanel(Product products){

        this.product = products;
        printComponents();

    }

    public void printComponents(){

        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(product.getImg())));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        Label idLabel = new Label("ID: " + product.getId());
        Label nameLabel = new Label(product.getName());
        Label stockLabel = new Label("Stock: " + product.getStock());

        this.getChildren().addAll(imageView, idLabel, nameLabel, stockLabel);

        this.setSpacing(10);
    }
}
