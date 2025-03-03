package com.selrvk.inventory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

public class CreateOrderPanel extends HBox {

    private final Product product;
    private CheckBox productCheckBox;
    private Button updateButton;

    public CreateOrderPanel(Product product){
        this.getChildren().clear();
        this.product = product;
        printComponents();
    }

    private void printComponents(){
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(5,0,0,30));

    }
}
