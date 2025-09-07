package com.selrvk.inventory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OrderProductsPanel extends HBox {

    private final OrdersProducts ordersProducts;

    public OrderProductsPanel(OrdersProducts ordersProducts){

        this.getStyleClass().add("order-products-panel");
        this.ordersProducts = ordersProducts;
        this.setPrefWidth(995);
        this.setPrefHeight(70);
        printComponents();
    }

    public void printComponents(){

        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(0,0,0,30));

        Label getProductName = new Label(ordersProducts.getProduct_name());
        getProductName.setFont(new Font("Montserrat", 20));
        getProductName.setPrefWidth(500);
        getProductName.setAlignment(Pos.CENTER);
        getProductName.setWrapText(true);

        Label getProductQuantity = new Label("" + ordersProducts.getOrder_quantity());
        getProductQuantity.setFont(new Font("Montserrat", 20));
        getProductQuantity.setPrefWidth(244);
        getProductQuantity.setAlignment(Pos.CENTER);

        Label getProductPrice = new Label("P " + ordersProducts.getProduct_price());
        getProductPrice.setFont(new Font("Montserrat", 20));
        getProductPrice.setPrefWidth(244);
        getProductPrice.setAlignment(Pos.CENTER);

        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , new BorderWidths(2))));
        this.getChildren().addAll(getProductName, getProductQuantity, getProductPrice);
    }
}
