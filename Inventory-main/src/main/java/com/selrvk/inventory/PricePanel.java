package com.selrvk.inventory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PricePanel extends HBox {

    private final Product product;

    public PricePanel(Product product){
        this.getStyleClass().add("price-panel");
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

        // SRP label

        Label sellingPriceInfo = new Label("Selling Price: ");
        sellingPriceInfo.getStyleClass().add("product-info");
        sellingPriceInfo.setFont(new Font("Montserrat", 20));

        Label srpLabel = new Label("PHP " + product.getSrp());
        srpLabel.setFont(new Font("Montserrat", 20));
        srpLabel.setMaxWidth(300);
        srpLabel.setMaxHeight(20);

        Region spacer = new Region();
        spacer.setMinWidth(120);

        // Buying price label
        Label buyingPriceInfo = new Label("Buying Price: ");
        buyingPriceInfo.getStyleClass().add("product-info");
        buyingPriceInfo.setFont(new Font("Montserrat", 20));

        Label buyingPriceLabel = new Label("PHP " + product.getBuyingPrice());
        buyingPriceLabel.setFont(new Font("Montserrat", 20));
        buyingPriceLabel.setMaxWidth(300);
        buyingPriceLabel.setMaxHeight(20);

        //srpLabel.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(0, 1, 0, 0))));
        //buyingPriceLabel.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(0, 1, 0, 0))));

        this.getChildren().addAll(sellingPriceInfo, srpLabel, spacer, buyingPriceInfo, buyingPriceLabel);

    }

}
