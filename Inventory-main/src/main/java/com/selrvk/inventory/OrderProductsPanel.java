package com.selrvk.inventory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OrderProductsPanel extends VBox {

    private final OrdersProducts ordersProducts;

    public OrderProductsPanel(OrdersProducts ordersProducts){

        this.ordersProducts = ordersProducts;
        this.setPrefWidth(300);
        this.setSpacing(0);
        printComponents();
    }

    public void printComponents(){

        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(0,0,0,0));

        Label getProductName = new Label(ordersProducts.getProduct_name());
        getProductName.setFont(new Font("Montserrat", 20));
        getProductName.setMaxWidth(300);
        getProductName.setPadding(new Insets(0, 5, 5, 5));
        getProductName.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , new BorderWidths(1,1,0,1))));
        getProductName.setWrapText(true);

        Label productName = new Label("Product Name");
        productName.setFont(new Font("Montserrat", 13));
        productName.setMaxWidth(300);
        productName.setPadding(new Insets(0, 5, 5, 5));
        productName.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , new BorderWidths(0,1,1,1))));

        Label getProductQuantity = new Label("" + ordersProducts.getOrder_quantity());
        getProductQuantity.setFont(new Font("Montserrat", 20));
        getProductQuantity.setMaxWidth(300);
        getProductQuantity.setPadding(new Insets(0, 5, 5, 5));
        getProductQuantity.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , new BorderWidths(1,1,0,1))));


        Label orderQuantity = new Label("Order Quantity");
        orderQuantity.setFont(new Font("Montserrat", 13));
        orderQuantity.setMaxWidth(300);
        orderQuantity.setPadding(new Insets(0, 5, 5, 5));
        orderQuantity.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , new BorderWidths(0,1,1,1))));

        Label getProductPrice = new Label("" + ordersProducts.getProduct_price());
        getProductPrice.setFont(new Font("Montserrat", 20));
        getProductPrice.setMaxWidth(300);
        getProductPrice.setPadding(new Insets(0, 5, 5, 5));
        getProductPrice.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , new BorderWidths(1,1,0,1))));

        Label productPrice = new Label("Product Price");
        productPrice.setFont(new Font("Montserrat", 13));
        productPrice.setMaxWidth(300);
        productPrice.setPadding(new Insets(0, 5, 5, 5));
        productPrice.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , new BorderWidths(0,1,1,1))));

        //this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , new BorderWidths(2))));
        this.getChildren().addAll(getProductName, productName, getProductQuantity ,orderQuantity, getProductPrice,productPrice);
    }
}
