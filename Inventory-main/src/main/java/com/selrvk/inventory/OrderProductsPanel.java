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
        this.setSpacing(5);
        printComponents();
    }

    public void printComponents(){

        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(5,0,0,30));

        Label getProductName = new Label(ordersProducts.getProduct_name());
        getProductName.setFont(new Font("Montserrat", 20));
        Label productName = new Label("Product Name");
        productName.setFont(new Font("Montserrat", 13));

        Label getProductQuantity = new Label("" + ordersProducts.getOrder_quantity());
        getProductQuantity.setFont(new Font("Montserrat", 20));
        Label orderQuantity = new Label("Order Quantity");
        orderQuantity.setFont(new Font("Montserrat", 13));

        Label getProductPrice = new Label("" + ordersProducts.getProduct_price());
        getProductPrice.setFont(new Font("Montserrat", 20));
        Label productPrice = new Label("Product Price");
        productPrice.setFont(new Font("Montserrat", 13));

        //this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , new BorderWidths(1))));
        this.getChildren().addAll(getProductName, productName, getProductQuantity ,orderQuantity, getProductPrice,productPrice);
    }
}
