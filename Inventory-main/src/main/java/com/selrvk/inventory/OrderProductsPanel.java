package com.selrvk.inventory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OrderProductsPanel extends VBox {

    private final OrdersProducts ordersProducts;

    public OrderProductsPanel(OrdersProducts ordersProducts){

        this.ordersProducts = ordersProducts;
        printComponents();
    }

    public void printComponents(){

        this.setAlignment(Pos.CENTER_LEFT);
        //this.setPadding(new Insets(5,0,0,30));

        Label productName = new Label("Product Name: " + ordersProducts.getProduct_name());
        productName.setFont(new Font("Montserrat", 20));
        Label orderQuantity = new Label("Order Quantity: " + ordersProducts.getOrder_quantity());
        orderQuantity.setFont(new Font("Montserrat", 20));
        Label productPrice = new Label("Product Price: " + ordersProducts.getProduct_price());
        productPrice.setFont(new Font("Montserrat", 20));

        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        this.getChildren().addAll(productName, orderQuantity, productPrice);
        this.setSpacing(20);
    }
}
