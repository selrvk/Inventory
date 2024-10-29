package com.selrvk.inventory;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.util.*;

public class Controller {

    @FXML
    private Button addNewBtn;
    @FXML
    private ScrollPane productsScrollPane;
    @FXML
    private TextField searchBar;

    private final DatabaseManager dbManager = new DatabaseManager();
    
    public void initialize(){

        printProducts();

    }

    public void addNewProduct(){

        dbManager.addNewProduct();

    }

    public void deleteProduct(){

        dbManager.removeProduct();
    }

    public void updateProduct(){

        dbManager.updateProduct();
    }

    public void printProducts(){

        VBox productsVBox = new VBox(15);

        List<Product> products = dbManager.getProducts();

        for(Product product : products){

            ProductsPanel panel = new ProductsPanel(product);
            productsVBox.getChildren().add(panel);

        }

        productsScrollPane.setContent(productsVBox);

    }
}
